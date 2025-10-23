package serviseDoska.steps;

import serviseDoska.pages.RegistrationPage;
import serviseDoska.api.ApiClient;
import serviseDoska.data.User;
import serviseDoska.data.UserGenerator;
import serviseDoska.pages.LoginPage;
import serviseDoska.pages.MainPage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;

import io.restassured.response.ValidatableResponse;

// Класс с шагами для тестирования авторизации
public class LoginSteps {

    private final LoginPage loginPage = new LoginPage();
    private final MainPage mainPage = new MainPage();
    private final RegistrationPage registrationPage = new RegistrationPage();
    private final ApiClient apiClient = new ApiClient();
    private User user;
    private String savedEmail;
    private String savedPassword;
    private String accessToken;
    private Integer userId;


    @Given("Пользователь зарегистрирован")
    public void userIsRegistered() {
        user = UserGenerator.generateUser();
        // Регистрируем через API
        ValidatableResponse registerResponse = apiClient.registerUser(user);

        // Сохраняем учетные данные
        savedEmail = user.getEmail();
        savedPassword = user.getPassword();

        // Добавляем логирование
        System.out.println("Сохраненный email при регистрации: " + user.getEmail());
        System.out.println("Сохраненный пароль при регистрации: " + user.getPassword());
    }

    @When("Пользователь открывает страницу входа")
    public void openLoginPage() {
        mainPage.openMainPage();
        mainPage.clickLoginAndRegisterButton();
    }

    @And("Он вводит свои учетные данные")
    public void enterCorrectCredentials() {
        User registerUser = new User();
        registerUser.setEmail(savedEmail);
        registerUser.setPassword(savedPassword);

        // Добавляем логирование
        System.out.println("Введенный email при авторизации: " + registerUser.getEmail());
        System.out.println("Введенный пароль при авторизации: " + registerUser.getPassword());

        loginPage.loginUser(registerUser);

        // Получаем токен и ID через API после успешной авторизации
        ValidatableResponse loginResponse = apiClient.loginUser(registerUser);
        accessToken = apiClient.getAccessToken(loginResponse);
        userId = apiClient.getUserId(loginResponse);

        // Добавляем детальное логирование accessToken и userId
        System.out.println("Полученный accessToken: " + accessToken);
        System.out.println("Полученный userId: " + userId);

        Hooks.setUserCredentials(accessToken, userId, apiClient);

    }

    @Then("Пользователь успешно авторизуется")
    public void userSuccessfullyLoggedIn() {
        mainPage.succsessAuthorized();
    }

}

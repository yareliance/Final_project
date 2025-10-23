package serviseDoska.steps;

import serviseDoska.data.*;
import serviseDoska.pages.CreateAdvertismentPage;
import serviseDoska.pages.PersonalPage;
import serviseDoska.api.ApiClient;
import serviseDoska.pages.LoginPage;
import serviseDoska.pages.MainPage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.restassured.response.ValidatableResponse;

public class CreateAdvertismentSteps {

    private final MainPage mainPage = new MainPage();
    private final LoginPage loginPage = new LoginPage();
    private final PersonalPage personalPage = new PersonalPage();
    private final CreateAdvertismentPage createAdPage = new CreateAdvertismentPage();
    private final ApiClient apiClient = new ApiClient();
    private User user;
    private Advertisment ad;
    private String savedEmail;
    private String savedPassword;
    private String accessToken;
    private Integer userId;

    @Given("Зарегистрированный")
    public void userIsRegistered() {
        user = UserGenerator.generateUser();
        ValidatableResponse registerResponse = apiClient.registerUser(user);

        // Сохраняем учетные данные
        savedEmail = user.getEmail();
        savedPassword = user.getPassword();

    }

    @And("Авторизованный пользователь")
    public void userIsLoggedIn() {
        mainPage.openMainPage();
        mainPage.clickLoginAndRegisterButton();
        User registerUser = new User();
        registerUser.setEmail(savedEmail);
        registerUser.setPassword(savedPassword);
        loginPage.loginUser(registerUser);

        ValidatableResponse loginResponse = apiClient.loginUser(registerUser);
        accessToken = apiClient.getAccessToken(loginResponse);
        userId = apiClient.getUserId(loginResponse);

        Hooks.setUserCredentials(accessToken, userId, apiClient);
    }

    @And("Открывает главную страницу")
    public void openStartPage() {
        mainPage.openMainPage();
        mainPage.clickCreateAdvertismentButton();
    }

    @When("Создает новое объявление")
    public void createNewAdvertisment() {
        ad = AdvertismentGenerator.generateAd();
        createAdPage.createAdvertisment(ad);
    }

    @Then("Объявление отображается в профиле пользователя")
    public void checkAdvertismentInProfile() {
        // Проверка наличия объявления в профиле
        personalPage.openPersonalPage();
        personalPage.checkAdvertismentInProfile(ad.getName());
    }

}

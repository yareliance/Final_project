package serviseDoska.steps;

import serviseDoska.api.*;
import serviseDoska.data.*;
import serviseDoska.pages.*;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import io.restassured.response.ValidatableResponse;

// Класс с шагами для тестирования регистрации
public class RegistrationSteps {
    private final MainPage mainPage = new MainPage();
    private final RegistrationPage registrationPage = new RegistrationPage();;
    private final ApiClient apiClient = new ApiClient();
    private User user;
    private String savedEmail;
    private String accessToken;
    private Integer userId;

    @Given("Создание тестовых данных пользователя")
    public void createTestUserData() {
        user = UserGenerator.generateUser();
    }

    @Given("Пользователь уже зарегистрирован")
    public void getExistingTestUserData() {
        user = UserGenerator.generateUser();

        ValidatableResponse registerResponse = apiClient.registerUser(user);

        accessToken = apiClient.getAccessToken(registerResponse);
        userId = apiClient.getUserId(registerResponse);
        savedEmail = user.getEmail();

        Hooks.setUserCredentials(accessToken, userId, apiClient);
    }

    @When("Пользователь открывает главную страницу")
    public void openMainPage() {
        mainPage.openMainPage();
    }

    @When("Переходит на форму регистрации")
    public void openRegistrationPage() {
        registrationPage.openRegisterForm();
    }

    @When("Заполняет форму уникальным Email")
    public void fillRegistrationForm() {
        registrationPage.fillRegisterForm(user);
    }

    @When("Создает аккаунт")
    public void submitRegistration() {
        registrationPage.submitRegistration(user);

        ValidatableResponse response = apiClient.loginUser(user);
        accessToken = apiClient.getAccessToken(response);
        userId = apiClient.getUserId(response);
    }

    @When("Заполняет форму с ранее использованным Email")
    public void tryRegisterAgain() {
        User newUser = new User();
        newUser.setEmail(savedEmail);
        String newPassword = RandomUtils.randomPassword();
        newUser.setPassword(newPassword);
        newUser.setSubmitPassword(newPassword);

        registrationPage.fillRegisterForm(newUser);
    }

    @Then("Пользователь успешно регистрируется с дефолтным именем")
    public void checkSuccessfulRegistration() {
        registrationPage.isDefaultNameUserVisible();
    }

    @Then("Появляется сообщение об ошибке")
    public void checkErrorRegistration() {
        registrationPage.isErrorMessageVisible();
    }

}
package serviseDoska.steps;

import serviseDoska.data.*;
import serviseDoska.pages.*;
import serviseDoska.api.ApiClient;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.restassured.response.ValidatableResponse;

import java.io.IOException;

public class EditAdvertismentSteps {

    private final MainPage mainPage = new MainPage();
    private final LoginPage loginPage = new LoginPage();
    private final PersonalPage personalPage = new PersonalPage();
    private final CreateAdvertismentPage createAdPage = new CreateAdvertismentPage();
    private final EditAdvertismentPage editAdPage = new EditAdvertismentPage();
    private final ApiClient apiClient = new ApiClient();
    private User user;
    private Advertisment originalAd;
    private Advertisment editedAd;
    private String savedEmail;
    private String savedPassword;
    private String accessToken;
    private Integer userId;
    private String createdAdTitle;

    @Given("Авторизованный пользователь заходит на портал")
    public void userRegisterAndAuthEnterProfile() {

        user = UserGenerator.generateUser();
        ValidatableResponse registerResponse = apiClient.registerUser(user);

        savedEmail = user.getEmail();
        savedPassword = user.getPassword();

        mainPage.openMainPage();
        mainPage.clickLoginAndRegisterButton();
        User registerUser = new User();
        registerUser.setEmail(savedEmail);
        registerUser.setPassword(savedPassword);
        loginPage.loginUser(registerUser);

        ValidatableResponse loginResponse = apiClient.loginUser(registerUser);
        accessToken = apiClient.getAccessToken(loginResponse);
        userId = apiClient.getUserId(loginResponse);

        mainPage.openMainPage();
        mainPage.clickCreateAdvertismentButton();

    }

    @And("У пользователя есть ранее созданное объявление")
    public void userHaveAd() throws IOException {

        originalAd = AdvertismentGenerator.generateAd();
        createAdPage.createAdvertisment(originalAd);

        createdAdTitle = originalAd.getName();

        personalPage.openPersonalPage();
    }

    @When("Он открывает объявление для редактирования")
    public void openAdForEdit() {

        personalPage.clickEditFirstAdvertisment();
        editAdPage.shouldHaveTitle(createdAdTitle); //тут проверяем, что открылось редактирование объявление, но тест падает из-за бага
    }

    @And("Вносит изменения в объявление")
    public void userEditAd() {

        editedAd = AdvertismentGenerator.generateAd();
        editAdPage.editAdvertisment(editedAd);
    }

    @Then("Внесенные изменения сохраняются")
    public void userEditsSaved() {

        personalPage.openPersonalPage();
        personalPage.checkAdvertismentInProfile(editedAd.getName()); //можно добавить проверки других полей, но редактирование объявления не работает

    }

}

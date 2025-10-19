package serviseDoska.steps;

import serviseDoska.data.*;
import serviseDoska.pages.*;
import serviseDoska.api.ApiClient;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.restassured.response.ValidatableResponse;

public class DeleteAdvertismentSteps {

    private final MainPage mainPage = new MainPage();
    private final LoginPage loginPage = new LoginPage();
    private final PersonalPage personalPage = new PersonalPage();
    private final CreateAdvertismentPage createAdPage = new CreateAdvertismentPage();
    private final EditAdvertismentPage editAdPage = new EditAdvertismentPage();
    private final ApiClient apiClient = new ApiClient();
    private User user;
    private Advertisment ad;
    private String savedEmail;
    private String savedPassword;
    private String accessToken;
    private Integer userId;
    private String createdAdTitle;

    @Given("Пользователь авторизован и находится в личном кабинете")
    public void userIsLoggedInAndInPersonalProfile() {
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

    }

    @And("У пользователя есть объявление для удаления")
    public void userHasAdvertismentToDelete() {
        mainPage.openMainPage();
        mainPage.clickCreateAdvertismentButton();

        ad = AdvertismentGenerator.generateAd();
        createAdPage.createAdvertisment(ad);

        personalPage.openPersonalPage();

    }

    @When("Пользователь пытается удалить объявление")
    public void userTriesToDeleteAdvertisment() {
        // Проверяем наличие кнопки удаления
        if (personalPage.deleteButton().isDisplayed()) {
            personalPage.deleteButton().click();
        } else {
            throw new AssertionError("Кнопка удаления объявления отсутствует на странице");
        }
    }

    @Then("Объявление должно быть удалено")
    public void advertismentShouldBeDeleted() {
        // Проверяем отсутствие объявления в списке
        personalPage.openPersonalPage();
        personalPage.checkAdvertismentInProfile(ad.getName());

    }


}

package serviseDoska.pages;

import io.qameta.allure.Step;
import serviseDoska.data.*;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.page;

public class LoginPage {
    public static final String LOGIN_PAGE_URL = "https://qa-desk.stand.praktikum-services.ru/login";



    private SelenideElement emailInput() { return $("input[name='email']"); }
    private SelenideElement passwordInput() { return $("input[name='password']"); }
    private SelenideElement loginButton() { return $x("//button[text()='Войти']"); }
    static SelenideElement registerButton() { return $x("//button[text()='Нет аккаунта']"); }

    // Открытие страницы авторизации
    public LoginPage openLoginPage() {
        open(LOGIN_PAGE_URL);
        return this;
    }

    // Авторизация пользователя: заполнение данных, нажатие на кнопку "Войти" и вернуться на главную
    @Step("Авторизация пользователя")
    public MainPage loginUser(User user) {

        // Добавляем логирование
        System.out.println("Вводим email: " + user.getEmail());
        System.out.println("Вводим password: " + user.getPassword());

        emailInput().setValue(user.getEmail());
        passwordInput().setValue(user.getPassword());
        loginButton().click();
        return page(MainPage.class);
    }

    @Step("Переход к форме регистрации")
    public RegistrationPage clickRegisterButton() {
        registerButton().click();
        return page(RegistrationPage.class);
    }

}

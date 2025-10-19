package serviseDoska.pages;

import io.qameta.allure.Step;
import serviseDoska.data.User;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import java.time.Duration;

public class RegistrationPage {

    private SelenideElement emailField() { return $x("//input[@name='email']"); }
    private SelenideElement passwordField() { return $x("//input[@name='password']"); }
    private SelenideElement submitPasswordField() { return $x("//input[@name='submitPassword']"); }
    private SelenideElement createAccountButton() { return $x("//button[text()='Создать аккаунт']"); }
    private SelenideElement registrationErrorMessage() { return $x("//span[text()='Ошибка']"); }
    private SelenideElement newUserName() {return $(".profileText.name");}

    @Step("Открытие формы регистрации")
    public void openRegisterForm() {
        MainPage.openMainPage();

        // Разделяем действия на отдельные строки
        SelenideElement loginButton = MainPage.loginAndRegisterButton();
        loginButton.shouldBe(visible, Duration.ofSeconds(2));
        loginButton.click();

        // Проверяем, что метод registerButton() находится в правильном классе
        SelenideElement registerButton = LoginPage.registerButton();
        registerButton.shouldBe(visible, Duration.ofSeconds(2));
        registerButton.click();
    }

    @Step("Заполнение формы регистрации")
    public void fillRegisterForm(User user) {
        emailField().shouldBe(visible).setValue(user.getEmail());
        passwordField().shouldBe(visible).setValue(user.getPassword());
        submitPasswordField().shouldBe(visible).setValue(user.getSubmitPassword());
    }

    @Step("Подтверждение регистрации после заполнения данных")
    public void submitRegistration(User user) {
        createAccountButton().shouldBe(visible).click();
    }

    @Step("Проверка дефолтного имени")
    public void isDefaultNameUserVisible() {
        newUserName().shouldBe(visible, Duration.ofSeconds(10)).shouldHave(text("User."));
    }

    @Step("Проверка сообщения об ошибке при повторной регистрации")
    public void isErrorMessageVisible() {
        registrationErrorMessage().shouldBe(visible);
    }
}

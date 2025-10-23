package serviseDoska.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class PersonalPage {

    public static final String PERSONAL_PAGE_URL = "https://qa-desk.stand.praktikum-services.ru/profile";
    public SelenideElement firstAdCard() { return $(".card"); }
    private SelenideElement editButton() { return firstAdCard().$(".editButton"); }
    public SelenideElement deleteButton() { return firstAdCard().$(".trashButton"); } // в элементах профиля есть .trashButton, но реально иконки нет

    @Step("Открытие персональной страницы пользователя")
    public static PersonalPage openPersonalPage() {
        open(PERSONAL_PAGE_URL);
        return new PersonalPage();
    }

    @Step("Проверка наличия созданного объявления в профиле пользователя")
    public PersonalPage checkAdvertismentInProfile(String adName) {
        firstAdCard().shouldBe(visible, Duration.ofSeconds(10)).shouldHave(text(adName));;
        return this;
    }

    @Step("Нажатие на кнопку редактирования объявления в профиле пользователя")
    public PersonalPage clickEditFirstAdvertisment() {
        editButton().shouldBe(exist).shouldBe(visible).click();
        return this;
    }


}

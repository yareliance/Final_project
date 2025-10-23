package serviseDoska.pages;

import io.qameta.allure.Step;
import com.codeborne.selenide.SelenideElement;
import serviseDoska.data.Advertisment;

import java.io.File;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class EditAdvertismentPage {

    private SelenideElement nameInput() {return $("input[name='name']");}
    private SelenideElement descriptionText() {return $("textarea[name='description']");}
    private SelenideElement priceInput() {return $("input[name='price']");}
    private SelenideElement publishButton() {return $x("//button[text()='Опубликовать']");}
    public SelenideElement uploadPhotoButton() { return $("input[type=file]");}
    private SelenideElement titleLabel() {return $(".createListing_title__IFtFs");}

    @Step("Редактирование объявления")
    public MainPage editAdvertisment(Advertisment generateAd) {
        uploadPhotoButton().uploadFile(new File(generateAd.getPhotoPath()));
        nameInput().setValue(generateAd.getName());
        descriptionText().setValue(generateAd.getDescription());
        priceInput().setValue(String.valueOf(generateAd.getPrice()));

        publishButton().click();
        return page(MainPage.class);
    }

    // На портале ошибка - вместо формы редактирования открывается форма создания нового объявления, поэтому проверяю заголовок
    @Step("Проверка заголовка редактируемого объявления")
    public EditAdvertismentPage shouldHaveTitle(String expectedTitle) {

        // Сначала проверяем видимость элемента
        titleLabel().shouldBe(visible);

        // Получаем актуальный текст
        String actualTitle = titleLabel().getText().trim();

        // Форматируем сообщение об ошибке с помощью String.format
        String errorMessage = String.format(
                "Неверное название объявления. Ожидалось: '%s', но получено: '%s'",
                expectedTitle,
                actualTitle
        );

        // Проверяем соответствие
        if (!actualTitle.equals(expectedTitle)) {
            throw new AssertionError(errorMessage);
        }

        return this;
    }

}

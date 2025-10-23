package serviseDoska.pages;

import io.qameta.allure.Step;
import com.codeborne.selenide.SelenideElement;
import serviseDoska.data.Advertisment;

import java.io.File;
import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CreateAdvertismentPage {

    private SelenideElement nameInput() {return $("input[name='name']");}
    private SelenideElement descriptionText() {return $("textarea[name='description']");}
    private SelenideElement priceInput() {return $("input[name='price']");}
    private SelenideElement publishButton() {return $x("//button[text()='Опубликовать']");}
    public SelenideElement uploadPhotoButton() { return $("input[type=file]");}

    @Step("Создание объявления")
    public MainPage createAdvertisment(Advertisment generateAd) {
        uploadPhotoButton().uploadFile(new File(generateAd.getPhotoPath()));
        nameInput().setValue(generateAd.getName());
        descriptionText().setValue(generateAd.getDescription());
        priceInput().setValue(String.valueOf(generateAd.getPrice()));

        publishButton().shouldBe(visible, Duration.ofSeconds(10)).click();
        return page(MainPage.class);
    }

}

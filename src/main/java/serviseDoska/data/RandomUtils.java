package serviseDoska.data;

import org.apache.commons.lang3.RandomStringUtils;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.github.javafaker.Faker;

// Класс для создания рандомных данных для тестов
public class RandomUtils {

    private static final Faker faker = new Faker();

    public static String randomEmail() {
        return faker.internet().emailAddress();
    }

    public static String randomPassword() {
        return faker.internet().password(10, 20, true, true, true);
    }

    public static String getAction() {
        String[] actions = {"куплю", "продам", "обменяю", "сдам", "сниму"};
        return actions[faker.number().numberBetween(0, actions.length - 1)];
    }

    public static String randomName() {
        return getAction() + " " + faker.commerce().productName();
    }

    public static String randomDescription() {
        return "Описание товара " + faker.lorem().sentence(30);
    }

    public static Integer randomPrice() {
        return faker.number().numberBetween(100, 100000);
    }




//    public static String randomEmail() {
//        return "newdoska" + RandomStringUtils.randomNumeric(4) + "@tt.ty";
//    }
//
//    public static String randomPassword() {
//        String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
//        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
//        String numbers = "0123456789";
//        String allCharacters = upperCaseLetters + lowerCaseLetters + numbers;
//
//        Random random = new Random();
//        StringBuilder password = new StringBuilder(5);
//
//        for (int i = 0; i < 10; i++) {
//            int index = random.nextInt(allCharacters.length());
//            password.append(allCharacters.charAt(index));
//        }
//
//        return password.toString();
//    }
//
//    public static String randomName() {
//        return "Объявление" + RandomStringUtils.randomNumeric(5);
//    }
//
//    public static String randomDescription() {
//        return "Описание" + "товара" + RandomStringUtils.randomAlphanumeric(30);
//    }
//
//    public static Integer randomPrice() {
//        return ThreadLocalRandom.current()
//                .nextInt(100, 100000);
//    }

}
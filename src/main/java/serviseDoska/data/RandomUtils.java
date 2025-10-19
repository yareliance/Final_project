package serviseDoska.data;

import org.apache.commons.lang3.RandomStringUtils;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

// Класс для создания рандомных данных для тестов
public class RandomUtils {
    public static String randomEmail() {
        return "newdoska" + RandomStringUtils.randomNumeric(4) + "@tt.tp";
    }

    public static String randomPassword() {
        String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String allCharacters = upperCaseLetters + lowerCaseLetters + numbers;

        Random random = new Random();
        StringBuilder password = new StringBuilder(5);

        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(allCharacters.length());
            password.append(allCharacters.charAt(index));
        }

        return password.toString();
    }

    public static String randomName() {
        return "Объявление" + RandomStringUtils.randomNumeric(5);
    }

    public static String randomDescription() {
        return "Описание" + "товара" + RandomStringUtils.randomAlphanumeric(30);
    }

    public static Integer randomPrice() {
        return ThreadLocalRandom.current()
                .nextInt(100, 100000);
    }

}
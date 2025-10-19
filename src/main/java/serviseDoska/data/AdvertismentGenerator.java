package serviseDoska.data;

import java.io.File;

public class AdvertismentGenerator {

    private static final String DEFAULT_IMAGE_PATH = "src/test/resources/images/example.jpg";
    // Генерация объявления
    public static Advertisment generateAd() {
        Advertisment generateAd = new Advertisment();
        generateAd.setName(RandomUtils.randomName());
        generateAd.setDescription(RandomUtils.randomDescription());
        generateAd.setPrice(RandomUtils.randomPrice());
        String absolutePath = new File(DEFAULT_IMAGE_PATH).getAbsolutePath();

        if (new File(absolutePath).exists()) {
            generateAd.setPhotoPath(absolutePath);
        } else {
            throw new RuntimeException("Файл изображения не найден по пути: " + absolutePath);
        }

        return generateAd;
    }

}

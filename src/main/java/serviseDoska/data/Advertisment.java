package serviseDoska.data;
import lombok.Data;

// Класс конструктор объявления
@Data
public class Advertisment {
    private String name; //имя
    private String description; //описание товара
    private int price; //стоимость
    private String photoPath; //путь к фото
}

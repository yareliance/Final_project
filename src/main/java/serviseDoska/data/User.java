package serviseDoska.data;
import lombok.Data;

// Класс-конструктор пользователя
@Data
public class User {
    private String email;
    private String password;
    private String submitPassword;

}

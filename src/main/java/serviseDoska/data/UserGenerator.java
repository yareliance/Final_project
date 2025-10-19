package serviseDoska.data;

public class UserGenerator {

    // Генерация пользователя
    public static User generateUser() {
        User user = new User();
        user.setEmail(RandomUtils.randomEmail());
        String password = RandomUtils.randomPassword();
        user.setPassword(password);
        user.setSubmitPassword(password);
        return user;
    }
}

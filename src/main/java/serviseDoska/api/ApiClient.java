package serviseDoska.api;

import serviseDoska.data.User;

import io.qameta.allure.Step;
import static io.restassured.RestAssured.given;

import static io.restassured.filter.log.LogDetail.ALL;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

// Класс для работы с API
public class ApiClient {
    private static final String BASE_URL = "https://qa-desk.stand.praktikum-services.ru";
    private static final String REGISTER_URL = "/api/signup";
    public static final String LOGIN_URL = "/api/signin";
    private static final String DELETE_USER_URL = "/api/delete-user/";

    private final RequestSpecification requestSpec;
    private final ResponseSpecification responseSpec;

    public ApiClient() {
        this.requestSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .log(ALL)
                .build();

        this.responseSpec = new ResponseSpecBuilder()
                .log(ALL)
                .build();
    }

    @Step("Регистрация пользователя")
    public ValidatableResponse registerUser(User user) {
        return given()
                .spec(requestSpec)
                .body(user)
                .post(REGISTER_URL)
                .then()
                .spec(responseSpec);
    }

    @Step("Авторизация пользователя")
    public ValidatableResponse loginUser(User user) {
        return given()
                .spec(requestSpec)
                .body(user)
                .post(LOGIN_URL)
                .then()
                .spec(responseSpec);
    }

    @Step("Получение accessToken")
    public String getAccessToken(ValidatableResponse response) {
        return response.extract().path("token.access_token");
    }

    @Step("Получение id пользователя")
    public Integer getUserId(ValidatableResponse response) {
        return response.extract().path("user.id");
    }

    @Step("Удаление пользователя по токену и id")
    public Response deleteUser(String accessToken, Integer userId) {
        return given()
                .baseUri(BASE_URL)
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .post(DELETE_USER_URL + userId)
                .then()
                .extract()
                .response();
    }

// В постмане ошибка при удалении с верным токеном - "message": "У вас нет прав", "error": "Unauthorized", "statusCode": 401
    @Step("Удаление пользователя после тестов")
    public void deleteUsersAfterTests(String accessToken, Integer userId) {

        if (accessToken != null && userId != null) {
            Response response = null;
            try {
                response = deleteUser(accessToken, userId);

                int statusCode = response.statusCode();
                if (statusCode == 200) {
                // Валидация успешного удаления
                assertThat(response.statusCode(), equalTo(200));
                assertThat(response.jsonPath().getBoolean("success"), equalTo(true));
                assertThat(response.jsonPath().getString("message"), equalTo("Пользователь успешно удален"));

                System.out.println("Пользователь успешно удален");

                } else if (statusCode == 401) {
                    // Обработка ошибки авторизации
                    String errorMessage = response.jsonPath().getString("message");
                    if (errorMessage.contains("У вас нет прав") || errorMessage.contains("Unauthorized")) {
                        System.out.println("Ошибка 401: Нет прав на удаление пользователя");
                    }

                } else {
                    // Обработка других ошибок
                    System.out.println("Ошибка при удалении пользователя:");
                    System.out.println(response.prettyPrint());
                }

            } catch (AssertionError e) {
                // Безопасная проверка на null
                if (response != null) {
                    System.out.println("Ошибка при проверке удаления пользователя:");
                    System.out.println(response.prettyPrint());
                }

            } catch (Exception e) {
                System.out.println("Произошла ошибка при удалении пользователя: " + e.getMessage());
            }
        } else {
            System.out.println("Не удалось удалить пользователя - отсутствуют данные: accessToken или userId");
        }
    }

}

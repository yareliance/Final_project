package serviseDoska.steps;

import io.cucumber.java.*;
import serviseDoska.api.ApiClient;
import serviseDoska.utils.TestUtils;

public class Hooks {

    private static String accessToken;
    private static Integer userId;
    private static ApiClient apiClient;

    // Для передачи данных из тестовых сценариев в хуки
    public static void setUserCredentials(String token, Integer id, ApiClient client) {
        accessToken = token;
        userId = id;
        apiClient = client;
    }

    @Before
    public void setUp() {
        TestUtils.setUp();
    }

    @After
    public void tearDown() {
        TestUtils.tearDown();
        if (accessToken != null && userId != null) {
            apiClient.deleteUsersAfterTests(accessToken, userId);
        }
    }

}

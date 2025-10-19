package serviseDoska.steps;

import io.cucumber.java.*;
import serviseDoska.api.ApiClient;
import serviseDoska.utils.TestUtils;

public class Hooks {

    private static ApiClient apiClient = new ApiClient();
    private String accessToken;
    private Integer userId;

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

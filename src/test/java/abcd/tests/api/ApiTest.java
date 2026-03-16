package abcd.tests.api;

import abcd.utils.CsvUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Example of API Test using REST Assured and Data-Driven approach.
 */
public class ApiTest {

    @DataProvider(name = "apiTestData")
    public Object[][] getApiData() {
        String csvPath = System.getProperty("user.dir") + "/src/test/resources/testdata/apiData.csv";
        return CsvUtils.getCsvData(csvPath);
    }

    @Test(dataProvider = "apiTestData", groups = { "api", "data-driven" })
    public void testGetUserApi(String userId, String expectedStatusCode) {
        // Base URI for the sample jsonplaceholder API
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Execute the GET request
        Response response = RestAssured.given()
                .header("User-Agent", "PostmanRuntime/7.28.4")
                .pathParam("id", userId)
                .when()
                .get("/users/{id}");

        // Validate the response status code matches our data
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, Integer.parseInt(expectedStatusCode),
                "Status code mismatch for user ID: " + userId);
    }
}

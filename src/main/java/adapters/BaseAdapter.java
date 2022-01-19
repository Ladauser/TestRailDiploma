package adapters;

import com.google.gson.Gson;
import constants.APIConstants;
import io.restassured.response.Response;
import utils.PropertyReader;

import static io.restassured.RestAssured.given;

public class BaseAdapter implements APIConstants {

    Gson converter = new Gson();

    public Response get(String url) {
        return given()
                .log().all()
                .auth()
                .preemptive()
                .basic(System.getenv().getOrDefault("email", PropertyReader.getProperty("email")),
                        System.getenv().getOrDefault("password", PropertyReader.getProperty("password")))
                .header(CONTENT_TYPE_VALUE, APPLICATION_JSON_VALUE)
                .when()
                .get(url)
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
    }

    public Response post(String url, String body) {
        return given()
                .auth()
                .preemptive()
                .basic(System.getenv().getOrDefault("email", PropertyReader.getProperty("email")),
                        System.getenv().getOrDefault("password", PropertyReader.getProperty("password")))
                .header(CONTENT_TYPE_VALUE, APPLICATION_JSON_VALUE)
                .body(body)
                .when()
                .post(url)
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
    }
}
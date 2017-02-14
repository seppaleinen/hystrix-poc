package se.david.gateway.controller;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import se.david.gateway.GatewayApplication;

import static com.jayway.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = GatewayApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "classpath:application-test.properties"
)
public class GatewayControllerTest {
    @LocalServerPort
    private int randomServerPort;

    @Before
    public void setup() {
        RestAssured.port = randomServerPort;
    }

    @Test
    public void serviceOne_MethodOne_CantAccess() {
        given().contentType(ContentType.JSON).param("hej").when().post(GatewayController.SERVICE_ONE_METHOD_ONE).
                then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        given().contentType(ContentType.JSON).param("hej").when().put(GatewayController.SERVICE_ONE_METHOD_ONE).
                then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        given().contentType(ContentType.JSON).param("hej").when().patch(GatewayController.SERVICE_ONE_METHOD_ONE).
                then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        given().contentType(ContentType.JSON).param("hej").when().delete(GatewayController.SERVICE_ONE_METHOD_ONE).
                then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
    }

    @Test
    public void serviceOne_MethodOne_canAccess() {
        given().param("param", "hej").when().get(GatewayController.SERVICE_ONE_METHOD_ONE).
                then().statusCode(HttpStatus.OK.value());
    }

    @Test
    public void serviceTwo_MethodOne_CantAccess() {
        given().contentType(ContentType.JSON).param("hej").when().post(GatewayController.SERVICE_TWO_METHOD_ONE).
                then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        given().contentType(ContentType.JSON).param("hej").when().put(GatewayController.SERVICE_TWO_METHOD_ONE).
                then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        given().contentType(ContentType.JSON).param("hej").when().patch(GatewayController.SERVICE_TWO_METHOD_ONE).
                then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        given().contentType(ContentType.JSON).param("hej").when().delete(GatewayController.SERVICE_TWO_METHOD_ONE).
                then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
    }

    @Test
    public void serviceTwo_MethodOne_canAccess() {
        given().param("param", "hej").when().get(GatewayController.SERVICE_TWO_METHOD_ONE).
                then().statusCode(HttpStatus.OK.value());
    }
}

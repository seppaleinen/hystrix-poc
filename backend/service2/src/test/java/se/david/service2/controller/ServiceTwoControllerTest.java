package se.david.service2.controller;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import se.david.service2.ServiceTwoApplication;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = ServiceTwoApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "classpath:application-test.properties"
)
public class ServiceTwoControllerTest {
    private static final String ROOT_URL = "/service-two";
    @LocalServerPort
    private int randomServerPort;

    @Before
    public void setup() {
        RestAssured.port = randomServerPort;
    }

    @Test
    public void canSave_NotAcceptableRequests() {
        given().contentType(ContentType.JSON).param("hej").when().post(ROOT_URL + ServiceTwoController.METHOD_ONE_URL).
                then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        given().contentType(ContentType.JSON).param("hej").when().put(ROOT_URL + ServiceTwoController.METHOD_ONE_URL).
                then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        given().contentType(ContentType.JSON).param("hej").when().patch(ROOT_URL + ServiceTwoController.METHOD_ONE_URL).
                then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        given().contentType(ContentType.JSON).param("hej").when().delete(ROOT_URL + ServiceTwoController.METHOD_ONE_URL).
                then().statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
    }

    @Test
    public void canAccess() {
        Response response = given().param("param", "hej").
                when().get("/service-two" + ServiceTwoController.METHOD_ONE_URL).
                thenReturn();

        assertNotNull(response);
        assertEquals("ERROR: " + response.prettyPrint(), HttpStatus.OK.value(), response.statusCode());
        assertEquals("hej!", response.print());
    }
}

package se.david.gateway.controller;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import se.david.gateway.GatewayApplication;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = GatewayApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "classpath:application-test.properties"
)
public class GatewayControllerTest {
    @LocalServerPort
    private int randomServerPort;
    @Autowired
    private GatewayController gatewayController;
    @Mock
    private RestTemplateBuilder restTemplateBuilder;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private ResponseEntity<String> responseEntity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(restTemplateBuilder.build()).thenReturn(restTemplate);
        Mockito.when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                eq(String.class),
                anyString())).thenReturn(responseEntity);
        ReflectionTestUtils.setField(gatewayController, "restTemplateBuilder", restTemplateBuilder);
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
        Mockito.when(responseEntity.getBody()).thenReturn("hej!");

        Response response = given().param("param", "hej").
                when().get(GatewayController.SERVICE_ONE_METHOD_ONE).
                thenReturn();

        assertNotNull(response);
        assertEquals("ERROR: " + response.prettyPrint(), HttpStatus.OK.value(), response.statusCode());
        assertEquals("hej!!", response.print());
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
        Mockito.when(responseEntity.getBody()).thenReturn("hej2!");

        Response response = given().param("param", "hej2").
                when().get(GatewayController.SERVICE_TWO_METHOD_ONE).
                thenReturn();

        assertNotNull(response);
        assertEquals("ERROR: " + response.prettyPrint(), HttpStatus.OK.value(), response.statusCode());
        assertEquals("hej2!!", response.print());
    }
}

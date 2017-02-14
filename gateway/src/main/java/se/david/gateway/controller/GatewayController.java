package se.david.gateway.controller;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import se.david.gateway.hystrix.GenericObservableCommand;
import se.david.gateway.hystrix.StringHystrixCommand;

import java.util.concurrent.ExecutionException;
import java.util.logging.Level;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@Log
class GatewayController {
    static final String SERVICE_ONE_METHOD_ONE = "/service-one/method-one";
    static final String SERVICE_TWO_METHOD_ONE = "/service-two/method-one";
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @RequestMapping(value = SERVICE_ONE_METHOD_ONE, method = RequestMethod.GET)
    ResponseEntity<String> serviceOneMethodOne(@RequestParam String param) throws ExecutionException, InterruptedException {
        log.log(Level.INFO, "Accepting parameter: " + param);
        String result = new GenericObservableCommand(
                "SERVICE_ONE_METHOD_ONE",
                restTemplateBuilder.build(),
                "http://localhost:10081" + SERVICE_ONE_METHOD_ONE + "?param={param}",
                param).getResult();
        return new ResponseEntity<String>(result + "!", HttpStatus.OK);
    }

    @RequestMapping(value = SERVICE_TWO_METHOD_ONE, method = RequestMethod.GET)
    ResponseEntity<String> serviceTwoMethodOne(@RequestParam String param) throws ExecutionException, InterruptedException {
        log.log(Level.INFO, "Accepting parameter: " + param);
        String result = new GenericObservableCommand(
                "SERVICE_TWO_METHOD_ONE",
                restTemplateBuilder.build(),
                "http://localhost:10082" + SERVICE_TWO_METHOD_ONE + "?param={param}",
                param).getResult();
        return new ResponseEntity<String>(result + "!", HttpStatus.OK);
    }
}

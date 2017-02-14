package se.david.gateway.controller;

import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@Log
class GatewayController {
    static final String SERVICE_ONE_METHOD_ONE = "/service-one/method-one";
    static final String SERVICE_TWO_METHOD_ONE = "/service-two/method-one";

    @RequestMapping(value = SERVICE_ONE_METHOD_ONE, method = RequestMethod.GET)
    ResponseEntity serviceOneMethodOne(@RequestParam String param) {
        log.log(Level.INFO, "Accepting parameter: " + param);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = SERVICE_TWO_METHOD_ONE, method = RequestMethod.GET)
    ResponseEntity serviceTwoMethodOne(@RequestParam String param) {
        log.log(Level.INFO, "Accepting parameter: " + param);
        return new ResponseEntity(HttpStatus.OK);
    }
}

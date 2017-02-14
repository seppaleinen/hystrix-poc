package se.david.service2.controller;

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
@RequestMapping(value = "service-two")
@Log
class ServiceTwoController {
    static final String METHOD_ONE_URL = "/method-one";

    @RequestMapping(value = METHOD_ONE_URL, method = RequestMethod.GET)
    ResponseEntity<String> methodOne(@RequestParam String param) {
        log.log(Level.INFO, "Accepting parameter: " + param);
        return new ResponseEntity<String>(param + "!", HttpStatus.OK);
    }
}

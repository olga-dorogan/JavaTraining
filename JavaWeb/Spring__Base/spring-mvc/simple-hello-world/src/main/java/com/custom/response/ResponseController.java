package com.custom.response;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by olga on 09.05.15.
 */
@Controller
@RequestMapping(value = "/response", method = RequestMethod.GET)
public class ResponseController {
    @RequestMapping(value = "/simple")
    public @ResponseBody String responseSimple() {
        return "Hello, world";
    }

    @RequestMapping(value = "/entity/status")
    public ResponseEntity<String> responseEntity() {
        return new ResponseEntity<String>("Page with status code 400", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "entity/headers")
    public ResponseEntity<String> responseEntityHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_PLAIN);
        return new ResponseEntity<String>("Page with http-header", httpHeaders, HttpStatus.OK);
    }
}

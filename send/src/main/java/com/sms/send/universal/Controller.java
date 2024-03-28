package com.sms.send.universal;

import com.sms.send.data.DataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {
    private final DataService dataService;

    public Controller(DataService dataService) {
        this.dataService = dataService;
    }

    @PostMapping(value = "/regex",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> putRegexList(@RequestBody List<String> regexes){
        dataService.storeRegexList(regexes);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
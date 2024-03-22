package com.sms.send.controller;

import com.sms.send.mongo.MongoService;
import com.sms.universal.UniversalMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {
    private final MongoService mongoService;

    public Controller(MongoService mongoService) {
        this.mongoService = mongoService;
    }

    @PostMapping(value = "/regex",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> putRegexList(@RequestBody List<String> regexes){
        mongoService.storeRegexList(regexes);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/regex")
    public ResponseEntity<List<String>> getRegexList(){
        return new ResponseEntity<>(mongoService.getRegexList(),HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UniversalMessage>> getAllUniversalMessages(){
        return new ResponseEntity<>(mongoService.getUniversalMessages(),HttpStatus.OK);
    }
}

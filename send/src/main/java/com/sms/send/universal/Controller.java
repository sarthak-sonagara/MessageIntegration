package com.sms.send.universal;

import com.sms.send.data.DataService;
import com.sms.send.data.entities.UniversalMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
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

    @GetMapping("/free-text-search/{input}")
    public List<UniversalMessage> freeTextSearch(@PathVariable String input){
        return dataService.getFreeTextSearchResult(input);
    }

}

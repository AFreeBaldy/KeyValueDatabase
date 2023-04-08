package com.laudwilliam.keyvaluedatabase.controllers;

import com.laudwilliam.keyvaluedatabase.models.*;
import com.laudwilliam.keyvaluedatabase.services.DataService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedList;
import java.util.Map;


@RestController
public class WebController {

    private final DataService dataService;

    @Autowired
    public WebController(DataService dataService) {
        this.dataService = dataService;
    }


    @GetMapping("/database/GET/{key}")
    public ResponseEntity<Entry> getData(@PathVariable String key) {
        return ResponseEntity.ok(dataService.getData(key));
    }

    @PutMapping("/database/SET/")
    public ResponseEntity<Entry> setData(@RequestBody Map<String, String> requestData) {
        System.out.println("Reached");
        return ResponseEntity.ok(dataService.setData(requestData.get("key"), requestData.get("data")));
    }

    @DeleteMapping("/database/DEL/{key}")
    public ResponseEntity<Entry> delData(@PathVariable String key) {
        return ResponseEntity.ok(dataService.delData(key));
    }

}

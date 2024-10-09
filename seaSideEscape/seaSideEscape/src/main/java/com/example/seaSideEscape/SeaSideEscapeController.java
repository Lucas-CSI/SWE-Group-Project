package com.example.seaSideEscape;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SeaSideEscapeController {

    @GetMapping("/")
    public String defaultPage(){
        return "SeaSide Escape TBD";
    }
}

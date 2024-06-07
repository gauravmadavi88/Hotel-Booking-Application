package com.ums3.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Api/v3/Auth")
public class CountryController {

    @PostMapping("/AddCountry")
    public String AddCountry(){
        return "done";
    }
}

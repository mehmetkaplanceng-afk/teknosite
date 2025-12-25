package com.ornek.teknolojisitesi.sohbet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SohbetDenetleyici {

    @GetMapping("/sohbet")
    public String sohbetSayfasi() {
        return "sohbet"; // sablonlar/sohbet.html
    }
}

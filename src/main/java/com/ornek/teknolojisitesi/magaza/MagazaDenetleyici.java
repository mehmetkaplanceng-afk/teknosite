package com.ornek.teknolojisitesi.magaza;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MagazaDenetleyici {

    private final MagazaServisi magazaServisi;

    public MagazaDenetleyici(MagazaServisi magazaServisi) {
        this.magazaServisi = magazaServisi;
    }

    // Liste sayfası
    @GetMapping("/magazalar")
    public String liste(Model model) {
        model.addAttribute("magazalar", magazaServisi.tumMagazalar());
        return "magazalar/liste"; // classpath:/sablonlar/magazalar/liste.html
    }

    // Harita sayfası
    @GetMapping("/magazalar/harita")
    public String harita() {
        return "magazalar/harita"; // classpath:/sablonlar/magazalar/harita.html
    }
}

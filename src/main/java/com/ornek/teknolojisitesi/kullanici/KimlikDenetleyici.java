package com.ornek.teknolojisitesi.kullanici;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class KimlikDenetleyici {

    private final KullaniciDeposu kullaniciDeposu;//dependency ile alınan veri 
    private final PasswordEncoder passwordEncoder;//dependency ile alınan veri

    public KimlikDenetleyici(KullaniciDeposu kullaniciDeposu, PasswordEncoder passwordEncoder) {
        this.kullaniciDeposu = kullaniciDeposu;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/kaydol")
    public String kaydolForm(Model model) {
        model.addAttribute("kullanici", new Kullanici());
        return "kaydol";
    }

    @PostMapping("/kaydol") //Bu, formdan gelen veriyi otomatik olarak Kullanici nesnesine dönüştürür.
    public String kaydol(@ModelAttribute("kullanici") Kullanici kullanici) {
        kullanici.setSifre(passwordEncoder.encode(kullanici.getSifre()));
        if (kullanici.getRol() == null || kullanici.getRol().isBlank()) {
            kullanici.setRol("ROLE_USER");
        }
        kullaniciDeposu.save(kullanici);
        return "redirect:/giris?kayit_ok";
    }

    //Login Sayfasını Gösteren Metot
    @GetMapping("/giris")
    public String giris() {
        return "giris";
    }
}

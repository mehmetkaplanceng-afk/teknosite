package com.ornek.teknolojisitesi.api.controller;

import com.ornek.teknolojisitesi.api.DtoDonusturucu;
import com.ornek.teknolojisitesi.api.dto.KullaniciDto;
import com.ornek.teknolojisitesi.kullanici.KullaniciDeposu;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/me")
public class MeApiController {

    private final KullaniciDeposu kullaniciDeposu;
    private final DtoDonusturucu dto;

    public MeApiController(KullaniciDeposu kullaniciDeposu, DtoDonusturucu dto) {
        this.kullaniciDeposu = kullaniciDeposu;
        this.dto = dto;
    }

    @GetMapping
    public KullaniciDto me(Authentication authentication) {
        String username = authentication.getName();
        return dto.kullaniciDto(kullaniciDeposu.findByKullaniciAdi(username).orElseThrow());
    }
}

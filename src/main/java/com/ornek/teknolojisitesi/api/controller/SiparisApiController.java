package com.ornek.teknolojisitesi.api.controller;

import com.ornek.teknolojisitesi.api.DtoDonusturucu;
import com.ornek.teknolojisitesi.api.dto.SiparisDto;
import com.ornek.teknolojisitesi.api.dto.SiparisOlusturIstek;
import com.ornek.teknolojisitesi.siparis.Siparis;
import com.ornek.teknolojisitesi.siparis.SiparisServisi;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/siparisler")
public class SiparisApiController {

    private final SiparisServisi siparisServisi;
    private final DtoDonusturucu dto;

    public SiparisApiController(SiparisServisi siparisServisi, DtoDonusturucu dto) {
        this.siparisServisi = siparisServisi;
        this.dto = dto;
    }

    @GetMapping
    public List<SiparisDto> benimSiparislerim(Authentication auth) {
        return siparisServisi.kullanicininSiparisleri(auth.getName()).stream()
                .map(dto::siparisDto)
                .toList();
    }

    @PostMapping
    public SiparisDto siparisOlustur(@RequestBody SiparisOlusturIstek istek, Authentication auth) {
        List<SiparisServisi.SiparisOlusturmaKalemi> kalemler = istek.kalemler().stream()
                .map(k -> new SiparisServisi.SiparisOlusturmaKalemi(k.urunId(), k.adet()))
                .toList();

        Siparis siparis = siparisServisi.siparisOlusturCoklu(kalemler, auth.getName());
        return dto.siparisDto(siparis);
    }
}

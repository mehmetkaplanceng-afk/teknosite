package com.ornek.teknolojisitesi.api.controller;

import com.ornek.teknolojisitesi.api.DtoDonusturucu;
import com.ornek.teknolojisitesi.api.dto.KategoriDto;
import com.ornek.teknolojisitesi.urun.depo.KategoriDeposu;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kategoriler")
public class KategoriApiController {

    private final KategoriDeposu kategoriDeposu;
    private final DtoDonusturucu dto;

    public KategoriApiController(KategoriDeposu kategoriDeposu, DtoDonusturucu dto) {
        this.kategoriDeposu = kategoriDeposu;
        this.dto = dto;
    }

    @GetMapping
    public List<KategoriDto> liste() {
        return kategoriDeposu.findAll().stream().map(dto::kategoriDto).toList();
    }
}

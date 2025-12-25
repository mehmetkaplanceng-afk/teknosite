package com.ornek.teknolojisitesi.api.controller;

import com.ornek.teknolojisitesi.api.DtoDonusturucu;
import com.ornek.teknolojisitesi.api.dto.UrunDto;
import com.ornek.teknolojisitesi.urun.Kategori;
import com.ornek.teknolojisitesi.urun.depo.KategoriDeposu;
import com.ornek.teknolojisitesi.urun.depo.UrunDeposu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/urunler")
public class UrunApiController {

    private final UrunDeposu urunDeposu;
    private final KategoriDeposu kategoriDeposu;
    private final DtoDonusturucu dto;

    public UrunApiController(UrunDeposu urunDeposu, KategoriDeposu kategoriDeposu, DtoDonusturucu dto) {
        this.urunDeposu = urunDeposu;
        this.kategoriDeposu = kategoriDeposu;
        this.dto = dto;
    }

    // Örnek: /api/urunler?page=0&size=20&q=iphone&kategori=Telefon
    @GetMapping
    public Page<UrunDto> liste(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "20") int size,
                              @RequestParam(required = false) String q,
                              @RequestParam(required = false) String kategori) {

        PageRequest pr = PageRequest.of(page, size);

        if (q != null && !q.isBlank()) {
            return urunDeposu.findByBaslikContainingIgnoreCase(q, pr).map(dto::urunDto);
        }

        if (kategori != null && !kategori.isBlank()) {
            Kategori kat = kategoriDeposu.findByAdIgnoreCase(kategori)
                    .orElseThrow(() -> new RuntimeException("Kategori bulunamadı: " + kategori));
            return urunDeposu.findByKategori(kat, pr).map(dto::urunDto);
        }

        return urunDeposu.findAll(pr).map(dto::urunDto);
    }

    @GetMapping("/{id}")
    public UrunDto detay(@PathVariable Long id) {
        return dto.urunDto(urunDeposu.findById(id).orElseThrow(() -> new RuntimeException("Ürün bulunamadı")));
    }

    @GetMapping("/one-cikanlar")
    public java.util.List<UrunDto> oneCikanlar() {
        return urunDeposu.findTop6ByOrderByIdDesc().stream().map(dto::urunDto).toList();
    }
}

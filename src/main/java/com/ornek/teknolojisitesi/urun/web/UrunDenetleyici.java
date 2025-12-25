package com.ornek.teknolojisitesi.urun.web;

import com.ornek.teknolojisitesi.urun.dto.UrunIstegi;
import com.ornek.teknolojisitesi.urun.servis.KategoriServisi;
import com.ornek.teknolojisitesi.urun.servis.UrunServisi;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

@Controller //Bu sınıf bir Spring MVC Controller
@RequestMapping("/urunler")          // Tüm endpointler /urunler ile başlar
public class UrunDenetleyici {

    private final UrunServisi urunServisi; // Ürün iş mantığı (service katmanı)
    private final KategoriServisi kategoriServisi; // Kategorileri listelemek için servis 
    // Constructor injection: Spring bu servislere otomatik instance verir
    public UrunDenetleyici(UrunServisi urunServisi, KategoriServisi kategoriServisi) {
        this.urunServisi = urunServisi;
        this.kategoriServisi = kategoriServisi;
    }

    // ----------------- ÜRÜN LİSTELEME -----------------

    @GetMapping
    public String liste(
            @RequestParam(value = "q", required = false) String q,
            @RequestParam(value = "kategoriId", required = false) Long kategoriId,
            Model model) {

        // Filtre + arama sonucu ürün listesi
        model.addAttribute("urunler", urunServisi.filtreliVeAramaliListe(q, kategoriId));

        // Kategori dropdown’ı için liste
        model.addAttribute("kategoriler", kategoriServisi.tumKategoriler());

        // Formda seçili kalması için model’e de ekliyoruz
        model.addAttribute("seciliKategoriId", kategoriId);
        model.addAttribute("aramaTerimi", q);

        return "urunler";
    }


    // ----------------- YENİ ÜRÜN FORMU (GET) -----------------

    @GetMapping("/yeni")
    @PreAuthorize("hasRole('ADMIN')")
    public String yeniUrunFormu(Model model) {
        model.addAttribute("id", null);
        model.addAttribute("istek", new UrunIstegi());
        model.addAttribute("kategoriler", kategoriServisi.tumKategoriler());
        return "urun-form";
    }

    // ----------------- YENİ ÜRÜN KAYDETME (POST) -----------------

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String yeniUrunKaydet(
            @Valid @ModelAttribute("istek") UrunIstegi istek,
            BindingResult bindingResult,
            Model model) {
        // Formda doğrulama hatası varsa (boş alan, geçersiz fiyat vs.)
        if (bindingResult.hasErrors()) {
            model.addAttribute("id", null);
            model.addAttribute("kategoriler", kategoriServisi.tumKategoriler());
            return "urun-form";
        }

        urunServisi.yeniUrunEkle(istek);
        return "redirect:/urunler";
    }



    @GetMapping("/{id}/duzenle")
    @PreAuthorize("hasRole('ADMIN')")
    public String duzenlemeFormu(@PathVariable Long id, Model model) {
        UrunIstegi istek = urunServisi.urunIstegiGetir(id);
        model.addAttribute("id", id);
        model.addAttribute("istek", istek);
        model.addAttribute("kategoriler", kategoriServisi.tumKategoriler());
        return "urun-form";
    }

    	@PostMapping("/{id}")
    	@PreAuthorize("hasRole('ADMIN')")
    	public String urunGuncelle(
            @PathVariable Long id,
            @Valid @ModelAttribute("istek") UrunIstegi istek,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("id", id);
            model.addAttribute("kategoriler", kategoriServisi.tumKategoriler());
            return "urun-form";
        }

        urunServisi.urunGuncelle(id, istek);
        return "redirect:/urunler";
    }

    @PostMapping("/{id}/sil")
    @PreAuthorize("hasRole('ADMIN')")
    public String urunSil(@PathVariable Long id) {
        urunServisi.urunSil(id);
        return "redirect:/urunler";
    }
}

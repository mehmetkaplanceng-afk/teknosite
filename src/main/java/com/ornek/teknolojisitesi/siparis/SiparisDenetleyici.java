package com.ornek.teknolojisitesi.siparis;

import com.ornek.teknolojisitesi.urun.Urun;
import com.ornek.teknolojisitesi.urun.depo.UrunDeposu;
import com.ornek.teknolojisitesi.siparis.Siparis;
import com.ornek.teknolojisitesi.siparis.SiparisServisi;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SiparisDenetleyici {

    private final UrunDeposu urunDeposu;
    private final SiparisServisi siparisServisi;

    public SiparisDenetleyici(UrunDeposu urunDeposu, SiparisServisi siparisServisi) {
        this.urunDeposu = urunDeposu;
        this.siparisServisi = siparisServisi;
    }

    // 1) SİPARİŞ OLUŞTURMA FORMU (GET /siparis/olustur/{urunId})
    @GetMapping("/siparis/olustur/{urunId}")
    public String siparisForm(@PathVariable Long urunId, Model model) {
        Urun urun = urunDeposu.findById(urunId)
                .orElseThrow(() -> new IllegalArgumentException("Ürün bulunamadı: " + urunId));

        model.addAttribute("urun", urun);
        model.addAttribute("adet", 1);
        return "siparis-olustur"; // siparis-olustur.html
    }

    // 2) SİPARİŞİ KAYDET (POST /siparis/olustur/{urunId})
    @PostMapping("/siparis/olustur/{urunId}")
    public String siparisOlustur(@PathVariable Long urunId,
                                 @RequestParam int adet,
                                 Authentication authentication) {

        String kullaniciAdi = authentication.getName();
        siparisServisi.siparisOlustur(urunId, adet, kullaniciAdi);

        return "redirect:/siparislerim";
    }

    // 3) MÜŞTERİNİN KENDİ SİPARİŞLERİ (GET /siparislerim)
    @GetMapping("/siparislerim")
    public String siparislerim(Authentication authentication, Model model) {
        String kullaniciAdi = authentication.getName();
        List<Siparis> siparisler = siparisServisi.kullanicininSiparisleri(kullaniciAdi);
        model.addAttribute("siparisler", siparisler);
        return "siparislerim"; // siparislerim.html
    }

    // 4) ADMIN İÇİN TÜM SİPARİŞLER (GET /admin/siparisler)
    @GetMapping("/admin/siparisler")
    public String adminSiparisler(Model model) {
        List<Siparis> siparisler = siparisServisi.tumSiparisler();
        model.addAttribute("siparisler", siparisler);
        return "admin-siparisler"; // admin-siparisler.html
    }
}

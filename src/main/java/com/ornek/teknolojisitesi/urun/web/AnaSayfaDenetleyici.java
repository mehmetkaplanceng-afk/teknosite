package com.ornek.teknolojisitesi.urun.web;

import com.ornek.teknolojisitesi.urun.Urun;
import com.ornek.teknolojisitesi.urun.depo.UrunDeposu;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AnaSayfaDenetleyici {

    private final UrunDeposu urunDeposu;

    public AnaSayfaDenetleyici(UrunDeposu urunDeposu) {
        this.urunDeposu = urunDeposu;
    }

    @GetMapping({"/", "/anasayfa"})
    public String anaSayfa(Model model) {
        // Son eklenen 6 ürünü getir (id'ye göre tersten)
        List<Urun> sonUrunler = urunDeposu.findTop6ByOrderByIdDesc();
        model.addAttribute("sonUrunler", sonUrunler);
        return "anasayfa";
    }
}

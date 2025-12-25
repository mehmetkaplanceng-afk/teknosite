package com.ornek.teknolojisitesi.urun.servis;

import com.ornek.teknolojisitesi.urun.Kategori;
import com.ornek.teknolojisitesi.urun.depo.KategoriDeposu;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KategoriServisi {

    private final KategoriDeposu kategoriDeposu;

    public KategoriServisi(KategoriDeposu kategoriDeposu) {
        this.kategoriDeposu = kategoriDeposu;
    }

    public List<Kategori> tumKategoriler() {
        return kategoriDeposu.findAll();
    }
}

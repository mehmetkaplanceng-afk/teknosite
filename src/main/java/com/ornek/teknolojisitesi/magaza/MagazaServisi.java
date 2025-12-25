package com.ornek.teknolojisitesi.magaza;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MagazaServisi {

    private final MagazaDeposu depo;

    public MagazaServisi(MagazaDeposu depo) {
        this.depo = depo;
    }

    public List<Magaza> tumMagazalar() {
        return depo.findAll();
    }

    public Magaza magazaBul(Integer id) {
        return depo.findById(id)
            .orElseThrow(() -> new RuntimeException("Mağaza bulunamadı: " + id));
    }


    public Magaza kaydet(Magaza m) {
        return depo.save(m);
    }

    public void sil(Integer id) {
        depo.deleteById(id);
    }
}

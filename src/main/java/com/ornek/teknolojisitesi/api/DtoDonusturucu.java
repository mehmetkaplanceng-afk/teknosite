package com.ornek.teknolojisitesi.api;

import com.ornek.teknolojisitesi.api.dto.*;
import com.ornek.teknolojisitesi.magaza.Magaza;
import com.ornek.teknolojisitesi.siparis.Siparis;
import com.ornek.teknolojisitesi.siparis.SiparisKalemi;
import com.ornek.teknolojisitesi.urun.Kategori;
import com.ornek.teknolojisitesi.urun.Urun;
import com.ornek.teknolojisitesi.kullanici.Kullanici;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DtoDonusturucu {

    public KategoriDto kategoriDto(Kategori k) {
        if (k == null) return null;
        return new KategoriDto(k.getId(), k.getAd());
    }

    public UrunDto urunDto(Urun u) {
        if (u == null) return null;
        return new UrunDto(
                u.getId(),
                u.getBaslik(),
                u.getFiyat(),
                u.getStok(),
                u.getGorselUrl(),
                u.getAciklama(),
                kategoriDto(u.getKategori())
        );
    }

    public MagazaDto magazaDto(Magaza m) {
        if (m == null) return null;
        return new MagazaDto(m.getId(), m.getAd(), m.getAdres(), m.getEnlem(), m.getBoylam(), m.getIl(), m.getIlce());
    }

    public KullaniciDto kullaniciDto(Kullanici k) {
        if (k == null) return null;
        return new KullaniciDto(k.getId(), k.getKullaniciAdi(), k.getRol(), k.getEmail());
    }

    public SiparisDto siparisDto(Siparis s) {
        if (s == null) return null;
        List<SiparisKalemiDto> kalemler = s.getKalemler().stream().map(this::kalemDto).toList();
        double toplam = kalemler.stream().mapToDouble(k -> k.adet() * k.birimFiyat()).sum();
        return new SiparisDto(s.getId(), s.getTarih(), s.getKullanici().getKullaniciAdi(), kalemler, toplam);
    }

    private SiparisKalemiDto kalemDto(SiparisKalemi k) {
        return new SiparisKalemiDto(
                k.getUrun().getId(),
                k.getUrun().getBaslik(),
                k.getAdet(),
                k.getUrun().getFiyat()
        );
    }
}

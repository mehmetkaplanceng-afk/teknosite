package com.ornek.teknolojisitesi.siparis;

import com.ornek.teknolojisitesi.kullanici.Kullanici;
import com.ornek.teknolojisitesi.kullanici.KullaniciDeposu;
import com.ornek.teknolojisitesi.urun.Urun;
import com.ornek.teknolojisitesi.urun.depo.UrunDeposu;
import com.ornek.teknolojisitesi.siparis.depo.SiparisDeposu;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SiparisServisi {

    private final SiparisDeposu siparisDeposu;
    private final UrunDeposu urunDeposu;
    private final KullaniciDeposu kullaniciDeposu;

    public SiparisServisi(SiparisDeposu siparisDeposu,
                          UrunDeposu urunDeposu,
                          KullaniciDeposu kullaniciDeposu) {
        this.siparisDeposu = siparisDeposu;
        this.urunDeposu = urunDeposu;
        this.kullaniciDeposu = kullaniciDeposu;
    }

    // 🔹 BUTONUN ÇAĞIRDIĞI METOD BURASI 🔹
    public void siparisOlustur(Long urunId, int adet, String kullaniciAdi) {

        // 1) Ürünü bul
        Urun urun = urunDeposu.findById(urunId)
                .orElseThrow(() -> new IllegalArgumentException("Ürün bulunamadı: " + urunId));

        if (adet <= 0) {
            throw new IllegalArgumentException("Adet 1 veya daha büyük olmalı");
        }
        if (urun.getStok() < adet) {
            throw new IllegalStateException("Yeterli stok yok (stok: " + urun.getStok() + ")");
        }

        // 2) Kullanıcıyı bul
        Kullanici kullanici = kullaniciDeposu.findByKullaniciAdi(kullaniciAdi)
                .orElseThrow(() -> new IllegalArgumentException("Kullanıcı bulunamadı: " + kullaniciAdi));

        // 3) Sipariş oluştur
        Siparis siparis = new Siparis();
        siparis.setTarih(LocalDateTime.now());
        siparis.setKullanici(kullanici);

        // 4) Sipariş kalemi oluştur
        SiparisKalemi kalem = new SiparisKalemi();
        kalem.setSiparis(siparis);
        kalem.setUrun(urun);
        kalem.setAdet(adet);

        List<SiparisKalemi> kalemler = new ArrayList<>();
        kalemler.add(kalem);
        siparis.setKalemler(kalemler);

        // 5) Stok düş
        urun.setStok(urun.getStok() - adet);

        // 6) Veritabanına kaydet
        siparisDeposu.save(siparis); // siparis + kalemler
        urunDeposu.save(urun);       // güncellenmiş stok
    }

// Mobil sepet: birden fazla ürünü tek siparişte oluştur
public Siparis siparisOlusturCoklu(List<SiparisOlusturmaKalemi> istekKalemleri, String kullaniciAdi) {

    if (istekKalemleri == null || istekKalemleri.isEmpty()) {
        throw new IllegalArgumentException("Sepet boş olamaz.");
    }

    Kullanici kullanici = kullaniciDeposu.findByKullaniciAdi(kullaniciAdi)
            .orElseThrow(() -> new IllegalArgumentException("Kullanıcı bulunamadı: " + kullaniciAdi));

    Siparis siparis = new Siparis();
    siparis.setTarih(LocalDateTime.now());
    siparis.setKullanici(kullanici);

    List<SiparisKalemi> kalemler = new ArrayList<>();

    for (SiparisOlusturmaKalemi item : istekKalemleri) {
        if (item.adet() == null || item.adet() <= 0) {
            throw new IllegalArgumentException("Adet 1 veya daha büyük olmalı.");
        }

        Urun urun = urunDeposu.findById(item.urunId())
                .orElseThrow(() -> new IllegalArgumentException("Ürün bulunamadı: " + item.urunId()));

        if (urun.getStok() < item.adet()) {
            throw new IllegalArgumentException("Stok yetersiz: " + urun.getBaslik());
        }

        // stok düş
        urun.setStok(urun.getStok() - item.adet());

        SiparisKalemi kalem = new SiparisKalemi();
        kalem.setSiparis(siparis);
        kalem.setUrun(urun);
        kalem.setAdet(item.adet());

        kalemler.add(kalem);
        urunDeposu.save(urun);
    }

    siparis.setKalemler(kalemler);
    return siparisDeposu.save(siparis);
}

// Servis içine minimal DTO: (controller'dan taşımak istemiyoruz)
public record SiparisOlusturmaKalemi(Long urunId, Integer adet) {}

    // Kullanıcının kendi siparişlerini listelemek için
    public List<Siparis> kullanicininSiparisleri(String kullaniciAdi) {
        return siparisDeposu.findByKullanici_KullaniciAdiOrderByTarihDesc(kullaniciAdi);
    }

    // Admin paneli için tüm siparişler
    public List<Siparis> tumSiparisler() {
        return siparisDeposu.findAllByOrderByTarihDesc();
    }
}

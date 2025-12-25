package com.ornek.teknolojisitesi.urun.servis;

import com.ornek.teknolojisitesi.urun.Kategori;
import com.ornek.teknolojisitesi.urun.Urun;
import com.ornek.teknolojisitesi.urun.depo.KategoriDeposu;
import com.ornek.teknolojisitesi.urun.depo.UrunDeposu;
import com.ornek.teknolojisitesi.urun.dto.UrunIstegi;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

@Service
public class UrunServisi {

    private final UrunDeposu urunDeposu;
    private final KategoriDeposu kategoriDeposu;

    public UrunServisi(UrunDeposu urunDeposu, KategoriDeposu kategoriDeposu) {
        this.urunDeposu = urunDeposu;
        this.kategoriDeposu = kategoriDeposu;
    }

    // TÜM ÜRÜNLERİ LİSTELE
    public List<Urun> tumUrunler() {
        return urunDeposu.findAll();
    }
    // ⭐⭐ ÖNEMLİ: Bu metot mutlaka class’ın içinde, son }’den önce olacak
    public List<Urun> filtreliVeAramaliListe(String q, Long kategoriId) {

        boolean aramaVar = (q != null && !q.isBlank());
        boolean kategoriVar = (kategoriId != null);

        // Hem arama hem kategori yoksa -> tüm ürünler
        if (!aramaVar && !kategoriVar) {
            return urunDeposu.findAll();
        }

        // Sadece arama varsa
        if (aramaVar && !kategoriVar) {
            Page<Urun> sayfa = urunDeposu
                    .findByBaslikContainingIgnoreCase(q, Pageable.unpaged());
            return sayfa.getContent();
        }

        // Buradan sonrası: kategoriId var
        return kategoriDeposu.findById(kategoriId)
                .map(kategori -> {
                    // Sadece kategori varsa
                    if (!aramaVar) {
                        Page<Urun> sayfa = urunDeposu
                                .findByKategori(kategori, Pageable.unpaged());
                        return sayfa.getContent();
                    }

                    // Hem kategori hem arama varsa:
                    Page<Urun> sayfa = urunDeposu
                            .findByKategori(kategori, Pageable.unpaged());
                    List<Urun> kategoriUrunleri = sayfa.getContent();

                    String kucukQ = q.toLowerCase();
                    return kategoriUrunleri.stream()
                            .filter(u -> u.getBaslik() != null &&
                                    u.getBaslik().toLowerCase().contains(kucukQ))
                            .toList();
                })
                .orElseGet(List::of);
    }


    // ID İLE ÜRÜN GETİR (sipariş tarafında kullanıyoruz)
    public Urun idIleGetir(Long id) {
        return urunDeposu.findById(id).orElse(null);
    }

    // YENİ ÜRÜN EKLE (DTO -> Entity)
    public void yeniUrunEkle(UrunIstegi istek) {
        Urun urun = new Urun();

        urun.setBaslik(istek.getBaslik());
        urun.setFiyat(istek.getFiyat());
        urun.setStok(istek.getStok());
        urun.setAciklama(istek.getAciklama());
        urun.setGorselUrl(istek.getGorselUrl());

        if (istek.getKategoriId() != null) {
            Kategori kategori = kategoriDeposu.findById(istek.getKategoriId()).orElse(null);
            urun.setKategori(kategori);
        }

        urunDeposu.save(urun);
    }

    // DÜZENLEME FORMU İÇİN DTO DOLDUR
    public UrunIstegi urunIstegiGetir(Long id) {
        Urun urun = urunDeposu.findById(id).orElseThrow();

        UrunIstegi istek = new UrunIstegi();
        istek.setBaslik(urun.getBaslik());
        istek.setFiyat(urun.getFiyat());
        istek.setStok(urun.getStok());
        istek.setAciklama(urun.getAciklama());
        istek.setGorselUrl(urun.getGorselUrl());

        if (urun.getKategori() != null) {
            istek.setKategoriId(urun.getKategori().getId());
        }

        return istek;
    }

    // VAR OLAN ÜRÜNÜ GÜNCELLE
    public void urunGuncelle(Long id, UrunIstegi istek) {
        Urun urun = urunDeposu.findById(id).orElseThrow();

        urun.setBaslik(istek.getBaslik());
        urun.setFiyat(istek.getFiyat());
        urun.setStok(istek.getStok());
        urun.setAciklama(istek.getAciklama());
        urun.setGorselUrl(istek.getGorselUrl());

        if (istek.getKategoriId() != null) {
            Kategori kategori = kategoriDeposu.findById(istek.getKategoriId()).orElse(null);
            urun.setKategori(kategori);
        } else {
            urun.setKategori(null);
        }

        urunDeposu.save(urun);
    }

    // ÜRÜN SİL
    public void urunSil(Long id) {
        urunDeposu.deleteById(id);
    }
}

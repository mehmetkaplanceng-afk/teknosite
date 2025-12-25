package com.ornek.teknolojisitesi.siparis;

import com.ornek.teknolojisitesi.kullanici.Kullanici;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Siparis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime tarih;
 // Bir siparişi oluşturan kullanıcıyı temsil eder.
 // @ManyToOne → Bir siparişin mutlaka 1 tane kullanıcısı olur,
 // ancak bir kullanıcı birden fazla sipariş verebilir.
 // optional = false → Kullanıcısı olmayan sipariş oluşturulamaz (zorunlu alan).
    @ManyToOne(optional = false)
    private Kullanici kullanici;
 // Siparişin içinde birden fazla sipariş kalemi olabilir.
 // Örnek: Aynı siparişte 3 ürün varsa 3 tane SiparisKalemi kaydı olur. Sepet Sistemi için
 // mappedBy = "siparis" → SiparisKalemi tablosunda 'siparis' alanı bu ilişkiyi yönetir.
 // cascade = ALL → Sipariş kaydedildiğinde/silindiğinde kalemler de otomatik işlem görür.
 // orphanRemoval = true → Siparişten çıkarılan kalemler otomatik olarak DB'den silinir.
    @OneToMany(mappedBy = "siparis", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SiparisKalemi> kalemler = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTarih() {
        return tarih;
    }

    public void setTarih(LocalDateTime tarih) {
        this.tarih = tarih;
    }

    public Kullanici getKullanici() {
        return kullanici;
    }

    public void setKullanici(Kullanici kullanici) {
        this.kullanici = kullanici;
    }

    public List<SiparisKalemi> getKalemler() {
        return kalemler;
    }

    public void setKalemler(List<SiparisKalemi> kalemler) {
        this.kalemler = kalemler;
    }
}

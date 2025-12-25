package com.ornek.teknolojisitesi.siparis;

import com.ornek.teknolojisitesi.urun.Urun;
import jakarta.persistence.*;

@Entity
public class SiparisKalemi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 // Bir sipariş kalemi mutlaka bir siparişe bağlıdır.
 // @ManyToOne → Bir sipariş (Siparis) içinde birden fazla sipariş kalemi olabilir.
 // optional = false → Siparişi olmayan bir kalem kaydedilemez.
    @ManyToOne(optional = false)
    private Siparis siparis;
 // Bir sipariş kalemi mutlaka bir ürünü temsil eder.
 // @ManyToOne → Aynı ürün bir siparişte birden çok kalemde kullanılabilir.
 // optional = false → Ürünsüz sipariş kalemi olmaz (zorunlu alan).
    @ManyToOne(optional = false)
    private Urun urun;

    private int adet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Siparis getSiparis() {
        return siparis;
    }

    public void setSiparis(Siparis siparis) {
        this.siparis = siparis;
    }

    public Urun getUrun() {
        return urun;
    }

    public void setUrun(Urun urun) {
        this.urun = urun;
    }

    public int getAdet() {
        return adet;
    }

    public void setAdet(int adet) {
        this.adet = adet;
    }
}

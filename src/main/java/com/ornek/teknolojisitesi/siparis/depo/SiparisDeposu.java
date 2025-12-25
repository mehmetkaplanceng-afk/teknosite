package com.ornek.teknolojisitesi.siparis.depo;

import com.ornek.teknolojisitesi.siparis.Siparis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SiparisDeposu extends JpaRepository<Siparis, Long> {

    // siparislerim sayfası
    List<Siparis> findByKullanici_KullaniciAdiOrderByTarihDesc(String kullaniciAdi);

    // admin sipariş listesi
    List<Siparis> findAllByOrderByTarihDesc();
}

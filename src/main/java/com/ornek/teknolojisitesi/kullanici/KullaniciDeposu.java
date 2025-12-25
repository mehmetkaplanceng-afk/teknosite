package com.ornek.teknolojisitesi.kullanici;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// Veritabanı ile kullanıcı tablosu arasında köprü kuran Repository arayüzü
public interface KullaniciDeposu extends JpaRepository<Kullanici, Long> {

    // Kullanıcıyı "kullaniciAdi" alanına göre bulur.
    // Optional: Kullanıcı bulunamazsa null yerine boş değer döner (hata önler)
    Optional<Kullanici> findByKullaniciAdi(String kullaniciAdi);
}

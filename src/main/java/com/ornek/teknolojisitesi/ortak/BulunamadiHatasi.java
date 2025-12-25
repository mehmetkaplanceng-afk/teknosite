package com.ornek.teknolojisitesi.ortak;

// Özel bir hata türü oluşturuyoruz.
// RuntimeException'dan miras aldığı için checked değil → try-catch zorunlu değil.
public class BulunamadiHatasi extends RuntimeException {

    // Hata fırlatılırken mesaj göndermek için constructor
    public BulunamadiHatasi(String mesaj) { 
        super(mesaj); // Üst sınıfa (RuntimeException) mesajı iletir
    }
}

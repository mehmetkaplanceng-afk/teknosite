package com.ornek.teknolojisitesi.kullanici;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

@Component  // Spring’e bu sınıfın bir bileşen olduğunu söyler, uygulama açılırken çalışacak
public class KullaniciVeriYukleyici implements CommandLineRunner {

    private final KullaniciDeposu kullaniciDeposu;     // Veritabanı işlemleri için repository
    private final PasswordEncoder passwordEncoder;     // Şifreleri BCrypt ile hashlemek için

    // Constructor -> Spring tarafından bağımlılıklar otomatik enjekte edilir
    public KullaniciVeriYukleyici(KullaniciDeposu kullaniciDeposu, PasswordEncoder passwordEncoder) {
        this.kullaniciDeposu = kullaniciDeposu;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Bu metod uygulama başlamasında 1 kez otomatik olarak çalışır

        // --- 1) Varsayılan admin kullanıcı yoksa oluştur ---
        if (kullaniciDeposu.findByKullaniciAdi("admin").isEmpty()) {   // admin kullanıcı adı yok mu?
            Kullanici admin = new Kullanici();                         // yeni kullanıcı nesnesi
            admin.setKullaniciAdi("admin");                            // kullanıcı adı
            admin.setEmail("admin@site.com");                          // e-posta
            admin.setRol("ROLE_ADMIN");                                // admin rolü
            admin.setSifre(passwordEncoder.encode("1234"));            // şifre hashlenerek kaydedilir
            kullaniciDeposu.save(admin);                               // veritabanına kaydedilir
        }

        // --- 2) Varsayılan normal kullanıcı yoksa oluştur ---
        if (kullaniciDeposu.findByKullaniciAdi("musteri").isEmpty()) { // musteri kullanıcı adı yok mu?
            Kullanici user = new Kullanici();                          // yeni kullanıcı nesnesi
            user.setKullaniciAdi("musteri");                           // kullanıcı adı
            user.setEmail("musteri@site.com");                         // e-posta
            user.setRol("ROLE_USER");                                  // normal kullanıcı rolü
            user.setSifre(passwordEncoder.encode("1234"));             // şifre hashlenerek kaydedilir
            kullaniciDeposu.save(user);                                // veritabanına kaydedilir
        }
    }
}

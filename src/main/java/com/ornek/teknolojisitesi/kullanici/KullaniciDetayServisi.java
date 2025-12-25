package com.ornek.teknolojisitesi.kullanici;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service  // Bu sınıfı Spring servisi haline getirir, Security kullanabilsin diye
public class KullaniciDetayServisi implements UserDetailsService {

    private final KullaniciDeposu depo;  // Kullanıcının veritabanında arandığı repository

    public KullaniciDetayServisi(KullaniciDeposu depo) {
        this.depo = depo;
    }

    // Spring Security login sırasında bu metodu otomatik olarak çağırır.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Veritabanında kullanıcı adıyla arama yapılır.
        // Eğer kullanıcı yoksa UsernameNotFoundException fırlatılır.
        Kullanici k = depo.findByKullaniciAdi(username)
                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı"));

        // Veritabanından gelen Kullanici nesnesi → Spring Security'nin anlayacağı User nesnesine çevrilir.
        return User.withUsername(k.getKullaniciAdi())   // Kullanıcı adı
                .password(k.getSifre())                // BCrypt ile hashlenmiş şifre
                .roles(k.getRol().replace("ROLE_", "")) // Spring Security role formatı: sadece USER veya ADMIN istiyor.
                                                      // Bizim DB "ROLE_USER" tuttuğu için ROLE_ kısmı temizleniyor.
                .build();                               // UserDetails oluşturulup Security'ye verilir.
    }
}

package com.ornek.teknolojisitesi.api.controller;

import com.ornek.teknolojisitesi.api.DtoDonusturucu;
import com.ornek.teknolojisitesi.api.dto.*;
import com.ornek.teknolojisitesi.guvenlik.api.JwtServisi;
import com.ornek.teknolojisitesi.kullanici.Kullanici;
import com.ornek.teknolojisitesi.kullanici.KullaniciDeposu;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthApiController {

    private final AuthenticationManager authenticationManager;
    private final JwtServisi jwtServisi;
    private final KullaniciDeposu kullaniciDeposu;
    private final PasswordEncoder encoder;
    private final DtoDonusturucu dto;

    public AuthApiController(AuthenticationManager authenticationManager,
                             JwtServisi jwtServisi,
                             KullaniciDeposu kullaniciDeposu,
                             PasswordEncoder encoder,
                             DtoDonusturucu dto) {
        this.authenticationManager = authenticationManager;
        this.jwtServisi = jwtServisi;
        this.kullaniciDeposu = kullaniciDeposu;
        this.encoder = encoder;
        this.dto = dto;
    }

    @PostMapping("/login")
    public AuthCevap login(@RequestBody LoginIstek istek) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(istek.kullaniciAdi(), istek.sifre())
        );

        String token = jwtServisi.tokenUret((org.springframework.security.core.userdetails.UserDetails) auth.getPrincipal());
        Kullanici k = kullaniciDeposu.findByKullaniciAdi(istek.kullaniciAdi()).orElseThrow();
        return new AuthCevap(token, dto.kullaniciDto(k));
    }

    @PostMapping("/register")
    public AuthCevap register(@RequestBody RegisterIstek istek) {
        kullaniciDeposu.findByKullaniciAdi(istek.kullaniciAdi()).ifPresent(x -> {
            throw new RuntimeException("Bu kullanıcı adı zaten kayıtlı.");
        });

        Kullanici k = new Kullanici();
        k.setKullaniciAdi(istek.kullaniciAdi());
        k.setSifre(encoder.encode(istek.sifre()));
        k.setRol("ROLE_USER");
        k.setEmail(istek.email());

        Kullanici kayit = kullaniciDeposu.save(k);

        // direkt token döndür
        org.springframework.security.core.userdetails.UserDetails userDetails =
                org.springframework.security.core.userdetails.User
                        .withUsername(kayit.getKullaniciAdi())
                        .password(kayit.getSifre())
                        .roles("USER")
                        .build();

        String token = jwtServisi.tokenUret(userDetails);
        return new AuthCevap(token, dto.kullaniciDto(kayit));
    }
}

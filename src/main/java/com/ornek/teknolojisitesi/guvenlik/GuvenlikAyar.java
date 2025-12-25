package com.ornek.teknolojisitesi.guvenlik;

import com.ornek.teknolojisitesi.kullanici.KullaniciDetayServisi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Order(2)
public class GuvenlikAyar {

    private final KullaniciDetayServisi kullaniciDetayServisi;

    public GuvenlikAyar(KullaniciDetayServisi kullaniciDetayServisi) {
        this.kullaniciDetayServisi = kullaniciDetayServisi;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/**")
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/", "/anasayfa",
                    "/css/**", "/js/**", "/images/**",
                    "/giris", "/kaydol",
                    "/magazalar", "/magazalar/**", "/magazalar/harita",
                    "/api/magazalar",
                    "/ws-sohbet/**",
                    "/urunler", "/urunler/*", "/urunler/kategori/**"
                ).permitAll()
                .requestMatchers("/urunler/yeni", "/urunler/*/duzenle", "/urunler/*/sil").hasRole("ADMIN")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/siparis/**", "/siparislerim").hasAnyRole("USER","ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/giris")
                .loginProcessingUrl("/giris")
                .usernameParameter("kullaniciAdi")
                .passwordParameter("sifre")
                .defaultSuccessUrl("/anasayfa", true)
                .failureUrl("/giris?hata")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/cikis")
                .logoutSuccessUrl("/giris?cikis")
                .permitAll()
            )
            .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    public UserDetailsService userDetailsService() { return kullaniciDetayServisi; }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(kullaniciDetayServisi);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}

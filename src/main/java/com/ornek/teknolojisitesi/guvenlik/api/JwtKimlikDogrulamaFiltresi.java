package com.ornek.teknolojisitesi.guvenlik.api;

import com.ornek.teknolojisitesi.kullanici.KullaniciDetayServisi;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtKimlikDogrulamaFiltresi extends OncePerRequestFilter {

    private final JwtServisi jwtServisi;
    private final KullaniciDetayServisi kullaniciDetayServisi;

    public JwtKimlikDogrulamaFiltresi(JwtServisi jwtServisi, KullaniciDetayServisi kullaniciDetayServisi) {
        this.jwtServisi = jwtServisi;
        this.kullaniciDetayServisi = kullaniciDetayServisi;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);
        try {
            String username = jwtServisi.kullaniciAdiCek(token);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails user = kullaniciDetayServisi.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (Exception ignored) {
            // token hatalı/expired ise authentication set etmiyoruz
        }

        filterChain.doFilter(request, response);
    }
}

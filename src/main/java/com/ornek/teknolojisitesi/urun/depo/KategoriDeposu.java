package com.ornek.teknolojisitesi.urun.depo;

import com.ornek.teknolojisitesi.urun.Kategori;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface KategoriDeposu extends JpaRepository<Kategori, Long> {
    Optional<Kategori> findByAdIgnoreCase(String ad);
}

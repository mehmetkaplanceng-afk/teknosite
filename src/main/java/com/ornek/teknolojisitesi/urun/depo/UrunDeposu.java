package com.ornek.teknolojisitesi.urun.depo;

import java.util.List;

import com.ornek.teknolojisitesi.urun.Urun;
import com.ornek.teknolojisitesi.urun.Kategori;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrunDeposu extends JpaRepository<Urun, Long> {
    Page<Urun> findByBaslikContainingIgnoreCase(String q, Pageable pageable);
    Page<Urun> findByKategori(Kategori kategori, Pageable pageable);
    List<Urun> findTop6ByOrderByIdDesc();
}

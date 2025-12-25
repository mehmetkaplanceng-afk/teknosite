package com.ornek.teknolojisitesi.urun;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Kategori {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String ad;

    public Kategori() {}
    public Kategori(String ad) { this.ad = ad; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAd() { return ad; }
    public void setAd(String ad) { this.ad = ad; }
}

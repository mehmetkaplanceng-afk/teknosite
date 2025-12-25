package com.ornek.teknolojisitesi.urun;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Urun {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String baslik;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    private Double fiyat;

    @NotNull
    @Min(0)
    private Integer stok;

    @ManyToOne(optional = false)
    private Kategori kategori;

    private String gorselUrl;

    @Size(max = 2000)
    private String aciklama;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBaslik() { return baslik; }
    public void setBaslik(String baslik) { this.baslik = baslik; }
    public Double getFiyat() { return fiyat; }
    public void setFiyat(Double fiyat) { this.fiyat = fiyat; }
    public Integer getStok() { return stok; }
    public void setStok(Integer stok) { this.stok = stok; }
    public Kategori getKategori() { return kategori; }
    public void setKategori(Kategori kategori) { this.kategori = kategori; }
    public String getGorselUrl() { return gorselUrl; }
    public void setGorselUrl(String gorselUrl) { this.gorselUrl = gorselUrl; }
    public String getAciklama() { return aciklama; }
    public void setAciklama(String aciklama) { this.aciklama = aciklama; }
}

package com.ornek.teknolojisitesi.magaza;

import jakarta.persistence.*;

@Entity
@Table(name = "MAGAZALAR")
public class Magaza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String ad;

    private String adres;

    @Column(nullable = false)
    private double enlem;

    @Column(nullable = false)
    private double boylam;

    private String il;
    private String ilce;

    // 🔴 EKSİK OLAN ALANLAR (HATAYI ÇIKARANLAR)
    private String telefon;
    private String aciklama;

    public Magaza() {}

    // ===== GETTER / SETTER =====
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getAd() { return ad; }
    public void setAd(String ad) { this.ad = ad; }

    public String getAdres() { return adres; }
    public void setAdres(String adres) { this.adres = adres; }

    public double getEnlem() { return enlem; }
    public void setEnlem(double enlem) { this.enlem = enlem; }

    public double getBoylam() { return boylam; }
    public void setBoylam(double boylam) { this.boylam = boylam; }

    public String getIl() { return il; }
    public void setIl(String il) { this.il = il; }

    public String getIlce() { return ilce; }
    public void setIlce(String ilce) { this.ilce = ilce; }

    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = telefon; }

    public String getAciklama() { return aciklama; }
    public void setAciklama(String aciklama) { this.aciklama = aciklama; }
}

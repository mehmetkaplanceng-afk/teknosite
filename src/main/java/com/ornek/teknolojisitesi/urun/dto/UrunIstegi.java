package com.ornek.teknolojisitesi.urun.dto;

import jakarta.validation.constraints.*;

public class UrunIstegi {
	@NotBlank(message = "Başlık boş olamaz")
	private String baslik;

	@NotNull(message = "Fiyat boş olamaz")
	@DecimalMin(value = "0.01", message = "Fiyat 0'dan büyük olmal1")
	private Double fiyat; // sende Double ise BigDecimal yerine Double yaz

	@NotNull(message = "Stok boş olamaz")
	@Min(value = 0, message = "Stok 0'dan küçük olamaz")
	private Integer stok;

	@NotNull(message = "Kategori seçmelisiniz")
	private Long kategoriId;

	@Size(max = 255, message = "Gorsel URL en fazla 255 karakter olabilir")
	private String gorselUrl;

	@Size(max = 1000, message = "Açıklama en fazla 1000 karakter olabilir")
	private String aciklama;
    public String getBaslik() { return baslik; }
    public void setBaslik(String baslik) { this.baslik = baslik; }
    public Double getFiyat() { return fiyat; }
    public void setFiyat(Double fiyat) { this.fiyat = fiyat; }
    public Integer getStok() { return stok; }
    public void setStok(Integer stok) { this.stok = stok; }
    public Long getKategoriId() { return kategoriId; }
    public void setKategoriId(Long kategoriId) { this.kategoriId = kategoriId; }
    public String getGorselUrl() { return gorselUrl; }
    public void setGorselUrl(String gorselUrl) { this.gorselUrl = gorselUrl; }
    public String getAciklama() { return aciklama; }
    public void setAciklama(String aciklama) { this.aciklama = aciklama; }
}

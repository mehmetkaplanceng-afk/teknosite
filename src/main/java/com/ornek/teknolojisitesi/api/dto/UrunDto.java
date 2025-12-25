package com.ornek.teknolojisitesi.api.dto;

public record UrunDto(
        Long id,
        String baslik,
        Double fiyat,
        Integer stok,
        String gorselUrl,
        String aciklama,
        KategoriDto kategori
) {}

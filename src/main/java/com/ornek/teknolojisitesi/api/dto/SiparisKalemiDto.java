package com.ornek.teknolojisitesi.api.dto;

public record SiparisKalemiDto(
        Long urunId,
        String urunBaslik,
        Integer adet,
        Double birimFiyat
) {}

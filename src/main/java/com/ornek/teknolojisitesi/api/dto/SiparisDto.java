package com.ornek.teknolojisitesi.api.dto;

import java.time.LocalDateTime;
import java.util.List;

public record SiparisDto(
        Long id,
        LocalDateTime tarih,
        String kullaniciAdi,
        List<SiparisKalemiDto> kalemler,
        Double toplamTutar
) {}

package com.ornek.teknolojisitesi.api.dto;

public record MagazaDto(
        Integer id,
        String ad,
        String adres,
        double enlem,
        double boylam,
        String il,
        String ilce
) {}

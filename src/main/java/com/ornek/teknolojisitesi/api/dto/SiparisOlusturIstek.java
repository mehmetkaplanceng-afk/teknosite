package com.ornek.teknolojisitesi.api.dto;

import java.util.List;

public record SiparisOlusturIstek(List<Kalem> kalemler) {
    public record Kalem(Long urunId, Integer adet) {}
}

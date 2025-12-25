package com.ornek.teknolojisitesi.magaza;

import com.ornek.teknolojisitesi.api.DtoDonusturucu;
import com.ornek.teknolojisitesi.api.dto.MagazaDto;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/magazalar")
public class MagazaApiController {

    private final MagazaServisi servis;
    private final DtoDonusturucu dto;

    public MagazaApiController(MagazaServisi servis, DtoDonusturucu dto) {
        this.servis = servis;
        this.dto = dto;
    }

    @GetMapping
    public List<MagazaDto> liste() {
        return servis.tumMagazalar().stream().map(dto::magazaDto).toList();
    }
}

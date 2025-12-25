package com.ornek.teknolojisitesi.sohbet;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class SohbetMesajDenetleyici {

    private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("HH:mm");

    @MessageMapping("/sohbet.gonder")  // /app/sohbet.gonder
    @SendTo("/topic/sohbet")           // Dinleyen herkes /topic/sohbet
    public SohbetMesaji yeniMesaj(SohbetMesaji gelenMesaj, Principal principal) {

        String kullaniciAdi = gelenMesaj.getGonderen();

        // Kullanıcı login ise Spring Security'den al
        if (principal != null) {
            kullaniciAdi = principal.getName();
        }

        String zaman = LocalDateTime.now().format(FORMAT);

        return new SohbetMesaji(kullaniciAdi, gelenMesaj.getIcerik(), zaman);
    }
}

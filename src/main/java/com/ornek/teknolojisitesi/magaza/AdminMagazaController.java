package com.ornek.teknolojisitesi.magaza;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/magazalar")
public class AdminMagazaController {

    private final MagazaServisi servis;

    public AdminMagazaController(MagazaServisi servis) {
        this.servis = servis;
    }

    @GetMapping
    public String liste(Model model) {
        model.addAttribute("magazalar", servis.tumMagazalar());
        return "admin/magaza-list";
    }

    @GetMapping("/yeni")
    public String yeniForm(Model model) {
        model.addAttribute("magaza", new Magaza());
        return "admin/magaza-form";
    }

    @GetMapping("/duzenle/{id}")
    public String duzenle(@PathVariable Integer id, Model model) {
        model.addAttribute("magaza", servis.magazaBul(id));
        return "admin/magaza-form";
    }

    @PostMapping
    public String kaydet(@ModelAttribute Magaza magaza, RedirectAttributes ra) {
        servis.kaydet(magaza);
        ra.addFlashAttribute("mesaj", "Mağaza kaydedildi.");
        return "redirect:/admin/magazalar";
    }

    @PostMapping("/sil/{id}")
    public String sil(@PathVariable Integer id, RedirectAttributes ra) {
        servis.sil(id);
        ra.addFlashAttribute("mesaj", "Mağaza silindi.");
        return "redirect:/admin/magazalar";
    }
}

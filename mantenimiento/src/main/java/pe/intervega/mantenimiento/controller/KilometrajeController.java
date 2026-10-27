package pe.intervega.mantenimiento.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KilometrajeController {
    @GetMapping("/kilometraje")
    public String kilometraje() {
        return "kilometraje";
    }
}

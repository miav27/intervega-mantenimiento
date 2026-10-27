package pe.intervega.mantenimiento.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pe.intervega.mantenimiento.service.VehiculoService;

@Controller
public class VehiculoController {

    private final VehiculoService vehiculoService;

    public VehiculoController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    @GetMapping("/vehiculos")
    public String listar(Model model) {
        model.addAttribute("vehiculos", vehiculoService.listarTodos());
        return "vehiculos";
    }
}

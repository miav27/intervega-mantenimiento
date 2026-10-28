package pe.intervega.mantenimiento.controller;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.intervega.mantenimiento.model.Usuario;
import pe.intervega.mantenimiento.service.KilometrajeService;
import pe.intervega.mantenimiento.service.UsuarioService;
import pe.intervega.mantenimiento.service.AlertaService;

// Controlador del RF02: muestra y registra el kilometraje (solo recibe y delega al servicio)
@Controller
public class KilometrajeController {

    private final KilometrajeService kilometrajeService;
    private final UsuarioService usuarioService;
    private final AlertaService alertaService;

    public KilometrajeController(KilometrajeService kilometrajeService, UsuarioService usuarioService,
                                 AlertaService alertaService) {
        this.kilometrajeService = kilometrajeService;
        this.usuarioService = usuarioService;
        this.alertaService = alertaService;
    }



    // Muestra la pantalla con los registros recientes del taller del usuario
    @GetMapping("/kilometraje")
    public String mostrar(Authentication auth, Model model) {
        Usuario usuario = usuarioService.obtenerPorUsername(auth.getName());
        // idTaller null (Administrador General) = ve todos los talleres
        model.addAttribute("registros", kilometrajeService.listarRecientes(usuario.getIdTaller()));
        // Alertas pendientes del taller del usuario (null = todos los talleres)
        model.addAttribute("alertas", alertaService.listarPendientes(usuario.getIdTaller()));
        return "kilometraje";
    }

    // Recibe el formulario y registra el kilometraje
    @PostMapping("/kilometraje")
    public String registrar(@RequestParam String placa,
                            @RequestParam int kilometraje,
                            @RequestParam(required = false) String observacion,
                            Authentication auth,
                            RedirectAttributes mensajes) {
        Usuario usuario = usuarioService.obtenerPorUsername(auth.getName());
        try {
            kilometrajeService.registrar(placa, kilometraje, observacion,
                                         usuario.getId(), usuario.getIdTaller());
            mensajes.addFlashAttribute("exito", "Kilometraje registrado correctamente.");
        } catch (IllegalArgumentException | AccessDeniedException e) {
            // Mensajes claros del servicio: placa inexistente, km menor al actual, otro taller...
            mensajes.addFlashAttribute("error", e.getMessage());
        }
        // Redirige para que recargar la página no repita el registro
        return "redirect:/kilometraje";
    }
}
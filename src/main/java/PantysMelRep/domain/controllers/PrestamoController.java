package PantysMelRep.domain.controllers;

import PantysMelRep.domain.entities.Prestamo;
import PantysMelRep.persistencia.PrestamoDAO;
import PantysMelRep.domain.controllers.GestorPrestamos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/biblioteca")
public class PrestamoController {

    @Autowired
    private GestorPrestamos gestorPrestamos;
    @Autowired
    private PrestamoDAO prestamoDAO;

    @PostMapping("/realizarPrestamo")
    public String realizarPrestamo(@RequestParam("isbn_prestamo") String isbn,
                                   @RequestParam("idEjemplar_prestamo") String idEjemplar,
                                   @RequestParam("idUsuario_prestamo") String idUsuario,
                                   RedirectAttributes redirectAttributes) {

        // Realizar el préstamo usando el Gestor de Préstamos
        gestorPrestamos.realizarPrestamo(isbn, idEjemplar, idUsuario);

        // Redirigir a la página principal o a donde desees
        return "redirect:/";
    }

    @PostMapping("/realizarDevolucion")
    public String realizarDevolucion(@RequestParam("isbn_devolucion") String isbn,
                                     @RequestParam("idUsuario_devolucion") String idUsuario,
                                     RedirectAttributes redirectAttributes) {

        // Realizar la devolución usando el Gestor de Préstamos
        gestorPrestamos.realizarDevolucion(isbn, idUsuario);

        // Redirigir a la página principal o a donde desees
        return "redirect:/";
    }

    @PostMapping("/realizarReserva")
    public String realizarReserva(@RequestParam("idUsuario_reserva") String idUsuario,
                                  @RequestParam("isbn_reserva") String isbn,
                                  RedirectAttributes redirectAttributes) {

        // Realizar la reserva usando el Gestor de Préstamos
        gestorPrestamos.realizarReserva(idUsuario, isbn);

        // Redirigir a la página principal o a donde desees
        return "redirect:/";
    }
}

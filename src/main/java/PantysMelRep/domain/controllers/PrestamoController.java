package PantysMelRep.domain.controllers;

import PantysMelRep.domain.entities.Prestamo;
import PantysMelRep.persistencia.PrestamoDAO;
import PantysMelRep.domain.controllers.GestorPrestamos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/biblioteca")
public class PrestamoController {

    private static final Logger logPrestamo = LoggerFactory.getLogger(PrestamoController.class);

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
        logPrestamo.info("Prestamo realizado con éxito");
        redirectAttributes.addFlashAttribute("success", "Prestamo realizado con éxito");

        // Redirigir a la página principal o a donde desees
        return "redirect:/homea74f88ojk345";
    }

    @PostMapping("/realizarDevolucion")
    public String realizarDevolucion(@RequestParam("isbn_devolucion") String isbn,
                                     @RequestParam("idUsuario_devolucion") String idUsuario,
                                     RedirectAttributes redirectAttributes) {

        // Realizar la devolución usando el Gestor de Préstamos
        gestorPrestamos.realizarDevolucion(isbn, idUsuario);
        logPrestamo.info("Devolución realizada con éxito");
        redirectAttributes.addFlashAttribute("success", "Devolución realizada con éxito");
        // Redirigir a la página principal o a donde desees
        return "redirect:/homea74f88ojk345";
    }

    @PostMapping("/realizarReserva")
    public String realizarReserva(@RequestParam("idUsuario_reserva") String idUsuario,
                                  @RequestParam("isbn_reserva") String isbn,
                                  RedirectAttributes redirectAttributes) {

        // Realizar la reserva usando el Gestor de Préstamos
        gestorPrestamos.realizarReserva(idUsuario, isbn);
        logPrestamo.info("Reserva realizada con éxito");
        redirectAttributes.addFlashAttribute("success", "Reserva realizada con éxito");
        // Redirigir a la página principal o a donde desees
        return "redirect:/homea74f88ojk345";
    }
}

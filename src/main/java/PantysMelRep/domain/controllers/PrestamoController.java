/*
 * Nombre del archivo: PrestamoController.java
 * Descripción: Clase PrestamoController de la aplicación PantysMelRep.
 * Autor: Pan TyS Mel SA
 */
package PantysMelRep.domain.controllers;


import PantysMelRep.persistencia.PrestamoDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/prestamos")
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
        gestorPrestamos.realizarPrestamo(isbn, idEjemplar, idUsuario, redirectAttributes);
        logPrestamo.info("Prestamo realizado con éxito");
        redirectAttributes.addFlashAttribute("success", "Prestamo realizado con éxito");

        // Redirigir a la página principal o a donde desees
        return "redirect:/homeUsuario";
    }

    @PostMapping("/realizarDevolucion")
    public String realizarDevolucion(@RequestParam("isbn_devolucion") String isbn,
                                     @RequestParam("idUsuario_devolucion") String idUsuario,
                                     RedirectAttributes redirectAttributes) {

        // Realizar la devolución usando el Gestor de Préstamos
        gestorPrestamos.realizarDevolucion(isbn, idUsuario, redirectAttributes);
        logPrestamo.info("Devolución realizada con éxito");
        redirectAttributes.addFlashAttribute("success", "Devolución realizada con éxito");
        // Redirigir a la página principal o a donde desees
        return "redirect:/homeUsuario";
    }

    @PostMapping("/realizarReserva")
    public String realizarReserva(@RequestParam("idUsuario_reserva") String idUsuario,
                                  @RequestParam("isbn_reserva") String isbn,
                                  RedirectAttributes redirectAttributes) {

        // Realizar la reserva usando el Gestor de Préstamos
        gestorPrestamos.realizarReserva(idUsuario, isbn, redirectAttributes);
        logPrestamo.info("Reserva realizada con éxito");
        redirectAttributes.addFlashAttribute("success", "Reserva realizada con éxito");
        // Redirigir a la página principal o a donde desees
        return "redirect:/homeUsuario";
    }

    @GetMapping("/realizarPrestamo")
    public String mostrarFormulariorealizarPrestamo(Model model) {
        // Aquí puedes realizar lógica si es necesario antes de mostrar el formulario
        return "realizarPrestamo";
    }

    @GetMapping("/realizarDevolucion")
    public String mostrarFormulariorealizarDevolucion(Model model) {
        // Aquí puedes realizar lógica si es necesario antes de mostrar el formulario
        return "realizarDevolucion";
    }

    @GetMapping("/realizarReserva")
    public String mostrarFormulariorealizarReserva(Model model) {
        // Aquí puedes realizar lógica si es necesario antes de mostrar el formulario
        return "realizarReserva";
    }
}

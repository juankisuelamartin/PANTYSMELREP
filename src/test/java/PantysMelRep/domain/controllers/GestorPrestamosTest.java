package PantysMelRep.domain.controllers;

import PantysMelRep.domain.controllers.GestorPenalizaciones;
import PantysMelRep.domain.controllers.GestorPrestamos;
import PantysMelRep.domain.entities.*;
import PantysMelRep.persistencia.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GestorPrestamosTest {

    @InjectMocks
    private GestorPrestamos gestorPrestamos;

    @Mock
    private PrestamoDAO prestamoDAO;

    @Mock
    private ReservaDAO reservaDAO;

    @Mock
    private TituloDAO tituloDAO;

    @Mock
    private UsuarioDAO usuarioDAO;

    @Mock
    private EjemplarDAO ejemplarDAO;

    @Mock
    private GestorPenalizaciones gestorPenalizaciones;

    @Mock
    private RedirectAttributes redirectAttributes;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void realizarPrestamo_Success() {
        // Mock data
        String isbn = "123";
        String idEjemplar = "7";
        String idUsuario = "admin";

        // Mock behavior
        when(tituloDAO.findById(isbn)).thenReturn(Optional.of(new Titulo()));
        when(usuarioDAO.findById(idUsuario)).thenReturn(Optional.of(new Usuario()));
        when(ejemplarDAO.findById(idEjemplar)).thenReturn(Optional.of(new Ejemplar()));
        when(prestamoDAO.findById(any())).thenReturn(Optional.empty());
        when(gestorPenalizaciones.comprobarPenalizacion(any())).thenReturn(false);

        // Test
        gestorPrestamos.realizarPrestamo(isbn, idEjemplar, idUsuario, redirectAttributes);

        // Verify
        verify(prestamoDAO, times(1)).save(any());
        // Add more verification as needed
    }

    @Test
    void realizarPrestamo_UserHasPenalty() {
        // Mock data
        String isbn = "123";
        String idEjemplar = "7";
        String idUsuario = "PENALTY";

        // Mock behavior
        when(tituloDAO.findById(isbn)).thenReturn(Optional.of(new Titulo()));
        when(usuarioDAO.findById(idUsuario)).thenReturn(Optional.of(new Usuario()));
        when(ejemplarDAO.findById(idEjemplar)).thenReturn(Optional.of(new Ejemplar()));
        when(prestamoDAO.findById(any())).thenReturn(Optional.empty());
        when(gestorPenalizaciones.comprobarPenalizacion(any())).thenReturn(true);

        // Test
        gestorPrestamos.realizarPrestamo(isbn, idEjemplar, idUsuario, redirectAttributes);

        // Verify
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("error"), any());
        // Add more verification as needed
    }

    @Test
    void realizarDevolucion_Success() {
        // Mock data
        String isbn = "120603";
        String idUsuario = "admin";

        // Mock behavior
        when(prestamoDAO.findById(any())).thenReturn(Optional.of(new Prestamo()));
        doNothing().when(gestorPenalizaciones).aplicarPenalizacion(any(), any());

        // Test
        gestorPrestamos.realizarDevolucion(isbn, idUsuario, redirectAttributes);

        // Verify
        verify(prestamoDAO, times(1)).save(any());

    }

    @Test
    void realizarDevolucion_UserHasPenalty() {
        // Mock data
        String isbn = "6678";
        String idUsuario = "PENALTY";

        // Mock behavior
        Prestamo prestamo = new Prestamo();
        prestamo.setFechaFin(new Date()); // Assuming the fechaFin is in the past
        when(prestamoDAO.findById(any())).thenReturn(Optional.of(prestamo));

        Usuario usuario = new Usuario();
        usuario.setFechaFinPenalizacion(null); // Assuming the user has no active penalty
        doAnswer(invocation -> {
            Usuario u = invocation.getArgument(0);
            Date fechaFin = invocation.getArgument(1);
            gestorPenalizaciones.aplicarPenalizacion(u, fechaFin); // Call the actual method
            usuarioDAO.save(u); // Save the user after applying the penalty
            return null;
        }).when(gestorPenalizaciones).aplicarPenalizacion(any(), any());

        // Test
        gestorPrestamos.realizarDevolucion(isbn, idUsuario, redirectAttributes);

        // Verify
        verify(usuarioDAO, times(1)).save(any()); // Check that save method is invoked
        verify(gestorPenalizaciones, times(1)).aplicarPenalizacion(any(), any()); // Verify other interactions
        // Add more verification as needed
    }



    @Test
    void realizarReserva_Success() {
        // Mock data
        String isbn = "123456789";
        String idUsuario = "user123";

        // Mock behavior
        when(prestamoDAO.findById(any())).thenReturn(Optional.empty());
        when(reservaDAO.findById(any())).thenReturn(Optional.empty());
        when(usuarioDAO.findById(idUsuario)).thenReturn(Optional.of(new Usuario()));
        when(tituloDAO.findById(isbn)).thenReturn(Optional.of(new Titulo()));

        // Test
        gestorPrestamos.realizarReserva(idUsuario, isbn, redirectAttributes);

        // Verify
        verify(reservaDAO, times(1)).save(any());
        // Add more verification as needed
    }

    @Test
    void realizarReserva_UserHasActiveLoan() {
        // Mock data
        String isbn = "123";
        String idUsuario = "user";

        // Mock behavior
        when(prestamoDAO.findById(any())).thenReturn(Optional.of(new Prestamo()));
        when(reservaDAO.findById(any())).thenReturn(Optional.empty());
        when(usuarioDAO.findById(idUsuario)).thenReturn(Optional.of(new Usuario()));
        when(tituloDAO.findById(isbn)).thenReturn(Optional.of(new Titulo()));

        // Test
        gestorPrestamos.realizarReserva(idUsuario, isbn, redirectAttributes);

        // Verify
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("error"), any());
        // Add more verification as needed
    }

    @Test
    void realizarReserva_UserHasActiveReservation() {
        // Mock data
        String isbn = "123";
        String idUsuario = "admin";

        // Mock behavior
        when(prestamoDAO.findById(any())).thenReturn(Optional.empty());
        when(reservaDAO.findById(any())).thenReturn(Optional.of(new Reserva()));
        when(usuarioDAO.findById(idUsuario)).thenReturn(Optional.of(new Usuario()));
        when(tituloDAO.findById(isbn)).thenReturn(Optional.of(new Titulo()));

        // Test
        gestorPrestamos.realizarReserva(idUsuario, isbn, redirectAttributes);

        // Verify
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("error"), any());
        // Add more verification as needed
    }
}

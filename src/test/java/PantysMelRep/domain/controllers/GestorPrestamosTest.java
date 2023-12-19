package PantysMelRep.domain.controllers;

import PantysMelRep.domain.controllers.GestorPenalizaciones;
import PantysMelRep.domain.controllers.GestorPrestamos;
import PantysMelRep.domain.entities.Titulo;
import PantysMelRep.domain.entities.Usuario;
import PantysMelRep.domain.entities.Prestamo;
import PantysMelRep.domain.entities.Ejemplar;
import PantysMelRep.domain.entities.Reserva;
import PantysMelRep.persistencia.PrestamoDAO;
import PantysMelRep.persistencia.ReservaDAO;
import PantysMelRep.persistencia.TituloDAO;
import PantysMelRep.persistencia.UsuarioDAO;
import PantysMelRep.persistencia.EjemplarDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;

@ExtendWith(MockitoExtension.class)
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
        String isbn = "193";
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
        verify(redirectAttributes, times(1)).addFlashAttribute(any(), eq("Prestamo realizado"));
        // Add more verification as needed
    }

    @Test
    void realizarPrestamo_ExistenteActivo() {
        // Mock data
        String isbn = "193";
        String idEjemplar = "7";
        String idUsuario = "admin";
        Prestamo prestamoExistente= new Prestamo();
        prestamoExistente.setActivo(true);
        // Mock behavior
        when(tituloDAO.findById(isbn)).thenReturn(Optional.of(new Titulo()));
        when(usuarioDAO.findById(idUsuario)).thenReturn(Optional.of(new Usuario()));
        when(ejemplarDAO.findById(idEjemplar)).thenReturn(Optional.of(new Ejemplar()));
        when(prestamoDAO.findById(any())).thenReturn(Optional.of(prestamoExistente));
        when(gestorPenalizaciones.comprobarPenalizacion(any())).thenReturn(false);

        // Test
        gestorPrestamos.realizarPrestamo(isbn, idEjemplar, idUsuario, redirectAttributes);
        verify(prestamoDAO, never()).save(any(Prestamo.class));
        verify(redirectAttributes, times(1)).addFlashAttribute(any(), eq("El usuario ya tiene un préstamo activo de este título."));
        // Add more verification as needed
    }

    @Test
    void realizarPrestamo_ExistenteNoActivo() {
        // Mock data
        String isbn = "193";
        String idEjemplar = "7";
        String idUsuario = "admin";
        Prestamo prestamoExistente= new Prestamo();
        prestamoExistente.setActivo(false);
        // Mock behavior
        when(tituloDAO.findById(isbn)).thenReturn(Optional.of(new Titulo()));
        when(usuarioDAO.findById(idUsuario)).thenReturn(Optional.of(new Usuario()));
        when(ejemplarDAO.findById(idEjemplar)).thenReturn(Optional.of(new Ejemplar()));
        when(prestamoDAO.findById(any())).thenReturn(Optional.of(prestamoExistente));
        when(gestorPenalizaciones.comprobarPenalizacion(any())).thenReturn(false);

        // Test
        gestorPrestamos.realizarPrestamo(isbn, idEjemplar, idUsuario, redirectAttributes);

        verify(prestamoDAO, times(1)).save(any(Prestamo.class));
        verify(redirectAttributes, times(1)).addFlashAttribute(any(), eq("Prestamo realizado"));
        // Add more verification as needed
    }

    @Test
    void realizarPrestamo_UserHasPenalty() {
        // Mock data
        String isbn = "123";
        String idEjemplar = "7";
        String idUsuario = "admin";

        // Mock behavior
        when(tituloDAO.findById(isbn)).thenReturn(Optional.of(new Titulo()));
        when(usuarioDAO.findById(idUsuario)).thenReturn(Optional.of(new Usuario()));
        when(ejemplarDAO.findById(idEjemplar)).thenReturn(Optional.of(new Ejemplar()));
        when(prestamoDAO.findById(any())).thenReturn(Optional.empty());
        when(gestorPenalizaciones.comprobarPenalizacion(any())).thenReturn(true);
        // Test
        gestorPrestamos.realizarPrestamo(isbn, idEjemplar, idUsuario, redirectAttributes);

        verify(prestamoDAO, never()).save(any(Prestamo.class));
        // Verify
        verify(redirectAttributes, times(1)).addFlashAttribute(any(), eq("El usuario tiene una penalización activa."));
        // Add more verification as needed
    }



    @Test
    void testRealizarDevolucion() {
        // Mock data
        String isbn = "prueba";

        //Crear Prestamo prueba activo
        String idUsuario = "user123";
        Date fecha = new Date(2033 - 1900, Calendar.JANUARY,1);
        Prestamo prestamoExistente = new Prestamo();
        prestamoExistente.setActivo(true);
        prestamoExistente.setFechaFin(fecha);


        // Mock behavior
        when(prestamoDAO.findById(any()))
                .thenReturn(Optional.of(prestamoExistente));


        gestorPrestamos.realizarDevolucion(isbn, idUsuario, redirectAttributes);

        verify(prestamoDAO, times(1)).save(any(Prestamo.class));
        verify(gestorPenalizaciones, never()).aplicarPenalizacion(any(Usuario.class), any(Date.class));

        verify(redirectAttributes, times(1)).addFlashAttribute(any(), eq("El usuario ha devuelto el libro a tiempo."));


    }

    @Test
    void testRealizarDevolucionTarde() {
        // Mock data
        String isbn = "prueba";

        //Crear Prestamo prueba activo
        String idUsuario = "user123";
        Date fecha = new Date(2000 - 1900, Calendar.JANUARY,1);
        Prestamo prestamoExistente = new Prestamo();
        prestamoExistente.setActivo(true);
        prestamoExistente.setFechaFin(fecha);


        // Mock behavior
        when(prestamoDAO.findById(any()))
                .thenReturn(Optional.of(prestamoExistente));


        gestorPrestamos.realizarDevolucion(isbn, idUsuario, redirectAttributes);

        verify(prestamoDAO, times(1)).save(any(Prestamo.class));
        verify(gestorPenalizaciones, never()).aplicarPenalizacion(any(Usuario.class), any(Date.class));

        verify(redirectAttributes, times(1)).addFlashAttribute(any(), eq("El usuario ha devuelto el libro fuera de plazo."));


    }

    @Test
    void testRealizarDevolucionPrestamoNoActivo() {
        // Mock data
        String isbn = "prueba";

        //Crear Prestamo prueba activo
        String idUsuario = "user123";
        Date fecha = new Date(2033 - 1900, Calendar.JANUARY,1);
        Prestamo prestamoExistente = new Prestamo();
        prestamoExistente.setActivo(false);
        prestamoExistente.setFechaFin(fecha);


        // Mock behavior
        when(prestamoDAO.findById(any()))
                .thenReturn(Optional.of(prestamoExistente));


        gestorPrestamos.realizarDevolucion(isbn, idUsuario, redirectAttributes);

        verify(prestamoDAO, never()).save(any(Prestamo.class));
        verify(gestorPenalizaciones, never()).aplicarPenalizacion(any(Usuario.class), any(Date.class));

        verify(redirectAttributes, times(1)).addFlashAttribute(any(), eq("El usuario no tiene un préstamo activo de este título."));


    }


    @Test
    void testRealizarDevolucionNoPrestadoNunca() {
        // Mock data
        String isbn = "prueba";

        //Crear Prestamo prueba activo
        String idUsuario = "user123";
        Date fecha = new Date(2033 - 1900, Calendar.JANUARY,1);

        // Mock behavior
        when(prestamoDAO.findById(any()))
                .thenReturn(Optional.empty());


        gestorPrestamos.realizarDevolucion(isbn, idUsuario, redirectAttributes);

        verify(prestamoDAO, never()).save(any(Prestamo.class));
        verify(gestorPenalizaciones, never()).aplicarPenalizacion(any(Usuario.class), any(Date.class));

        verify(redirectAttributes, times(1)).addFlashAttribute(any(), eq("El usuario no tiene un préstamo activo de este título."));


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
        verify(redirectAttributes, times(1)).addFlashAttribute(any(), eq("Reserva realizada con éxito."));
    }

    @Test
    void realizarReserva_UserHasActiveLoan() {
        // Mock data
        String isbn = "235";
        String idUsuario = "user";

        Prestamo prestamo= new Prestamo();
        prestamo.setActivo(true);
        // Mock behavior
        when(prestamoDAO.findById(any())).thenReturn(Optional.of(prestamo));


        // Test
        gestorPrestamos.realizarReserva(idUsuario, isbn, redirectAttributes);

        // Verify
        verify(redirectAttributes, times(1)).addFlashAttribute(any(), eq("El usuario ya tiene un préstamo activo de este título. No se puede realizar una reserva."));
    }

    @Test
    void realizarReserva_UserHasActiveReservation() {
        // Mock data
        String isbn = "123";
        String idUsuario = "admin";

        // Mock behavior
        when(prestamoDAO.findById(any())).thenReturn(Optional.empty());
        when(reservaDAO.findById(any())).thenReturn(Optional.of(new Reserva()));


        // Test
        gestorPrestamos.realizarReserva(idUsuario, isbn, redirectAttributes);

        // Verify
        verify(redirectAttributes, times(1)).addFlashAttribute(any(), eq("El usuario ya tiene una reserva activa de este título."));

    }
}

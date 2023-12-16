package PantysMelRep.domain.controllers;

import PantysMelRep.domain.controllers.GestorPenalizaciones;
import PantysMelRep.domain.entities.Usuario;
import PantysMelRep.persistencia.UsuarioDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GestorPenalizacionesTest {

    @Mock
    private UsuarioDAO usuarioDAO;

    @InjectMocks
    private GestorPenalizaciones gestorPenalizaciones;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void aplicarPenalizacion() {
        Usuario usuario = new Usuario();
        Date fechaActual = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaActual);
        calendar.add(Calendar.DAY_OF_MONTH, 30);
        Date fechaFin = calendar.getTime();

        gestorPenalizaciones.aplicarPenalizacion(usuario, fechaFin);

        assertNotNull(usuario.getFechaFinPenalizacion());
        // Puedes agregar más aserciones según tus requisitos
    }

    @Test
    void comprobarPenalizacion_sinPenalizacion() {
        Usuario usuario = new Usuario();
        assertFalse(gestorPenalizaciones.comprobarPenalizacion(usuario));
        // Asegúrate de que el resultado es el esperado cuando no hay penalización
    }

    @Test
    void comprobarPenalizacion_conPenalizacion() {
        Usuario usuario = new Usuario();
        Date fechaActual = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaActual);
        calendar.add(Calendar.DAY_OF_MONTH, 30);
        Date fechaFinPenalizacion = calendar.getTime();
        usuario.setFechaFinPenalizacion(fechaFinPenalizacion);

        assertTrue(gestorPenalizaciones.comprobarPenalizacion(usuario));
        // Asegúrate de que el resultado es el esperado cuando hay penalización
    }
}

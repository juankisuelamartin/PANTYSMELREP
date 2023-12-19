package PantysMelRep.domain.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import PantysMelRep.domain.controllers.GestorPrestamos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ExtendWith(MockitoExtension.class)
public class PrestamosControllerTest {

    @Mock
    private GestorPrestamos mockGestor;

    @Mock
    private RedirectAttributes mockRedirectAttributes;

    @Mock
    private Model mockModel;

    @InjectMocks
    private PrestamoController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRealizarPrestamo() {
        String result = controller.realizarPrestamo("isbn123", "ejemplar1", "usuario1", mockRedirectAttributes);

        verify(mockGestor).realizarPrestamo("isbn123", "ejemplar1", "usuario1", mockRedirectAttributes);
        assertEquals("redirect:/homeUsuario", result);
    }

    @Test
    public void testRealizarDevolucion() {
        String result = controller.realizarDevolucion("isbn123", "usuario1", mockRedirectAttributes);

        verify(mockGestor).realizarDevolucion("isbn123", "usuario1", mockRedirectAttributes);
        assertEquals("redirect:/homeUsuario", result);
    }

    @Test
    public void testRealizarReserva() {
        String result = controller.realizarReserva("usuario1", "isbn123", mockRedirectAttributes);

        verify(mockGestor).realizarReserva("usuario1", "isbn123", mockRedirectAttributes);
        assertEquals("redirect:/homeUsuario", result);
    }


    @Test
    public void testMostrarFormularioRealizarPrestamo() {
        PrestamoController controller = new PrestamoController();
        Model mockModel = mock(Model.class);

        String viewName = controller.mostrarFormulariorealizarPrestamo(mockModel);

        assertEquals("realizarPrestamo", viewName);
    }
    @Test
    public void testMostrarFormulariorealizarDevolucion() {
        PrestamoController controller = new PrestamoController();
        Model mockModel = mock(Model.class);

        String viewName = controller.mostrarFormulariorealizarDevolucion(mockModel);

        assertEquals("realizarDevolucion", viewName);
    }
    @Test
    public void testMostrarFormularioRealizarReserva() {
        PrestamoController controller = new PrestamoController();
        Model mockModel = mock(Model.class);

        String viewName = controller.mostrarFormulariorealizarReserva(mockModel);

        assertEquals("realizarReserva", viewName);
    }

}
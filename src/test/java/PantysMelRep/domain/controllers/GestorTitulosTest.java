
package PantysMelRep.domain.controllers;


import PantysMelRep.domain.entities.*;
import PantysMelRep.persistencia.EjemplarDAO;
import PantysMelRep.persistencia.PrestamoDAO;
import PantysMelRep.persistencia.TituloDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GestorTitulosTest {

    @InjectMocks
    private GestorTitulos gestorTitulos;

    @Mock
    private TituloDAO tituloDAO;

    @Mock
    private EjemplarDAO ejemplarDAO;

    @Mock
    private PrestamoDAO prestamoDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testAltaTitulo() {
        // Mock data
        String titulo = "Libro de Prueba";
        String isbn = "123456789";
        Collection<Autor> autores = new ArrayList<>();
        byte[] fotoBytes = new byte[100];

        // Mock objects
        Titulo nuevoTitulo = new Libro();
        nuevoTitulo.setTitulo(titulo);
        nuevoTitulo.setIsbn(isbn);
        nuevoTitulo.setFoto(fotoBytes);

        // Mock behavior
        when(tituloDAO.save(any(Titulo.class))).thenReturn(nuevoTitulo);

        // Test
        Titulo resultado = gestorTitulos.altaTitulo(titulo, isbn, autores, 1, fotoBytes);

        // Verify
        assertNotNull(resultado);
        assertEquals(nuevoTitulo, resultado);
        verify(tituloDAO, times(1)).save(any(Titulo.class));
    }

    @Test
    void testAltaTituloExceptionHandling() {
        // Mock data
        String titulo = "Libro de Prueba";
        String isbn = "123456789";
        Collection<Autor> autores = new ArrayList<>();
        byte[] fotoBytes = new byte[100];

        // Mock behavior
        when(tituloDAO.save(any(Titulo.class))).thenThrow(new DataIntegrityViolationException("Duplicate entry"));

        // Test
        Titulo resultado = gestorTitulos.altaTitulo(titulo, isbn, autores, 1, fotoBytes);

        // Verify
        assertNull(resultado);
        verify(tituloDAO, times(1)).save(any(Titulo.class));
    }

    @Test
    void testBorrarTitulo() {
        // Mock data
        String isbn = "123456789";

        // Mock behavior
        when(tituloDAO.findById(isbn)).thenReturn(java.util.Optional.of(new Libro()));

        // Test
        assertDoesNotThrow(() -> gestorTitulos.borrarTitulo(isbn));

        // Verify
        verify(tituloDAO, times(1)).delete(any(Titulo.class));
    }

    @Test
    void testAltaEjemplar() {
        // Mock data
        String isbn = "123456789";

        // Mock behavior
        when(tituloDAO.findById(isbn)).thenReturn(java.util.Optional.of(new Libro()));
        when(ejemplarDAO.save(any(Ejemplar.class))).thenReturn(new Ejemplar());

        // Test
        RedirectAttributesMock redirectAttributes = new RedirectAttributesMock();
        gestorTitulos.altaEjemplar(isbn, redirectAttributes);

        // Verify
        assertTrue(redirectAttributes.containsAttribute("success"));
        verify(ejemplarDAO, times(1)).save(any(Ejemplar.class));
    }

    @Test
    void testAltaEjemplarExceptionHandling() {
        // Mock data
        String isbn = "123456789";

        // Mock behavior
        when(tituloDAO.findById(isbn)).thenReturn(java.util.Optional.empty());

        // Test
        RedirectAttributesMock redirectAttributes = new RedirectAttributesMock();
        gestorTitulos.altaEjemplar(isbn, redirectAttributes);

        // Verify
        assertTrue(redirectAttributes.containsAttribute("error"));
        verify(ejemplarDAO, never()).save(any(Ejemplar.class));
    }

    @Test
    void testBajaEjemplar() {
        // Mock data
        String id = "1";

        // Mock behavior
        when(ejemplarDAO.findById(id)).thenReturn(java.util.Optional.of(new Ejemplar()));
        when(prestamoDAO.findByejemplarId(Long.parseLong(id))).thenReturn(java.util.Optional.of(new Prestamo()));

        // Test
        RedirectAttributesMock redirectAttributes = new RedirectAttributesMock();
        gestorTitulos.bajaEjemplar(id, redirectAttributes);

        // Verify
        assertTrue(redirectAttributes.containsAttribute("success"));
        verify(prestamoDAO, times(1)).delete(any(Prestamo.class));
        verify(ejemplarDAO, times(1)).delete(any(Ejemplar.class));
    }

    @Test
    void testBajaEjemplarNotPrestado() {
        // Mock data
        String id = "1";

        // Mock behavior
        when(ejemplarDAO.findById(id)).thenReturn(java.util.Optional.of(new Ejemplar()));
        when(prestamoDAO.findByejemplarId(Long.parseLong(id))).thenReturn(java.util.Optional.empty());

        // Test
        RedirectAttributesMock redirectAttributes = new RedirectAttributesMock();
        gestorTitulos.bajaEjemplar(id, redirectAttributes);

        // Verify
        assertTrue(redirectAttributes.containsAttribute("success"));
        verify(prestamoDAO, never()).delete(any(Prestamo.class));
        verify(ejemplarDAO, times(1)).delete(any(Ejemplar.class));
    }

    @Test
    void testBajaEjemplarNotFound() {
        // Mock data
        String id = "1";

        // Mock behavior
        when(ejemplarDAO.findById(id)).thenReturn(java.util.Optional.empty());

        // Test
        RedirectAttributesMock redirectAttributes = new RedirectAttributesMock();
        gestorTitulos.bajaEjemplar(id, redirectAttributes);

        // Verify
        assertTrue(redirectAttributes.containsAttribute("error"));
        verify(prestamoDAO, never()).delete(any(Prestamo.class));
        verify(ejemplarDAO, never()).delete(any(Ejemplar.class));
    }
}

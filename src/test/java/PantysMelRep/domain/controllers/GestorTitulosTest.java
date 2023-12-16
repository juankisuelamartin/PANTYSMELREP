
package PantysMelRep.domain.controllers;


import PantysMelRep.domain.entities.Autor;
import PantysMelRep.domain.entities.Titulo;
import PantysMelRep.domain.entities.Libro;
import PantysMelRep.domain.entities.Ejemplar;
import PantysMelRep.domain.entities.Prestamo;

import PantysMelRep.persistencia.AutorDAO;
import PantysMelRep.persistencia.EjemplarDAO;
import PantysMelRep.persistencia.PrestamoDAO;
import PantysMelRep.persistencia.TituloDAO;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;


class GestorTitulosTest {

    RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

    @InjectMocks
    private GestorTitulos gestorTitulos;

    @InjectMocks
    private TituloController tituloController;

    @Mock
    private TituloDAO tituloDAO;

    @Mock
    private AutorDAO autorDAO;

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
    void testAltaTitulo() throws Exception {
        // Mock data
        String isbn = "12332567575";
        String titulo = "Nuevo Libro";
        String nuevosAutores = "Autor 1, Autor 2";
        int DType = 1;
        byte[] fotoBytes = new byte[0];

        // Convertir la cadena de texto en una Collection<Autor>
        String[] nombresAutores = nuevosAutores.split(", ");
        Collection<Autor> autores = new ArrayList<>();
        for (String nombreCompleto : nombresAutores) {
            String[] partes = nombreCompleto.split(" ");
            String nombre = partes[0];
            String apellido = partes[1];
            autores.add(new Autor(nombre, apellido));
        }

        // Crear un mock de RedirectAttributes
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

        // Mock behavior
        when(tituloDAO.findById(isbn)).thenReturn(Optional.empty());
        when(tituloDAO.save(any(Titulo.class))).thenReturn(new Libro());

        // Test
        Titulo result = gestorTitulos.altaTitulo(titulo, isbn, autores, DType, fotoBytes, redirectAttributes);

        // Simular redireccion para que el comportamiento de los atributos flash sea el esperado.
        MockHttpServletResponse response = new MockHttpServletResponse();
        for (Map.Entry<String, ?> entry : redirectAttributes.getFlashAttributes().entrySet()) {
            response.addCookie(new Cookie(entry.getKey(), entry.getValue().toString()));
        }

        // Verify
        assertTrue(response.getCookie("success").getValue().equals("El título ha sido dado de alta con éxito"));
        verify(tituloDAO, times(1)).findById(isbn);
        verify(tituloDAO, times(1)).save(any(Titulo.class));
    }


    @Test
    void procesarAutores_CasoValido() {
        // Arrange
        String nuevosAutores = "John Doe, Jane Smith";
        RedirectAttributes redirectAttributes = Mockito.mock(RedirectAttributes.class);

        // Configurar el comportamiento simulado del autorDAO
        Autor autor1 = new Autor("John", "Doe");
        Autor autor2 = new Autor("Jane", "Smith");
        when(autorDAO.findById(any())).thenReturn(Optional.of(autor1), Optional.of(autor2));
        // Act
        List<Autor> listaAutores = tituloController.procesarAutores(nuevosAutores, redirectAttributes);

        // Assert
        assertEquals(2, listaAutores.size());
        // Agrega más aserciones según sea necesario para verificar que los autores se hayan procesado correctamente.
    }

    @Test
    void procesarAutores_NombreAutorNoValido() {
        // Arrange
        String nuevosAutores = "John"; // Nombre sin apellido, lo cual debería generar un error
        RedirectAttributes redirectAttributes = Mockito.mock(RedirectAttributes.class);

        // Act
        List<Autor> listaAutores = tituloController.procesarAutores(nuevosAutores, redirectAttributes);

        // Assert
        assertNull(listaAutores); // Esperamos que retorne null en caso de error
        // Agrega aserciones adicionales para verificar que se haya registrado un error en RedirectAttributes.
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
        Titulo resultado = gestorTitulos.altaTitulo(titulo, isbn, autores, 1, fotoBytes, redirectAttributes);

        // Verify
        assertNull(resultado);
        verify(tituloDAO, times(1)).save(any(Titulo.class));
    }

    @Test
    void testBorrarTitulo() {
        // Mock data
        String isbn = "123";

        // Mock behavior
        when(tituloDAO.findById(isbn)).thenReturn(java.util.Optional.of(new Libro()));

        // Test
        try {
            gestorTitulos.borrarTitulo(isbn, redirectAttributes);
        } catch (RuntimeException e) {
            // Handle the exception
            System.out.println("Caught expected exception: " + e.getMessage());
            assertTrue(e.getMessage().equals("INFO: Título borrado exitosamente"));
        }

        // Verify
        verify(tituloDAO, times(1)).delete(any(Titulo.class));
    }



    @Test
    void testAltaEjemplarWithExistingIsbn() throws Exception {
        // Mock data
        String isbn = "235";
        Titulo existingTitulo = new Titulo();
        existingTitulo.setIsbn(isbn);

        // Mock behavior
        when(tituloDAO.findById(isbn)).thenReturn(java.util.Optional.of(existingTitulo));
        when(ejemplarDAO.save(any(Ejemplar.class))).thenReturn(new Ejemplar());

        // Test
        MockHttpServletResponse response = new MockHttpServletResponse();
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        gestorTitulos.altaEjemplar(isbn, redirectAttributes);

        // Simular redireccion para que el comportamiento de los atributos flash sea el esperado.
        for (Map.Entry<String, ?> entry : redirectAttributes.getFlashAttributes().entrySet()) {
            response.addCookie(new Cookie(entry.getKey(), entry.getValue().toString()));
        }

        // Verify
        assertTrue(response.getCookie("success").getValue().equals("Ejemplar dado de alta con éxito"));
        verify(ejemplarDAO, times(1)).save(any(Ejemplar.class));
    }


    @Test
    void testAltaEjemplarExceptionHandling() throws Exception {
        // Mock data
        String isbn = "123456789";

        // Mock behavior
        when(tituloDAO.findById(isbn)).thenReturn(java.util.Optional.empty());

        // Test
        MockHttpServletResponse response = new MockHttpServletResponse();
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

        try {
            gestorTitulos.altaEjemplar(isbn, redirectAttributes);
        } catch (RuntimeException e) {
            // Handle the exception
            System.out.println("Caught expected exception: " + e.getMessage());
        }

        // Simulate a redirection
        for (Map.Entry<String, ?> entry : redirectAttributes.getFlashAttributes().entrySet()) {
            response.addCookie(new Cookie(entry.getKey(), entry.getValue().toString()));
        }

        // Verify
        assertTrue(response.getCookie("error").getValue().equals("ERROR: Título del ejemplar no encontrado"));
        verify(ejemplarDAO, never()).save(any(Ejemplar.class));
    }


    @Test
    void testBajaEjemplarNotFound() {
        // Mock data
        String id = "234235345345634";

        // Mock behavior
        when(ejemplarDAO.findById(id)).thenReturn(java.util.Optional.empty());

        // Test
        try {
            gestorTitulos.bajaEjemplar(id, redirectAttributes);
            fail("Expected an RuntimeException to be thrown");
        } catch (RuntimeException e) {
            // Handle the exception
            System.out.println("Caught expected exception: " + e.getMessage());
            assertTrue(e.getMessage().equals("Ejemplar no encontrado"));
        }

        // Verify
        verify(prestamoDAO, never()).delete(any(Prestamo.class));
        verify(ejemplarDAO, never()).delete(any(Ejemplar.class));
    }



    @Test
    void testBajaEjemplarEjemplarPrestado() throws Exception {
        // Mock data
        String id = "22";

        // Mock behavior
        when(ejemplarDAO.findById(id)).thenReturn(java.util.Optional.of(new Ejemplar()));
        Prestamo prestamo = new Prestamo();
        prestamo.setActivo(true);
        when(prestamoDAO.findByejemplarId(Long.parseLong(id))).thenReturn(java.util.Optional.of(prestamo));

        // Test
        MockHttpServletResponse response = new MockHttpServletResponse();
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        gestorTitulos.bajaEjemplar(id, redirectAttributes);

        // Simulate a redirection
        for (Map.Entry<String, ?> entry : redirectAttributes.getFlashAttributes().entrySet()) {
            response.addCookie(new Cookie(entry.getKey(), entry.getValue().toString()));
        }

        // Verify
        Cookie errorCookie = response.getCookie("error");
        if (errorCookie != null) {
            assertTrue(errorCookie.getValue().equals("El ejemplar no se puede borrar porque está prestado"));
        } else {
            fail("Error cookie not found");
        }
        verify(prestamoDAO, never()).delete(any(Prestamo.class));
        verify(ejemplarDAO, never()).delete(any(Ejemplar.class));
    }

    @Test
    void testBajaEjemplarEjemplarNotPrestado() throws Exception {
        // Mock data
        String id = "5";

        // Mock behavior
        when(ejemplarDAO.findById(id)).thenReturn(java.util.Optional.of(new Ejemplar()));
        when(prestamoDAO.findByejemplarId(Long.parseLong(id))).thenReturn(java.util.Optional.empty());

        // Test
        MockHttpServletResponse response = new MockHttpServletResponse();
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        gestorTitulos.bajaEjemplar(id, redirectAttributes);

        // Simulate a redirection
        for (Map.Entry<String, ?> entry : redirectAttributes.getFlashAttributes().entrySet()) {
            response.addCookie(new Cookie(entry.getKey(), entry.getValue().toString()));
        }

        // Verify
        Cookie successCookie = response.getCookie("success");
        if (successCookie != null) {
            assertTrue(successCookie.getValue().equals("Ejemplar borrado."));
        } else {
            fail("Success cookie not found");
        }
        verify(prestamoDAO, never()).delete(any(Prestamo.class));
        verify(ejemplarDAO, times(1)).delete(any(Ejemplar.class));
    }



}

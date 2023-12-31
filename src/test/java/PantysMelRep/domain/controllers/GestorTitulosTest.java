
package PantysMelRep.domain.controllers;


import PantysMelRep.domain.entities.Autor;
import PantysMelRep.domain.entities.Ejemplar;
import PantysMelRep.domain.entities.Libro;
import PantysMelRep.domain.entities.Revista;
import PantysMelRep.domain.entities.Titulo;
import PantysMelRep.domain.entities.Prestamo;

import PantysMelRep.persistencia.AutorDAO;
import PantysMelRep.persistencia.EjemplarDAO;
import PantysMelRep.persistencia.PrestamoDAO;
import PantysMelRep.persistencia.TituloDAO;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import java.io.IOException;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.mock;



@ExtendWith(MockitoExtension.class)
class GestorTitulosTest {

    @Mock
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
    void testAltaTituloLibro() throws Exception {
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
        assertTrue(result instanceof Libro);
    }


    @Test
    void testAltaTituloRevista() throws Exception {
        // Mock data
        String isbn = "12332567575";
        String titulo = "Nuevo Libro";
        String nuevosAutores = "Autor 1, Autor 2";
        int DType = 2;
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
        // Verificar específicamente que el resultado es una instancia de Libro
        assertTrue(result instanceof Revista);

    }


    @Test
    void procesarAutores_CasoValido() {
        // Arrange
        String nuevosAutores = "John Doe, Jane Smith";
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

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
        String nuevosAutores = ""; // Nombre sin apellido, lo cual debería generar un error
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Act
        List<Autor> listaAutores = tituloController.procesarAutores(nuevosAutores, redirectAttributes);

        // Assert
        assertNull(listaAutores); // Esperamos que retorne null en caso de error
        // Agrega aserciones adicionales para verificar que se haya registrado un error en RedirectAttributes.
    }

    @Test
    void testAltaTituloExistente() {
        // Datos de prueba
        String titulo = "Libro de Prueba";
        String isbn = "123456789";
        Collection<Autor> autores = new ArrayList<>();
        byte[] fotoBytes = new byte[100];

        // Comportamiento simulado del DAO
        when(tituloDAO.findById(isbn)).thenReturn(Optional.of(new Titulo()));

        // Test
        Titulo resultado = gestorTitulos.altaTitulo(titulo, isbn, autores, 1, fotoBytes, redirectAttributes);

        // Verificación
        verify(redirectAttributes, times(1)).addFlashAttribute(any(), eq("Error: El ISBN ya existe en la Base de Datos."));

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
    void testBajaEjemplarEjemplarNotPrestadoNunca() throws Exception {
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


    @Test
    public void testBajaEjemplarConPrestamoInactivo() {
        // Configurar el objeto de prueba
        String ejemplarId = "1";
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Configurar el ejemplar
        Ejemplar ejemplar = new Ejemplar();
        ejemplar.setId(Long.parseLong(ejemplarId));

        // Configurar el préstamo inactivo
        Prestamo prestamoInactivo = new Prestamo();
        prestamoInactivo.setActivo(false);

        // Configurar el resultado esperado cuando se busque el ejemplar por ID
        when(ejemplarDAO.findById(ejemplarId)).thenReturn(Optional.of(ejemplar));

        // Configurar el resultado esperado cuando se busque el préstamo por ID
        when(prestamoDAO.findByejemplarId(Long.parseLong(ejemplarId))).thenReturn(Optional.of(prestamoInactivo));

        // Llamar al método que se está probando
        gestorTitulos.bajaEjemplar(ejemplarId, redirectAttributes);

        // Verificar que se haya llamado el método delete del prestamoDAO
        verify(prestamoDAO).delete(prestamoInactivo);

        // Verificar que se haya llamado el método delete del ejemplarDAO
        verify(ejemplarDAO).delete(ejemplar);

        // Verificar el mensaje de éxito en los atributos de redirección
        verify(redirectAttributes).addFlashAttribute("success", "Ejemplar borrado. y prestamo inactivo borrado.");
    }

    @Test
    void anadirFoto_ConFotoValida() throws IOException {
        MockMultipartFile foto = new MockMultipartFile("foto", "foto.jpg", "image/jpeg", "test content".getBytes());
        byte[] fotoBytes = tituloController.anadirFoto(foto, redirectAttributes);
        assertNotNull(fotoBytes);
        assertEquals("test content", new String(fotoBytes));
    }

    @Test
    void anadirFoto_SinFotoYCargaImagenPorDefecto() throws IOException {
        MockMultipartFile foto = new MockMultipartFile("foto", "", "image/jpeg", new byte[0]);

        byte[] fotoBytes = tituloController.anadirFoto(foto, redirectAttributes);
        assertNotNull(fotoBytes);
        assertTrue(fotoBytes.length > 0);
    }


    @Test
    void anadirFoto_ConErrorAlLeerFoto() throws IOException {
        // Subclase de MockMultipartFile que lanza IOException
        MockMultipartFile mockFoto = new MockMultipartFile("foto", "test.jpg", "image/jpeg", "test content".getBytes()) {
            @Override
            public byte[] getBytes() throws IOException {
                throw new IOException("Simulated read error");
            }
        };

        RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

        byte[] resultado = tituloController.anadirFoto(mockFoto, redirectAttributes);

        // Verificar que el resultado es null y se agregó un mensaje de error a redirectAttributes
        assertNull(resultado);
        assertEquals("Error al leer la foto. Por favor, inténtalo de nuevo.", redirectAttributes.getFlashAttributes().get("error"));
    }

}

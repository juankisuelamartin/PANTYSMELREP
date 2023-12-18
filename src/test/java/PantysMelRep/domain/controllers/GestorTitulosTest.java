
package PantysMelRep.domain.controllers;


import PantysMelRep.domain.entities.*;

import PantysMelRep.persistencia.AutorDAO;
import PantysMelRep.persistencia.EjemplarDAO;
import PantysMelRep.persistencia.PrestamoDAO;
import PantysMelRep.persistencia.TituloDAO;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class GestorTitulosTest {


    private Model mockModel;
    @Mock
    RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

    @InjectMocks
    private GestorTitulos gestorTitulos;
    @Mock
    private GestorTitulos gestorTituloMock;

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
    @Test
    public void testMostrarFormularioAltaTitulo() {
        String view = tituloController.mostrarFormularioAltaTitulo();
        assertEquals("altaTitulo", view);
    }

    @Test
    public void testMostrarFormularioActualizarTitulo() {
        String view = tituloController.mostrarFormularioActualizarTitulo(mockModel);
        assertEquals("actualizarTitulo", view);
    }

    @Test
    public void testMostrarFormularioBorrarTitulo() {
        String view = tituloController.mostrarFormularioBorrarTitulo(mockModel);
        assertEquals("borrarTitulo", view);
    }

    @Test
    public void testMostrarFormularioAltaEjemplar() {
        String view = tituloController.mostrarFormularioAltaEjemplar(mockModel);
        assertEquals("altaEjemplar", view);
    }

    @Test
    public void testMostrarFormularioBajaEjemplar() {
        String view = tituloController.mostrarFormularioBajaEjemplar(mockModel);
        assertEquals("bajaEjemplar", view);
    }
    @Test
    public void bajaEjemplarCasoExitoso() throws InterruptedException {
        // Configura los mocks y datos de prueba
        Titulo titulo = new Titulo(); // Asegúrate de crear un Titulo válido
        titulo.setIsbn("isbn_test"); // Establece datos de prueba para el Titulo
        Ejemplar ejemplar = new Ejemplar();
        ejemplar.setTitulo(titulo);

        when(ejemplarDAO.findById(anyString())).thenReturn(Optional.of(ejemplar));
        when(ejemplarDAO.contarEjemplaresConMismoTitulo(any(Titulo.class))).thenReturn(2L);

        // Llama al método
        String resultado = tituloController.bajaEjemplar("id_ejemplar", redirectAttributes);

        // Verifica comportamientos y resultados
        verify(gestorTituloMock).bajaEjemplar("id_ejemplar", redirectAttributes);
        assertEquals("redirect:/home", resultado);
    }

    @Test
    public void bajaEjemplarCasoFracaso() throws InterruptedException {
        // Configura los mocks y datos de prueba
        Titulo titulo = new Titulo();
        Ejemplar ejemplar = new Ejemplar();
        ejemplar.setTitulo(titulo);

        when(ejemplarDAO.findById(anyString())).thenReturn(Optional.of(ejemplar));
        when(ejemplarDAO.contarEjemplaresConMismoTitulo(any(Titulo.class))).thenReturn(1L);

        // Llama al método
        String resultado = tituloController.bajaEjemplar("id_ejemplar", redirectAttributes);

        // Verifica comportamientos y resultados
        verify(gestorTituloMock, never()).bajaEjemplar(anyString(), any(RedirectAttributes.class));
        assertEquals("redirect:/home", resultado);
    }

    @Test
    public void bajaEjemplarCasoExcepcion() throws InterruptedException {
        // Configura los mocks para lanzar una excepción
        when(ejemplarDAO.findById(anyString())).thenThrow(new RuntimeException("Ejemplar no encontrado"));

        // Llama al método
        String resultado = tituloController.bajaEjemplar("id_ejemplar", redirectAttributes);

        // Verifica comportamientos y resultados
        verify(gestorTituloMock, never()).bajaEjemplar(anyString(), any(RedirectAttributes.class));
        assertEquals("redirect:/home", resultado);
    }




/**
    @Test
    public void anadirFoto_WhenDefaultImageNotFound_ShouldThrowException() throws IOException {
        // Arrange
        MockMultipartFile foto = null;

        // Mockear el comportamiento del getResourceAsStream para la imagen por defecto
        // Configurar el mock para devolver null cuando se llame a getResourceAsStream
        ClassLoader classLoaderMock = mock(ClassLoader.class);
        Thread threadMock = mock(Thread.class);
        when(threadMock.getContextClassLoader()).thenReturn(classLoaderMock);
        when(classLoaderMock.getResourceAsStream("static/images/default_Portada.jpg")).thenReturn(null);

        // Act y Assert
        assertThrows(IOException.class, () -> {
            tituloController.anadirFoto(foto, redirectAttributes);
        });

        verify(redirectAttributes).addFlashAttribute(eq("error"), anyString());
    }
**/
}

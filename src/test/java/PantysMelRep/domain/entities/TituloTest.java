package PantysMelRep.domain.entities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class TituloTest {

    private Titulo titulo;
    private AutorId autor1;
    private AutorId autor2;

    @BeforeEach
    void setUp() {
        Autor autor1 = new Autor();
        autor1.getId().setNombre("Autor 1");
        Autor autor2 = new Autor();
        autor2.getId().setNombre("Autor 2");

        Titulo titulo = new Titulo();

        Collection<Autor> autores = new ArrayList<>();
        autores.add(autor1);
        autores.add(autor2);
        titulo.setAutores(autores);

        Ejemplar ejemplar1 = new Ejemplar();
        ejemplar1.setId(57777L);

        Ejemplar ejemplar2 = new Ejemplar();
        ejemplar2.setId(5L);

        Collection<Ejemplar> ejemplares = new ArrayList<>();
        ejemplares.add(ejemplar1);
        ejemplares.add(ejemplar2);

        titulo.setEjemplares(ejemplares);

        byte[] foto= new byte[6767];
        titulo.setFoto(foto);

        Prestamo prestamo1 = new Prestamo();
        prestamo1.setTitulo(titulo);

        Prestamo prestamo2 = new Prestamo();
        prestamo2.setTitulo(titulo);

        Collection<Prestamo> prestamos= new ArrayList<>();
        prestamos.add(prestamo1);
        prestamos.add(prestamo2);

        titulo.setPrestamos(prestamos);


        //titulo.setReservas(1);
        titulo.setTitulo("Título de prueba");
        titulo.setIsbn("123-456-789");
        titulo.setNumReserva("3");
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void testGetAutores() {
        Collection<Autor> autores = titulo.getAutores();
        assertNotNull(autores);
        assertEquals(2, autores.size());
    }

    @Test
    void testSetAutores() {
        Autor autor3 = new Autor();
        autor3.getId().setNombre("Autor 3");

        Collection<Autor> autores = new ArrayList<>();
        autores.add(autor3);

        titulo.setAutores(autores);

        assertEquals(autores, titulo.getAutores());
    }

    @Test
    void testGetEjemplares() {
        Collection<Ejemplar> ejemplares = titulo.getEjemplares();
        assertNotNull(ejemplares);
        assertEquals(2, ejemplares.size());
    }

    @Test
    void testSetEjemplares() {
        Ejemplar ejemplar3 = new Ejemplar();
        ejemplar3.setId(10L);

        Collection<Ejemplar> ejemplares = new ArrayList<>();
        ejemplares.add(ejemplar3);

        titulo.setEjemplares(ejemplares);

        assertEquals(ejemplares, titulo.getEjemplares());
    }

    @Test
    void testGetFoto() {
        byte[] foto = titulo.getFoto();
        assertNotNull(foto);
        assertEquals(6767, foto.length);
    }

    @Test
    void testSetFoto() {
        byte[] newFoto = new byte[10000];
        titulo.setFoto(newFoto);

        assertEquals(newFoto, titulo.getFoto());
    }

    @Test
    void testGetPrestamos() {
        Collection<Prestamo> prestamos = titulo.getPrestamos();
        assertNotNull(prestamos);
        assertEquals(2, prestamos.size());
    }

    @Test
    void testSetPrestamos() {
        Prestamo prestamo3 = new Prestamo();
        prestamo3.setTitulo(titulo);

        Collection<Prestamo> prestamos = new ArrayList<>();
        prestamos.add(prestamo3);

        titulo.setPrestamos(prestamos);

        assertEquals(prestamos, titulo.getPrestamos());
    }

    @Test
    void testGetReservas() {
        Collection<Reserva> reservas = titulo.getReservas();
        assertNotNull(reservas);
        assertEquals(0, reservas.size()); // Assuming the initial value is 0
    }

    @Test
    void testSetReservas() {
        Reserva reserva3 = new Reserva();
        reserva3.setTitulo(titulo);

        Collection<Reserva> reservas= new ArrayList<>();
        reservas.add(reserva3);

        titulo.setReservas(reservas);

        assertEquals(reservas, titulo.getReservas());
    }

    @Test
    void testGetTitulo() {
        String tituloStr = titulo.getTitulo();
        assertEquals("Título de prueba", tituloStr);
    }

    @Test
    void testSetTitulo() {
        titulo.setTitulo("Nuevo Título");

        assertEquals("Nuevo Título", titulo.getTitulo());
    }

    @Test
    void testGetIsbn() {
        String isbn = titulo.getIsbn();
        assertEquals("123-456-789", isbn);
    }

    @Test
    void testSetIsbn() {
        titulo.setIsbn("987-654-321");

        assertEquals("987-654-321", titulo.getIsbn());
    }

    @Test
    void testGetNumReserva() {
        String numReserva = titulo.getNumReserva();
        assertEquals("3", numReserva);
    }

    @Test
    void testSetNumReserva() {
        titulo.setNumReserva("10");

        assertEquals("10", titulo.getNumReserva());
    }
}
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

        //titulo.setPrestamos(2);
        //titulo.setReservas(1);
        titulo.setTitulo("Título de prueba");
        titulo.setIsbn("123-456-789");
        titulo.setNumReserva("3");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAutores() {
    }

    @Test
    void setAutores() {
    }

    @Test
    void getEjemplares() {
    }

    @Test
    void setEjemplares() {
    }

    @Test
    void getFoto() {
    }

    @Test
    void setFoto() {
    }

    @Test
    void getPrestamos() {
    }

    @Test
    void setPrestamos() {
    }

    @Test
    void getReservas() {
    }

    @Test
    void setReservas() {
    }

    @Test
    void getTitulo() {
    }

    @Test
    void setTitulo() {
    }

    @Test
    void getIsbn() {
    }

    @Test
    void setIsbn() {
    }

    @Test
    void getNumReserva() {
    }

    @Test
    void setNumReserva() {
    }
}
package PantysMelRep.domain.entities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario("123456789", "John", "Doe", new Date(), "ROLE_USER");
        usuario.setContrasena("password");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testGetId() {
        assertEquals("123456789", usuario.getId());
    }

    @Test
    void testSetId() {
        usuario.setId("987654321");
        assertEquals("987654321", usuario.getId());
    }

    @Test
    void testGetNombre() {
        assertEquals("John", usuario.getNombre());
    }

    @Test
    void testSetNombre() {
        usuario.setNombre("Jane");
        assertEquals("Jane", usuario.getNombre());
    }

    @Test
    void testGetApellidos() {
        assertEquals("Doe", usuario.getApellidos());
    }

    @Test
    void testSetApellidos() {
        usuario.setApellidos("Smith");
        assertEquals("Smith", usuario.getApellidos());
    }

    @Test
    void testGetContrasena() {
        assertEquals("password", usuario.getContrasena());
    }

    @Test
    void testSetContrasena() {
        usuario.setContrasena("newPassword");
        assertEquals("newPassword", usuario.getContrasena());
    }

    @Test
    void testGetRol() {
        assertEquals("ROLE_USER", usuario.getRol());
    }

    @Test
    void testSetRol() {
        usuario.setRol("ROLE_ADMIN");
        assertEquals("ROLE_ADMIN", usuario.getRol());
    }

    @Test
    void testGetFechaFinPenalizacion() {
        assertNotNull(usuario.getFechaFinPenalizacion());
    }

    @Test
    void testSetFechaFinPenalizacion() {
        Date newDate = new Date();
        usuario.setFechaFinPenalizacion(newDate);
        assertEquals(newDate, usuario.getFechaFinPenalizacion());
    }
}

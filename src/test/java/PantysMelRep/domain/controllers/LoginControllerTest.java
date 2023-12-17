package PantysMelRep.domain.controllers;

import PantysMelRep.domain.entities.Prestamo;
import PantysMelRep.domain.entities.Usuario;
import PantysMelRep.persistencia.AutorDAO;
import PantysMelRep.persistencia.UsuarioDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private UsuarioDAO usuarioDAO;

    @Mock
    private RedirectAttributes redirectAttributes;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void iniciarSIUsuarioNoexiste() {
        String dni = "12345678";
        String contrasena = "12345678";

        when(usuarioDAO.findById(dni)).thenReturn(Optional.empty());

        loginController.iniciarS(dni, contrasena, redirectAttributes);

        verify(redirectAttributes, times(1)).addFlashAttribute(any(), eq("Credenciales de inicio de sesión incorrectas"));
    }

    @Test
    void iniciarSContrasenaIncorrecta() {
        String dni = "12345678";
        String contrasena = "claveIncorrecta";

        // Configuración del mock para indicar que el usuario existe
        when(usuarioDAO.findById(dni)).thenReturn(Optional.of(new Usuario(dni, "nombre", "apellidos", "contrasenaCorrecta", "USUARIO")));

        // Llamada al método que estás probando
        loginController.iniciarS(dni, contrasena, redirectAttributes);

        // Verificación de que se agregó el mensaje de error correspondiente
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("error"), eq("Credenciales de inicio de sesión incorrectas"));
    }

    @Test
    void iniciarSContrasenaAdmin() {
        String dni = "12345678";
        String contrasena = "claveCorrecta";

        // Configuración del mock para indicar que el usuario existe
        when(usuarioDAO.findById(dni)).thenReturn(Optional.of(new Usuario(dni, "nombre", "apellidos", contrasena, "ADMIN")));

        // Llamada al método que estás probando
        loginController.iniciarS(dni, contrasena, redirectAttributes);

        // Verificación de que se agregó el mensaje de error correspondiente
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("error"), eq("Credenciales de inicio de sesión incorrectas"));
    }

    @Test
    void iniciarSContrasenaUser() {
        String dni = "12345678";
        String contrasena = "claveCorrecta";

        // Configuración del mock para indicar que el usuario existe
        when(usuarioDAO.findById(dni)).thenReturn(Optional.of(new Usuario(dni, "nombre", "apellidos", contrasena, "USER")));

        // Llamada al método que estás probando
        loginController.iniciarS(dni, contrasena, redirectAttributes);

        // Verificación de que se agregó el mensaje de error correspondiente
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("error"), eq("Credenciales de inicio de sesión incorrectas"));
    }



    @Test
    void registrarUsuario() {
        String dni = "dniExistente";
        String contrasena = "claveCorrecta";

        // Configuración del mock para indicar que el usuario existe
        when(usuarioDAO.findById(dni)).thenReturn(Optional.of(new Usuario(dni, "nombre", "apellidos", contrasena, "ADMIN")));

        // Llamada al método que estás probando
        loginController.registrarUsuario(dni, "nombre", "apellidos", contrasena, redirectAttributes);



        // Verificación de que se agregó el mensaje de error correspondiente
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("error"), eq("Ya existe un usuario con el mismo DNI"));
    }
}
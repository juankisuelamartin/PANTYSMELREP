package PantysMelRep.domain.controllers;

import PantysMelRep.domain.controllers.LoginController;
import PantysMelRep.domain.entities.Usuario;
import PantysMelRep.persistencia.UsuarioDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @Mock
    private UsuarioDAO usuarioDAO;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void home_RedirectToLoginWhenFirstTimeIsNull() {
        String result = loginController.home(null, redirectAttributes);
        assertEquals("redirect:/login", result);
    }

    @Test
    void home_RedirectToHomePageWhenFirstTimeIsNotNull() {
        String result = loginController.home("yes", redirectAttributes);
        assertEquals("redirect:/home", result);
    }

    @Test
    void iniciarS_UsuarioAdminCorrecto() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "password";
        String hashedPassword = encoder.encode(rawPassword);

        Usuario mockUsuario = new Usuario();
        mockUsuario.setContrasena(hashedPassword);
        mockUsuario.setRol("ADMIN");

        when(usuarioDAO.findById("12345678")).thenReturn(java.util.Optional.of(mockUsuario));

        String result = loginController.iniciarS("12345678", rawPassword, redirectAttributes);
        assertEquals("redirect:/home", result);
    }
    @Test
    void iniciarS_UsuarioNormalCorrecto() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "password";
        String hashedPassword = encoder.encode(rawPassword);

        Usuario mockUsuario = new Usuario();
        mockUsuario.setContrasena(hashedPassword);
        mockUsuario.setRol("USUARIO");

        when(usuarioDAO.findById("12345678")).thenReturn(java.util.Optional.of(mockUsuario));

        String result = loginController.iniciarS("12345678", rawPassword, redirectAttributes);
        assertEquals("redirect:/homeUsuario", result);
    }





    @Test
    void iniciarS_UsuarioIncorrecto() {
        when(usuarioDAO.findById("12345678")).thenReturn(java.util.Optional.empty());

        String result = loginController.iniciarS("12345678", "password", redirectAttributes);
        assertEquals("redirect:/login", result);
        verify(redirectAttributes).addFlashAttribute("error", "Credenciales de inicio de sesión incorrectas");
    }

    @Test
    void showLoginForm() {
        String result = loginController.showLoginForm();
        assertEquals("login", result);
    }

    @Test
    void showSigninform() {
        String result = loginController.showSigninform();
        assertEquals("signin", result);
    }

    @Test
    void registrarUsuario_UsuarioYaExiste() {
        when(usuarioDAO.findById("12345678")).thenReturn(java.util.Optional.of(new Usuario()));

        String result = loginController.registrarUsuario("12345678", "Nombre", "Apellidos", "password", redirectAttributes);
        assertEquals("redirect:/login", result);
        verify(redirectAttributes).addFlashAttribute("error", "Ya existe un usuario con el mismo DNI");
    }

    @Test
    void registrarUsuario_UsuarioNuevo() {
        when(usuarioDAO.findById("12345678")).thenReturn(java.util.Optional.empty());

        String result = loginController.registrarUsuario("12345678", "Nombre", "Apellidos", "password", redirectAttributes);
        assertEquals("redirect:/login", result);
        verify(usuarioDAO).save(any(Usuario.class));
    }

    @Test
    void verificarContrasena_Correcta() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode("password");
        assertTrue(loginController.verificarContrasena("password", hashedPassword));
    }

    @Test
    void verificarContrasena_Incorrecta() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode("password");
        assertFalse(loginController.verificarContrasena("wrongPassword", hashedPassword));
    }

}

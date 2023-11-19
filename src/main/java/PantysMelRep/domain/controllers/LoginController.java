package PantysMelRep.domain.controllers;

import PantysMelRep.domain.entities.Usuario;
import PantysMelRep.persistencia.UsuarioDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    private static final Logger logLogin = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UsuarioDAO usuarioDAO;

    @GetMapping("/")
    public String home(@RequestParam(value = "firstTime", required = false) String firstTime, RedirectAttributes redirectAttributes) {
        if (firstTime == null) {
            redirectAttributes.addAttribute("firstTime", "no");
            return "redirect:/login";
        }


        return "redirect:/home"; // Nombre de la vista de la página principal
    }

    @GetMapping("/home")
    public String homePage() {
        return "home"; // Nombre de la vista de tu página principal
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // Nombre de la vista de tu formulario de inicio de sesión
    }
    @GetMapping("/signin")
    public String showSigninform() {
        return "signin"; // Nombre de la vista de tu formulario de inicio de sesión
    }


    @PostMapping("/login")
    public String iniciarS( @RequestParam("dni_log")String dni,
                            @RequestParam("contrasena_log")String contrasena,
                            RedirectAttributes redirectAttributes) {
        // Buscar al usuario en la base de datos por DNI
        Usuario usuario = usuarioDAO.findById(dni).orElse(null);

        // Comprobar si el usuario existe y la contraseña es correcta
        if (usuario != null && verificarContrasena(contrasena,usuario.getContrasena())) {
            // Aquí puedes realizar la autenticación del usuario
            // Por ejemplo, establecer una sesión de usuario
            // Comprobar el rol del usuario
            String rol = usuario.getRol();
            if (rol.equals("ADMIN")) {
                logLogin.info("Credenciales correctas - Administrador");
                return "redirect:/home";
            } else if (rol.equals("USUARIO")) {
                logLogin.info("Credenciales correctas - Usuario");
                return "redirect:/homeUsuario";
            } else {
                logLogin.info("Rol no reconocido");
                return "redirect:/error";
            }
        } else {
            // Si el inicio de sesión falla, puedes agregar un mensaje de error y redirigir nuevamente a la página de inicio de sesión
            redirectAttributes.addFlashAttribute("error", "Credenciales de inicio de sesión incorrectas");
            logLogin.info("Credenciales incorrectas");
            return "redirect:/login";
        }
    }

    @PostMapping("signin")
    public String registrarUsuario(@RequestParam("dni")String dni,
                                   @RequestParam("nombre")String nombre,
                                   @RequestParam("apellidos")String apellidos,
                                   @RequestParam("contrasena")String contrasena
                                   ) {
        // Verificar si ya existe un usuario con el mismo DNI en la base de datos
        Usuario usuarioExistente = usuarioDAO.findById(dni).orElse(null);
        if (usuarioExistente != null) {
            // El usuario con el mismo DNI ya existe, puedes manejar este caso como desees
            logLogin.info("Ya existe un usuario con el mismo DNI");
            // Puedes redirigir a una página de error, mostrar un mensaje al usuario, etc.
            return "redirect:/error";
        } else {
            // Crear un nuevo usuario con la información proporcionada
            String contrasenaHasheada = hashPassword(contrasena);

            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setId(dni);
            nuevoUsuario.setNombre(nombre);
            nuevoUsuario.setApellidos(apellidos);
            nuevoUsuario.setContrasena(contrasenaHasheada);
            nuevoUsuario.setRol("USUARIO");

            // Guardar el nuevo usuario en la base de datos
            usuarioDAO.save(nuevoUsuario);
            System.out.println("Usuario registrado");

            // Redirigir al usuario al inicio de sesión después de registrarse
            return "redirect:/login";
        }
    }

    //Método Encriptar Contraseña
    public String hashPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    //Método desencriptar contraseña
    public boolean verificarContrasena(String contrasenaIngresada, String contrasenaAlmacenada) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(contrasenaIngresada, contrasenaAlmacenada);
    }
}
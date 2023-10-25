package PantysMelRep.domain.controllers;

import PantysMelRep.domain.entities.*;
import PantysMelRep.persistencia.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/biblioteca")
public class TituloController {

    @Autowired
    private GestorTitulos gestorTitulos;
    @Autowired
    private TituloDAO tituloDAO;
    @Autowired
    private AutorDAO autorDAO;
// TODO AVISAR DE LOS ERRORES. ISBN YA EXISTE... ETC
    @PostMapping("/altaTitulo")
    public String altaTitulo(@RequestParam("titulo") String titulo,
                             @RequestParam("isbn") String isbn,
                             @RequestParam("autores") List<String> autores,
                             @RequestParam("DType") int DType,
                             RedirectAttributes redirectAttributes) {
        // Comprobar si el ISBN ya existe en la base de datos
        if (tituloDAO.findById(isbn).isPresent()) {
            // El ISBN ya existe, mostrar un mensaje de error
            System.out.println("El ISBN ya existe en la base de datos.");
            redirectAttributes.addFlashAttribute("error", "El ISBN ya existe en la base de datos.");
            return "redirect:/"; // Redirige a la página principal o a donde desees
        }

        // Procesar la lista de autores
        List<Autor> listaAutores = new ArrayList<>();
        for (String autorNombre : autores) {
            // Separar el nombre y el apellido del autor
            String[] nombreApellido = autorNombre.split(" ");
            if (nombreApellido.length == 2) {
                // Comprobar si el autor ya existe en la base de datos
                AutorId autorId = new AutorId(nombreApellido[0], nombreApellido[1]);
                Autor autorExistente = (Autor) autorDAO.findById(autorId).orElse(null);

                if (autorExistente != null) {
                    // El autor ya existe, usar el autor existente en lugar de crear uno nuevo
                    System.out.println("El autor: " + nombreApellido[0] + " " + nombreApellido[1] + " ya existe en la base de datos.");
                    listaAutores.add(autorExistente);
                } else {
                    // Crear un nuevo autor y guardarlo en la base de datos
                    Autor nuevoAutor = new Autor();
                    nuevoAutor.setId(autorId);
                    listaAutores.add(nuevoAutor);
                }
            }
        }

        // Utilizar el gestorTitulos para dar de alta el título
        Titulo nuevoTitulo = gestorTitulos.altaTitulo(titulo, isbn, listaAutores, DType);

        if (nuevoTitulo != null) {
            gestorTitulos.altaEjemplar(isbn);
            System.out.println("Ejemplar añadido");
            // El título se dio de alta exitosamente en la base de datos
            return "redirect:/"; // Redirige a la página principal o a donde desees
        } else {
            // Hubo un error al dar de alta el título
            // Puedes redirigir a una página de error o mostrar un mensaje al usuario
            return "error"; // Cambia "error" al nombre de tu vista de error
        }
    }


    @PostMapping("/actualizarTitulo")
    public String actualizarTitulo(@RequestParam("isbn_actualizar") String isbn,
                                   @RequestParam("titulo_actualizar") String nuevoTitulo,
                                   @RequestParam("autores_actualizar") String nuevosAutores,
                                   @RequestParam("DType_actualizar") int DType) {
        /*Titulo titulo = new Titulo();
        titulo.setIsbn(isbn);
        titulo.setTitulo(nuevoTitulo);*/

        // Recuperar el título existente de la base de datos
        Optional<Titulo> optionalTitulo = tituloDAO.findById(isbn);
        if (!optionalTitulo.isPresent()) {
            // Manejar el caso en el que el título no existe
            return "error"; // Puedes redirigir a una página de error o mostrar un mensaje al usuario
        }

        Titulo titulo = optionalTitulo.get();

        if (!nuevosAutores.isEmpty()) {

            // Separar los nombres de los autores
            String[] nombresAutores = nuevosAutores.split(",");

            // Crear una lista para almacenar los autores actualizados
            List<Autor> listaAutoresActualizados = new ArrayList<>();

            for (String nombreAutor : nombresAutores) {
                String[] nombres = nombreAutor.trim().split(" ");

                // Crear un nuevo AutorId con nombre y apellido
                AutorId autorId = new AutorId(nombres[0], nombres[1]);

                Autor autor;
                if (autorDAO.existsById(autorId.toString())) {
                    // Obtener el autor existente
                    autor = autorDAO.findById(autorId.toString()).get();
                } else {
                    // Crear un nuevo autor y guardarlo en la base de datos
                    autor = new Autor(nombres[0], nombres[1]);
                    autorDAO.save(autor);
                }
                // Agregar el autor a la lista de autores actualizados
                listaAutoresActualizados.add(autor);
            }

            // Actualizar la lista de autores del título
            titulo.setAutores(listaAutoresActualizados);
        }
        tituloDAO.save(titulo);

        /*GestorTitulos.actualizarTitulo(titulo, DType);*/
        return "redirect:/"; // Redirige a la página principal
    }



    // TODO SI EXISTE UN EJEMPLAR NO SE PUEDE BORRAR EL TITULO.
    @PostMapping("/borrarTitulo")
    public String borrarTitulo(@RequestParam("isbn_borrar") String isbn) {
        // Obtener el ISBN del formulario and enviarlo al servicio GestorTitulos

        //TODO BORRAR EJEMPLARES ANTES QUE EL TITULO

        gestorTitulos.borrarTitulo(isbn);
        return "redirect:/"; // Redirige a la página principal
    }
// TODO GESTION DE ERRORES

    @PostMapping("/altaEjemplar")
    public String altaEjemplar(@RequestParam("isbn_ejemplar") String isbn, Model model) {
        // Verificar si el ISBN del título existe en la base de datos

        Titulo titulo = tituloDAO.findById(isbn).orElse(null);

        if (titulo != null) {
            // Si el título existe, agregar el ejemplar
            gestorTitulos.altaEjemplar(isbn);
        } else {
            // Si el título no existe, puedes manejar el error aquí
            model.addAttribute("error", "El ISBN proporcionado no existe en la base de datos.");
            return "altaEjemplar"; // Retorna a la misma vista con un mensaje de error
        }

        return "redirect:/"; // Redirige a la página principal
    }


    // TODO COMPROBAR QUE EXISTE EL EJEMPLAR/TITULO A BORRAR
    @PostMapping("/bajaEjemplar")
    public String bajaEjemplar(@RequestParam("id_baja") String id) {
        // Obtener el ID del formulario y enviarlo al servicio GestorTitulos
        gestorTitulos.bajaEjemplar(id);

        return "redirect:/"; // Redirige a la página principal
    }
}
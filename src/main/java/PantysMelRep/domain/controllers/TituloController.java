package PantysMelRep.domain.controllers;

import PantysMelRep.domain.entities.*;
import PantysMelRep.persistencia.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.stream.Collectors;

import java.util.*;

@Controller
@Transactional
@RequestMapping("/titulos")
public class TituloController {

    private static final Logger logTitulo = LoggerFactory.getLogger(TituloController.class);


    @Autowired
    private GestorTitulos gestorTitulos;
    @Autowired
    private TituloDAO tituloDAO;
    @Autowired
    private EjemplarDAO ejemplarDAO;
    @Autowired
    private AutorDAO autorDAO;
// TODO AVISAR DE LOS ERRORES. ISBN YA EXISTE... ETC

    @Transactional
    @PostMapping("/altaTitulo")
    public String altaTitulo(@RequestParam("titulo") String titulo,
                             @RequestParam("isbn") String isbn,
                             @RequestParam("autores") List<String> autores,
                             @RequestParam("DType") int DType,
                             @RequestPart("foto") MultipartFile foto,
                             RedirectAttributes redirectAttributes) throws InterruptedException {

        logTitulo.info("Received parameters:");
        logTitulo.info("titulo: " + titulo);
        logTitulo.info("isbn: " + isbn);
        logTitulo.info("autores: " + autores);
        logTitulo.info("DType: " + DType);
        logTitulo.info("foto: " + foto);

        // Verificar si el tipo de contenido del archivo es una imagen
        if (!Objects.requireNonNull(foto.getContentType()).startsWith("image/")) {
            redirectAttributes.addFlashAttribute("error", "Error: El archivo no es una imagen válida.");
            return "redirect:/home";
        }

        // Comprobar si el ISBN ya existe en la base de datos
        if (tituloDAO.findById(isbn).isPresent()) {
            // El ISBN ya existe, mostrar un mensaje de error
            logTitulo.info("El ISBN ya existe en la Base de Datos.");
            redirectAttributes.addFlashAttribute("error", "Error: El ISBN ya existe en la Base de Datos.");
            return "redirect:/home"; // Redirige a la página principal o a donde desees
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
                    logTitulo.info("El autor: " + nombreApellido[0] + " " + nombreApellido[1] + " ya existe en la base de datos.");
                    listaAutores.add(autorExistente);
                } else {
                    // Crear un nuevo autor y guardarlo en la base de datos
                    Autor nuevoAutor = new Autor();
                    nuevoAutor.setId(autorId);
                    listaAutores.add(nuevoAutor);
                    logTitulo.info("Autor creado con éxito.");
                    redirectAttributes.addFlashAttribute("success", "Autor creado con éxito.");
                }
            }
        }
        byte[] fotoBytes;
        try {
        // Convertir la foto a un array de bytes
        fotoBytes = foto.getBytes();
        } catch (IOException e) {
            //log.error("Error al leer los bytes de la foto", e);
            redirectAttributes.addFlashAttribute("error", "Error al leer la foto. Por favor, inténtalo de nuevo.");
            return "redirect:/home";
        }
        // Utilizar el gestorTitulos para dar de alta el título
        Titulo nuevoTitulo = gestorTitulos.altaTitulo(titulo, isbn, listaAutores, DType, fotoBytes);

        if (nuevoTitulo != null) {

            gestorTitulos.altaEjemplar(nuevoTitulo.getIsbn(), redirectAttributes);
            logTitulo.info("El título ha sido dado de alta con éxito.");
            redirectAttributes.addFlashAttribute("success", "El título ha sido dado de alta con éxito");
            // El título se dio de alta exitosamente en la base de datos
        } else {

            // Hubo un error al dar de alta el título
            // Puedes redirigir a una página de error o mostrar un mensaje al usuario
            logTitulo.info("ERROR: No se ha podido dar de alta el título.");
            redirectAttributes.addFlashAttribute("error", "ERROR: No se ha podido dar de alta el título.");
        }
        return "redirect:/home"; // Redirige a la página principal o a donde desees
    }



    @Transactional
    @PostMapping("/actualizarTitulo")
    public String actualizarTitulo(@RequestParam("isbn_actualizar") String isbn,
                                   @RequestParam("titulo_actualizar") String nuevoTitulo,
                                   @RequestParam("autores_actualizar") String nuevosAutores,
                                   @RequestParam("DType_actualizar") int DType,
                                   @RequestPart("foto") MultipartFile foto,
                                   RedirectAttributes redirectAttributes) throws InterruptedException {

        // Recuperar el título existente de la base de datos
        Optional<Titulo> optionalTitulo = tituloDAO.findById(isbn);
        if (!optionalTitulo.isPresent()) {
            // Manejar el caso en el que el título no existe
            logTitulo.info("ERROR: El título no existe.");
            redirectAttributes.addFlashAttribute("error", "ERROR: El título no existe.");
            return "redirect:/home"; // Puedes redirigir a una página de error o mostrar un mensaje al usuario
        }

        Titulo tituloExistente = optionalTitulo.get();

        // Recoge todos los ejemplares asociados al título existente
        Collection<Ejemplar> ejemplares = tituloExistente.getEjemplares();

        // Recoge los IDs de los ejemplares existentes
        List<Long> ejemplarIds = ejemplares.stream().map(Ejemplar::getId).collect(Collectors.toList());

        // Elimina los ejemplares asociados al título
        for (Ejemplar ejemplar : ejemplares) {
            ejemplarDAO.delete(ejemplar);
        }
        // Elimina el título existente
        tituloDAO.delete(tituloExistente);

        byte[] fotoBytes;
        try {
            // Convertir la foto a un array de bytes
            fotoBytes = foto.getBytes();
        } catch (IOException e) {
            //log.error("Error al leer los bytes de la foto", e);
            redirectAttributes.addFlashAttribute("error", "Error al leer la foto. Por favor, inténtalo de nuevo.");
            return "redirect:/home";
        }
        // Crea un nuevo título con la información actualizada
        Titulo tituloActualizado = gestorTitulos.altaTitulo(nuevoTitulo, isbn,null, DType,fotoBytes);
        tituloActualizado.setIsbn(isbn);
        tituloActualizado.setTitulo(nuevoTitulo);

        if (!nuevosAutores.isEmpty()) {
            // Procesa los autores y actualiza la lista de autores del título actualizado
            List<Autor> listaAutoresActualizados = procesarAutores(nuevosAutores);
            tituloActualizado.setAutores(listaAutoresActualizados);
        }

        for (Ejemplar ejemplar : ejemplares) {
            ejemplar.setTitulo(tituloActualizado); // Asocia el ejemplar al nuevo título
            tituloActualizado.getEjemplares().add(ejemplar); // Agrega el ejemplar a la lista de ejemplares del título
        }

        // Guarda el título actualizado en la base de datos
        tituloDAO.save(tituloActualizado);
        logTitulo.info("Título actualizado con éxito.");
        redirectAttributes.addFlashAttribute("success", "Título actualizado con éxito.");

        // Agrega los ejemplares al nuevo título
        return "redirect:/home"; // Redirige a la página principal

    }



    private List<Autor> procesarAutores(String nuevosAutores) {

        // Separar los nombres de los autores
        String[] nombresAutores = nuevosAutores.split(",");

        // Crear una lista para almacenar los autores actualizados
        List<Autor> listaAutoresActualizados = new ArrayList<>();

        for (String nombreAutor : nombresAutores) {
            String[] nombres = nombreAutor.trim().split(" ");

            // Crear un nuevo AutorId con nombre y apellido
            AutorId autorId = new AutorId(nombres[0], nombres[1]);

            Autor autor;
            Optional<Autor> optionalAutor = autorDAO.findById(autorId);
            if (optionalAutor.isPresent()) {
                // Obtener el autor existente
                autor = optionalAutor.get();
            } else {
                // Crear un nuevo autor y guardarlo en la base de datos
                autor = new Autor(nombres[0], nombres[1]);
                autorDAO.save(autor);
            }
            // Agregar el autor a la lista de autores actualizados
            listaAutoresActualizados.add(autor);

        }
        return listaAutoresActualizados;
    }



    @PostMapping("/borrarTitulo")
    public String borrarTitulo(@RequestParam("isbn_borrar") String isbn,
                               RedirectAttributes redirectAttributes) throws InterruptedException {

        // Comprobar si existen ejemplares para el título
        Optional<Titulo> optionalTitulo = tituloDAO.findById(isbn);
        if (!optionalTitulo.isPresent()) {
            // Manejar el caso en el que el título no existe
            logTitulo.info("ERROR: El título no existe.");
            redirectAttributes.addFlashAttribute("error", "ERROR: El título no existe.");
            return "redirect:/home"; // Puedes redirigir a una página de error o mostrar un mensaje al usuario
        } else {
            Titulo titulo = optionalTitulo.get(); // Obtener el valor del Optional

            if (titulo.getEjemplares() != null && !titulo.getEjemplares().isEmpty()) {
                // Si hay ejemplares, eliminarlos
                for (Ejemplar ejemplar : titulo.getEjemplares()) {
                    gestorTitulos.bajaEjemplar(ejemplar.getId().toString(), redirectAttributes);
                }
            }

            // Después de eliminar los ejemplares, puedes eliminar el título
            gestorTitulos.borrarTitulo(isbn);
            logTitulo.info("Título eliminado con éxito");
            redirectAttributes.addFlashAttribute("success", "Título eliminado con éxito");
        }

        return "redirect:/home"; // Redirige a la página principal
    }

// TODO GESTION DE ERRORES

    @PostMapping("/altaEjemplar")
    public String altaEjemplar(@RequestParam("isbn_ejemplar") String isbn, Model model,
                               RedirectAttributes redirectAttributes) throws InterruptedException {

            gestorTitulos.altaEjemplar(isbn, redirectAttributes);
        logTitulo.info("Ejemplar dado de alta con éxito.");
        redirectAttributes.addFlashAttribute("success", "Ejemplar dado de alta con éxito");
        return "redirect:/home"; // Redirige a la página principal
    }


    // TODO COMPROBAR QUE EXISTE EL EJEMPLAR/TITULO A BORRAR
    @PostMapping("/bajaEjemplar")
    public String bajaEjemplar(@RequestParam("id_baja") String id,
                               RedirectAttributes redirectAttributes) throws InterruptedException {

        // Obtener el ID del formulario y enviarlo al servicio GestorTitulos
        // Contar cuántos ejemplares existen con el mismo Titulo_Id
        Titulo tituloId = ejemplarDAO.findById(id).orElseThrow(() -> new RuntimeException("Ejemplar no encontrado")).getTitulo();
        long countEjemplares = ejemplarDAO.contarEjemplaresConMismoTitulo(tituloId);;
        if (countEjemplares > 1) {
            // Permitir la eliminación del ejemplar
            gestorTitulos.bajaEjemplar(id, redirectAttributes);
            countEjemplares -= 1;
            logTitulo.info("Ejemplar con ID de ejemplar: "+id+ " y ISBN: " +tituloId.getIsbn()+ " borrado. Numero de ejemplares: " +countEjemplares);
            redirectAttributes.addFlashAttribute("success", "Ejemplar con ID de ejemplar: "+id+ " y ISBN: " +tituloId.getIsbn()+ " borrado. Numero de ejemplares: " +countEjemplares);
        } else {
           logTitulo.info("ERROR: Ejemplar con ID de ejemplar: "+id+ " y ISBN: " +tituloId.getIsbn()+ " no ha sido borrado. Numero de ejemplares: " +countEjemplares);
            redirectAttributes.addFlashAttribute("error", "ERROR: Ejemplar con ID de ejemplar: "+id+ " y ISBN: " +tituloId.getIsbn()+ " no ha sido borrado. Numero de ejemplares: " +countEjemplares);
            // No permitir la eliminación y mostrar un mensaje de error o redirigir a una página de error
            // Puedes agregar un mensaje de error o redirigir a una página apropiada aquí.
        }

        return "redirect:/home"; // Redirige a la página principal
    }
    @GetMapping("/altaTitulo")
    public String mostrarFormularioAltaTitulo() {
        // Aquí puedes realizar lógica si es necesario antes de mostrar el formulario
        return "altaTitulo";
    }

    @GetMapping("/actualizarTitulo")
    public String mostrarFormularioActualizarTitulo(Model model) {
        // Aquí puedes realizar lógica si es necesario antes de mostrar el formulario
        return "actualizarTitulo";
    }

    @GetMapping("/borrarTitulo")
    public String mostrarFormularioBorrarTitulo(Model model) {
        // Aquí puedes realizar lógica si es necesario antes de mostrar el formulario
        return "borrarTitulo";
    }

    @GetMapping("/altaEjemplar")
    public String mostrarFormularioAltaEjemplar(Model model) {
        // Aquí puedes realizar lógica si es necesario antes de mostrar el formulario
        return "altaEjemplar";
    }

    @GetMapping("/bajaEjemplar")
    public String mostrarFormularioBajaEjemplar(Model model) {
        // Aquí puedes realizar lógica si es necesario antes de mostrar el formulario
        return "Ejemplares :: bajaEjemplarFragment";
    }
}
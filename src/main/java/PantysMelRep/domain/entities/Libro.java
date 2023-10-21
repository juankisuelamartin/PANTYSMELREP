package PantysMelRep.domain.entities;

import java.util.Collection;

public class Libro extends Titulo {
    public Libro(Collection<Autor> autores, Collection<Ejemplar> ejemplares, Collection<Prestamo> prestamos, Collection<Reserva> reservas, String titulo, String isbn, String numReserva) {
        super(autores, ejemplares, prestamos, reservas, titulo, isbn, numReserva);
    }

}
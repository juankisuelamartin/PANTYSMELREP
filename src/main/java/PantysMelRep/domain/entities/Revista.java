package PantysMelRep.domain.entities;

import java.util.Collection;

public class Revista extends Titulo {
    public Revista(Collection<Autor> autores, Collection<Ejemplar> ejemplares, Collection<Prestamo> prestamos, Collection<Reserva> reservas, String titulo, String isbn, String numReserva) {
        super(autores, ejemplares, prestamos, reservas, titulo, isbn, numReserva);
    }
}
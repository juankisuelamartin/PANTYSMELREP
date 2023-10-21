package PantysMelRep.domain.entities;

import java.util.Collection;

public class Titulo {
	Collection<Autor> autores;
	Collection<Ejemplar> ejemplares;
	Collection<Prestamo> prestamos;
	Collection<Reserva> reservas;
	private String titulo;
	private String isbn;
	private String numReserva;

	public Titulo(Collection<Autor> autores, Collection<Ejemplar> ejemplares, Collection<Prestamo> prestamos, Collection<Reserva> reservas, String titulo, String isbn, String numReserva) {
		this.autores = autores;
		this.ejemplares = ejemplares;
		this.prestamos = prestamos;
		this.reservas = reservas;
		this.titulo = titulo;
		this.isbn = isbn;
		this.numReserva = numReserva;
	}

	public Collection<Autor> getAutores() {
		return autores;
	}

	public void setAutores(Collection<Autor> autores) {
		this.autores = autores;
	}

	public Collection<Ejemplar> getEjemplares() {
		return ejemplares;
	}

	public void setEjemplares(Collection<Ejemplar> ejemplares) {
		this.ejemplares = ejemplares;
	}

	public Collection<Prestamo> getPrestamos() {
		return prestamos;
	}

	public void setPrestamos(Collection<Prestamo> prestamos) {
		this.prestamos = prestamos;
	}

	public Collection<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(Collection<Reserva> reservas) {
		this.reservas = reservas;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getNumReserva() {
		return numReserva;
	}

	public void setNumReserva(String numReserva) {
		this.numReserva = numReserva;
	}
}
package PantysMelRep.domain.entities;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "titulos")
public class Titulo {

	@Id
	protected String isbn;
	@OneToMany(mappedBy = "titulo")
	private Collection<Ejemplar> ejemplares;

	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "autor_titulo",
			joinColumns = @JoinColumn(name = "ISBN"),
			inverseJoinColumns = {@JoinColumn(name = "autor_nombre"), @JoinColumn(name = "autor_apellido")})
	private Collection<Autor> autores;


	@OneToMany(mappedBy = "titulo")
	private Collection<Prestamo> prestamos;

	@OneToMany(mappedBy = "titulo")
	private Collection<Reserva> reservas;

	@Column(name = "titulo")
	private String titulo;

	@Column(name = "num_reserva")
	private String numReserva;

	public Titulo() {
		// Constructor vacío requerido por JPA
	}

	public Titulo(String isbn, Collection<Ejemplar> ejemplares, Collection<Autor> autores, Collection<Prestamo> prestamos, Collection<Reserva> reservas, String titulo, String numReserva) {
		this.isbn = isbn;
		this.ejemplares = ejemplares;
		this.autores = autores;
		this.prestamos = prestamos;
		this.reservas = reservas;
		this.titulo = titulo;
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
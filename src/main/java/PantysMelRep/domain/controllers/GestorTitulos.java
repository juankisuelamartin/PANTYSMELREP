package PantysMelRep.domain.controllers;

import PantysMelRep.domain.entities.Titulo;
import PantysMelRep.persistencia.AutorDAO;
import PantysMelRep.persistencia.EjemplarDAO;
import PantysMelRep.persistencia.TituloDAO;
public class GestorTitulos {

	TituloDAO tituloDAO;
	EjemplarDAO ejemplarDAO;
	AutorDAO autorDAO;

	/**
	 * 
	 * @param titulo
	 * @param isbn
	 * @param autores
	 */
	public Titulo altaTítulo(String titulo, String isbn, String[] autores) {
		// TODO - implement GestorTitulos.altaTítulo
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param t
	 */
	public void actualizarTitulo(Titulo t) {
		// TODO - implement GestorTitulos.actualizarTitulo
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param t
	 */
	public void borrarTitulo(Titulo t) {
		// TODO - implement GestorTitulos.borrarTitulo
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param t
	 */
	public void altaEjemplar(Titulo t) {
		// TODO - implement GestorTitulos.altaEjemplar
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param t
	 */
	public void bajaEjemplar(Titulo t) {
		// TODO - implement GestorTitulos.bajaEjemplar
		throw new UnsupportedOperationException();
	}

}
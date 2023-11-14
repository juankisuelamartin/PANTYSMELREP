package PantysMelRep.persistencia;

import PantysMelRep.domain.entities.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroDAO extends JpaRepository<Libro, Integer> {

}
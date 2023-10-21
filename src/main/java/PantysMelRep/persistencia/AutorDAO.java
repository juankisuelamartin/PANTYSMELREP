package PantysMelRep.persistencia;

import PantysMelRep.domain.entities.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorDAO extends JpaRepository<Autor, String>{

}

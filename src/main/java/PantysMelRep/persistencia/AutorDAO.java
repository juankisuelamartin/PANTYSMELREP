package PantysMelRep.persistencia;

import PantysMelRep.domain.entities.Autor;
import PantysMelRep.domain.entities.AutorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AutorDAO extends JpaRepository<Autor, AutorId> {

    Optional<Autor> findById(AutorId autorId);
}

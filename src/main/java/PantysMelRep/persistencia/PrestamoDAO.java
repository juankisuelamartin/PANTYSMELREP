package PantysMelRep.persistencia;
import PantysMelRep.domain.entities.Ejemplar;
import PantysMelRep.domain.entities.Prestamo;
import PantysMelRep.domain.entities.PrestamoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Optional;


@Repository
public interface PrestamoDAO extends JpaRepository<Prestamo, Date>{
    Optional<Prestamo> findById(PrestamoId prestamoId);
}
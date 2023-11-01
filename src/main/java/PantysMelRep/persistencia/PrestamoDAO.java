package PantysMelRep.persistencia;
import PantysMelRep.domain.entities.Prestamo;
import PantysMelRep.domain.entities.PrestamoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrestamoDAO extends JpaRepository<Prestamo, PrestamoId> {
    Optional<Prestamo> findById(PrestamoId prestamoId);
    Optional<Prestamo> findByejemplarId(Long id);
}
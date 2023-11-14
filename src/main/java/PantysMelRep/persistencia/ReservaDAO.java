package PantysMelRep.persistencia;
import PantysMelRep.domain.entities.Autor;
import PantysMelRep.domain.entities.AutorId;
import PantysMelRep.domain.entities.Reserva;
import PantysMelRep.domain.entities.ReservaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Optional;


@Repository
public interface ReservaDAO extends JpaRepository<Reserva, ReservaId>{
    Optional<Reserva> findById(ReservaId reservaId);
}
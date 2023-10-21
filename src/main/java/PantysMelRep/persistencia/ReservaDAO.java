package PantysMelRep.persistencia;
import PantysMelRep.domain.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;


@Repository
public interface ReservaDAO extends JpaRepository<Reserva, Date>{

}
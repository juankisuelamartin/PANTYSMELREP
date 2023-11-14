package PantysMelRep.persistencia;
import PantysMelRep.domain.entities.Reserva;
import PantysMelRep.domain.entities.Revista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;


@Repository
public interface RevistaDAO extends JpaRepository<Revista, String>{

}
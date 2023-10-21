package PantysMelRep.persistencia;
import PantysMelRep.domain.entities.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;


@Repository
public interface PrestamoDAO extends JpaRepository<Prestamo, Date>{

}
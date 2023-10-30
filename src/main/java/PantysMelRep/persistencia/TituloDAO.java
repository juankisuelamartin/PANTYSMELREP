package PantysMelRep.persistencia;
import PantysMelRep.domain.entities.Titulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TituloDAO extends JpaRepository<Titulo, String>{
}
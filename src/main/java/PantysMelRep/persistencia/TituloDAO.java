package PantysMelRep.persistencia;
import PantysMelRep.domain.entities.Titulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TituloDAO extends JpaRepository<Titulo, String>{

}
package PantysMelRep.persistencia;
import PantysMelRep.domain.entities.Ejemplar;
import PantysMelRep.domain.entities.Titulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;



@Repository
public interface EjemplarDAO extends JpaRepository<Ejemplar, String>{

    @Query("SELECT MAX(e.id) FROM Ejemplar e")
    Long findMaxId();
}
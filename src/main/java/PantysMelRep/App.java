package PantysMelRep;
import java.sql.SQLException;
import PantysMelRep.persistencia.AgenteBBDD;
import PantysMelRep.persistencia.AutorDAO;

/**
 * Hello world!
 *
 *
 *
 */
public class App
{
    public static void main( String[] args ) throws SQLException {
        AgenteBBDD agente = new AgenteBBDD();
        System.out.println( "Hello World!" );
    }
}


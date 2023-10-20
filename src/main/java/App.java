package main.java;


import persistencia.AgenteBBDD;

import java.sql.SQLException;

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


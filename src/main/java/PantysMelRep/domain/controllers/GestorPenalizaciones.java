/*
 * Nombre del archivo: GestorPenalizaciones.java
 * Descripción: Clase GestorPenalizaciones de la aplicación PantysMelRep.
 * Autor: Pan TyS Mel SA
 */
package PantysMelRep.domain.controllers;

import PantysMelRep.domain.entities.Usuario;
import PantysMelRep.persistencia.UsuarioDAO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class GestorPenalizaciones {

    private final UsuarioDAO usuarioDAO;

    // Constructor que recibe UsuarioDAO como parámetro
    public GestorPenalizaciones(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    // Método para aplicar una penalización a un usuario en función del retraso en la entrega de un ejemplar
    @Transactional
    public void aplicarPenalizacion(Usuario u, Date fechaFin) {
        // Obtener la fecha actual
        Date fechaActual = new Date();

        // Crear un objeto Calendar y establecer la fecha actual
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaActual);

        // Agregar 30 días al plazo de penalización por defecto
        calendar.add(Calendar.DAY_OF_MONTH, 30);

        // Calcular la diferencia en días entre la fecha de entrega y la fecha actual
        long diffInMillies = Math.abs(fechaFin.getTime() - fechaActual.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        // Agregar días adicionales de penalización (5 días por cada día de retraso)
        calendar.add(Calendar.DAY_OF_MONTH, (int) (diff * 5));

        // Obtener la nueva fecha de finalización de la penalización
        Date fechaFinPenalizacion = calendar.getTime();

        // Establecer la nueva fecha de finalización de la penalización y guardar los cambios en la base de datos
        u.setFechaFinPenalizacion(fechaFinPenalizacion);
        usuarioDAO.save(u);
    }

    // Método para comprobar si un usuario está penalizado
    @Transactional
    public boolean comprobarPenalizacion(Usuario u) {
        // Obtener la fecha actual
        Date fechaActual = new Date();

        // Crear un objeto Calendar y establecer la fecha actual
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaActual);

        // Agregar 30 días al plazo de penalización por defecto
        calendar.add(Calendar.DAY_OF_MONTH, 30);

        // Verificar si el usuario está penalizado
        if (u.getFechaFinPenalizacion() == null) {
            return true; // El usuario no está penalizado
        } else {
            // Verificar si la fecha de finalización de la penalización ha pasado
            if (u.getFechaFinPenalizacion().before(fechaActual)) {
                // Restablecer la penalización y guardar los cambios en la base de datos
                u.setFechaFinPenalizacion(null);
                usuarioDAO.save(u);
                return false; // El usuario ya no está penalizado
            }
        }
        return true; // El usuario está penalizado
    }
}

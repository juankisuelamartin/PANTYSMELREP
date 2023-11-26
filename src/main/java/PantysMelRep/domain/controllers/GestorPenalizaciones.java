package PantysMelRep.domain.controllers;
import PantysMelRep.domain.entities.Usuario;
import PantysMelRep.persistencia.UsuarioDAO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class GestorPenalizaciones {


    @Autowired
    private UsuarioDAO usuarioDAO;

    @Transactional
    public void aplicarPenalizacion(Usuario u, Date fechaFin) {
        Date fechaActual = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaActual);
        calendar.add(Calendar.DAY_OF_MONTH, 30);
        long diffInMillies = Math.abs(fechaFin.getTime() - fechaActual.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        calendar.add(Calendar.DAY_OF_MONTH, (int) (diff * 5));
        Date fechafinpenalizacion = calendar.getTime();
        u.setFechaFinPenalizacion(fechafinpenalizacion);
        usuarioDAO.save(u);
    }



    @Transactional
    public boolean comprobarPenalizacion(Usuario u) {
        Date fechaActual = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaActual);
        calendar.add(Calendar.DAY_OF_MONTH, 30);
        if (u.getFechaFinPenalizacion().before(fechaActual)) {
            u.setFechaFinPenalizacion(null);
            usuarioDAO.save(u);
            return false;
        } else {
            return true;
        }

    }

}
package ar.unrn.tp4.ejercicio1.main;

import ar.unrn.tp4.ejercicio1.database.JDBCRegistroParticipante;
import ar.unrn.tp4.ejercicio1.model.Concurso;
import ar.unrn.tp4.ejercicio1.ui.VentanaPrincipal;

import java.awt.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    var registroParticipante = new JDBCRegistroParticipante(
                            "jdbc:derby://localhost:1527/participantes",
                            "app",
                            "app"
                    );
                    var concurso = new Concurso(registroParticipante);
                    new VentanaPrincipal(concurso).AgregarParticipante();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });
    }
}
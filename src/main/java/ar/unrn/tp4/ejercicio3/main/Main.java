package ar.unrn.tp4.ejercicio3.main;

import ar.unrn.tp4.ejercicio1.database.JDBCRegistroParticipante;
import ar.unrn.tp4.ejercicio3.persistencia.ArchivoConcursoRepository;
import ar.unrn.tp4.ejercicio3.persistencia.ArchivoInscripcionRepository;
import ar.unrn.tp4.ejercicio3.ui.RadioCompetition;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) { // [cite: 28]
        SwingUtilities.invokeLater(new Runnable() { // [cite: 28]
            @Override
            public void run() { // [cite: 28]
                try {
                    new Main().start(); // [cite: 28]
                } catch (Exception e) { // [cite: 28]
                    // log exception...
                    System.err.println("Error al iniciar la aplicación: " + e.getMessage()); // [cite: 28]
                    e.printStackTrace();
                }
            }
        });
    }

    private void start() { //
        // Crear la instancia de la implementación de persistencia
        JDBCRegistroParticipante registro = new ArchivoConcursoRepository(); // <--- Problema aquí

        // Inyectar la dependencia en la UI
        new RadioCompetition(registro); //
    }
}
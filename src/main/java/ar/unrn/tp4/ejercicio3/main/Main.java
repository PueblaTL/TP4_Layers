package ar.unrn.tp4.ejercicio3.main;

import ar.unrn.tp4.ejercicio1.database.JDBCRegistroParticipante;
import ar.unrn.tp4.ejercicio3.persistencia.ArchivoConcursoRepository;
import ar.unrn.tp4.ejercicio3.persistencia.ArchivoInscripcionRepository;
import ar.unrn.tp4.ejercicio3.ui.RadioCompetition;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new Main().start();
                } catch (Exception e) {
// log exception...
                    System.out.println(e);
                }
            }
        });
    }
    private void start() {
        new RadioCompetition();
    }
}
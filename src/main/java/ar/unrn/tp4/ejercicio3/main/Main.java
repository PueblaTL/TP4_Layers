package ar.unrn.tp4.ejercicio3.main;

// --- FILE Persistence Imports ---
import ar.unrn.tp4.ejercicio3.persistencia.JdbcConcursoRepository;
import ar.unrn.tp4.ejercicio3.persistencia.ArchivoConcursoRepository;
import ar.unrn.tp4.ejercicio3.persistencia.ArchivoInscripcionRepository;
import ar.unrn.tp4.ejercicio3.persistencia.JdbcInscripcionRepository;

// --- JDBC Persistence (Descomentar si se usa la base de datos) ---
// import ar.unrn.ejercicio3.persistencia.JdbcConcursoRepository;
// import ar.unrn.ejercicio3.persistencia.JdbcInscripcionRepository;

import ar.unrn.tp4.ejercicio3.ui.RadioCompetitionView;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // --- Escritor en archivo
                String concursosFile = "src/main/resources/concursos.txt";
                String inscriptosFile = "src/main/resources/inscriptos.txt";
                JdbcConcursoRepository concursoRepo = new ConcursoRepository(concursosFile);
                InscripcionRepository inscripcionRepo = new ArchivoInscripcionRepository(inscriptosFile);

                // --- Escritor en bd
                // String dbUrl = "jdbc:h2:./radio_db"; // Path to H2 file db
                // String dbUser = "sa"; // Default H2 user
                // String dbPassword = ""; // Default H2 password
                // ConcursoRepository concursoRepo = new JdbcConcursoRepository(dbUrl, dbUser, dbPassword);
                // InscripcionRepository inscripcionRepo = new JdbcInscripcionRepository(dbUrl, dbUser, dbPassword);
                // // Optional: Create tables if they don't exist (implement this logic)
                // setupDatabase(dbUrl, dbUser, dbPassword);


                // Inyeccion de dependencias
                new RadioCompetitionUI(concursoRepo, inscripcionRepo);

            } catch (Exception e) {
                System.err.println("Application startup error: " + e.getMessage());
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error grave al iniciar la aplicación:\n" + e.getMessage(),
                        "Error de Inicio", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

   /* private static void setupDatabase(String url, String user, String pass) {
        String createConcursos = "CREATE TABLE IF NOT EXISTS concursos (" +
                                "id_concurso INT PRIMARY KEY, " +
                                "nombre VARCHAR(255) NOT NULL, " +
                                "fecha_inicio_inscripcion DATE NOT NULL, " +
                                "fecha_fin_inscripcion DATE NOT NULL)";
        String createInscriptos = "CREATE TABLE IF NOT EXISTS inscriptos (" +
                                "id_inscripcion INT AUTO_INCREMENT PRIMARY KEY, " +
                                "apellido VARCHAR(100) NOT NULL, " +
                                "nombre VARCHAR(100) NOT NULL, " +
                                "dni VARCHAR(20) NOT NULL UNIQUE, " +
                                "telefono VARCHAR(20), " +
                                "email VARCHAR(100), " +
                                "id_concurso INT, " +
                                "FOREIGN KEY (id_concurso) REFERENCES concursos(id_concurso))";
         try (Connection conn = DriverManager.getConnection(url, user, pass);
              Statement stmt = conn.createStatement()) {
             stmt.execute(createConcursos);
             stmt.execute(createInscriptos);
             System.out.println("Database tables checked/created.");
         } catch (SQLException e) {
             System.err.println("Error setting up database schema: " + e.getMessage());
             // Decide if the application should exit or continue
         }
    }*/
}
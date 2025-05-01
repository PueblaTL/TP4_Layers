package ar.unrn.tp4.ejercicio3.main;

// Importaciones necesarias de las interfaces y clases concretas
import ar.unrn.tp4.ejercicio3.modelo.ConcursoRepository;
import ar.unrn.tp4.ejercicio3.modelo.InscripcionRepository;
import ar.unrn.tp4.ejercicio3.persistencia.ArchivoConcursoRepository;
import ar.unrn.tp4.ejercicio3.persistencia.ArchivoInscripcionRepository;
// Importa también las clases JDBC si vas a usarlas
import ar.unrn.tp4.ejercicio3.persistencia.JdbcConcursoRepository;
import ar.unrn.tp4.ejercicio3.persistencia.JdbcInscripcionRepository;
import ar.unrn.tp4.ejercicio3.persistencia.SetupBaseDeDatos; // Si usas JDBC y quieres crear tablas aquí
import ar.unrn.tp4.ejercicio3.ui.RadioCompetitionView;

import javax.swing.SwingUtilities;
import java.sql.SQLException; // Importa SQLException si usas JDBC

public class Main {

    // Define aquí las rutas a los archivos o la configuración de la BD
    // Opción 1: Rutas para persistencia en Archivos
    private static final String RUTA_CONCURSOS_TXT = "concursos.txt"; // Asegúrate que el archivo exista o la ruta sea correcta
    private static final String RUTA_INSCRIPTOS_TXT = "inscriptos.txt"; // Se creará si no existe

    // Opción 2: Configuración para persistencia JDBC (Ejemplo H2 en archivo)
    private static final String DB_URL = "jdbc:h2:./database/radio_competition_db"; // Crea carpeta 'database' en raíz del proyecto
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Elige aquí qué tipo de persistencia usar:
                boolean usarBaseDeDatos = false; // Cambia a true para usar JDBC

                new Main().start(usarBaseDeDatos);

            } catch (Exception e) {
                // Log exception o mostrar un mensaje de error más amigable
                System.err.println("Error fatal al iniciar la aplicación:");
                e.printStackTrace(); // Imprime el stack trace en la consola de error
                // Considera mostrar un JOptionPane de error aquí si es crítico
                // JOptionPane.showMessageDialog(null, "No se pudo iniciar la aplicación.\n" + e.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /**
     * Configura las dependencias (repositorios) y lanza la interfaz gráfica.
     * @param usarBD True para usar persistencia JDBC, False para usar archivos de texto.
     */
    private void start(boolean usarBD) {
        ConcursoRepository repoConcursos;
        InscripcionRepository repoInscripciones;

        if (usarBD) {
            System.out.println("Usando persistencia con Base de Datos (JDBC)...");
            try {
                // Opcional: Asegurar que las tablas existan al inicio
                SetupBaseDeDatos.crearTablasSiNoExisten(DB_URL, DB_USER, DB_PASSWORD);
                System.out.println("Configuración de base de datos completada.");

                repoConcursos = new JdbcConcursoRepository(DB_URL, DB_USER, DB_PASSWORD);
                repoInscripciones = new JdbcInscripcionRepository(DB_URL, DB_USER, DB_PASSWORD);

            } catch (SQLException e) {
                System.err.println("Error al configurar o conectar con la base de datos: " + e.getMessage());
                // Fallback a archivos o lanzar error? Por ahora, lanzamos RuntimeException
                throw new RuntimeException("No se pudo inicializar la persistencia JDBC", e);
            }
        } else {
            System.out.println("Usando persistencia con Archivos de Texto...");
            // Asegúrate que los archivos existan o las rutas sean correctas
            // Podrías añadir lógica aquí para verificar/crear los archivos si es necesario
            repoConcursos = new ArchivoConcursoRepository(RUTA_CONCURSOS_TXT);
            repoInscripciones = new ArchivoInscripcionRepository(RUTA_INSCRIPTOS_TXT);
        }

        // Crear la vista inyectando las implementaciones de repositorio elegidas
        // Ahora la llamada al constructor coincide con la firma requerida
        new RadioCompetitionView(repoConcursos, repoInscripciones);
    }
}
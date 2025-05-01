package ar.unrn.tp4.ejercicio3.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SetupBaseDeDatos {

    public static void crearTablasSiNoExisten(String url, String user, String password) throws SQLException {
        // SQL para crear tabla CONCURSOS (IF NOT EXISTS es seguro)
        String sqlCreateTableConcursos = "CREATE TABLE IF NOT EXISTS concursos (" +
                "    id_concurso INT PRIMARY KEY," + // Usamos el ID del archivo como PK natural
                "    nombre VARCHAR(255) NOT NULL," +
                "    fecha_inicio_inscripcion DATE NOT NULL," +
                "    fecha_fin_inscripcion DATE NOT NULL" +
                ");";

        // SQL para crear tabla INSCRIPTOS (IF NOT EXISTS es seguro)
        String sqlCreateTableInscriptos = "CREATE TABLE IF NOT EXISTS inscriptos (" +
                "    id_inscripcion INT AUTO_INCREMENT PRIMARY KEY," + // PK surrogate autogenerada
                "    apellido VARCHAR(100) NOT NULL," +
                "    nombre VARCHAR(100) NOT NULL," +
                "    dni VARCHAR(20) NOT NULL," + // Añadido DNI
                "    telefono VARCHAR(20) NOT NULL," +
                "    email VARCHAR(150) NOT NULL," +
                // "    email VARCHAR(150) NOT NULL UNIQUE," + // Considerar si el email debe ser único
                "    id_concurso INT NOT NULL," +
                "    fecha_inscripcion TIMESTAMP DEFAULT CURRENT_TIMESTAMP," + // Fecha/Hora registro
                "    FOREIGN KEY (id_concurso) REFERENCES concursos(id_concurso) ON DELETE RESTRICT" + // Evitar borrar concursos con inscriptos
                ");";

        // Crear índices (IF NOT EXISTS es seguro en H2 y MySQL modernos)
        String sqlIndexConcursosFechas = "CREATE INDEX IF NOT EXISTS idx_concurso_fechas ON concursos(fecha_inicio_inscripcion, fecha_fin_inscripcion);";
        String sqlIndexInscriptosConcurso = "CREATE INDEX IF NOT EXISTS idx_inscripto_concurso ON inscriptos(id_concurso);";
        String sqlIndexInscriptosEmail = "CREATE INDEX IF NOT EXISTS idx_inscripto_email ON inscriptos(email);"; // Si email es buscado frecuentemente


        // Usar try-with-resources para asegurar cierre de conexión y statement
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {

            System.out.println("Verificando/Creando tabla 'concursos'...");
            stmt.execute(sqlCreateTableConcursos);
            System.out.println("Tabla 'concursos' lista.");

            System.out.println("Verificando/Creando tabla 'inscriptos'...");
            stmt.execute(sqlCreateTableInscriptos);
            System.out.println("Tabla 'inscriptos' lista.");

            System.out.println("Verificando/Creando índices...");
            try { stmt.execute(sqlIndexConcursosFechas); } catch (SQLException ignored) { System.out.println("Índice idx_concurso_fechas ya existe o no soportado."); }
            try { stmt.execute(sqlIndexInscriptosConcurso); } catch (SQLException ignored) { System.out.println("Índice idx_inscripto_concurso ya existe o no soportado."); }
            try { stmt.execute(sqlIndexInscriptosEmail); } catch (SQLException ignored) { System.out.println("Índice idx_inscripto_email ya existe o no soportado."); }
            System.out.println("Índices listos.");

        } catch (SQLException e) {
            System.err.println("Error durante la configuración de la base de datos: " + e.getMessage());
            throw e;
        }
    }
}
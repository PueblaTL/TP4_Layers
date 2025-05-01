package ar.unrn.tp4.ejercicio3.persistencia;

import ar.unrn.tp4.ejercicio3.modelo.Concurso;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcConcursoRepository implements ConcursoRepository {
    private String dbUrl;
    private String user;
    private String password;

    public JdbcConcursoRepository(String dbUrl, String user, String password) {
        this.dbUrl = dbUrl; // e.g., "jdbc:h2:./radio_db"
        this.user = user;
        this.password = password;
        // Consider loading driver explicitly if needed: Class.forName("org.h2.Driver");
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, user, password);
    }

    @Override
    public List<Concurso> concursosAbiertos() {
        List<Concurso> concursos = new ArrayList<>();
        String sql = "SELECT id_concurso, nombre, fecha_inicio_inscripcion, fecha_fin_inscripcion " +
                "FROM concursos WHERE ? BETWEEN fecha_inicio_inscripcion AND fecha_fin_inscripcion";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, Date.valueOf(LocalDate.now()));

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // Ensure Concurso constructor matches expected date format (yyyy-MM-dd from DB)
                    // Or format here: rs.getDate(...).toLocalDate().format(DateTimeFormatter.ISO_DATE) -> yyyy-MM-dd
                    // Or format here: rs.getDate(...).toLocalDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
                    concursos.add(new Concurso(
                            rs.getInt("id_concurso"),
                            rs.getString("nombre"),
                            // Adjust date format based on Concurso constructor expectation
                            rs.getDate("fecha_inicio_inscripcion").toLocalDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                            rs.getDate("fecha_fin_inscripcion").toLocalDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar con la base de datos", e);
        }
        return concursos;
    }
}
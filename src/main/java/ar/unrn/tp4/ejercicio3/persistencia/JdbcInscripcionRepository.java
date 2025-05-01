package ar.unrn.tp4.ejercicio3.persistencia;

import ar.unrn.tp4.ejercicio3.modelo.Concurso;
import ar.unrn.tp4.ejercicio3.modelo.Inscripcion;
import ar.unrn.tp4.ejercicio3.modelo.Participante;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class JdbcInscripcionRepository implements InscripcionRepository {
    private String dbUrl;
    private String user;
    private String password;

    public JdbcInscripcionRepository(String dbUrl, String user, String password) {
        this.dbUrl = dbUrl;
        this.user = user;
        this.password = password;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, user, password);
    }

    @Override
    public void save(Inscripcion inscripcion) {
        String sql = "INSERT INTO inscriptos (apellido, nombre, dni, telefono, email, id_concurso) VALUES (?, ?, ?, ?, ?, ?)";
        Participante p = inscripcion.getParticipante();
        Concurso c = inscripcion.getConcurso();

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, p.getApellido());
            pstmt.setString(2, p.getNombre());
            pstmt.setString(3, p.getDni());
            pstmt.setString(4, p.getTelefono());
            pstmt.setString(5, p.getEmail());
            pstmt.setInt(6, c.getId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error guardando la inscripcion", e);
        }
    }
}
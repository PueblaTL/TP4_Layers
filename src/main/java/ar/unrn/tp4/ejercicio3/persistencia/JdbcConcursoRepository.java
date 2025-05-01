package ar.unrn.tp4.ejercicio3.persistencia;

import ar.unrn.tp4.ejercicio3.modelo.Concurso;
import ar.unrn.tp4.ejercicio3.modelo.ConcursoRepository;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class JdbcConcursoRepository implements ConcursoRepository {
    private String dbUrl;
    private String user;
    private String password;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public JdbcConcursoRepository(String dbUrl, String user, String password) {
        this.dbUrl = dbUrl;
        this.user = user;
        this.password = password;
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
                    LocalDate fechaInicio = rs.getDate("fecha_inicio_inscripcion").toLocalDate();
                    LocalDate fechaFin = rs.getDate("fecha_fin_inscripcion").toLocalDate();

                    concursos.add(new Concurso(
                            rs.getInt("id_concurso"),
                            rs.getString("nombre"),
                            fechaInicio.format(DATE_FORMATTER),
                            fechaFin.format(DATE_FORMATTER)
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar con la base de datos", e);
        }
        return concursos;
    }
}
package ar.unrn.tp4.ejercicio1.database;

import ar.unrn.tp4.ejercicio1.model.Participante;
import ar.unrn.tp4.ejercicio1.model.RegistroParticipante;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCRegistroParticipante implements RegistroParticipante {
    private String url;
    private String user;
    private String password;

    public JDBCRegistroParticipante(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }


    @Override
    public void registrarParticipate(Participante participante) {
        String query = "insert into participantes(nombre, telefono, region) values(?,?,?)";

        try (
                Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement st = conn.prepareStatement(query);
        ) {
            st.setString(1, participante.getNombre());
            st.setString(2, participante.getTelefono());
            st.setString(3, participante.getRegion());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void registrarParticipante(Participante participante) {

    }
}
package ar.unrn.tp4.ejercicio3.persistencia;

import ar.unrn.tp4.ejercicio3.modelo.Concurso;
import ar.unrn.tp4.ejercicio3.modelo.Inscripcion;
import ar.unrn.tp4.ejercicio3.modelo.InscripcionRepository;
import ar.unrn.tp4.ejercicio3.modelo.Participante;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ArchivoInscripcionRepository implements InscripcionRepository {
    private final String filePath;

    public ArchivoInscripcionRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void save(Inscripcion inscripcion) {
        Participante p = inscripcion.getParticipante();
        Concurso c = inscripcion.getConcurso();

        // Formatear según el requisito: apellido, nombre, teléfono, email, idconcurso
        String line = String.join(", ",
                p.getApellido(),
                p.getNombre(),
                p.getTelefono(),
                p.getEmail(),
                String.valueOf(c.getId())
        );

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error escribiendo el archivo: " + filePath, e);
        }
    }
}
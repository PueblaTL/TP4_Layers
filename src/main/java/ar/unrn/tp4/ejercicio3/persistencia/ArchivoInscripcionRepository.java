package ar.unrn.tp4.ejercicio3.persistencia;

import ar.unrn.tp4.ejercicio3.modelo.Concurso;
import ar.unrn.tp4.ejercicio3.modelo.Inscripcion;
import ar.unrn.tp4.ejercicio3.modelo.Participante;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ArchivoInscripcionRepository implements InscripcionRepository {
    private final String filePath; // = "src/main/resources/inscriptos.txt";

    // Constructor para inyectar la ruta
    public ArchivoInscripcionRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void save(Inscripcion inscripcion) {
        Participante p = inscripcion.getParticipante();
        Concurso c = inscripcion.getConcurso();
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
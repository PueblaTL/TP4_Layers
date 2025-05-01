package ar.unrn.tp4.ejercicio3.persistencia;

import ar.unrn.tp4.ejercicio3.modelo.Concurso;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArchivoConcursoRepository implements ConcursoRepository {
    private final String filePath; // = "src/main/resources/concursos.txt";

    // Constructor para inyectar la ruta (mejor práctica)
    public ArchivoConcursoRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Concurso> concursosAbiertos() {
        List<Concurso> concursos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    try {
                        Concurso concurso = new Concurso(
                                Integer.parseInt(parts[0].trim()),
                                parts[1].trim(),
                                parts[2].trim(),
                                parts[3].trim()
                        );
                        if (concurso.estaAbierto()) {
                            concursos.add(concurso);
                        }
                    } catch (Exception e) { // Catch broader exception for parsing/date issues
                        System.err.println("Skipping invalid line in concursos.txt: " + line + " - " + e.getMessage());
                    }
                } else {
                    System.err.println("Skipping malformed line in concursos.txt: " + line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading concursos file: " + filePath, e);
        }
        return concursos;
    }
}
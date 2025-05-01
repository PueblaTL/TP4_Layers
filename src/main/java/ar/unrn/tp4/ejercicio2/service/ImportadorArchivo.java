package ar.unrn.tp4.ejercicio2.service;

import ar.unrn.tp4.ejercicio2.model.Importador;
import ar.unrn.tp4.ejercicio2.model.Persona;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImportadorArchivo implements Importador {
    private String path;

    public ImportadorArchivo(String path) {
        this.path = path;
    }

    @Override
    public List<Persona> importarPersonas() {
        List<Persona> personas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",\\s*");
                if (partes.length == 4) {
                    String apellido = partes[0].trim();
                    String nombre = partes[1].trim();
                    String fechaStr = partes[2].trim(); // Ya en formato yyyy/MM/dd
                    String email = partes[3].trim();

                    personas.add(new Persona(apellido, nombre, fechaStr, email));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Hubo un error al leer los datos del archivo: " + e.getMessage(), e);
        }

        return personas;
    }
}
package ar.unrn.tp4.ejercicio3.modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Concurso {
    private int id;
    private String nombre;
    private LocalDate fechaInicioInscripcion;
    private LocalDate fechaFinInscripcion;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public Concurso(int id, String nombre, String fechaInicio, String fechaFin) {
        this.id = id;
        this.nombre = nombre;
        this.fechaInicioInscripcion = LocalDate.parse(fechaInicio, DATE_FORMATTER);
        this.fechaFinInscripcion = LocalDate.parse(fechaFin, DATE_FORMATTER);
    }

    public boolean estaAbierto() {
        LocalDate hoy = LocalDate.now();
        return !hoy.isBefore(fechaInicioInscripcion) && !hoy.isAfter(fechaFinInscripcion);
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }

    @Override
    public String toString() {
        return nombre;
    }
    // Getters...
}
package ar.unrn.tp4.ejercicio2.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Persona {
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String correo;

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public Persona(String apellido, String nombre, LocalDate fechaNacimiento, String correo) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.correo = correo;
    }

    public Persona(String apellido, String nombre, String fechaNacimientoStr, String correo) {
        this.apellido = apellido;
        this.nombre = nombre;
        // Convertimos directamente desde el formato yyyy/MM/dd sin reemplazar caracteres
        this.fechaNacimiento = LocalDate.parse(fechaNacimientoStr, FORMATO_FECHA);
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getCorreo() {
        return correo;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", correo='" + correo + '\'' +
                '}';
    }

    public boolean cumpleAños() {
        LocalDate hoy = LocalDate.now();
        return this.fechaNacimiento.getMonth() == hoy.getMonth() &&
                this.fechaNacimiento.getDayOfMonth() == hoy.getDayOfMonth();
    }
}
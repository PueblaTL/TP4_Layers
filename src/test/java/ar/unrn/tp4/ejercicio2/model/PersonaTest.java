package ar.unrn.tp4.ejercicio2.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class PersonaTest {

    @Test
    public void testConstructorYGetters() {
        LocalDate fechaNacimiento = LocalDate.of(1990, 5, 10);
        Persona persona = new Persona("Pérez", "Juan", fechaNacimiento, "juan.perez@mail.com");

        assertEquals("Juan", persona.getNombre());
        assertEquals("Pérez", persona.getApellido());
        assertEquals(fechaNacimiento, persona.getFechaNacimiento());
        assertEquals("juan.perez@mail.com", persona.getCorreo());
    }

    @Test
    public void testConstructorConFechaString() {
        String fechaNacimientoStr = "1990/05/10";
        Persona persona = new Persona("Pérez", "Juan", fechaNacimientoStr, "juan.perez@mail.com");

        assertEquals("Juan", persona.getNombre());
        assertEquals("Pérez", persona.getApellido());
        assertEquals(LocalDate.of(1990, 5, 10), persona.getFechaNacimiento());
        assertEquals("juan.perez@mail.com", persona.getCorreo());
    }

    @Test
    public void testGetNombreCompleto() {
        Persona persona = new Persona("Pérez", "Juan", LocalDate.of(1990, 5, 10), "juan.perez@mail.com");
        assertEquals("Juan Pérez", persona.getNombreCompleto());
    }

    @Test
    public void testToString() {
        Persona persona = new Persona("Pérez", "Juan", LocalDate.of(1990, 5, 10), "juan.perez@mail.com");
        String expected = "Persona{nombre='Juan', apellido='Pérez', fechaNacimiento=1990-05-10, correo='juan.perez@mail.com'}";
        assertEquals(expected, persona.toString());
    }

    @Test
    public void testNoCumpleAños() {
        // Creamos una persona con una fecha de cumpleaños diferente a hoy
        LocalDate fechaNacimiento = LocalDate.of(1990, 5, 10); // No es el día de hoy
        Persona persona = new Persona("Pérez", "Juan", fechaNacimiento, "juan.perez@mail.com");

        assertFalse(persona.cumpleAños());
    }
}

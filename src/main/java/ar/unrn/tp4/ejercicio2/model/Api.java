package ar.unrn.tp4.ejercicio2.model;

import java.util.List;

public class Api {
    private Notificador notificador;
    private Importador importador;

    public Api(Notificador notificador, Importador importador) {
        this.notificador = notificador;
        this.importador = importador;
    }

    public List<Persona> obtenerPersonas() {
        return importador.importarPersonas();
    }

    public void saludarCumpleaños(Persona persona) {
        if (persona.cumpleAños()) {
            notificador.saludarCumpleaños(persona);
        }
    }

    public void saludarCumpleañeros() {
        List<Persona> personas = this.obtenerPersonas();
        for (Persona persona : personas) {
            this.saludarCumpleaños(persona);
        }
    }
}
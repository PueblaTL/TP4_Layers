package ar.unrn.tp4.ejercicio1.model;

public class Concurso {
    private RegistroParticipante registroParticipante;

    public Concurso(RegistroParticipante registroParticipante) {
        this.registroParticipante = registroParticipante;
    }

    public void registrarParticipante(String nombre, String telefono, String region) {
        Participante participante = new Participante(nombre, telefono, region);
        this.registroParticipante.registrarParticipante(participante);
    }
}
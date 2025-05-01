package ar.unrn.tp4.ejercicio3.modelo;

public class Inscripcion {
    private Participante participante;
    private Concurso concurso;

    public Inscripcion(Participante participante, Concurso concurso) {
        // Consider adding null checks here
        this.participante = participante;
        this.concurso = concurso;
    }

    public Participante getParticipante() { return participante; }
    public Concurso getConcurso() { return concurso; }
    // Getters...
}
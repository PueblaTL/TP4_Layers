package ar.unrn.tp4.ejercicio3.modelo;

import ar.unrn.tp4.ejercicio3.modelo.Concurso;

import java.util.List;

public interface ConcursoRepository {
    List<Concurso> concursosAbiertos();
}
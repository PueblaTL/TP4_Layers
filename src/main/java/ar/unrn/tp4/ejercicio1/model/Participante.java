package ar.unrn.tp4.ejercicio1.model;

public class Participante {
    private String nombre;
    private String telefono;
    private String region;

    public Participante(String nombre, String telefono, String region) {
        if (nombre.equals("")) {
            throw new RuntimeException("El participante tiene que tener un nombre.");
        }
        if (telefono.equals("")) {
            throw new RuntimeException("El participante tiene que tener un telefono");
        }
        if (!validarTelefono(telefono)) {
            throw new RuntimeException("El numero de telefono tiene que tener el siguiente formato: NNNN-NNNNNN");
        }
        if (!region.equals("China") && !region.equals("US") && !
                region.equals("Europa")) {
            throw new RuntimeException("Region desconocida. Las conocidas son:China, US, Europa");
        }
        this.nombre = nombre;
        this.telefono = telefono;
        this.region = region;
    }

    private boolean validarTelefono(String telefono) {
        String regex = "\\d{4}-\\d{6}";
        return telefono.matches(regex);
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public String getRegion() {
        return this.region;
    }
}
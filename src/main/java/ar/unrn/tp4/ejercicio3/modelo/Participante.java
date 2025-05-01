package ar.unrn.tp4.ejercicio3.modelo;

public class Participante {
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String email;

    public Participante(String nombre, String apellido, String dni, String telefono, String email) {
        if (nombre == null || nombre.trim().isEmpty()) throw new RuntimeException("Nombre no puede ser vacio");
        if (apellido == null || apellido.trim().isEmpty()) throw new RuntimeException("Apellido no puede ser vacio");
        if (dni == null || dni.trim().isEmpty()) throw new RuntimeException("DNI no puede ser vacio");
        if (!validarEmail(email)) throw new RuntimeException("Email debe ser válido");
        if (!validarTelefono(telefono)) throw new RuntimeException("El teléfono debe ingresarse de la siguiente forma: NNNN-NNNNNN");

        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
        this.email = email;
    }

    private boolean validarEmail(String email) {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email != null && email.matches(regex);
    }

    private boolean validarTelefono(String telefono) {
        String regex = "\\d{4}-\\d{6}";
        return telefono != null && telefono.matches(regex);
    }

    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }
    public String getDni() { return dni; }
    // Getters...
}
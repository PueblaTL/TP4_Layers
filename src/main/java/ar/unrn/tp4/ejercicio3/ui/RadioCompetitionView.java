package ar.unrn.tp4.ejercicio3.ui;

import ar.unrn.tp4.ejercicio3.modelo.Concurso;
import ar.unrn.tp4.ejercicio3.modelo.ConcursoRepository;
import ar.unrn.tp4.ejercicio3.modelo.Inscripcion;
import ar.unrn.tp4.ejercicio3.modelo.InscripcionRepository;
import ar.unrn.tp4.ejercicio3.modelo.Participante;


import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RadioCompetitionView extends JFrame {

    // Repositorios inyectados (dependencias hacia las interfaces del modelo)
    private final ConcursoRepository concursoRepository;
    private final InscripcionRepository inscripcionRepository;

    // Componentes de la UI
    private JComboBox<Concurso> comboBoxConcursos; // Renombrado para claridad
    private JButton btnOk;
    private JTextField txtName;
    private JTextField txtLastName;
    private JTextField txtId; // Asumo que es DNI
    private JTextField txtPhone;
    private JTextField txtEmail;

    /**
     * Constructor que recibe las dependencias de los repositorios.
     *
     * @param concursoRepo      Implementación de ConcursoRepository (Archivo o JDBC).
     * @param inscripcionRepo Implementación de InscripcionRepository (Archivo o JDBC).
     */
    public RadioCompetitionView(ConcursoRepository concursoRepo, InscripcionRepository inscripcionRepo) {
        // Inyección de dependencias
        this.concursoRepository = concursoRepo;
        this.inscripcionRepository = inscripcionRepo;

        // Configuración básica de la ventana
        setTitle("Inscripción a Concurso");
        setSize(450, 350); // Tamaño ajustado
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar en pantalla

        // Inicializar y organizar componentes
        initComponents();
        setupLayout();

        // Cargar los concursos disponibles al iniciar
        cargarConcursosDisponibles();

        // Hacer visible la ventana
        setVisible(true);
    }

    /**
     * Inicializa los componentes visuales de la interfaz gráfica.
     */
    private void initComponents() {
        txtName = new JTextField(20);
        txtLastName = new JTextField(20);
        txtId = new JTextField(20);
        txtPhone = new JTextField(20);
        txtEmail = new JTextField(20);
        comboBoxConcursos = new JComboBox<>();
        btnOk = new JButton("Inscribir");

        // Configurar el ActionListener del botón para llamar a guardarInscripcion
        btnOk.addActionListener(e -> guardarInscripcion());
    }

    /**
     * Configura la disposición de los componentes en la ventana usando GridBagLayout.
     */
    private void setupLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Márgenes entre componentes
        gbc.anchor = GridBagConstraints.WEST; // Alinear a la izquierda

        // Fila Apellido
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Apellido:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        add(txtLastName, gbc);
        gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0; // Reset

        // Fila Nombre
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        add(txtName, gbc);
        gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;

        // Fila DNI
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("DNI:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        add(txtId, gbc);
        gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;

        // Fila Teléfono
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Teléfono (NNNN-NNNNNN):"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        add(txtPhone, gbc);
        gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;

        // Fila Email
        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        add(txtEmail, gbc);
        gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;

        // Fila Concurso
        gbc.gridx = 0; gbc.gridy = 5;
        add(new JLabel("Concurso:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        add(comboBoxConcursos, gbc);
        gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;

        // Fila Botón
        gbc.gridx = 1; gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.EAST; // Alinear botón a la derecha
        add(btnOk, gbc);
    }


    /**
     * Carga los concursos cuya inscripción está abierta desde la capa de persistencia
     * (a través de la interfaz ConcursoRepository) y los muestra en el ComboBox.
     */
    private void cargarConcursosDisponibles() {
        comboBoxConcursos.removeAllItems(); // Limpiar antes de cargar

        try {
            // Usa la interfaz inyectada ConcursoRepository
            List<Concurso> concursos = this.concursoRepository.concursosAbiertos();

            if (concursos.isEmpty()) {
                // Informar al usuario si no hay concursos.
                JOptionPane.showMessageDialog(this,
                        "No hay concursos abiertos disponibles en este momento.",
                        "Información", JOptionPane.INFORMATION_MESSAGE);
                // Opcional: Deshabilitar el formulario si no hay concursos
                btnOk.setEnabled(false);
                comboBoxConcursos.setEnabled(false);
            } else {
                // Poblar el ComboBox con los objetos Concurso.
                // El método toString() de Concurso se usará para mostrar el nombre.
                for (Concurso c : concursos) {
                    comboBoxConcursos.addItem(c);
                }
                btnOk.setEnabled(true); // Asegurarse que esté habilitado si hay concursos
                comboBoxConcursos.setEnabled(true);
            }
        } catch (RuntimeException e) { // Captura errores de Runtime (ej. error de conexión a BD o lectura de archivo)
            JOptionPane.showMessageDialog(this,
                    "Error al cargar los concursos: " + e.getMessage(),
                    "Error de Carga", JOptionPane.ERROR_MESSAGE);
            // Loggear el error 'e' sería bueno en un escenario real.
            btnOk.setEnabled(false); // Deshabilitar si no se pudieron cargar
            comboBoxConcursos.setEnabled(false);
        } catch (Exception e) { // Captura genérica por si acaso
            JOptionPane.showMessageDialog(this,
                    "Ocurrió un error inesperado al cargar concursos.",
                    "Error Inesperado", JOptionPane.ERROR_MESSAGE);
            // Loggear el error 'e'
            btnOk.setEnabled(false);
            comboBoxConcursos.setEnabled(false);
        }
    }

    /**
     * Intenta guardar la inscripción del participante en el concurso seleccionado.
     * Realiza validaciones de UI, crea los objetos de dominio (Participante, Inscripcion)
     * y llama al repositorio de inscripciones para persistir los datos.
     */
    private void guardarInscripcion() {
        // 1. Validación de la UI
        if (!validations()) {
            return; // Detener si la validación básica de la UI falla
        }

        // 2. Obtener datos de la UI
        String nombre = txtName.getText().trim();
        String apellido = txtLastName.getText().trim();
        String dni = txtId.getText().trim();
        String telefono = txtPhone.getText().trim();
        String email = txtEmail.getText().trim();
        Concurso concursoSeleccionado = (Concurso) comboBoxConcursos.getSelectedItem();

        // 3. Verificación adicional (aunque validations() ya lo cubre)
        if (concursoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un concurso.", "Error de Selección", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 4. Deshabilitar botón para evitar doble submit mientras procesa
        btnOk.setEnabled(false);

        try {
            // 5. Crear objeto del dominio (Participante)
            // La validación de formato/negocio está en el constructor de Participante
            Participante nuevoParticipante = new Participante(nombre, apellido, dni, telefono, email);

            // 6. Crear objeto del dominio (Inscripcion)
            Inscripcion nuevaInscripcion = new Inscripcion(nuevoParticipante, concursoSeleccionado);

            // 7. Llamar a la capa de persistencia a través de la interfaz inyectada
            this.inscripcionRepository.save(nuevaInscripcion);

            // 8. Feedback al usuario (éxito)
            JOptionPane.showMessageDialog(this,
                    "¡Inscripción registrada exitosamente!",
                    "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);

            // 9. Limpiar formulario para nueva entrada
            limpiarFormulario();

        } catch (RuntimeException ex) { // Captura errores de validación de Participante o errores de Repositorio
            JOptionPane.showMessageDialog(this,
                    "Error al procesar la inscripción: " + ex.getMessage(),
                    "Error de Datos o Persistencia", JOptionPane.WARNING_MESSAGE);
            // Loggear error ex
        }
        // Puedes añadir un catch específico para ModeloException si la usas en los repositorios
        // catch (ModeloException me) { ... }
        catch (Exception e) { // Captura genérica final para errores inesperados
            JOptionPane.showMessageDialog(this,
                    "Ocurrió un error inesperado al guardar la inscripción.",
                    "Error Inesperado", JOptionPane.ERROR_MESSAGE);
            // Loggear el error 'e'
        } finally {
            // 10. Rehabilitar el botón independientemente del resultado
            // (solo si hay concursos disponibles)
            if (comboBoxConcursos.getItemCount() > 0) {
                btnOk.setEnabled(true);
            }
        }
    }

    // --- Métodos auxiliares de la UI (Validación y Limpieza) ---

    /**
     * Realiza validaciones básicas de los campos del formulario.
     * Verifica que no estén vacíos y que teléfono y email tengan formatos válidos.
     *
     * @return true si todas las validaciones pasan, false en caso contrario.
     */
    private boolean validations() {
        if (txtLastName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Apellido no puede ser vacío.", "Dato Requerido", JOptionPane.WARNING_MESSAGE); return false; }
        if (txtName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombre no puede ser vacío.", "Dato Requerido", JOptionPane.WARNING_MESSAGE); return false; }
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "DNI no puede ser vacío.", "Dato Requerido", JOptionPane.WARNING_MESSAGE); return false; }
        if (txtPhone.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Teléfono no puede ser vacío.", "Dato Requerido", JOptionPane.WARNING_MESSAGE); return false; }
        if (!checkPhone(txtPhone.getText().trim())) {
            JOptionPane.showMessageDialog(this, "El teléfono debe tener el formato NNNN-NNNNNN.", "Formato Inválido", JOptionPane.WARNING_MESSAGE); return false; }
        if (txtEmail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email no puede ser vacío.", "Dato Requerido", JOptionPane.WARNING_MESSAGE); return false; }
        if (!checkEmail(txtEmail.getText().trim())) {
            JOptionPane.showMessageDialog(this, "Email no válido.", "Formato Inválido", JOptionPane.WARNING_MESSAGE); return false; }
        if (comboBoxConcursos.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un concurso.", "Dato Requerido", JOptionPane.WARNING_MESSAGE); return false; }
        return true;
    }

    /**
     * Verifica si una cadena tiene el formato de email básico.
     * @param email La cadena a verificar.
     * @return true si parece un email válido, false en caso contrario.
     */
    private boolean checkEmail(String email) {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email != null && email.matches(regex);
    }

    /**
     * Verifica si una cadena tiene el formato telefónico NNNN-NNNNNN.
     * @param telefono La cadena a verificar.
     * @return true si cumple el formato, false en caso contrario.
     */
    private boolean checkPhone(String telefono) {
        String regex = "\\d{4}-\\d{6}";
        return telefono != null && telefono.matches(regex);
    }

    /**
     * Limpia todos los campos de entrada del formulario.
     */
    private void limpiarFormulario() {
        txtLastName.setText("");
        txtName.setText("");
        txtId.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        // Si hay concursos, selecciona el primero, si no, deja sin selección
        if (comboBoxConcursos.getItemCount() > 0) {
            comboBoxConcursos.setSelectedIndex(0);
        } else {
            comboBoxConcursos.setSelectedIndex(-1);
        }
        txtLastName.requestFocus(); // Pone el foco en el primer campo
    }
}
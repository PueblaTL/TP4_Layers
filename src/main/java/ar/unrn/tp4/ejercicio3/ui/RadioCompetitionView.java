package ar.unrn.tp4.ejercicio3.ui;

import ar.unrn.tp4.ejercicio3.modelo.Concurso;
import ar.unrn.tp4.ejercicio3.modelo.Participante;
import ar.unrn.tp4.ejercicio3.persistencia.ArchivoInscripcionRepository;
import ar.unrn.tp4.ejercicio3.persistencia.JdbcConcursoRepository;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RadioCompetition extends JFrame { // Hereda de JFrame directamente

    private JPanel contentPane;
    private JLabel lblName;
    private JTextField txtName;
    private JLabel lblLastName;
    private JTextField txtLastName;
    private JLabel lblId; // Cambiado de Iblld a lblId para consistencia
    private JTextField txtId;
    private JLabel lblPhone; // Cambiado de IbIPhone a lblPhone
    private JTextField txtPhone;
    private JLabel lblEmail; // Cambiado de IblEmail a lblEmail
    private JTextField txtEmail;
    private JComboBox<Concurso> comboBox; // Ahora guarda objetos Concurso
    private JButton btnOk;
    private JLabel lblCompetition; // Cambiado de IblCompetition a lblCompetition

    // Inyección de dependencia (podría hacerse con un framework o manualmente)
    private RegistroConcursos registroConcursos;

    public RadioCompetition(RegistroConcursos registro) { // Recibe la dependencia
        this.registroConcursos = registro;
        initComponents();
        setupLayout();
        todosLosConcursos(); // Cargar concursos al iniciar
        setVisible(true);
    }

    private void initComponents() {
        setTitle("Inscription to Competition");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 451, 260); // Ajustado el alto
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        lblName = new JLabel("Nombre:");
        txtName = new JTextField();
        txtName.setColumns(10);

        lblLastName = new JLabel("Apellido:");
        txtLastName = new JTextField();
        txtLastName.setColumns(10);

        lblId = new JLabel("Dni:");
        txtId = new JTextField();
        txtId.setColumns(10);

        lblPhone = new JLabel("Telefono:");
        txtPhone = new JTextField();
        txtPhone.setColumns(10);

        lblEmail = new JLabel("Email:");
        txtEmail = new JTextField();
        txtEmail.setColumns(10);

        lblCompetition = new JLabel("Concurso:");
        comboBox = new JComboBox<>(); // Tipado con Concurso

        btnOk = new JButton("Ok");
        btnOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveInscription();
            }
        });
    }

    /**
     * Llama a la capa de persistencia para obtener los concursos abiertos
     * y los carga en el ComboBox.
     */
    private void todosLosConcursos() { // [cite: 4]
        comboBox.removeAllItems(); // Limpiar items previos
        // Añadir item por defecto o placeholder si se desea
        // comboBox.addItem(null); // O un objeto Concurso especial

        try {
            List<Concurso> concursos = registroConcursos.todosLosConcursosAbiertos();
            if (concursos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay concursos abiertos disponibles.", "Información", JOptionPane.INFORMATION_MESSAGE);
            } else {
                for (Concurso c : concursos) {
                    comboBox.addItem(c); // Añade el objeto Concurso directamente [cite: 5]
                }
            }
        } catch (ModeloException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar los concursos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Valida los datos del formulario y llama a la capa de persistencia
     * para guardar la inscripción.
     */
    private void saveInscription() { // [cite: 5]
        if (!validations()) {
            return; // Si la validación falla, no continuar
        }

        btnOk.setEnabled(false); // Deshabilitar botón mientras procesa

        try {
            // Crear objeto Participante con los datos del formulario
            Participante nuevoParticipante = new Participante(
                    txtName.getText(),
                    txtLastName.getText(),
                    txtId.getText(),
                    txtPhone.getText(),
                    txtEmail.getText()
            );

            // Obtener el concurso seleccionado (el objeto Concurso entero)
            Concurso concursoSeleccionado = (Concurso) comboBox.getSelectedItem();

            // Llamar a la capa de persistencia para guardar
            registroConcursos.saveInscription(nuevoParticipante, concursoSeleccionado.getId()); // [cite: 5]

            JOptionPane.showMessageDialog(this, "Inscripción guardada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            // Opcional: Limpiar formulario después de guardar
            limpiarFormulario();

        } catch (ModeloException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar la inscripción: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(this, "Error inesperado al obtener datos del concurso.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            btnOk.setEnabled(true); // Rehabilitar botón
        }
    }

    private void limpiarFormulario() {
        txtName.setText("");
        txtLastName.setText("");
        txtId.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        comboBox.setSelectedIndex(-1); // O 0 si hay un item placeholder
    }

    /**
     * Realiza las validaciones básicas de los campos del formulario.
     * @return true si todas las validaciones pasan, false en caso contrario.
     */
    private boolean validations() { // [cite: 19, 20, 21, 22, 23]
        if (txtName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombre no puede ser vacío"); // [cite: 19]
            return false;
        }
        if (txtLastName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Apellido no puede ser vacío"); // [cite: 20]
            return false;
        }
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "DNI no puede ser vacío"); // [cite: 20]
            return false;
        }
        if (txtPhone.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Teléfono no puede ser vacío");
            return false;
        }
        if (!checkPhone(txtPhone.getText().trim())) { // [cite: 22]
            JOptionPane.showMessageDialog(this, "El teléfono debe ingresarse de la siguiente forma: NNNN-NNNNNN"); // [cite: 22]
            return false;
        }
        if (txtEmail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email no puede ser vacío");
            return false;
        }
        if (!checkEmail(txtEmail.getText().trim())) { // [cite: 21]
            JOptionPane.showMessageDialog(this, "Email debe ser válido"); // [cite: 21]
            return false;
        }
        if (comboBox.getSelectedItem() == null || comboBox.getSelectedIndex() < 0) { // Valida selección [cite: 23]
            JOptionPane.showMessageDialog(this, "Debe elegir un Concurso"); // [cite: 23]
            return false;
        }
        return true; // [cite: 23]
    }

    // Métodos de validación de formato (podrían estar en una clase de utilidad)
    private boolean checkEmail(String email) { // [cite: 24]
        // Regex simple para validación de email
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"; // [cite: 24] Ajustado para Java
        return email.matches(regex); // [cite: 24]
    }

    private boolean checkPhone(String telefono) { // [cite: 24]
        // Regex para formato NNNN-NNNNNN
        String regex = "\\d{4}-\\d{6}"; // [cite: 24] Simplificado para Java
        return telefono.matches(regex); // [cite: 24]
    }


    // --- Layout (copiado y adaptado del original, nombres de variables cambiados) ---
    private void setupLayout() { // [cite: 25, 26, 27]
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                                        .addComponent(lblLastName)
                                                        .addComponent(lblId)
                                                        .addComponent(lblPhone)
                                                        .addComponent(lblEmail)
                                                        .addComponent(lblName)
                                                        .addComponent(lblCompetition))
                                                .addPreferredGap(ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
                                                        .addComponent(comboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(txtEmail, Alignment.TRAILING)
                                                        .addComponent(txtPhone, Alignment.TRAILING)
                                                        .addComponent(txtId, Alignment.TRAILING)
                                                        .addComponent(txtLastName, Alignment.TRAILING)
                                                        .addComponent(txtName, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)))
                                        .addComponent(btnOk, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblName))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblLastName)
                                        .addComponent(txtLastName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(lblId)
                                        .addComponent(txtId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addComponent(lblPhone)
                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                .addComponent(lblEmail))
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addComponent(txtPhone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                                        .addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblCompetition))))
                                .addPreferredGap(ComponentPlacement.UNRELATED) // Ajuste de espacio
                                .addComponent(btnOk)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)) // Ajustado para que el botón quede pegado abajo
        );
        contentPane.setLayout(gl_contentPane);
    }
}
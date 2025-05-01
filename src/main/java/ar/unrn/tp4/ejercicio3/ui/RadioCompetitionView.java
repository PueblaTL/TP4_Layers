package ar.unrn.tp4.ejercicio3.ui;

import ar.unrn.tp4.ejercicio3.modelo.Concurso;
import ar.unrn.tp4.ejercicio3.modelo.Inscripcion;
import ar.unrn.tp4.ejercicio3.modelo.Participante;
import ar.unrn.tp4.ejercicio3.persistencia.JdbcConcursoRepository;
import ar.unrn.tp4.ejercicio3.persistencia.JdbcInscripcionRepository;

import javax.swing.*;
import java.awt.*; // For other AWT classes if needed
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class RadioCompetitionView {

    private JTextField txtName;
    private JTextField txtLastName;
    private JTextField txtId; // DNI
    private JTextField txtPhone;
    private JTextField txtEmail;
    private JComboBox<Concurso> comboBox;
    private JButton btnOk;
    private JPanel contentPane;

    private final ConcursoRepository concursoRepository;
    private final InscripcionRepository inscripcionRepository;

    public RadioCompetitionView(ConcursoRepository concursoRepository, InscripcionRepository inscripcionRepository) {
        this.concursoRepository = concursoRepository;
        this.inscripcionRepository = inscripcionRepository;
        createAndShowUI();
    }

    private void createAndShowUI() {
        JFrame frame = new JFrame("Inscription to Competition");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 451, 260);
        contentPane = new JPanel();

        // Initialize components
        txtName = new JTextField(10);
        txtLastName = new JTextField(10);
        txtId = new JTextField(10);
        txtPhone = new JTextField(10);
        txtEmail = new JTextField(10);
        comboBox = new JComboBox<>();
        btnOk = new JButton("Ok");

        setupLayout(); // Setup GroupLayout
        setupActions();
        loadConcursos();

        frame.setContentPane(contentPane);
        frame.setVisible(true);
    }

    private void setupLayout() {
        // *** IMPORTANT: Paste your GroupLayout code here ***
        // Ensure all labels and components used in the layout code below are declared
        // (either as fields like txtName, or locally like lblName)

        JLabel lblName = new JLabel("Nombre:");
        JLabel lblLastName = new JLabel("Apellido:");
        JLabel lblId = new JLabel("Dni:");
        JLabel lblPhone = new JLabel("Telefono:");
        JLabel lblEmail = new JLabel("Email:");
        JLabel lblCompetition = new JLabel("Concurso:");


        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(gl_contentPane
                .createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
                        .addGroup(gl_contentPane
                                .createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(gl_contentPane
                                        .createSequentialGroup()
                                        .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(lblLastName)
                                                .addComponent(lblId)
                                                .addComponent(lblPhone)
                                                .addComponent(lblEmail)
                                                .addComponent(lblName) // Corrected variable name from PDF IbIName
                                                .addComponent(lblCompetition)) // Corrected variable name from PDF IblCompetition
                                        .addPreferredGap(ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                                        .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                .addComponent(comboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(txtEmail, GroupLayout.Alignment.TRAILING)
                                                .addComponent(txtPhone, GroupLayout.Alignment.TRAILING)
                                                .addComponent(txtId, GroupLayout.Alignment.TRAILING) // Corrected variable name from PDF txtld
                                                .addComponent(txtLastName, GroupLayout.Alignment.TRAILING)
                                                .addComponent(txtName, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)))
                                .addComponent(btnOk, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
                        .addContainerGap()));

        gl_contentPane.setVerticalGroup(gl_contentPane
                .createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(gl_contentPane.createSequentialGroup()
                        .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblName)) // Corrected variable name from PDF IbIName
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblLastName)
                                .addComponent(txtLastName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.TRAILING) // Corrected Alignment from PDF
                                .addComponent(lblId) // Corrected variable name from PDF Iblld
                                .addComponent(txtId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)) // Corrected variable name from PDF txtld
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(gl_contentPane.createSequentialGroup()
                                        .addComponent(lblPhone) // Corrected variable name from PDF IbIPhone
                                        .addPreferredGap(ComponentPlacement.UNRELATED) // Corrected Placement from PDF UNRELATED
                                        .addComponent(lblEmail)) // Corrected variable name from PDF IblEmail
                                .addGroup(gl_contentPane.createSequentialGroup()
                                        .addComponent(txtPhone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(ComponentPlacement.RELATED)
                                        .addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(ComponentPlacement.RELATED)
                                        .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(lblCompetition)))) // Corrected variable name from PDF IblCompetition
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(btnOk)
                        .addContainerGap(67, Short.MAX_VALUE))); // AdjustedaddContainerGap slightly from PDF if needed

        contentPane.setLayout(gl_contentPane);
    }

    private void setupActions() {
        btnOk.addActionListener(e -> saveInscriptionAction());
    }

    private void loadConcursos() {
        try {
            List<Concurso> concursos = concursoRepository.concursosAbiertos();
            comboBox.removeAllItems();
            // Add a placeholder item that is not a Concurso instance
            comboBox.addItem(new Concurso(0, "Seleccione un concurso...", "1900/01/01", "1900/01/01")); // Placeholder

            if (!concursos.isEmpty()) {
                for (Concurso c : concursos) {
                    comboBox.addItem(c);
                }
            } else {
                // Optional: Show message or disable combo/button
            }
            comboBox.setSelectedIndex(0); // Select the placeholder
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(contentPane, "Error cargando: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveInscriptionAction() {
        // Check if a real concurso is selected (index > 0 because 0 is placeholder)
        if (comboBox.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(contentPane, "Debe elegir un Concurso", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Concurso selectedConcurso = (Concurso) comboBox.getSelectedItem();


        try {
            Participante participante = new Participante(
                    txtName.getText(),
                    txtLastName.getText(),
                    txtId.getText(),
                    txtPhone.getText(),
                    txtEmail.getText()
            );

            Inscripcion inscripcion = new Inscripcion(participante, selectedConcurso);
            inscripcionRepository.save(inscripcion);

            JOptionPane.showMessageDialog(contentPane, "Inscripción guardada exitosamente!");
            clearForm();

        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(contentPane, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void clearForm() {
        txtName.setText("");
        txtLastName.setText("");
        txtId.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        comboBox.setSelectedIndex(0); // Reset to placeholder
    }
}
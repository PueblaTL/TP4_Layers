package ar.unrn.tp4.ejercicio2.service;

import ar.unrn.tp4.ejercicio2.model.Notificador;
import ar.unrn.tp4.ejercicio2.model.Persona;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class NotificadorEmail implements Notificador {
    private final String username;
    private final String password;
    private final String host;
    private final int port;
    private final String remitente;
    private final boolean habilitarEnvio;

    public NotificadorEmail() {
        this.username = "f5d2f689d53a50";
        this.password = "f92a5b2a23a391";
        this.host = "sandbox.smtp.mailtrap.io";
        this.port = 2525;
        this.remitente = "notificaciones@unrn.com";
        this.habilitarEnvio = true;
    }

    public NotificadorEmail(String username, String password, String host, int port, String remitente, boolean habilitarEnvio) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
        this.remitente = remitente;
        this.habilitarEnvio = habilitarEnvio;
    }

    @Override
    public void saludarCumpleaños(Persona persona) {
        System.out.println("Enviando saludo de cumpleaños a: " + persona.getNombreCompleto() + " (" + persona.getCorreo() + ")");

        if (habilitarEnvio) {
            try {
                enviarEmail(persona);
            } catch (MessagingException e) {
                System.err.println("Error al enviar el email: " + e.getMessage());
            }
        } else {
            System.out.println("El envío de emails está deshabilitado. Configurar el servidor para habilitar.");
        }
    }

    private void enviarEmail(Persona persona) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(remitente));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(persona.getCorreo()));
        message.setSubject("¡Feliz Cumpleaños!");

        String contenido = String.format(
                "Estimado/a %s,\n\n" +
                        "¡Feliz cumpleaños! Te deseamos un día maravilloso y un año lleno de éxitos.\n\n" +
                        "Saludos cordiales,\n" +
                        "Equipo de la UNRN",
                persona.getNombreCompleto()
        );

        message.setText(contenido);

        Transport.send(message);
    }
}
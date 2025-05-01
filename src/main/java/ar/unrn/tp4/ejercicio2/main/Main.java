package ar.unrn.tp4.ejercicio2.main;

import ar.unrn.tp4.ejercicio2.model.Api;
import ar.unrn.tp4.ejercicio2.service.ImportadorArchivo;
import ar.unrn.tp4.ejercicio2.service.NotificadorEmail;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {
    private static final String CONFIG_DEFAULT = "C:\\Users\\agupu\\Documents\\GitHub\\POO2\\POO2_TP4_Layers\\src\\resources\\config.properties";
    private static final String ARCHIVO_DEFAULT = "C:\\Users\\agupu\\Documents\\GitHub\\POO2\\POO2_TP4_Layers\\src\\resources\\empleados.txt";

    public static void main(String[] args) {
        try {
            // Cargar la configuración
            Properties config = new Properties();
            String configPath = args.length > 0 ? args[0] : CONFIG_DEFAULT;
            String archivoEmpleados = args.length > 1 ? args[1] : ARCHIVO_DEFAULT;

            try {
                config.load(new FileInputStream(configPath));
                System.out.println("Configuración cargada desde: " + configPath);
            } catch (IOException e) {
                System.out.println("No se pudo cargar la configuración. Usando valores por defecto.");
            }

            // Crear componentes
            String username = config.getProperty("email.username", "username_mailtrap");
            String password = config.getProperty("email.password", "password_mailtrap");
            String host = config.getProperty("email.host", "sandbox.smtp.mailtrap.io");
            int port = Integer.parseInt(config.getProperty("email.port", "2525"));
            String remitente = config.getProperty("email.remitente", "notificaciones@empresa.com");
            boolean habilitarEnvio = Boolean.parseBoolean(config.getProperty("email.habilitar", "true"));

            var notificador = new NotificadorEmail(username, password, host, port, remitente, habilitarEnvio);
            var importador = new ImportadorArchivo(archivoEmpleados);

            System.out.println("Usando archivo de empleados: " + archivoEmpleados);

            // Crear API y ejecutar lógica
            var api = new Api(notificador, importador);

            System.out.println("Verificando empleados que cumplen años hoy...");
            api.saludarCumpleañeros();

            System.out.println("Proceso finalizado con éxito.");

        } catch (Exception e) {
            System.err.println("Error en la aplicación: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
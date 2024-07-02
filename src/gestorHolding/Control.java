package gestorHolding;

import java.io.IOException;

public class Control {

    public void ejecutar() {

        SistemaGestor sistemaGestor = new SistemaGestor();

        boolean seguir;
        try {
            sistemaGestor = sistemaGestor.deSerializar("holding.txt");
            seguir = EntradaSalida.leerBoolean("SISTEMA GESTOR DE LA INFORMACION DE HOLDINGS\nDesea ingresar?");
        } catch (Exception e) {
            String usuario = EntradaSalida.leerString("Arranque inicial del sistema.\n"
                    + "Sr(a) Administrador(a), ingrese su nombre de usuario:");
            if (usuario.equals("")) {
                throw new NullPointerException("ERROR: El usuario no puede ser nulo.");
            }
            String password = EntradaSalida.leerString("Ingrese su password:");
            if (password.equals("")) {
                throw new NullPointerException("ERROR: El password no puede ser nulo.");
            }
            sistemaGestor.getUsuarios().add(new Administrador(usuario, password));
            try {
                sistemaGestor.serializar("holding.txt");
                EntradaSalida.mostrarString("El arranque ha sido exitoso. Ahora se debe reiniciar el sistema...");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            seguir = false;
        }

        while (seguir) {
            EntradaSalida.mostrarString("INGRESO");
            String usuario = EntradaSalida.leerString("Ingrese el usuario:");
            String password = EntradaSalida.leerString("Ingrese el password:");

            Usuario p = sistemaGestor.buscarUsuario(usuario + ":" + password);

            if (p == null) {
                EntradaSalida.mostrarString("ERROR: La combinacion usuario/password ingresada no es valida.");
            } else {
                seguir = p.proceder(sistemaGestor);
            }
        }
    }
}

package modelo.interfaces;

public interface Notificable {

    void notificar(String mensaje);

    String obtenerContacto();

    boolean aceptaNotificaciones();
}
package modelo.excepciones;

public class CapacidadExcedidaException extends SistemaBancarioException {

    private int capacidadMaxima;

    public CapacidadExcedidaException(String elemento, int capacidadMaxima) {
        super("Se alcanzó la capacidad máxima de " + elemento
              + ". Máximo permitido: " + capacidadMaxima, "CAP-001");
        this.capacidadMaxima = capacidadMaxima;
    }

    public int getCapacidadMaxima() { return capacidadMaxima; }
}
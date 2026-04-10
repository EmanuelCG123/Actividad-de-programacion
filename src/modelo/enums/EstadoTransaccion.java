
package modelo.enums;

public enum EstadoTransaccion {
    PENDIENTE,
    PROCESANDO,
    COMPLETADA,
    RECHAZADA,
    REVERTIDA;

    public boolean puedeTransicionarA(EstadoTransaccion nuevo) {
        switch (this) {
            case PENDIENTE:
                return nuevo == PROCESANDO || nuevo == RECHAZADA;
            case PROCESANDO:
                return nuevo == COMPLETADA || nuevo == RECHAZADA;
            case COMPLETADA:
                return nuevo == REVERTIDA;
            case RECHAZADA:
                return false;
            case REVERTIDA:
                return false;
            default:
                return false;
        }
    }
}

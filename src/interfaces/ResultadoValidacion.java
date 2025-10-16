package interfaces;

public class ResultadoValidacion {
    private final boolean valido;
    private final String mensajeError;
    private final double peso;
    private final double distancia;

    private ResultadoValidacion(boolean valido, String mensajeError, double peso, double distancia) {
        this.valido = valido;
        this.mensajeError = mensajeError;
        this.peso = peso;
        this.distancia = distancia;
    }

    public static ResultadoValidacion exitoso(double peso, double distancia) {
        return new ResultadoValidacion(true, null, peso, distancia);
    }

    public static ResultadoValidacion error(String mensaje) {
        return new ResultadoValidacion(false, mensaje, 0, 0);
    }

    public boolean esValido() {
        return valido;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public double getPeso() {
        return peso;
    }

    public double getDistancia() {
        return distancia;
    }
}

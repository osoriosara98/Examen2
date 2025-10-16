package modelos;


public class EnvioAereo extends Envio {
    public EnvioAereo(String codigo, String cliente, double peso, double distancia) {
        super(codigo, cliente, peso, distancia);
    }

    @Override
    public double calcularTarifa() {
        return getDistancia() * 1000 + getPeso() * 400;
    }
}

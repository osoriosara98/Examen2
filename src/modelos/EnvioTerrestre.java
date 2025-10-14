package modelos;


public class EnvioTerrestre extends Envio {
    public EnvioTerrestre(String codigo, String cliente, double peso, double distancia) {
        super(codigo, cliente, peso, distancia);
    }

    @Override
    public double calcularTarifa() {
        return getDistancia() * 500 + getPeso() * 200;
    }
}
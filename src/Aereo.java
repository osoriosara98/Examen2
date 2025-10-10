class EnvioAereo extends Envio {
    public EnvioAereo(String codigo, String cliente, double peso, double distancia) {
        super(codigo, cliente, peso, distancia);
    }

    @Override
    public double calcularTarifa() {
        return distancia * 1000 + peso * 400;
    }
}

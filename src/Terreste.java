/* creo las clases hijas usando polimorfismo, recordar que polimorfismo es la diferentes formas de hacer lo mismo 
EX: volar: puedo volar con capa o con poderes*/
class EnvioTerrestre extends Envio {
    public EnvioTerrestre(String codigo, String cliente, double peso, double distancia) {
        super(codigo, cliente, peso, distancia);
    }

    // Sobrescribe el método calcularTarifa
    @Override
    public double calcularTarifa() {
        return distancia * 500 + peso * 200;
    }
}

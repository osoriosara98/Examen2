/*Creo una clase padre, declaro las variables que voy a usar */
abstract class Envio {
    private final String codigo;
    private final String cliente;
    private final double peso;
    private final double distancia;

    public Envio(String codigo, String cliente, double peso, double distancia) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.peso = peso;
        this.distancia = distancia;
    }

    public String getCodigo()    { return codigo; }
    public String getCliente()   { return cliente; }
    public double getPeso()      { return peso; }
    public double getDistancia() { return distancia; }

    public abstract double calcularTarifa();
}







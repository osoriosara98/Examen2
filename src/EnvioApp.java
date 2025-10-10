/*Creo una clase padre, declaro las variables que voy a usar */
class Envio {
    String codigo;
    String cliente;
    double peso;
    double distancia;

    public Envio(String codigo, String cliente, double peso, double distancia) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.peso = peso;
        this.distancia = distancia;
    }

    public double calcularTarifa() {
        // este es un método que voy a usar en todas las clases hijas 
        return 0;
    }
}






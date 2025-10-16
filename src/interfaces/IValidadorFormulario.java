package interfaces;

public interface IValidadorFormulario {
    ResultadoValidacion validar(String codigo, String cliente, String peso, String distancia);
}

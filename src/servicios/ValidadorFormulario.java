package servicios;

import interfaces.IValidadorFormulario;
import interfaces.ResultadoValidacion;

public class ValidadorFormulario implements IValidadorFormulario {

    @Override
    public ResultadoValidacion validar(String codigo, String cliente, String peso, String distancia) {
        if (codigo == null || codigo.trim().isEmpty() || cliente == null || cliente.trim().isEmpty()) {
            return ResultadoValidacion.error("Número y Cliente son obligatorios.");
        }

        double pesoValor;
        try {
            pesoValor = Double.parseDouble(peso.trim().replace(',', '.'));
        } catch (Exception ex) {
            return ResultadoValidacion.error("Peso inválido.");
        }

        double distanciaValor;
        try {
            distanciaValor = Double.parseDouble(distancia.trim().replace(',', '.'));
        } catch (Exception ex) {
            return ResultadoValidacion.error("Distancia inválida.");
        }

        return ResultadoValidacion.exitoso(pesoValor, distanciaValor);
    }
}

package servicios;

import modelos.*;

public class FabricaEnvios {

    public static Envio crearEnvio(int tipoIndex, String codigo, String cliente, double peso, double distancia) {
        return switch (tipoIndex) {
            case 0 -> new EnvioTerrestre(codigo, cliente, peso, distancia);
            case 1 -> new EnvioAereo(codigo, cliente, peso, distancia);
            default -> new EnvioMaritimo(codigo, cliente, peso, distancia);
        };
    }

    public static String obtenerTipoNombre(Envio envio) {
        if (envio instanceof EnvioTerrestre) return "Terrestre";
        if (envio instanceof EnvioAereo) return "Aéreo";
        return "Marítimo";
    }
}

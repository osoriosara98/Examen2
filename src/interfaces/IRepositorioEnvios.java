package interfaces;

import modelos.Envio;
import java.util.List;

public interface IRepositorioEnvios {
    void agregar(Envio envio);
    boolean eliminarPorCodigo(String codigo);
    List<Envio> obtenerTodos();
}

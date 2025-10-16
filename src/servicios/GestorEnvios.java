package servicios;

import modelos.Envio;
import interfaces.IRepositorioEnvios;
import java.util.ArrayList;
import java.util.List;

public class GestorEnvios implements IRepositorioEnvios {
    private final ArrayList<Envio> lista = new ArrayList<>();

    @Override
    public void agregar(Envio e) {
        if (e != null) lista.add(e);
    }

    @Override
    public boolean eliminarPorCodigo(String codigo) {
        if (codigo == null || codigo.isEmpty()) return false;
        for (int i = 0; i < lista.size(); i++) {
            if (codigo.equals(lista.get(i).getCodigo())) {
                lista.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Envio> obtenerTodos() {
        return new ArrayList<>(lista);
    }
}

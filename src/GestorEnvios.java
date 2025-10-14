import java.util.ArrayList;
import java.util.List;

/** CRUD básico de envíos  */
public class GestorEnvios {
    private final ArrayList<Envio> lista = new ArrayList<>();

    public void agregarEnvio(Envio e) {
        if (e != null) lista.add(e);
    }

    public boolean retirarEnvioPorCodigo(String codigo) {
        if (codigo == null || codigo.isEmpty()) return false;
        for (int i = 0; i < lista.size(); i++) {
            if (codigo.equals(lista.get(i).getCodigo())) {
                lista.remove(i);
                return true;
            }
        }
        return false;
    }

    public List<Envio> obtenerEnvios() {
        return lista; 
    }
}

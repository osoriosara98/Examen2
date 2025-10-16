package servicios;

import modelos.Envio;
import configuraciones.FormateadorHelper;
import javax.swing.table.DefaultTableModel;

public class ServicioTabla {

    public void agregarFila(DefaultTableModel model, Envio envio) {
        String tipo = FabricaEnvios.obtenerTipoNombre(envio);
        model.addRow(new Object[]{
                tipo,
                envio.getCodigo(),
                envio.getCliente(),
                FormateadorHelper.formatearNumero(envio.getPeso()),
                FormateadorHelper.formatearNumero(envio.getDistancia()),
                FormateadorHelper.formatearMoneda(envio.calcularTarifa())
        });
    }

    public void limpiarTabla(DefaultTableModel model) {
        model.setRowCount(0);
    }

    public void eliminarFila(DefaultTableModel model, int fila) {
        model.removeRow(fila);
    }
}

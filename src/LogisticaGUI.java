import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.util.List;

import modelos.*;
import servicios.*;
import configuraciones.*;
import interfaces.*;

public class LogisticaGUI extends JFrame {

    private final IRepositorioEnvios repositorio;
    private final IValidadorFormulario validador;
    private final ServicioTabla servicioTabla;

    private final JTextField numeroField    = new JTextField();
    private final JTextField clienteField   = new JTextField();
    private final JTextField pesoField      = new JTextField();
    private final JTextField distanciaField = new JTextField();
    private final JComboBox<String> tipoBox =
            new JComboBox<>(new String[]{"Terrestre", "Aéreo", "Marítimo"});

    private final String[] COLS = {"Tipo", "Código", "Cliente", "Peso (kg)", "Distancia (km)", "Costo"};
    private final DefaultTableModel model = new DefaultTableModel(COLS, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable tabla = new JTable(model);

    public LogisticaGUI(IRepositorioEnvios repositorio, IValidadorFormulario validador, ServicioTabla servicioTabla) {
        super("Operador Logístico");

        this.repositorio = repositorio;
        this.validador = validador;
        this.servicioTabla = servicioTabla;

        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));
        setMinimumSize(new Dimension(900, 560));
        setLocationRelativeTo(null);

        configurarDimensiones();
        add(crearToolbar(), BorderLayout.NORTH);
        add(crearCentro(),  BorderLayout.CENTER);

        configurarTabla();
        refrescarTablaCompleta();

        setVisible(true);
    }

    private void configurarDimensiones() {
        Dimension alto28  = new Dimension(220, 28);
        Dimension alto28S = new Dimension(160, 28);
        numeroField.setPreferredSize(alto28);
        clienteField.setPreferredSize(new Dimension(280, 28));
        pesoField.setPreferredSize(alto28S);
        distanciaField.setPreferredSize(alto28S);
        tipoBox.setPreferredSize(new Dimension(170, 28));
    }

    private JComponent crearToolbar() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 10));
        p.setBorder(new EmptyBorder(8, 12, 0, 12));

        JButton btnAgregar = new JButton();
        JButton btnRetirar = new JButton();

        ImageIcon add = CargadorIconos.cargarIcono("/img/add.png");
        ImageIcon rem = CargadorIconos.cargarIcono("/img/remove.png");
        if (add != null) btnAgregar.setIcon(add); else btnAgregar.setText("Agregar");
        if (rem != null) btnRetirar.setIcon(rem); else btnRetirar.setText("Retirar");

        Dimension big = new Dimension(120, 86);
        btnAgregar.setPreferredSize(big);
        btnRetirar.setPreferredSize(big);

        btnAgregar.addActionListener(e -> guardarDesdeFormulario());
        btnRetirar.addActionListener(e -> retirarSeleccionado());

        p.add(btnAgregar);
        p.add(btnRetirar);
        return p;
    }

    private JComponent crearCentro() {
        JPanel cont = new JPanel(new BorderLayout());
        cont.setBorder(new EmptyBorder(0, 12, 12, 12));

        cont.add(crearFormulario(), BorderLayout.NORTH);

        JScrollPane sp = new JScrollPane(tabla);
        sp.setBorder(BorderFactory.createTitledBorder("Listado de envíos"));
        cont.add(sp, BorderLayout.CENTER);

        return cont;
    }

    private JPanel crearFormulario() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(new EmptyBorder(6, 6, 6, 6));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 8, 6, 8);
        c.anchor = GridBagConstraints.WEST;

        Font fLbl = form.getFont().deriveFont(Font.PLAIN, 13f);

        java.util.function.BiConsumer<String,int[]> addLabel = (txt,pos) -> {
            JLabel l = new JLabel(txt);
            l.setFont(fLbl);
            c.gridx = pos[0]; c.gridy = pos[1];
            c.gridwidth = 1; c.weightx = 0; c.fill = GridBagConstraints.NONE;
            form.add(l, c);
        };
        java.util.function.BiConsumer<JComponent,int[]> addField = (comp,pos) -> {
            c.gridx = pos[0]; c.gridy = pos[1];
            c.gridwidth = 1; c.weightx = 1; c.fill = GridBagConstraints.HORIZONTAL;
            form.add(comp, c);
        };

        addLabel.accept("Número", new int[]{0,0});
        addField .accept(numeroField, new int[]{1,0});
        addLabel.accept("Tipo", new int[]{2,0});
        addField .accept(tipoBox, new int[]{3,0});

        addLabel.accept("Cliente", new int[]{0,1});
        addField .accept(clienteField, new int[]{1,1});
        addLabel.accept("Distancia (km)", new int[]{2,1});
        addField .accept(distanciaField, new int[]{3,1});

        addLabel.accept("Peso (kg)", new int[]{0,2});
        addField .accept(pesoField, new int[]{1,2});

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        JButton btnGuardar  = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        btnGuardar.addActionListener(e -> guardarDesdeFormulario());
        btnCancelar.addActionListener(e -> limpiarFormulario());
        acciones.add(btnGuardar);
        acciones.add(btnCancelar);

        c.gridx = 2; c.gridy = 2; c.gridwidth = 2; c.weightx = 0; c.fill = GridBagConstraints.NONE;
        form.add(acciones, c);

        return form;
    }

    private void guardarDesdeFormulario() {
        ResultadoValidacion resultado = validador.validar(
            numeroField.getText(),
            clienteField.getText(),
            pesoField.getText(),
            distanciaField.getText()
        );

        if (!resultado.esValido()) {
            JOptionPane.showMessageDialog(this, resultado.getMensajeError());
            return;
        }

        Envio envio = FabricaEnvios.crearEnvio(
            tipoBox.getSelectedIndex(),
            numeroField.getText().trim(),
            clienteField.getText().trim(),
            resultado.getPeso(),
            resultado.getDistancia()
        );

        repositorio.agregar(envio);
        servicioTabla.agregarFila(model, envio);
        limpiarFormulario();
    }

    private void retirarSeleccionado() {
        int viewRow = tabla.getSelectedRow();
        if (viewRow < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un envío en la tabla para retirarlo.");
            return;
        }
        int modelRow = tabla.convertRowIndexToModel(viewRow);
        String codigo = model.getValueAt(modelRow, 1).toString();
        if (repositorio.eliminarPorCodigo(codigo)) {
            servicioTabla.eliminarFila(model, modelRow);
        }
    }

    private void limpiarFormulario() {
        numeroField.setText("");
        clienteField.setText("");
        pesoField.setText("");
        distanciaField.setText("");
        tipoBox.setSelectedIndex(0);
        numeroField.requestFocusInWindow();
    }

    private void refrescarTablaCompleta() {
        servicioTabla.limpiarTabla(model);
        List<Envio> envios = repositorio.obtenerTodos();
        for (Envio e : envios) {
            servicioTabla.agregarFila(model, e);
        }
    }

    private void configurarTabla() {
        tabla.setFillsViewportHeight(true);
        tabla.setRowHeight(22);

        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        tabla.setShowGrid(true);
        tabla.setGridColor(new Color(180,180,180));
        tabla.setIntercellSpacing(new Dimension(1,1));

        JTableHeader header = tabla.getTableHeader();
        header.setReorderingAllowed(false);
        header.setFont(header.getFont().deriveFont(Font.BOLD, 12f));

        DefaultTableCellRenderer right = new DefaultTableCellRenderer();
        right.setHorizontalAlignment(SwingConstants.RIGHT);
        tabla.getColumnModel().getColumn(3).setCellRenderer(right);
        tabla.getColumnModel().getColumn(4).setCellRenderer(right);
        tabla.getColumnModel().getColumn(5).setCellRenderer(right);

        DefaultTableCellRenderer clientR = new DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(
                    JTable t, Object v, boolean sel, boolean foc, int r, int c) {
                Component comp = super.getTableCellRendererComponent(t, v, sel, foc, r, c);
                setToolTipText(v == null ? null : v.toString());
                return comp;
            }
        };
        tabla.getColumnModel().getColumn(2).setCellRenderer(clientR);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            IRepositorioEnvios repositorio = new GestorEnvios();
            IValidadorFormulario validador = new ValidadorFormulario();
            ServicioTabla servicioTabla = new ServicioTabla();
            new LogisticaGUI(repositorio, validador, servicioTabla);
        });
    }
}

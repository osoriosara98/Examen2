import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogisticaGUI extends JFrame {

    private GestorEnvios gestor = new GestorEnvios();

    private JTextField codigoField = new JTextField(10);
    private JTextField clienteField = new JTextField(10);
    private JTextField pesoField = new JTextField(6);
    private JTextField distanciaField = new JTextField(6);
    private JComboBox<String> tipoBox = new JComboBox<>(new String[]{"Terrestre", "Aéreo", "Marítimo"});

    public LogisticaGUI() {
        super("Logística — Sistema de Envíos");
        initUI();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(500, 300));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initUI() {
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 13);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);

        JLabel lCodigo = new JLabel("Código:"); lCodigo.setFont(labelFont);
        c.gridx = 0; c.gridy = 0; form.add(lCodigo, c);
        codigoField.setFont(fieldFont);
        codigoField.setPreferredSize(new Dimension(160, 24));
        c.gridx = 1; form.add(codigoField, c);

        JLabel lCliente = new JLabel("Cliente:"); lCliente.setFont(labelFont);
        c.gridx = 0; c.gridy = 1; form.add(lCliente, c);
        clienteField.setFont(fieldFont);
        clienteField.setPreferredSize(new Dimension(160, 24));
        c.gridx = 1; form.add(clienteField, c);

        JLabel lPeso = new JLabel("Peso (kg):"); lPeso.setFont(labelFont);
        c.gridx = 0; c.gridy = 2; form.add(lPeso, c);
        pesoField.setFont(fieldFont);
        pesoField.setPreferredSize(new Dimension(100, 24));
        c.gridx = 1; form.add(pesoField, c);

        JLabel lDist = new JLabel("Distancia (km):"); lDist.setFont(labelFont);
        c.gridx = 0; c.gridy = 3; form.add(lDist, c);
        distanciaField.setFont(fieldFont);
        distanciaField.setPreferredSize(new Dimension(100, 24));
        c.gridx = 1; form.add(distanciaField, c);

        JLabel lTipo = new JLabel("Tipo:"); lTipo.setFont(labelFont);
        c.gridx = 0; c.gridy = 4; form.add(lTipo, c);
        tipoBox.setFont(fieldFont);
        c.gridx = 1; form.add(tipoBox, c);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 6));
        botones.setBorder(new EmptyBorder(8, 0, 8, 0));
        JButton agregarBtn = new JButton("Agregar");
        JButton retirarBtn = new JButton("Retirar");
        JButton listarBtn = new JButton("Listar");
        agregarBtn.setFont(fieldFont);
        agregarBtn.setToolTipText("Agregar envío");
        retirarBtn.setFont(fieldFont);
        retirarBtn.setToolTipText("Retirar envío");
        listarBtn.setFont(fieldFont);
        listarBtn.setToolTipText("Mostrar listado de envíos");
        botones.add(agregarBtn);
        botones.add(retirarBtn);
        botones.add(listarBtn);

        agregarBtn.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { agregarEnvioDesdeFormulario(); }
        });

        retirarBtn.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                String codigo = (String) JOptionPane.showInputDialog(
                        LogisticaGUI.this, "Código del envío a retirar:",
                        "Logística - Retirar envío", JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (codigo != null && !codigo.trim().isEmpty()) {
                    boolean ok = gestor.retirarEnvioPorCodigo(codigo.trim());
                    if (ok) JOptionPane.showMessageDialog(LogisticaGUI.this, "Envío retirado correctamente.",
                            "Logística - Retirar envío", JOptionPane.INFORMATION_MESSAGE);
                    else JOptionPane.showMessageDialog(LogisticaGUI.this, "Envío no encontrado.",
                            "Logística - Retirar envío", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        listarBtn.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { mostrarListadoEnDialogo(); }
        });

        Container cp = getContentPane();
        cp.setLayout(new BorderLayout(8, 8));
        JPanel top = new JPanel(new BorderLayout());
        top.setBorder(new EmptyBorder(12, 12, 0, 12));
        top.add(form, BorderLayout.CENTER);
        top.add(botones, BorderLayout.SOUTH);
        cp.add(top, BorderLayout.NORTH);
        cp.add(Box.createVerticalStrut(8), BorderLayout.CENTER); // centro vacío
    }

    private void agregarEnvioDesdeFormulario() {
        String codigo = codigoField.getText().trim();
        String cliente = clienteField.getText().trim();
        if (codigo.isEmpty() || cliente.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Código y Cliente son obligatorios.");
            return;
        }
        double peso;
        double distancia;
        try {
            peso = Double.parseDouble(pesoField.getText().trim().replace(',', '.'));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Peso inválido. Usa un número, por ejemplo 12.5");
            return;
        }
        try {
            String d = distanciaField.getText().trim().toLowerCase().replace(',', '.');
            if (d.endsWith("m") && !d.endsWith("km")) {
                double metros = Double.parseDouble(d.substring(0, d.length()-1).trim());
                distancia = metros / 1000.0;
            } else if (d.endsWith("km")) {
                distancia = Double.parseDouble(d.substring(0, d.length()-2).trim());
            } else {
                distancia = Double.parseDouble(d);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Distancia inválida. Ejemplos válidos: 10, 10km, 1000m");
            return;
        }

        int tipo = tipoBox.getSelectedIndex() + 1; // 1..3
        Envio envio;
        switch (tipo) {
            case 1 -> envio = new EnvioTerrestre(codigo, cliente, peso, distancia);
            case 2 -> envio = new EnvioAereo(codigo, cliente, peso, distancia);
            default -> envio = new EnvioMaritimo(codigo, cliente, peso, distancia);
        }
        gestor.agregarEnvio(envio);
        JOptionPane.showMessageDialog(this, "Envío agregado.", "Logística — Agregar envío", JOptionPane.INFORMATION_MESSAGE);
        limpiarFormulario();
    }

    private void limpiarFormulario() {
        codigoField.setText("");
        clienteField.setText("");
        pesoField.setText("");
        distanciaField.setText("");
    }

    private void mostrarListadoEnDialogo() {
        java.util.List<Envio> lista = gestor.obtenerEnvios();
        String[] columnas = {"Código", "Cliente", "Peso (kg)", "Distancia (km)", "Tarifa"};
        Object[][] datos = new Object[lista.size()][];
        for (int i = 0; i < lista.size(); i++) {
            Envio e = lista.get(i);
            datos[i] = new Object[]{
                    e.getCodigo(), e.getCliente(),
                    String.format("%.2f", e.getPeso()),
                    String.format("%.2f", e.getDistancia()),
                    String.format("$%.2f", e.calcularTarifa())
            };
        }

        javax.swing.table.DefaultTableModel model =
                new javax.swing.table.DefaultTableModel(datos, columnas) {
                    @Override public boolean isCellEditable(int r, int c) { return false; }
                };

        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(600, 300));

        JDialog dialog = new JDialog(this, "Logística — Listado de envíos", true);
        dialog.getContentPane().setLayout(new BorderLayout(8, 8));
        dialog.getContentPane().add(scroll, BorderLayout.CENTER);

        JButton close = new JButton("Cerrar");
        close.setToolTipText("Cerrar — Listado de envíos");
        close.addActionListener(ae -> dialog.dispose());
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        p.add(close);
        dialog.getContentPane().add(p, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LogisticaGUI::new);
    }
}

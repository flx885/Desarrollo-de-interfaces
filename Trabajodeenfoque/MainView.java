package Trabajodeenfoque;

import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class MainView extends JFrame {

    private JButton btnBuscar;
    private JDateChooser dateInicio;
    private JDateChooser dateFin;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public MainView() {
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() { // Configura propiedades básicas de la ventana.
        setTitle("SmartOcupation - Gestión de Alquileres");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void inicializarComponentes() {

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10)); // Crea panel principal con BorderLayout y espacios.
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Añade margen interno al panel.

        JPanel panelSuperior = new JPanel(new BorderLayout());

        // TÍTULO
        JLabel lblTitulo = new JLabel("Consulta de alquileres", SwingConstants.CENTER); // Crea etiqueta de título centrada.
        lblTitulo.setFont(lblTitulo.getFont().deriveFont(16f)); // Cambia tamaño de fuente a 16.
        panelSuperior.add(lblTitulo, BorderLayout.NORTH);

        // PANEL SUPERIOR (FECHAS)
        JPanel panelFechas = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Crea panel para fechas con FlowLayout centrado.

        JLabel lblInicio = new JLabel("Fecha inicio:"); // Etiqueta para fecha inicio.
        JLabel lblFin = new JLabel("Fecha fin:"); // Etiqueta para fecha fin.

        dateInicio = new JDateChooser(); // Selector de fecha de inicio.
        dateInicio.setPreferredSize(new Dimension(140, 25)); // Establece tamaño preferido.

        dateFin = new JDateChooser(); // Selector de fecha de fin.
        dateFin.setPreferredSize(new Dimension(140, 25)); // Establece tamaño preferido.

        panelFechas.add(lblInicio); // Añade etiqueta inicio al panel.
        panelFechas.add(dateInicio); // Añade selector inicio al panel.
        panelFechas.add(lblFin); // Añade etiqueta fin al panel.
        panelFechas.add(dateFin); // Añade selector fin al panel.

        // Panel para título y fechas juntos
        panelSuperior.add(panelFechas, BorderLayout.SOUTH);
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);

        // ===== TABLA =====
        modeloTabla = new DefaultTableModel( // Crea modelo de tabla con cabeceras.
                new Object[]{"ID", "Fecha inicio", "Duración", "cliente_id", "vivienda_id"}, 0
        );
        tabla = new JTable(modeloTabla); // Crea JTable con el modelo.
        JScrollPane scrollTabla = new JScrollPane(tabla); // Envuelve la tabla en un JScrollPane.

        panelPrincipal.add(scrollTabla, BorderLayout.CENTER); // Añade tabla con scroll al centro del panel.

        // ===== BOTÓN =====
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Crea panel para el botón centrado.
        btnBuscar = new JButton("Buscar alquileres"); // Crea botón de búsqueda.

        // Añade ActionListener al botón de búsqueda.
        btnBuscar.addActionListener(e -> {
            try {
                java.util.Date inicio = dateInicio.getDate(); // Obtiene fecha inicio del selector.
                java.util.Date fin = dateFin.getDate(); // Obtiene fecha fin del selector.

                // Limpiar tabla siempre
                modeloTabla.setRowCount(0); // Borra todas las filas de la tabla.

                DAO dao = new DAO(); // Crea instancia del DAO.

                // Si NO hay fechas -> mostrar todo
                if (inicio == null && fin == null) { // Si no se seleccionaron fechas.
                    List<Object[]> filas = dao.listarTabla(); // Obtiene todos los datos CON IDs.
                    for (Object[] fila : filas) {
                        modeloTabla.addRow(fila); // Añade cada fila a la tabla.
                    }
                    return; 
                }

                // Si una fecha está y la otra no -> aviso
                if (inicio == null || fin == null) { // Si solo una fecha está seleccionada.
                    // Muestra advertencia.
                    JOptionPane.showMessageDialog(
                            this,
                            "Selecciona ambas fechas o ninguna",
                            "Fechas incompletas",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return; 
                }

                // Validar orden
                if (inicio.after(fin)) { // Si la fecha inicio es posterior a fecha fin.
                    // Muestra advertencia.
                    JOptionPane.showMessageDialog(
                            this,
                            "La fecha inicio debe ser anterior o igual a la fecha fin",
                            "Fechas incorrectas",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return; // Sale del método.
                }

                // Con fechas → filtrar
                List<Object[]> filas = dao.buscarFechasTabla(inicio, fin); // Busca datos en el rango CON IDs.
                for (Object[] fila : filas) {
                    modeloTabla.addRow(fila); // Añade cada fila filtrada a la tabla.
                }

            } catch (Exception ex) { // Captura excepciones.
                // Muestra mensaje de error.
                JOptionPane.showMessageDialog(
                        this,
                        "Error al realizar la búsqueda",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                ex.printStackTrace(); // Imprime traza del error en consola.
            }
        });

        // Botón para crear informe
        JButton btnInforme = new JButton("Crear informe"); // Crea botón para generar informe.
        btnInforme.addActionListener(e -> { // Asigna acción al botón.
            try {
                DAO dao = new DAO(); // Crea objeto DAO para acceder a la base de datos.
                List<Object[]> datos; // Lista para almacenar los datos del informe.

                // Obtiene las fechas seleccionadas en los selectores.
                java.util.Date inicio = dateInicio.getDate();
                java.util.Date fin = dateFin.getDate();

                // Decide qué consulta usar basándose en las fechas.
                if (inicio == null || fin == null) {
                    // Si no hay fechas seleccionadas, obtiene todos los alquileres con nombres completos.
                    datos = dao.listarTablaCompleta(); // Método que incluye JOINs (nombres).
                } else {
                    // Si hay fechas, obtiene alquileres filtrados por rango con nombres completos.
                    datos = dao.buscarFechasTablaCompleta(inicio, fin); // Método con JOINs y filtro (nombres).
                }

                // Crea el archivo de texto para el informe.
                File informe = new File("informe_alquileres.txt");
                PrintWriter pw = new PrintWriter(informe); // Abre el archivo para escritura.

                // Escribe la cabecera del informe con tabulaciones como separadores.
                pw.println("ID\tFecha inicio\tDuración\tCliente\tVivienda");

                // Escribe cada fila de datos en el archivo.
                for (Object[] fila : datos) {
                    // Concatena los valores de la fila separados por tabulaciones.
                    pw.println(fila[0] + "\t" + fila[1] + "\t" + fila[2] + "\t" + fila[3] + "\t" + fila[4]);
                }

                pw.close(); // Cierra el archivo.
                JOptionPane.showMessageDialog(null, "Informe creado: " + informe.getName()); // Muestra confirmación.

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al crear el informe"); // Maneja errores.
            }
        });

        // Añade ambos botones al panel
        panelBoton.add(btnBuscar); // Añade el botón de búsqueda.
        panelBoton.add(btnInforme); // Añade el botón de informe.

        panelPrincipal.add(panelBoton, BorderLayout.SOUTH); // Añade panel del botón en la zona sur.

        add(panelPrincipal); // Añade el panel principal al JFrame.
    }
}
package view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import control.Controller;


public class TurnCalculatorDialog extends JDialog {
    
    private Controller ctrl;
    private int heroIndex;
    private JTextField rangoField;
    private JTextField emField;
    private JTextArea resultadoArea;
    private JLabel decisionLabel;
    
    public TurnCalculatorDialog(JFrame parent, Controller ctrl, int heroIndex) {
        super(parent, "Calculadora de Turn - Hero vs Villano", true);
        this.ctrl = ctrl;
        this.heroIndex = heroIndex;
        
        initGUI();
    }
    
    private void initGUI() {
        setLayout(new BorderLayout(10, 10));
        setSize(500, 400);
        setLocationRelativeTo(getParent());
        

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        

        JLabel titleLabel = new JLabel("Cálculo de Outs en el Turn");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(15));
        
        // Explicación
        JTextArea explicacion = new JTextArea(
            "Este cálculo solo funciona con 2 jugadores (Hero vs Villano).\n" +
            "Introduce el rango del villano y el Equity Mínimo (EM) requerido.\n" +
            "El sistema calculará si debes hacer Call o Fold."
        );
        explicacion.setEditable(false);
        explicacion.setWrapStyleWord(true);
        explicacion.setLineWrap(true);
        explicacion.setOpaque(false);
        explicacion.setFont(new Font("SansSerif", Font.PLAIN, 11));
        mainPanel.add(explicacion);
        mainPanel.add(Box.createVerticalStrut(15));
        

        JPanel rangoPanel = new JPanel(new BorderLayout(5, 5));
        JLabel rangoLabel = new JLabel("Rango del Villano:");
        rangoLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        rangoField = new JTextField();
        rangoField.setToolTipText("Ejemplo: AA, QQ, AKs, KQo (separados por comas)");
        rangoPanel.add(rangoLabel, BorderLayout.NORTH);
        rangoPanel.add(rangoField, BorderLayout.CENTER);
        mainPanel.add(rangoPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        

        JPanel emPanel = new JPanel(new BorderLayout(5, 5));
        JLabel emLabel = new JLabel("Equity Mínimo (EM) en %:");
        emLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        emField = new JTextField();
        emField.setToolTipText("Ejemplo: 30 (para 30%)");
        emPanel.add(emLabel, BorderLayout.NORTH);
        emPanel.add(emField, BorderLayout.CENTER);
        mainPanel.add(emPanel);
        mainPanel.add(Box.createVerticalStrut(15));
        
 
        JButton calcularBtn = new JButton("Calcular");
        calcularBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        calcularBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        calcularBtn.addActionListener(e -> calcular());
        mainPanel.add(calcularBtn);
        mainPanel.add(Box.createVerticalStrut(15));
        
      
        JLabel resultLabel = new JLabel("Resultados:");
        resultLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        mainPanel.add(resultLabel);
        mainPanel.add(Box.createVerticalStrut(5));
        
        resultadoArea = new JTextArea(5, 40);
        resultadoArea.setEditable(false);
        resultadoArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultadoArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane scrollPane = new JScrollPane(resultadoArea);
        mainPanel.add(scrollPane);
        mainPanel.add(Box.createVerticalStrut(10));
        

        decisionLabel = new JLabel("");
        decisionLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        decisionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(decisionLabel);
        
        add(mainPanel, BorderLayout.CENTER);
        
     
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton cerrarBtn = new JButton("Cerrar");
        cerrarBtn.addActionListener(e -> dispose());
        buttonPanel.add(cerrarBtn);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void calcular() {
        try {

        	//Esto se hace para validar si estamos en la fase del Turn
            if (!"TURN".equals(ctrl.getFase())) {
                JOptionPane.showMessageDialog(this,
                    "Este cálculo solo se puede realizar en el Turn",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            

            String rangoTexto = rangoField.getText().trim();
            if (rangoTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Por favor, introduce el rango del villano",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            List<String> rangoVillano = new ArrayList<>();
            String[] rangos = rangoTexto.split(",");
            for (String r : rangos) {
                String rango = r.trim();
                if (!rango.isEmpty()) {
                    rangoVillano.add(rango);
                }
            }
            

            String emTexto = emField.getText().trim();
            if (emTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Por favor, introduce el Equity Mínimo",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            double emMinimo;
            try {
                emMinimo = Double.parseDouble(emTexto);
                if (emMinimo < 0 || emMinimo > 100) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                    "El EM debe ser un número entre 0 y 100",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            

            String info = ctrl.obtenerInfoOutsTurn(heroIndex, rangoVillano);
            boolean debeHacerCall = ctrl.debeHacerCallTurn(heroIndex, rangoVillano, emMinimo);
            

            resultadoArea.setText(info);
            
            if (debeHacerCall) {
                decisionLabel.setText("DECISIÓN: CALL");
                decisionLabel.setForeground(new Color(0, 150, 0));
            } else {
                decisionLabel.setText("DECISIÓN: FOLD");
                decisionLabel.setForeground(Color.RED);
            }
            
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(this,
                ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error al realizar el cálculo: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
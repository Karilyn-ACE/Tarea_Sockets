import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;

//Aplicación cliente
public class Cliente extends JFrame {
        public Cliente()
            {
            super("Convertidor de Temperatura ");
//creación de la interfaz
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(255, 182, 193)); 
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitle = new JLabel("Convertidor de Temperatura");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(lblTitle);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        inputPanel.setOpaque(false);

        JLabel lblValue = new JLabel("Valor:");
        lblValue.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblValue.setForeground(Color.WHITE);
        inputPanel.add(lblValue);

        JTextField txtValue = new JTextField(8);
        txtValue.setFont(new Font("SansSerif", Font.PLAIN, 14));
        inputPanel.add(txtValue);

        JComboBox<String> cbDir = new JComboBox<>(new String[]{"F → C", "C → F"});
        cbDir.setFont(new Font("SansSerif", Font.PLAIN, 14));
        cbDir.setBackground(Color.WHITE);
        inputPanel.add(cbDir);

        JButton btnConvert = new JButton("Convertir");
        btnConvert.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnConvert.setBackground(new Color(255, 105, 180)); 
        btnConvert.setForeground(Color.WHITE);
        btnConvert.setFocusPainted(false);
        btnConvert.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 2),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        inputPanel.add(btnConvert);

        mainPanel.add(inputPanel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        JLabel lblResult = new JLabel("Resultado: ---");
        lblResult.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblResult.setForeground(Color.WHITE);
        lblResult.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(lblResult);

        btnConvert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String text = txtValue.getText().trim();
                    double v = Double.parseDouble(text);
                    String code = cbDir.getSelectedItem().equals("F → C") ? "F2C" : "C2F";
                    String msg = code + ":" + v + "\n";
// crea cliente socket que conecta al servidor
                    try (Socket clientsocket = new Socket("localhost", 6789);
                         DataOutputStream out = new DataOutputStream(clientsocket.getOutputStream());
                         BufferedReader in = new BufferedReader(
                             new InputStreamReader(clientsocket.getInputStream()))) {

                        out.writeBytes(msg);
                        String resp = in.readLine();
                        lblResult.setText("Resultado: " + resp);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                        Cliente.this,
                        "Ingresa un número válido.",
                        "Error de formato",
                        JOptionPane.ERROR_MESSAGE
                    );
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(
                        Cliente.this,
                        "Error de conexión: " + ex.getMessage(),
                        "Socket Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        setContentPane(mainPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(380, 260);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Cliente();
            }
        });
    }
}

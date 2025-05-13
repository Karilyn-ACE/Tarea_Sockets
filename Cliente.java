import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;

public class Cliente {
       public static void main(String argv[]) throws Exception     
        {    
               JFrame frame = new JFrame("Conversos de grados");
        frame.setLayout(new FlowLayout(10, 10, 10));

        JTextField txtValor = new JTextField(6);
        JComboBox<String> cbDir = new JComboBox<>(new String[]{"F2C", "C2F"});
        JButton btnConvertir = new JButton("Convertir");
        JLabel lblResultado = new JLabel("---");   
                    
                    frame.add(new JLabel("Valor:"));
                    frame.add(txtValor);
                    frame.add(cbDir);
                    frame.add(btnConvertir);
                    frame.add(lblResultado);
        btnConvertir.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                try {
                   double v = Double.parseDouble(txtValor.getText().trim());
                     String msg = cbDir.getSelectedItem() + ":" + v + "\n";

                    Socket socket = new Socket("localhost", 6789);
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));

                    out.writeBytes(msg);
                    String resp = in.readLine();          
                    lblResultado.setText(resp);             

                    socket.close();                   
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "INGRESE CORRECTAMENTE NUMERO ", "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch( IOException ex) {
                     JOptionPane.showMessageDialog(frame,
                        "Error de conexión: " + ex.getMessage(), "Socket error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }

            
        });

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
            








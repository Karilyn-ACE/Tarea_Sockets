import java.io.*; 
 import java.net.*;  
import javax.swing.*;
//Apliación servidor
public class Servidor {
   public static void main(String argv[]) throws Exception  
   {  
    //creacion de la interfaz del servidor 
        JFrame frame = new JFrame("Servidor");
        JTextArea logArea = new JTextArea(20, 40);
        logArea.setEditable(false);
        frame.add(new JScrollPane(logArea));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


//creacion de un hilo para poder crear el socket de bienvenida que pondra en contacto con los clientes
          new Thread(new Runnable() {
            public void run() {
                try {
                    ServerSocket welcomeSocket = new ServerSocket(6789);
                    appendLog(logArea, "Servidor escuchando en puerto 6789...");
                    
                    while (true) {
                        Socket connectionSocket = welcomeSocket.accept();
                        
                        BufferedReader inFromClient = 
                            new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                        DataOutputStream outToClient = 
                            new DataOutputStream(connectionSocket.getOutputStream());
                        
                            //la información para que el servidor realice la conversión del clima
                        String peticiónCliente = inFromClient.readLine(); 
                        
                        String[] parts = peticiónCliente.split(":", 2);
                        String codigo  = parts[0];
                        double valor = Double.parseDouble(parts[1]);
                        double result;
                        String unit;
                        
                        if ("F2C".equals(codigo)) {
                            result = (valor - 32) * 5.0 / 9.0;
                            unit = "C";
                        } else {
                            result = valor * 9.0 / 5.0 + 32;
                            unit = "F";
                        }
                        
                        String respuesta = unit + ":" + String.format("%.4f", result) + "\n";
                        
                        outToClient.writeBytes(respuesta);
                        connectionSocket.close();
                        
                        appendLog(logArea, 
                            "Petición: " + peticiónCliente + " → Respuesta: " + respuesta.trim()
                        );
                    }
                } catch (IOException e) {
                    appendLog(logArea, "ERROR de servidor: " + e.getMessage());
                }
            }
            
            private void appendLog(final JTextArea area, final String text) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        area.append(text + "\n");
                    }
                });
            }
        }).start();
    }
}
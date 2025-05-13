import java.io.*; 
 import java.net.*;  

public class Servidor {
   public static void main(String argv[]) throws Exception  
   {  
          String peticionCliente;        
          String resultado;      
             ServerSocket welcomeSocket = new ServerSocket(6789);    
                    while(true) {                
                         Socket connectionSocket = welcomeSocket.accept();            
                           BufferedReader inFromClient =  new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));  
    DataOutputStream  outToClient =  new DataOutputStream(connectionSocket.getOutputStream());  

   peticionCliente = inFromClient.readLine(); 

        String[]p =peticionCliente.split(":",2);
            String codigo = p[0];
            double valor = Double.parseDouble(p[1]);    
            
            double resultadoCliente;
            if ("F2C".equals(codigo)){
                resultadoCliente = (valor - 32)* 5/  9;
               
                resultado= "C:"
        + String.format("%.4f",resultadoCliente) + "\n";  }

        else{
           resultadoCliente = valor * 9/5+32;
           resultado = "F:" + String.format("%.4f",resultadoCliente) + "\n";  
        }
        outToClient.writeBytes(resultado);
        System.out.println("Petición: "+ peticionCliente + "respuesta: " + resultadoCliente);


        }}}

            
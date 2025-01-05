import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

class Client {
    private static final String LOCALHOST = "localhost";
    private static final int PORT = 8888;

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("username required as argument");
            return;
        }
        String username = args[0];

        try (
                Socket serverSocket = new Socket(LOCALHOST, PORT);
                PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
                BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))

        ) {
           String helloServer = "!hello " + username;
           out.println(helloServer);
           String serverTest = in.readLine();
           if(!"!ack".equalsIgnoreCase(serverTest))
           {
               throw new RuntimeException("Server didn't properly respond");
           }
           String userInput;
           while((userInput = consoleInput.readLine()) != null) {
               out.println(userInput);

               String serverResponse = in.readLine();
               System.out.println("Server: " + serverResponse);

               if ("!bye".equalsIgnoreCase(serverResponse) || "!byebye".equalsIgnoreCase(serverResponse)) {
                   System.out.println("Connection closed by the server.");
                   break;
               }
           }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
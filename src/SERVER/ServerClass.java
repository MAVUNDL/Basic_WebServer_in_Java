package SERVER;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class defines the server
 */
public class ServerClass
{
    /**
     * This constructor creates the ServerSocket that will listen on port 4321
     */
    public ServerClass()
    {
        int port = 4321; //  port number to listen for connections
        // creating the ServerSocket instance to listen on port 4321
        try(ServerSocket serverSocket = new ServerSocket(port))
        {
            System.out.println("Server started");
            /*
                Here I am creating an infite loop to keep the server listening for incoming connection.
             */
          while(true)
          {
              Socket socket = serverSocket.accept(); // accept any incoming connect from clients
              new ClientHandler(socket).start(); // creating a Thread for each client connected to handle requests from that client.
          }
        }
        catch(IOException e)
        {
            System.out.println("Server side IOException: " + e.getMessage());
        }
    }
}

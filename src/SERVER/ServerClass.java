package SERVER;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerClass
{
    public ServerClass()
    {
        // create server socket
        int port = 4321;
        try(ServerSocket serverSocket = new ServerSocket(port))
        {
          while(true)
          {
              // accept client
              Socket socket = serverSocket.accept();
              System.out.println("New client connected");
              // accept client with threading
              new ClientHandler(socket).start();
          }
        }
        catch(IOException e)
        {
            System.out.println("Server side IOException: " + e.getMessage());
        }
    }
}

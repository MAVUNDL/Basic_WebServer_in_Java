package SERVER;

import REQUESTS.RequestsHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread
{
    private Socket socket;

    public ClientHandler(Socket socket)
    {
        this.socket = socket;
    }

    @Override
    public void run()
    {
        try(BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true))
        {
            // I have to know read the communication from the client
            String inputLine = in.readLine();
            // check if we read an empty line
            if(inputLine.isEmpty())
            {
                System.out.println("Request received is empty");
            }
            // handle the request
            RequestsHandler handler =  new RequestsHandler(inputLine, out, socket);
        }
        catch(IOException e)
        {
            System.out.println("ClientHandler IOException: " + e.getMessage());
        }
    }


}

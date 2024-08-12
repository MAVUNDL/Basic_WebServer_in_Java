package SERVER;

import REQUESTS.RequestsHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * This class extends the Thread class to create and Manage threads
 * NB: I am inheriting from Thread instead of implementing Runnable deliberately. This is allowed in Programming !!!!!!! and I know what I am doing
 */
public class ClientHandler extends Thread
{
    private Socket socket; // Socket instance to connect server with client

    /**
     * This constructor initializes the socket instance on our class
     * @param socket is the socket instance from the Server
     */
    public ClientHandler(Socket socket)
    {
        this.socket = socket; // initialize
    }

    /**
     * ClientHandler overrides the run Moth from the Thread class to define the thread's task
     */
    @Override
    public void run()
    {
        // Create the streams to read and write the requests from and to the client
        try(BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true))
        {
            /* Here I am only reading the first line from the request deliberately
               Because I am only interested on this line since it has the:  Request type - Request Url - Protocol version.
               I could have used a while loop to read all tye lines from the  request  and used a StringBuilder to store each line
               But there's no need for that, it would be a waste of time.
             */
            String inputLine = in.readLine();
            // check if we read an empty line
            if(inputLine.isEmpty())
            {
                System.out.println("Request received is empty");
            }
            // if the line in not empty handle the request
            RequestsHandler handler =  new RequestsHandler(inputLine, out, socket);
        }
        catch(IOException e)
        {
            System.out.println("ClientHandler IOException: " + e.getMessage());
        }
    }


}

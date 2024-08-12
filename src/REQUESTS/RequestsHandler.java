package REQUESTS;

import java.io.*;
import java.net.Socket;

/**
 * This class defines methods that handle the requests from the user
 */
public class RequestsHandler
{
    private final String request; //  request from user
    private final PrintWriter out_to_client; // stream to communicate with the client
    private final Socket socket; // socket instance that connects the server with the client

    /**
     * This constructor initializes the socket , the stream, and  request from client
     * @param request Request from client
     * @param out stream to write to the browser
     * @param socket socket instance that connects the server with client
     */
    public RequestsHandler(String request, PrintWriter out, Socket socket)
    {
        // initialize variables
        this.request = request;
        this.out_to_client = out;
        this.socket = socket;

        handle_request(); // handle the request from the client
    }

    /**
     * This method handles the requests from the client by serving the files and communicate with the browser
     */
    private void handle_request()
    {
        /*
            Here I take that line I read from the request and I extract the necessary information I need which is:
            1. Request type
            2. Request Url
            3. Protocol Version

            I first split the string my spaces and store the results on a String array. Then access information sequentially
         */
        String[] request_message = request.split(" ");
        // the type of request
        String request_type = request_message[0];
        // the requested url
        String request_url = request_message[1];
        // the protocol version
        String request_protocol_version = (request_message[2]);
        // check if the request type is get
        if(request_type.equals("GET"))
        {
            // handle the request by using the requested url
            switch (request_url)
            {
                case "/Joburg":
                    serve("data/data/Joburg.html", out_to_client);
                    break;
                case "/Durban":
                    serve("data/data/Durban.html", out_to_client);
                    break;
                case "/Cape":
                    serve("data/data/CapeWithImage.html", out_to_client);
                    break;
                case "/Africa":
                    serve("data/data/Sample.m4v", out_to_client);
                    break;
                case "/Africa.jpg":
                    serve("data/data/Africa.jpg", out_to_client);
                default:
                    String[] arr = request_protocol_version.split("\r\n");
                    out_to_client.write(arr[0] + " 404" + " Not found\r\n" + "Content-Type: text/html\r\n" + "\r\n" + "File not found");
                    out_to_client.flush();
            }
        }
    }

    /**
     * This method handles the functionality to server the files to the browser
     * @param file_path path to the file
     * @param writer stream used to send headers to the browser
     */
    private void serve(String file_path, PrintWriter writer)
    {
        // creating a byte stream to send the files to the browser
        try(DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream())))
        {
            // get the file from path and read it
            File file_to_return = new File(file_path);
            // ensure if the file exists
            if(file_to_return.exists())
            {
                // first send the headers
                String headers = "HTTP/1.1 200 OK\r\n" +
                    "Content-Length: " + file_to_return.length() + "\r\n" + "\r\n";
                writer.write(headers);
                writer.flush();

                // read the file to memory
                try(FileInputStream in = new FileInputStream(file_to_return))
                {
                    // create a buffer to store the bytes read from the file
                    byte[] buffer = new byte[1024];
                    int bytesRead = 0;
                    // read the file into the buffer
                    while((bytesRead = in.read(buffer)) > 0)
                    {
                        // write the data from the buffer from byte zero to the last into the data output stream
                        out.write(buffer, 0, bytesRead);
                        out.flush();
                    }
                }
                catch (IOException e)
                {
                   writer.write("HTTP/1.1 500 Internal Server Error\r\n" + "\r\n" +
                           "Content-Type: text/html\r\n" + "\r\n" + "Internal Server Error");
                   writer.flush();
                }
            }
            else
            {
                System.out.println("File does not exist or could not be read");
            }
        }
        catch(IOException e)
        {
            System.out.println("Serving file IOException: " + e.getMessage());
        }
    }
}

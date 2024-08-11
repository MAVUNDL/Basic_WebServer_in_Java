package REQUESTS;

import java.io.*;
import java.net.Socket;

public class RequestsHandler
{
    private final String request;
    private final PrintWriter out_to_client;
    private final Socket socket;

    public RequestsHandler(String request, PrintWriter out, Socket socket)
    {
        // initialize variables
        this.request = request;
        this.out_to_client = out;
        this.socket = socket;

        // handle the requests
        handle_request();
    }

    private void handle_request()
    {
        // split the request message
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
            // handle the request
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
                    System.out.println(arr[0]);
                    out_to_client.write(arr[0] + " 404" + " Not found\r\n" + "Content-Type: text/plain\r\n" + "\r\n" + "File not found");
                    out_to_client.flush();
            }
        }
    }

    private void serve(String file_path, PrintWriter writer)
    {
        try(DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream())))
        {
            File file_to_return = new File(file_path);

            if(file_to_return.exists())
            {
                // first send the headers
                String headers = "HTTP/1.1 200 OK\r\n" +
                    "Content-Length: " + file_to_return.length() + "\r\n" + "\r\n";
                writer.write(headers);
                writer.flush();

                // now sending the actual file
                try(FileInputStream in = new FileInputStream(file_to_return))
                {
                    byte[] buffer = new byte[30000];
                    int bytesRead = 0;
                    // read the file into the byte array
                    while((bytesRead = in.read(buffer)) > 0)
                    {
                        // write the data from the buffer from byte zero to the last into the data output stream
                        out.write(buffer, 0, bytesRead);
                        out.flush();
                    }
                }
                catch (IOException e)
                {
                    System.out.println("FileInputStream Error");
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

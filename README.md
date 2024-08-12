# Web Server Implementation

## Overview

 A web server socket that listens on port `4321`. The server  accepts client connections, establishs input/output streams, and process requests according to HTTP. The server handles various response codes and serve different pages based on the requested URL.

## Requirements

### Server Setup

- **Port**: The server listens on port `4321`.
- **Streams**: Establishes the necessary input and output streams for handling client connections.
- **HTTP Handling**: Process requests following HTTP protocols.

### Response Codes

- **200 OK**: When a request can be served without issues. The server is capable of handling binary files, such as images.
- **404 Not Found**: When a requested page or content cannot be found.
- **500 Internal Server Error**: When an error occurs on the server side.

### URL Handling

The server handles the following requests and serve the corresponding files:

- `localhost:4321/Joburg` should serve the `Joburg.html` file.
- `localhost:4321/Durban` should serve the `Durban.html` file.
- `localhost:4321/Cape` should serve the `CapeWithImage.html` file.
- `localhost:4321/Africa.jpg` should serve the `Africa.jpg` file.

### Testing

- **Browsers**: Test the web server using different web browsers such as Opera, Firefox, and Chrome on `localhost`.

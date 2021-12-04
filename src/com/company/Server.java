package cs158.com.company;
import java.net.*;
import java.io.*;
import java.nio.file.Files;

public class Server{
    public static void main(String[] args) throws IOException {
        ServerSocket serversocket = new ServerSocket(80); // establish HTTP receiver socket
        System.out.println("Socket created on " + InetAddress.getLocalHost() + "/" + serversocket.getLocalPort());
        final String CRLF = "\r\n";
        try {
            // boot up server
            Socket socket = serversocket.accept();

            // begin receiving
            InputStream instream = socket.getInputStream();
            BufferedReader br1 = new BufferedReader(new InputStreamReader(instream));

            // receive request
            String request = br1.readLine();
            System.out.println("Request Received: " + request); // print the whole request

            // locate requested file
            String[] requestParams = request.split(" ");
            String path = "assets" + requestParams[1], response;
            System.out.println("Requested file = " + path); // print the requested file

            // send response
            OutputStream out = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(out, true);
            File file = new File(path); //access file
            if (!file.exists()) { // if requested file doesn't exist, response with 404
                response = "HTTP/1.1 404 Not Found ";
                writer.write(response); //send code
                // end response
                writer.append(CRLF).flush();
                out.flush();
            } else { // file exists, respond with 200 and file requested
                response = "HTTP/1.1 200 OK ";
                writer.write(response); // send code
                // create headers
                writer.append("Content-Disposition: form-data; name=\"binaryFile\"; filename=\"")
                        .append(file.getName())
                        .append("\"")
                        .append(CRLF);
                String contentType = URLConnection.guessContentTypeFromName(file.getName());
                writer.append("Content-Type: ")
                        .append(contentType)
                        .append(";charset=utf-8")
                        .append(CRLF);
                writer.append("Content-Transfer-Encoding: binary")
                        .append(CRLF);
                writer.append(CRLF).flush();
                Files.copy(file.toPath(), out); //send file
                writer.append(CRLF).flush();
                out.flush();
            }
            // close all open readers and writers
            writer.close();
            out.close();
            br1.close();
            System.out.println("Sent response: " + response); // print response code
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

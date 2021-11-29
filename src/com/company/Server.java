package cs158.com.company;
import java.net.*;
import java.io.*;

public class Server{
    public static boolean running;

    public static void main(String[] args) throws IOException {
        ServerSocket serversocket = new ServerSocket(5500); // establish HTTP receiver socket
        System.out.println("Socket created on " + InetAddress.getLocalHost() + "/" + serversocket.getLocalPort());
        try {
            //while (true) {
                Socket socket = serversocket.accept();

                System.out.println("A message has been received!");

                InputStream instream = socket.getInputStream();
                BufferedReader br1 = new BufferedReader(new InputStreamReader(instream));
                String request = br1.readLine();
                String[] requestParams = request.split(" ");
                //br.close();

                System.out.println("Request Received: " + request);

                String path = requestParams[1].substring(1), response;
                System.out.println("Requested file = " + path);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                File file = new File(path);
                if (!file.exists()){
                    out.write("404");
                } else {
                    out.write("200");
                    FileReader fr = new FileReader(file);
                    BufferedReader br2 = new BufferedReader(fr);
                    String line;
                    StringBuilder content = new StringBuilder();
                    while ((line = br2.readLine()) != null) {
                        content.append(line);
                    }
                    out.write(content.toString());
                    fr.close();
                    br2.close();
                }
                //br1.close();
                out.close();
          //  }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String GenerateHTTPResponse(int code, String message){
        return "HTTP/1.1 " + code + " " + message + "\n";
    }
}

package cs158.com.company;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpClient.*;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

//simulates client.
public class Client {
    public static void main(String[] args) {
        // creates and sends HTTP GET request based on command line argument
        String requested_file = args[0]; // target specific path
        HttpClient client = HttpClient.newHttpClient(); //establish http client
        try {
            System.out.println("Client running on " + InetAddress.getLocalHost());
            // create http request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:5500/" + requested_file)) // set request parameters
                    .GET() // set http verb
                    .version(Version.HTTP_1_1)
                    .build(); // build the request
            System.out.println("client sending request: " + request.toString());
            // send the request and receive the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

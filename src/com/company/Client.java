package cs158.com.company;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


//simulates client.
public class Client {
    private static HttpURLConnection con;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter file you want to get: ");
        String requestedFile = scanner.nextLine(); // grab file request
        final int PORT = 80;

        try {
            URL myurl = new URL("http://localhost:" + PORT + "/" + requestedFile);
            System.out.println("Sending GET request to " + myurl);
            InputStream res = myurl.openStream();
            //prepare file to save to
            File file = new File("local/" + requestedFile);
            file.getParentFile().mkdirs();
            file.createNewFile();
            //create writer
            BufferedWriter writer = new BufferedWriter(new FileWriter(file,false));
            System.out.println("Response Received:");
            //save the file
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(res, StandardCharsets.UTF_8))) {
                //write in line by line
                String line;
                while ((line = in.readLine()) != null) {
                    writer.write(line);
                    writer.flush();
                    System.out.println(line);
                }
            }
            //end save
            writer.close();
            System.out.println("Saved to "+ file.getCanonicalPath()); // print save path
        } catch (FileNotFoundException e) {
            System.out.println(requestedFile + " Not found!");
            e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}

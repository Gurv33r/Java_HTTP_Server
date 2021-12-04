package cs158.com.company;
import java.io.*;
import java.net.*;
import java.nio.channels.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter file you want to get: ");
        String requestedFile = scanner.nextLine(); // grab file request
        final int PORT = 80;
        File file = new File("local/" + requestedFile); // make file object
        try {
            URL url = new URL("http://localhost:" + PORT + "/" + requestedFile);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            System.out.println("Sending GET request to " + url);
            // send request and open response
            ReadableByteChannel byteChannel = Channels.newChannel(conn.getInputStream());
            System.out.println("Response Received: " + conn.getResponseMessage()); // print response header
            //prepare file to save to
            file.getParentFile().mkdir(); // make local directory if it doesn't exist
            file.createNewFile(); // make file
            // setup file saver
            FileOutputStream fileOutputStream = new FileOutputStream(file.getPath());
            FileChannel fileChannel = fileOutputStream.getChannel();
            // transfer file info from response
            fileChannel.transferFrom(byteChannel, 0, Long.MAX_VALUE);
            System.out.println("Saved to " + file.getCanonicalPath()); // print save location
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}

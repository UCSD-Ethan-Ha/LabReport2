import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    int num = 0;
    int sequence = 0;
    ArrayList messages = new ArrayList();

    public String handleRequest(URI url) {
        
        if (url.getPath().equals("/")) {
            return String.format("Number: %d", num);
        } else if (url.getPath().equals("/increment")) {
            num += 1;
            return String.format("Number incremented!");
        } else {
            if (url.getPath().contains("/add-message")) {
                // parameter[0] = s &  [1]= Hello
                String[] parameters = url.getQuery().split("=");
                if (parameters.length >= 2 && parameters[0].equals("s")) {
                    String output = "";
                    int sequence = 1;

                    messages.add(parameters[1]);
                    for (int i = 0; i < messages.size(); i++){
                        output += sequence + ". " + messages.get(i) + "\n";
                        sequence++;
                    } 
                    
                    return output;
                } else {
                    return "Please enter s=(insert value here)";
                }
            }
            return "404 Not Found!";
        }
    }
}

class StringServer {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}

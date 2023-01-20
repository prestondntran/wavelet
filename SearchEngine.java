import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> strings = new ArrayList<String>();

    public String handleRequest(URI url) {
        if (url.getPath().contains("/add")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                strings.add(parameters[1]);
                return String.format("Added " + parameters[1] + " to list!");
            }
        }
        else if (url.getPath().contains("/search")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                String substr = parameters[1];
                ArrayList<String> searches = new ArrayList<String>();
                for (int i = 0; i < strings.size(); i++) {
                    if (strings.get(i).contains(substr))
                        searches.add(strings.get(i));
                }
                String searchList = "";
                if (searches.size() == 0)
                    return "No matches found";
                for (int i = 0; i < searches.size(); i++)
                    searchList += searches.get(i) + " ";
                return searchList;
            }
        }
        else 
            return "Search engine!";
        return null;
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
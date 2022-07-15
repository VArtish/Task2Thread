import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.Callable;

record RemoteResourcesReaderThread(String link) implements Callable<String> {

    @Override
    public String call() throws Exception {

        StringBuilder result = new StringBuilder();
        URL url = new URL(link);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null)
        {
            result.append(inputLine);
        }

        return result.toString();
    }
}

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        String link = "https://ru.wikipedia.org/w/api.php?action=query&list=search&utf8=&format=json&srsearch=";
        Scanner in = new Scanner(System.in);
        String line = in.nextLine();

        String[] words = line.split(";");

        ExecutorService executorService = Executors.newFixedThreadPool(words.length);
        List<Callable<String>> callables = new ArrayList<>();

        for (String word : words) {
            String query = link.concat(word);
            callables.add(new RemoteResourcesReaderThread(query));
        }

        try (FileWriter fileWriter = new FileWriter("src/main/resources/result")) {
            List<Future<String>> futures = executorService.invokeAll(callables);
            executorService.shutdown();

            if(!futures.isEmpty()) {
                for (Future future : futures) {
                    fileWriter.write(future.get().toString());
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        } catch (ExecutionException executionException) {
            executionException.printStackTrace();
        }
    }
}

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final FileReader reader = new CSharpReader(-10, 10);

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        System.out.print("Enter an absolute path: ");
//        Path dir = Paths.get(scanner.nextLine());
        Path dir = Paths.get("/Users/admin/Desktop");

        try(DirectoryStream<Path> paths = Files.newDirectoryStream(dir)) {
            ExecutorService executorService = Executors.newCachedThreadPool();
            CountDownLatch cdl = new CountDownLatch(Objects.requireNonNull(dir.toFile().list()).length);

            for (Path p : paths) {
                executorService.submit(() -> {
                    bypassAndProceedFile(p);
                    cdl.countDown();
                });
            }

            cdl.await();
            executorService.shutdown();

            System.out.println("\nTime was taken: " + (System.currentTimeMillis() - time));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void bypassAndProceedFile(Path path) {
        if (path.toFile().isDirectory()) {
            try (DirectoryStream<Path> paths = Files.newDirectoryStream(path)) {
                for (Path p : paths) {
                    bypassAndProceedFile(p);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            reader.read(path);
        }
    }
}

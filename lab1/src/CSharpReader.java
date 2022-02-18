import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CSharpReader implements FileReader {

    private static final String fileExt = ".cs";
    private final Pattern pattern = Pattern.compile("(-?[1-9][0-9]*)");
    private final int fromValue, toValue;

    public CSharpReader(int from, int to) {
        this.fromValue = from;
        this.toValue = to;
    }

    @Override
    public void read(Path path) {
        if (!path.toString().toLowerCase().endsWith(fileExt))
            return;

        try {
            String data = Files.readString(path);

            String[] rows = data.split("\n");
            for (String row : rows) {
                Matcher matcher = pattern.matcher(row);
                while (matcher.find()) {
                    int number = Integer.parseInt(matcher.group());
                    if (fromValue <= number && number <= toValue) {
                        System.out.println("File name: " + path.getFileName() + " -> " + row.trim());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class SolutionsToSubmit {

    private static int keep[] = {5, 7, 11, 17, 23, 29, 37, 47, 59, 71, 83, 97, 113, 131, 149, 167, 191, 223, 257, 293, 331, 373, 419, 467, 521};

    public static void main(String args[]) throws Exception {
        Stream<String> stream = Files.lines(Paths.get("solutions.txt"));
        stream.forEach(line -> doline(line));
    }

    private static void doline(String line) {
        line = line.trim();
        if (isValidPuzzel(line)) {
            String answer = between(line, "[", "]");
            System.out.println(answer + ";");
        }
    }

    private static boolean isValidPuzzel(String line) {
        if(line.startsWith("min(") || line.startsWith("max")) {
            Integer number = Integer.valueOf(between(line, "(", ")"));
            if (Arrays.binarySearch(keep, number) >= 0) {
                return true;
            }
        }
        return false;
    }

    public static String between(String str, String open, String close) {
        if (str == null || open == null || close == null) {
            return null;
        }
        int start = str.indexOf(open);
        if (start != -1) {
            int end = str.indexOf(close, start + open.length());
            if (end != -1) {
                return str.substring(start + open.length(), end);
            }
        }
        return null;
    }
}
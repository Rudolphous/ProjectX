import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class RCChecker {

    private final int RC_CACHE[][];
    private final int maxWidth;
    private final Map<Double, Integer> doubleToCounter = new TreeMap<>();
    private boolean usedRC[];

    public RCChecker(int maxWidth) {
        this.maxWidth = maxWidth-1;

        //if width is 100 we can go from 0, 100 and 100 to 0.
        //to support both directions we double the size
        int arraysize = maxWidth * 2;
        RC_CACHE = new int[arraysize][arraysize];
        init();
    }

    private void init() {
        Integer counter = 0;
        for (int x = -maxWidth; x <= maxWidth; x++) {
            for (int y = -maxWidth; y <= maxWidth; y++) {
                Double value = calcRC(x, y);
                Integer rcNumber = doubleToCounter.get(value);
                if (rcNumber == null) {
                    rcNumber = ++counter;
                    doubleToCounter.put(value, rcNumber);
                }
                RC_CACHE[x + maxWidth][y + maxWidth] = rcNumber;
            }
         }

         usedRC = new boolean[counter];

        System.out.println("total unique rc's: " + counter);
    }

    public int lookupRC(int x1, int y1, int x2, int y2) {
        int deltax = x2 - x1;
        int deltay = y2 - y1;
        return RC_CACHE[maxWidth + deltax][maxWidth + deltay];
    }

    public void useRC(int rc) {
        usedRC[rc] = true;
    }

    public void unUseRC(int rc) {
        usedRC[rc] = false;
    }

    public boolean isUsed(int rc) {
        return usedRC[rc];
    }

    public void clear() {
        Arrays.fill(usedRC, false);
    }

    private double calcRC(int x, int y) {
        if (y == 0) {
            return Double.MAX_VALUE;
        }
        if (x == 0) {
            return 0;
        }
        return (double)x / y;
    }
}

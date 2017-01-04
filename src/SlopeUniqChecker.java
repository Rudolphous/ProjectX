import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class SlopeUniqChecker {

    private final int SLOPE_TO_NUMBER_CACHE[][];
    private final int maxWidth;
    private final Map<Double, Integer> doubleToCounter = new TreeMap<>();
    private boolean usedSlopes[];

    public SlopeUniqChecker(int maxWidth) {
        this.maxWidth = maxWidth-1;

        //if width is 100 we can go from 0, 100 and 100 to 0.
        //to support both directions we double the size
        int arraysize = maxWidth * 2;
        SLOPE_TO_NUMBER_CACHE = new int[arraysize][arraysize];
        init();
    }

    private void init() {
        Integer slopes = 0;
        for (int x = -maxWidth; x <= maxWidth; x++) {
            for (int y = -maxWidth; y <= maxWidth; y++) {
                Double value = calcSlope(x, y);
                Integer slopeId = doubleToCounter.get(value);
                if (slopeId == null) {
                    slopeId = ++slopes;
                    doubleToCounter.put(value, slopeId);
                }
                SLOPE_TO_NUMBER_CACHE[x + maxWidth][y + maxWidth] = slopeId;
            }
         }

         usedSlopes = new boolean[slopes];

        System.out.println("total unique slopes: " + slopes);
    }

    public int lookupSlope(int x1, int y1, int x2, int y2) {
        int deltax = x2 - x1;
        int deltay = y2 - y1;
        return SLOPE_TO_NUMBER_CACHE[maxWidth + deltax][maxWidth + deltay];
    }

    public void useSlope(int slope) {
        usedSlopes[slope] = true;
    }

    public void unuseSlope(int slope) {
        usedSlopes[slope] = false;
    }

    public boolean isSlopeUsed(int slope) {
        return usedSlopes[slope];
    }

    public void clear() {
        Arrays.fill(usedSlopes, false);
    }

    private double calcSlope(int x, int y) {
        if (y == 0) {
            return Double.MAX_VALUE;
        }
        if (x == 0) {
            return 0;
        }
        return (double)x / y;
    }
}

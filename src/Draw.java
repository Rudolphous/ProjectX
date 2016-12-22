import javax.swing.*;
import java.awt.*;

public class Draw extends JFrame{

    private static int maxWidth, maxHeight;
    private static double scaleX, scaleY;
    private static int borderx, bordery;

    public Draw(){
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.maxWidth = 800;
        this.maxHeight = 800;
        this.borderx = 15;
        this.bordery = 30;
        setSize(maxWidth, maxHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String a[]){
        new Draw();
    }

    public void paint(Graphics g){
        String input = "(1,1), (2,2), (4,3), (3,4), (6,5), (5,7), (7,6), (8,8), (9,11), (12,10), (11,13), (14,15), (16,12), (13,14), (15,9), (18,16), (20,19), (24,18), (23,22), (19,21), (22,25), (26,28), (25,24), (28,20), (32,17), (31,23), (36,26), (41,27), (40,32), (45,31), (46,36), (51,34), (49,29), (55,30), (52,35), (57,37), (60,42), (65,39), (64,33), (68,38), (67,45), (73,44), (78,40), (79,47), (75,52), (70,48), (63,49), (56,51), (54,58), (47,57), (50,50), (48,43), (39,41), (33,46), (29,53), (34,59), (27,56), (30,64), (37,66), (44,63), (43,55), (35,60), (42,54), (53,61), (61,62), (69,65), (76,69), (82,74), (77,80), (83,73), (80,81), (66,82), (58,83), (59,72), (71,77), (62,78), (74,79), (81,75), (72,67), (38,70), (17,71), (10,76), (21,68)";
        Point[] points = convertToPoints(input);
        scaleX = ((maxWidth - 2 * borderx) / points.length);
        scaleY = ((maxHeight - 2 * bordery) / points.length);
        drawPoint(g, points[points.length-1], points[0]);
        for (int i=0; i<points.length; i++) {
            drawPoint(g, points[i], points[i+1]);
        }
    }

    private void drawPoint(Graphics g, Point p1, Point p2) {
        int x1 = (int)(p1.x * scaleX) + borderx;
        int y1 = (int)(p1.y * scaleY) + bordery;
        int x2 = (int)(p2.x * scaleX) + borderx;
        int y2 = (int)(p2.y * scaleY) + bordery;
        g.drawLine(x1, y1, x2, y2);
    }

    private Point[] convertToPoints(String s) {
        s = s.replaceAll(" ", "");
        String parts[] = s.split(",\\(");
        Point[] points = new Point[parts.length];
        for (int i = 0; i<points.length; i++) {
            points[i] = converToPoint(parts[i]);
        }
        return points;
    }

    private Point converToPoint(String s) {
        s = s.replaceAll("[()]", "");
        String parts[] = s.split(",");
        int x = Integer.valueOf(parts[0].trim());
        int y = Integer.valueOf(parts[1].trim());
        return new Point(x-1, y-1);
    }
}
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
        String input = "(2,1), (1,2), (3,4), (5,5), (4,3), (8,6), (6,7), (9,8), (12,10), (11,12), (10,9), (7,13), (13,14), (14,11), (17,15), (15,18), (18,17), (20,20), (19,16), (24,19), (23,23), (26,21), (25,26), (21,25), (16,24), (27,27), (22,28), (29,29), (28,22)";
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
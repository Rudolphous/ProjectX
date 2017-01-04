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
        String input = "(22,25), (24,28), (55,32), (40,31), (49,38), (46,33), (53,41), (58,27), (51,47), (45,45), (50,46), (52,40), (44,35), (41,42), (37,48), (42,39), (43,34), (35,36), (33,43), (34,37), (29,44), (30,49), (56,55), (47,53), (61,58), (68,64), (64,61), (65,65), (70,68), (66,54), (71,71), (59,66), (67,69), (48,60), (63,67), (54,62), (69,70), (60,59), (62,63), (57,57), (38,51), (27,50), (10,56), (21,52), (39,30), (17,29), (9,8), (4,4), (3,3), (6,7), (2,2), (5,11), (1,1), (7,6), (8,5), (11,12), (15,13), (12,15), (14,16), (19,10), (16,14), (20,9), (13,18), (18,26), (23,24), (26,19), (25,23), (32,20), (36,17), (31,21), (28,22)";
        Point[] points = SolutionToPoinsConvertor.convertToPoints(input);
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
}
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
        String input = "(70,46), (67,45), (75,48), (69,52), (58,50), (62,43), (53,56), (50,63), (44,71), (43,65), (42,72), (27,68), (33,58), (29,64), (34,55), (37,60), (39,67), (38,62), (35,53), (21,78), (30,70), (47,74), (48,66), (56,59), (63,61), (54,57), (59,51), (76,54), (82,41), (83,79), (74,77), (71,69), (72,76), (66,73), (80,82), (3,83), (4,80), (16,75), (1,81), (2,6), (8,5), (13,9), (17,12), (9,4), (11,3), (15,8), (10,1), (81,2), (61,16), (20,15), (12,10), (7,7), (14,13), (6,11), (28,18), (26,22), (5,14), (23,21), (22,25), (18,26), (19,28), (25,23), (24,24), (31,19), (41,17), (55,20), (60,29), (36,40), (40,37), (49,32), (46,33), (32,42), (45,36), (52,35)";
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
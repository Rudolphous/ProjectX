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
        String input = "(24,18), (15,19), (19,28), (28,17), (31,21), (26,20), (25,24), (36,22), (23,25), (32,23), (22,26), (41,36), (46,35), (49,30), (45,31), (55,27), (57,32), (52,29), (51,34), (40,37), (60,45), (64,46), (81,42), (68,40), (78,41), (67,38), (73,39), (65,33), (85,44), (86,68), (89,61), (94,50), (93,56), (95,49), (96,54), (90,59), (88,64), (87,71), (92,77), (97,69), (91,80), (84,70), (80,75), (75,57), (74,65), (70,51), (63,47), (71,48), (76,55), (79,67), (82,72), (83,43), (33,52), (42,63), (77,78), (39,62), (38,74), (37,60), (30,82), (44,84), (47,92), (56,73), (48,90), (59,76), (54,83), (50,96), (58,95), (66,88), (72,81), (62,93), (69,89), (61,94), (53,97), (34,91), (43,87), (27,86), (21,79), (10,66), (29,85), (35,58), (8,8), (9,11), (7,7), (17,53), (6,6), (4,5), (1,4), (3,1), (20,9), (5,2), (2,3), (12,10), (11,13), (14,15), (18,12), (16,14), (13,16)";
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
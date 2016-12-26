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
        String input = "(34,83), (21,60), (17,97), (30,105), (58,106), (74,99), (77,88), (69,81), (80,90), (76,98), (97,87), (59,107), (100,113), (83,111), (88,112), (72,109), (62,108), (19,104), (4,110), (15,102), (6,4), (2,3), (3,10), (1,1), (92,16), (84,15), (5,2), (8,7), (12,5), (14,12), (16,14), (20,19), (13,11), (18,24), (11,6), (10,9), (7,8), (9,33), (25,28), (23,23), (28,27), (40,22), (22,17), (41,21), (55,13), (36,25), (65,18), (32,26), (24,31), (26,39), (31,45), (67,42), (52,34), (46,36), (45,37), (51,29), (49,32), (60,38), (86,49), (85,46), (64,20), (81,41), (78,35), (89,51), (93,54), (87,50), (73,44), (57,43), (68,63), (94,57), (99,30), (98,48), (103,70), (111,52), (109,40), (113,61), (112,56), (105,82), (104,86), (108,75), (101,95), (107,74), (110,55), (106,68), (90,79), (82,85), (91,78), (102,69), (96,47), (95,59), (79,62), (71,64), (66,77), (70,92), (75,89), (53,103), (56,101), (33,91), (38,93), (54,100), (63,96), (61,53), (27,58), (29,73), (35,80), (47,66), (43,71), (50,65), (42,94), (48,67), (37,84), (44,72), (39,76)";
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
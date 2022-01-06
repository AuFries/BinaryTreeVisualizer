import java.awt.*;

public class Node { //This class represents a node object, which is the basis of a binary tree

    private int x, y; //X, Y coordinates
    private int diameter; //Diameter of circle
    private int value; //The number within the node
    private int level; //Height in the tree
    private Color color;

    private Node left; //Left/right children and the parent node
    private Node right;
    private Node parent;

    public Node(int x, int y, int diameter, int value, int level, Color color) { //The offset the node is drawn at is x - diameter/2 and y - diameter/2
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.value = value;
        this.color = color;
        this.level = level;
        parent = null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDiameter() {
        return diameter;
    }

    public int getValue() {
        return value;
    }

    public int getLevel() {
        return level;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public Node getParent() {
        return parent;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setLoc(Point p){
        x = p.x;
        y = p.y;
    }

    public void setLeft(Node n) {
        if (n != null) {
            n.setLevel(level+1);
        } else if (left != null) {
            left.setLevel(0);
        }
        left = n;
    }

    public void setRight(Node n) {
        if (n != null) {
            n.setLevel(level+1);
        } else if (right != null) {
            right.setLevel(0);
        }
        right = n;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void updateDiameterAndOffset(int diameterChange) {
        x -= diameterChange/2;
        y -= diameterChange/2;
        diameter += diameterChange;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x,y,diameter,diameter);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Serif", Font.PLAIN, 30));
        g.drawString(String.valueOf(value),x,y);
    }
}

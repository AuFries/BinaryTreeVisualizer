import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import java.lang.Math;

public class NodePanel extends JPanel {

    public List<Node> nodes = new LinkedList<>();

    Node prevSelectedNode; //Holds the previous selected node when traversing
    private String traverseString = ""; //A string which is drawn to canvas to show order of traversal

    public NodePanel() {
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(600,500));
    }

    public String getTraverseString() {
        return traverseString;
    }

    public void setTraverseString(String newString) {
        traverseString = newString;
    }

    public void addNode(Node node) {
        nodes.add(node);
        this.repaint();
    }

    public void removeNode(Node node) {
        nodes.remove(node);
    }

    public Node getRoot() { //Returns null if there is no root or more than 1 root, otherwise returns the root
        Node root = null;
        int numZero = 0;
        for (Node n : nodes) {
            if (n.getLevel() == 0) {
                numZero++;
                root = n;
            }
            if (numZero > 1) {
                return null;
            }
        }
        return root;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setFont(new Font("Serif", Font.PLAIN, 30));
        g.drawString(traverseString,10,getHeight()-20);
        for (Node n : nodes) { //Draw the nodes
            n.draw(g);
            if(n.getLeft() != null) { //Draw the lines to the children nodes
                g.setColor(Color.blue);
                g.drawLine(n.getX()+n.getDiameter()/2,n.getY()+n.getDiameter()/2,n.getLeft().getX()+n.getLeft().getDiameter()/2,n.getLeft().getY()+n.getLeft().getDiameter()/2);
            }
            if(n.getRight() != null) {
                g.setColor(Color.red);
                g.drawLine(n.getX()+n.getDiameter()/2,n.getY()+n.getDiameter()/2,n.getRight().getX()+n.getRight().getDiameter()/2,n.getRight().getY()+n.getRight().getDiameter()/2);
            }
        }
    }

    public Node checkNodeClick(MouseEvent e) { //Checks if a node is clicked and returns the node clicked.
        for (Node n : nodes) { //Must account for the offset
            double distance = Math.sqrt(Math.pow(n.getX() + n.getDiameter()/2.0 -e.getX(),2)+Math.pow(n.getY() + n.getDiameter()/2.0 -e.getY(),2)); //Distance from clicked point to center of node
            if (distance < n.getDiameter()/2.0) { //If distance is less than radius, the user has clicked within the node
                return n;
            }
        }
        return null; //Return null if no node is clicked
    }

    public void reconfigureTree() { //Places all nodes in perfect binary tree format
        Node root = getRoot();
        if (root != null) {
            for (Node n : nodes) { //Set all nodes position to the top of screen
                n.setX(getWidth()/2);
                n.setY(30);
            }
            int bottomWidth = (int) Math.pow(2, treeHeight(root) - 1) * 15; //Width at bottom is calculated by (2^height * some offset)
            reconfigureHelper(root,root.getX(),root.getY(),bottomWidth);

        }
        this.repaint();
    }

    private void reconfigureHelper(Node root, int xPos, int yPos,int width) { //Recursively places all nodes at their correct locations
        if (root == null) {
            return;
        }
        root.setX(xPos);
        root.setY(yPos);
        width /= 2;
        reconfigureHelper(root.getLeft(),xPos-width,yPos+50,width);
        reconfigureHelper(root.getRight(),xPos+width,yPos+50,width);
    }

    public int treeHeight(Node root) { //Returns the max height of the binary tree
        if (root == null) {
            return 0;
        }
        int leftHeight = treeHeight(root.getLeft());
        int rightHeight = treeHeight(root.getRight());
        return Math.max(leftHeight,rightHeight) + 1;
    }

    public void selectNodeForTraversal(Node node) { //Used for all traversal methods to select a node and change it's color
        for (Node n : nodes) {
            if (node == n) {
                if (prevSelectedNode != null && n == prevSelectedNode) {
                    prevSelectedNode.setColor(Color.green);
                } else if (prevSelectedNode != null) {
                    prevSelectedNode.setColor(Color.blue);
                }
                node.setColor(Color.yellow);
                this.paintImmediately(0,0,800,800); //This is called because .repaint() doesn't work within recursive calls
                prevSelectedNode = node;
                break;
            }
        }
    }
}

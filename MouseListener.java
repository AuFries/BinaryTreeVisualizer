import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MouseListener implements java.awt.event.MouseListener, MouseMotionListener, MouseWheelListener {

    private static Node prevClickedNode;

    private final NodePanel nodePanel;
    private final NodeSettings nodeSettings;

    private Node _selectedNode;
    private Point _startingLoc;

    private int curZoom = 24;
    private int prevZoom = 24;

    public MouseListener(NodePanel nodePanel, NodeSettings nodeSettings) {
        this.nodePanel = nodePanel;
        this.nodeSettings = nodeSettings;
    }

    public static Node getPrevClicked() { //Returns the previous clicked node
        return prevClickedNode;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        _selectedNode = nodePanel.checkNodeClick(e);
        _startingLoc = new Point(e.getX(), e.getY());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Node n = nodePanel.checkNodeClick(e);
        if (n == null) { //No node clicked
            if (!nodeSettings.getPlaced()) { //If a node hasn't been placed before, change the top text
                nodeSettings.placedANode();
            }
            nodePanel.addNode(new Node(e.getX() - 12, e.getY() - 12, curZoom, -1, 0, Color.blue));
        } else { //Bring up settings of clicked node
            if (prevClickedNode != null && prevClickedNode != n && n.getParent() == null && nodeSettings.getLeftListener().getActiveState() && n != nodePanel.getRoot()) { //Select a left node
                n.setParent(prevClickedNode);
                prevClickedNode.setLeft(n);
            } else if (prevClickedNode != null && prevClickedNode != n && n.getParent() == null && nodeSettings.getRightListener().getActiveState() && n != nodePanel.getRoot()) { //Select a right node
                n.setParent(prevClickedNode);
                prevClickedNode.setRight(n);
            }
            if (!nodeSettings.getClicked()) { //If a node hasn't been clicked, create the panel and settings components
                nodeSettings.clickedANode();
            }
            if (prevClickedNode != null && prevClickedNode != n) { //If the previous clicked node isn't null, and it's not the current node, return previous clicked node back to regular status
                prevClickedNode.setColor(Color.blue);
                prevClickedNode.updateDiameterAndOffset(-10);
                nodeSettings.updateNodeSettings(n);
            }
            if (n != prevClickedNode) { //If the node isn't already clicked, change color and diameter
                n.setColor(Color.green);
                n.updateDiameterAndOffset(10);
            }
            nodePanel.repaint();
            prevClickedNode = n;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        _selectedNode = null;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (_selectedNode != null) {
            _selectedNode.setX(e.getX()-_selectedNode.getDiameter()/2);
            _selectedNode.setY(e.getY()-_selectedNode.getDiameter()/2);
        } else {
            for (Node n : nodePanel.nodes) {
                System.out.println((e.getX() - _startingLoc.x));
                n.setX(n.getX() + (e.getX() - _startingLoc.x));
                n.setY(n.getY() + (e.getY() - _startingLoc.y));
            }
            _startingLoc = new Point(e.getX(), e.getY());
        }
        nodePanel.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) { //UPDATE THIS LATER THERE ARE SOME ERRORS
        curZoom = curZoom+ e.getWheelRotation();
        for (Node n : nodePanel.nodes) {;
            if (curZoom > prevZoom) { //Increasing zoom
                n.updateDiameterAndOffset(curZoom-n.getDiameter());
            } else { //Decreasing zoom
                n.updateDiameterAndOffset(-1*(n.getDiameter()-curZoom));
            }
            n.setLoc(getScaledPoint(n.getX(),n.getY(),e.getX()-n.getDiameter()/2,e.getY()-n.getDiameter()/2,24.0/(24+ e.getWheelRotation())));
        }
        nodePanel.repaint();
        prevZoom = curZoom;
    }

    public Point getScaledPoint(int x, int y,int startX,int startY, double scale){
        return new Point((int) ((x-startX) * scale)+startX, (int) ((y-startY) * scale)+startY);
    }
}

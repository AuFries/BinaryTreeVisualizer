import java.awt.*;
import javax.swing.*;

public class BinaryTreeApplication { //This class holds and runs the structure of the entire application.

    public static void main(String[] args) {
        BinaryTreeApplication app = new BinaryTreeApplication();
        JFrame frame = new JFrame("Binary Tree");
        app.setUpFrame(frame);
        app.addComponentsToFrame(frame);
    }

    private void setUpFrame(JFrame frame) { //Sets up basic window frame layout and settings
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800,800);
    }

    private void addComponentsToFrame(JFrame frame) { //Add all the components to the frame (canvas, buttons, etc)
        NodePanel nodePanel = new NodePanel(); //Create canvas panel
        ControlPanel controlPanel = new ControlPanel(nodePanel); //Create bottom control panel
        TraversalPanel traversalPanel = new TraversalPanel(nodePanel); //Create left traversal panel
        NodeSettings nodeSettings = new NodeSettings(nodePanel); //Create top node settings panel

        MouseListener ccl = new MouseListener(nodePanel,nodeSettings); //Create the mouse listener
        nodePanel.addMouseListener(ccl);
        nodePanel.addMouseMotionListener(ccl); //Add the listener to the graphics canvas
        nodePanel.addMouseWheelListener(ccl);

        frame.add(nodeSettings,BorderLayout.NORTH); //Add all the components to the window
        frame.add(controlPanel,BorderLayout.SOUTH);
        frame.add(traversalPanel,BorderLayout.WEST);
        frame.add(nodePanel,BorderLayout.CENTER);
        frame.setVisible(true); //Paint method is called whenever canvas needs to be drawn
    }
}

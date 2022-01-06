import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NodeSettings extends JPanel {

    private JButton leftChild; //Button to assign left child nodes
    private JButton rightChild; //Button to assign right child nodes

    private JTextField newValue; //A text field to take input for new node values
    private boolean placedANode = false; //Whether a node has been placed yet
    private boolean clickedANode = false; //Whether a node had been clicked yet

    private JButton clickedChildButton; //The current child button that is clicked (left, right, or none)

    private ButtonActionListener leftListener; //Listeners for left/right buttons to manage clicks
    private ButtonActionListener rightListener;

    private JLabel levelText; //The level of the current selected node (height in binary tree)

    private final NodePanel nodePanel;

    public NodeSettings(NodePanel nodePanel) {
        this.nodePanel = nodePanel;
        JLabel clickText = new JLabel("Click anywhere to place a node.");
        clickText.setFont(new Font("Serif", Font.PLAIN, 30));
        this.add(clickText);
    }

    public boolean getPlaced() {
        return placedANode;
    }

    public boolean getClicked() {
        return clickedANode;
    }

    public JButton getClickedChildButton() {
        return clickedChildButton;
    }

    public ButtonActionListener getLeftListener() {
        return leftListener;
    }

    public ButtonActionListener getRightListener() {
        return rightListener;
    }

    public void setClickedChildButton(JButton button) {
        clickedChildButton = button;
    }

    public void flipClickedChildButton() {
        if (leftListener.getActiveState()) {
            rightListener.setActivateState(true);
            leftListener.setActivateState(false);
            clickedChildButton = rightChild;
        } else {
            leftListener.setActivateState(true);
            rightListener.setActivateState(false);
            clickedChildButton = leftChild;
        }
        leftListener.flipColor();
        rightListener.flipColor();
    }

    public void placedANode() { //Called upon the first placement of a node
        placedANode = true;
        removeAll();
        JLabel clickText = new JLabel("Click a node to configure it's settings."); //Updates the text
        clickText.setFont(new Font("Serif", Font.PLAIN, 30));
        this.add(clickText);
        this.revalidate();
    }

    public void clickedANode() { //Called upon the first click of a node. Removes text and adds node setting components.
        removeAll();
        clickedANode = true;
        leftChild = new JButton("Set Left"); //Initialize child buttons
        rightChild = new JButton("Set Right");
        leftChild.setBackground(Color.GRAY);
        rightChild.setBackground(Color.GRAY);
        leftChild.setForeground(Color.WHITE);
        rightChild.setForeground(Color.WHITE);

        JButton deleteNode = new JButton("Delete Node"); //Initialize delete node button
        deleteNode.setBackground(Color.GRAY);
        deleteNode.setForeground(Color.WHITE);

        deleteNode.addActionListener(new ActionListener() { //When a node is deleted, it updates the child of the parent as well
            public void actionPerformed(ActionEvent e) {
                Node deletedNode = MouseListener.getPrevClicked();
                Node parent = deletedNode.getParent();
                if (parent != null && parent.getLeft() == deletedNode) {
                    parent.setLeft(null);
                } else if (parent != null && parent.getRight() == deletedNode) {
                    parent.setRight(null);
                }
                nodePanel.removeNode(deletedNode);
                nodePanel.repaint();
            }
        });
        this.add(deleteNode);

        JLabel enterValueText = new JLabel("Enter value: ");
        newValue = new JTextField("Enter",3);
        newValue.addActionListener(new ActionListener() { //Updates the selected node with the new entered value
            public void actionPerformed(ActionEvent ev) {
                Node currentClicked = MouseListener.getPrevClicked();
                currentClicked.setValue(Integer.parseInt(newValue.getText()));
                nodePanel.repaint();
            }
        });

        leftListener = new ButtonActionListener(leftChild,this,nodePanel,Color.blue,true); //Add listeners to the buttons
        rightListener = new ButtonActionListener(rightChild,this,nodePanel,Color.red,false);
        leftChild.addActionListener(leftListener);
        rightChild.addActionListener(rightListener);

        levelText = new JLabel("Level: 0");

        this.add(leftChild); //Add all the components to the panel
        this.add(rightChild);
        this.add(enterValueText);
        this.add(newValue);
        this.add(levelText);
        this.revalidate();
    }

    public void updateNodeSettings(Node node) { //Called to change the node settings when a new node is selected
        if(node.getLeft() != null) {
            leftChild.setText("Unassign Left");
        } else {
            leftChild.setText("Set Left");
        }
        if(node.getRight() != null) {
            rightChild.setText("Unassign Right");
        } else {
            rightChild.setText("Set Right");
        }
        levelText.setText("Level: " + node.getLevel());
        this.revalidate();
    }
}

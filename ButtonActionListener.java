import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonActionListener implements ActionListener {

    private boolean activateState = false; //Whether the button is currently active

    private final JButton button;
    private final NodeSettings nodeSettings;
    private final Color setColor;
    private final boolean isLeftButton; //Determines whether the button is right or left
    private final NodePanel nodePanel;

    public ButtonActionListener(JButton button, NodeSettings nodeSettings, NodePanel nodePanel, Color setColor, boolean isLeftButton) {
        this.button = button;
        this.nodeSettings = nodeSettings;
        this.setColor = setColor;
        this.nodePanel = nodePanel;
        this.isLeftButton = isLeftButton;
    }

    public boolean getActiveState() {
        return activateState;
    }

    public void setActivateState(boolean activateState) {
        this.activateState = activateState;
    }

    public void flipColor() {
        if (button.getBackground() == Color.GRAY) {
            button.setBackground(setColor);
        } else {
            button.setBackground(Color.GRAY);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(activateState);
        Node currentNode = MouseListener.getPrevClicked();
        if (currentNode != null) { //This section defines the "Unassign child" feature
            if (isLeftButton) {
                currentNode.setLeft(null);
            } else {
                currentNode.setRight(null);
            }
            nodePanel.repaint();
            nodeSettings.updateNodeSettings(currentNode);
        }
        JButton clickedChildButton = nodeSettings.getClickedChildButton();
        System.out.println(clickedChildButton);
        if (clickedChildButton == null) { //No button is currently clicked
            nodeSettings.setClickedChildButton(button);
            activateState = true;
            flipColor();
        } else if (clickedChildButton == button) { //Flip between the active and nonactive state when clicked
            activateState = false;
            flipColor();
            nodeSettings.setClickedChildButton(null);
        } else { //Set the other button to active if this one is active but the user clicked the other one
            nodeSettings.flipClickedChildButton();
        }
        nodeSettings.revalidate();
    }
}

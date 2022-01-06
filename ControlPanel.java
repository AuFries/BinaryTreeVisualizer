import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel { //This class represents the bottom control panel containing sort, reconfigure, and invert buttons

    public ControlPanel(NodePanel nodePanel) {
        JButton sortButton = new JButton("Sort");
        JButton reconfigureButton = new JButton("Reconfigure");
        JButton invertButton = new JButton("Invert");
        JButton randomizeButton = new JButton("Randomize");
        invertButton.setBackground(Color.GRAY);
        invertButton.setForeground(Color.WHITE);
        sortButton.setBackground(Color.GRAY);
        sortButton.setForeground(Color.WHITE);
        reconfigureButton.setBackground(Color.GRAY);
        reconfigureButton.setForeground(Color.WHITE);
        randomizeButton.setBackground(Color.GRAY);
        randomizeButton.setForeground(Color.WHITE);

        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BinaryTree unsortedTree = new BinaryTree(nodePanel);
                unsortedTree.sort();
            }
        });

        reconfigureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nodePanel.reconfigureTree();
            }
        });

        invertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BinaryTree tree = new BinaryTree(nodePanel);
                tree.invert();
            }
        });

        randomizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BinaryTree tree = new BinaryTree(nodePanel);
                tree.randomizeTree();
            }
        });
        this.add(sortButton);
        this.add(reconfigureButton);
        this.add(invertButton);
        this.add(randomizeButton);
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TraversalPanel extends JPanel { //A panel containing all the traversal methods (post, in, pre)

    public TraversalPanel(NodePanel nodePanel) {
        this.setLayout(new GridLayout(4,1,0,10));

        JButton preOrderButton = new JButton("Preorder");
        preOrderButton.setBackground(Color.GRAY);
        preOrderButton.setForeground(Color.WHITE);
        preOrderButton.addActionListener(new PreOrderListener(nodePanel));
        this.add(preOrderButton);

        JButton inOrderButton = new JButton("InOrder");
        inOrderButton.setBackground(Color.GRAY);
        inOrderButton.setForeground(Color.WHITE);
        inOrderButton.addActionListener(new InOrderListener(nodePanel));
        this.add(inOrderButton);

        JButton postOrderButton = new JButton("PostOrder");
        postOrderButton.setBackground(Color.GRAY);
        postOrderButton.setForeground(Color.WHITE);
        postOrderButton.addActionListener(new PostOrderListener(nodePanel));
        this.add(postOrderButton);

        JButton levelOrderButton = new JButton("LevelOrder");
        levelOrderButton.setBackground(Color.GRAY);
        levelOrderButton.setForeground(Color.WHITE);
        levelOrderButton.addActionListener(new LevelOrderListener(nodePanel));
        this.add(levelOrderButton);
    }
}

class PreOrderListener implements ActionListener {

    private final NodePanel nodePanel;

    public PreOrderListener(NodePanel nodePanel) {
        this.nodePanel = nodePanel;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        BinaryTree tree = new BinaryTree(nodePanel);
        tree.traversePreOrder();

    }
}

class InOrderListener implements ActionListener {

    private final NodePanel nodePanel;

    public InOrderListener(NodePanel nodePanel) {
        this.nodePanel = nodePanel;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        BinaryTree tree = new BinaryTree(nodePanel);
        tree.traverseInOrder();
    }
}

class PostOrderListener implements ActionListener {

    private final NodePanel nodePanel;

    public PostOrderListener(NodePanel nodePanel) {
        this.nodePanel = nodePanel;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        BinaryTree tree = new BinaryTree(nodePanel);
        tree.traversePostOrder();
    }
}

class LevelOrderListener implements ActionListener {

    private final NodePanel nodePanel;

    public LevelOrderListener(NodePanel nodePanel) {
        this.nodePanel = nodePanel;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        BinaryTree tree = new BinaryTree(nodePanel);
        tree.traverseLevelOrder();
    }
}

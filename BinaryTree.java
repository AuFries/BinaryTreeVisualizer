import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class BinaryTree {
    private final Node root;
    private final NodePanel nodePanel;

    public BinaryTree(NodePanel nodePanel) {
        this.root = nodePanel.getRoot();
        this.nodePanel = nodePanel;
    }

    public void randomizeTree() {
        nodePanel.nodes.clear(); //Clear all existing nodes
        Random rand = new Random();
        int numNodes = rand.nextInt((16 - 5) + 1) + 5;
        Node root = new Node(0,0,24,1,0,Color.GREEN);
        LinkedList<Node> randomTree = new LinkedList<>();
        randomTree.add(root);
        int i = 0;
        while(i < numNodes) {
            boolean successfullyAdded = false;
            Node newNode = null;
            while (!successfullyAdded) {
                Node chosenNode = randomTree.get(rand.nextInt(randomTree.size()));
                newNode = new Node(0,0,24,rand.nextInt(200),chosenNode.getLevel()+1,Color.BLUE);
                if (rand.nextBoolean() && chosenNode.getLeft() == null) {
                    chosenNode.setLeft(newNode);
                    successfullyAdded = true;
                } else if (chosenNode.getRight() == null){
                    chosenNode.setRight(newNode);
                    successfullyAdded = true;
                }
            }
            randomTree.add(newNode);
            i++;
        }
        addNodesToPanelInOrder(root);
        nodePanel.reconfigureTree();
    }

    private void resetNodeColors() { //Resets all node colors on the panel
        for (Node n : nodePanel.nodes) {
            n.setColor(Color.BLUE);
        }
        if (MouseListener.getPrevClicked() != null) {
            MouseListener.getPrevClicked().setColor(Color.GREEN);
        }
        nodePanel.repaint();
    }

    public void traverseInOrder() {
        traverseInOrderHelper(root);
        resetNodeColors();
        nodePanel.setTraverseString("");
    }
    private void traverseInOrderHelper(Node node) {
        if (node == null) {
            return;
        }
        traverseInOrderHelper(node.getLeft());
        nodePanel.setTraverseString(nodePanel.getTraverseString() + node.getValue() + " ");
        nodePanel.selectNodeForTraversal(node);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        traverseInOrderHelper(node.getRight());
    }

    public void traversePreOrder(){
        traversePreorderHelper(root);
        resetNodeColors();
        nodePanel.setTraverseString("");
    }
    private void traversePreorderHelper(Node node) {
        if (node == null) {
            return;
        }
        nodePanel.setTraverseString(nodePanel.getTraverseString() + node.getValue() + " ");
        nodePanel.selectNodeForTraversal(node);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        traversePreorderHelper(node.getLeft());
        traversePreorderHelper(node.getRight());
    }

    public void traversePostOrder() {
        traversePostorderHelper(root);
        resetNodeColors();
        nodePanel.setTraverseString("");
    }
    private void traversePostorderHelper(Node node) {
        if (node == null) {
            return;
        }
        traversePostorderHelper(node.getLeft());
        traversePostorderHelper(node.getRight());
        nodePanel.setTraverseString(nodePanel.getTraverseString() + node.getValue() + " ");
        nodePanel.selectNodeForTraversal(node);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void traverseLevelOrder() {
        int h = nodePanel.treeHeight(root);
        for (int i = 1; i <= h; i++)
            traverseLevelOrderHelper(root, i);
        resetNodeColors();
        nodePanel.setTraverseString("");
    }


    public void traverseLevelOrderHelper(Node node, int level) {
        if (node == null)
            return;
        if (level == 1) {
            nodePanel.setTraverseString(nodePanel.getTraverseString() + node.getValue() + " ");
            nodePanel.selectNodeForTraversal(node);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (level > 1) {
            traverseLevelOrderHelper(node.getLeft(), level - 1);
            traverseLevelOrderHelper(node.getRight(), level - 1);
        }
    }

    private int populateArrayInOrder(Node node, int index, int[] treeArray) { //Fills the array with all elements in the binary tree
        if (node == null) {
            return index;
        }
        index = populateArrayInOrder(node.getLeft(),index,treeArray);
        treeArray[index] = node.getValue();
        index = populateArrayInOrder(node.getRight(),index+1,treeArray);
        return index;
    }

    public void sort() {
        int numElements = numElements(root);
        int[] unsortedTree = new int[numElements];
        populateArrayInOrder(root,0,unsortedTree);

        nodePanel.nodes.clear();

        Random rand = new Random();
        for (int i = 0; i < unsortedTree.length; i++) { //Scrambles the array to get a unique sort each time
            int randomIndexToSwap = rand.nextInt(unsortedTree.length);
            int temp = unsortedTree[randomIndexToSwap];
            unsortedTree[randomIndexToSwap] = unsortedTree[i];
            unsortedTree[i] = temp;
        }
        int rootVal = unsortedTree[rand.nextInt(unsortedTree.length)]; //Chooses random root node
        BinarySearchTree sortedTree = new BinarySearchTree(new Node(0,0,24,rootVal,0, Color.BLUE));
        for (int num : unsortedTree) {
            sortedTree.insert(sortedTree.getRoot(),num,0);
        }
        addNodesToPanelInOrder(sortedTree.getRoot());

        for (Node n : nodePanel.nodes) {
            System.out.println(n.getValue() + " " + n.getLevel());
        }
        nodePanel.reconfigureTree();
        nodePanel.repaint();
    }

    private void addNodesToPanelInOrder(Node node) { //Adds all nodes in a tree to the list of nodes within nodePanel
        if (node == null) {
            return;
        }
        addNodesToPanelInOrder(node.getLeft());
        nodePanel.nodes.add(node);
        addNodesToPanelInOrder(node.getRight());
    }

    private int numElements(Node node) { //Returns the number of elements in the tree
        if (node == null) {
            return 0;
        }
        return 1 + numElements(node.getLeft()) + numElements(node.getRight());
    }

    public void invert() { //Inverts the binary tree then reconfigures it
        invertHelper(root);
        resetLevels(root,0);
        nodePanel.reconfigureTree();
        nodePanel.repaint();
    }

    private void invertHelper(Node node) {
        if(node == null) {
            return;
        }
        Node temp = node.getLeft();
        node.setLeft(node.getRight());
        node.setRight(temp);

        invertHelper(node.getLeft());
        invertHelper(node.getRight());
    }

    private void resetLevels(Node node, int level) {
        if (node == null) {
            return;
        }
        node.setLevel(level);
        resetLevels(node.getLeft(),level+1);
        resetLevels(node.getRight(),level+1);
    }
}

class BinarySearchTree {

    private final Node root;

    public BinarySearchTree(Node root) {
        this.root = root;
    }

    public Node getRoot() {
        return root;
    }

    public Node insert(Node node, int value, int level){ //Inserts elements to create a binary search tree
        if(node == null){
            return new Node(400,300,24,value,level, Color.blue);
        }
        // Move to left for value less than parent node
        if(value < node.getValue()){
            node.setLeft(insert(node.getLeft(), value, level+1));
        }
        // Move to right for value greater than parent node
        else if(value > node.getValue()){
            node.setRight(insert(node.getRight(), value, level+1));
        }
        return node;
    }
}


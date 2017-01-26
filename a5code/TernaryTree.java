//Ben Aston 
//CS 445, Monday/Wednesday night lecture
//Professor Garrision
//Date last updated: 4/19/2016
//Assignment 5, TernaryTree
import java.util.Iterator;
import java.util.NoSuchElementException;
import StackAndQueuePackage.*; // Needed by tree iterators

public class TernaryTree<T> implements TernaryTreeInterface<T>{
    private TernaryNode<T> ternaryTreeRoot;
    private int numberOfNodes;
    
    //-------------------------------------------------------------------------
    //TernaryTree: constructor that creates an empty ternary tree
    //-------------------------------------------------------------------------
    public TernaryTree() {
        ternaryTreeRoot = null;
    }
    
    //-------------------------------------------------------------------------
    //TernaryTree: creates a ternary tree with the root data T rootData
    //-------------------------------------------------------------------------
    public TernaryTree(T rootData) {
        ternaryTreeRoot = new TernaryNode<>(rootData);
    }

    //-------------------------------------------------------------------------
    //TernaryTree: creates a ternary tree with rootData, left, middle, and right
    //subtrees
    //-------------------------------------------------------------------------
    public TernaryTree(T rootData, TernaryTree<T> leftTree,TernaryTree<T> middleTree,
                      TernaryTree<T> rightTree) {
        privateSetTree(rootData, leftTree, middleTree, rightTree);
    }
    
    //-------------------------------------------------------------------------
    //setTree: sets the root data of the tree to T data
    //-------------------------------------------------------------------------
    public void setTree(T data){
        ternaryTreeRoot = new TernaryNode(data);
    }
    
    //-------------------------------------------------------------------------
    //setTree: sets the tree to have root data, data, and children left, middle,
    //and right
    //-------------------------------------------------------------------------
    public void setTree(T data, TernaryTreeInterface<T> left, TernaryTreeInterface<T> middle, TernaryTreeInterface<T> right){
        ternaryTreeRoot = new TernaryNode<>(data);

        privateSetTree(data, (TernaryTree)left,(TernaryTree)middle, (TernaryTree)right);
    }
    
    //-------------------------------------------------------------------------
    //privateSetTree: sets the tree's data 
    //-------------------------------------------------------------------------
    private void privateSetTree(T rootData, TernaryTree<T> leftTree, TernaryTree<T> middleTree,
                                TernaryTree<T> rightTree) {
        ternaryTreeRoot = new TernaryNode<>(rootData);

        if ((leftTree != null) && !leftTree.isEmpty()) {
            ternaryTreeRoot.setLeftChild(leftTree.ternaryTreeRoot);
        }

        if ((rightTree != null) && !rightTree.isEmpty()) {
            if (rightTree != leftTree || rightTree != middleTree) {
                ternaryTreeRoot.setRightChild(rightTree.ternaryTreeRoot);
            } else {
                ternaryTreeRoot.setRightChild(rightTree.ternaryTreeRoot.copy());
            }
        }
        
        if ((middleTree != null) && !middleTree.isEmpty()){
            if(middleTree != leftTree || rightTree != middleTree){
                ternaryTreeRoot.setMiddleChild(middleTree.ternaryTreeRoot);
            } else {
                ternaryTreeRoot.setMiddleChild(middleTree.ternaryTreeRoot);
            }
        }

        if ((leftTree != null) && (leftTree != this)) {
            leftTree.clear();
        }
        
        if ((middleTree != null) && (middleTree != this)) {
            middleTree.clear();
        }

        if ((rightTree != null) && (rightTree != this)) {
            rightTree.clear();
        }
    }
    
    //-------------------------------------------------------------------------
    //getRootData: returns the root data
    //-------------------------------------------------------------------------
    public T getRootData(){
        if(ternaryTreeRoot == null)
            throw new EmptyTreeException();
        else
            return ternaryTreeRoot.getData();
    }
    
    //-------------------------------------------------------------------------
    //getHeight: returns the height of the tree including the root
    //-------------------------------------------------------------------------
    public int getHeight(){
        return ternaryTreeRoot.getHeight();
    }
    
    //-------------------------------------------------------------------------
    //isEmpty: returns whether or not the root data has been initialized 
    //-------------------------------------------------------------------------
    public boolean isEmpty(){
        return ternaryTreeRoot == null;
    }
    
    //-------------------------------------------------------------------------
    //clear: sets the root data to null
    //-------------------------------------------------------------------------
    public void clear(){
        ternaryTreeRoot = null;
    }
    
    //-------------------------------------------------------------------------
    //getNumberOfNodes: returns the number of nodes in the tree
    //-------------------------------------------------------------------------
    public int getNumberOfNodes(){
        return ternaryTreeRoot.getNumberOfNodes();
    }
    
    //-------------------------------------------------------------------------
    //getPreoderIterator: returns a new PreorderIterator on the tree
    //-------------------------------------------------------------------------
    public Iterator getPreorderIterator(){
        return new PreorderIterator();
    }
    
    //-------------------------------------------------------------------------
    //getPostorderIterator: returns a new PostorderIterator on the tree
    //-------------------------------------------------------------------------
    public Iterator getPostorderIterator(){
        return new PostorderIterator();
    }
    
    /* An inorder iterator of a ternary tree is not defined because an inorder
    *iterator traverses the tree by starting at the root's left subtree then
    *moving to the root itself and then moving to the right subtree. Thus there
    *is no apparent place for the middle tree to be traversed, it is ambiguous
    */
    public Iterator getInorderIterator(){
        throw new UnsupportedOperationException();
    }
    
    //-------------------------------------------------------------------------
    //getLevelOrderIterator: returns a new Level Order Iterator on the tree
    //-------------------------------------------------------------------------
    public Iterator getLevelOrderIterator(){
        return new LevelOrderIterator();
    }
    
    //-------------------------------------------------------------------------
    //PostorderIterator: private inner class that handles the creation of
    //a postorder iterator to iterate over the data
    //-------------------------------------------------------------------------
    private class PostorderIterator implements Iterator<T> {
        private StackInterface<TernaryNode<T>> nodeStack;
        private TernaryNode<T> currentNode;
        
        //-------------------------------------------------------------------------
        //PostorderIterator: constructor
        //-------------------------------------------------------------------------
        public PostorderIterator(){
            nodeStack = new LinkedStack<>();
            currentNode = ternaryTreeRoot;
        }
        
        //-------------------------------------------------------------------------
        //hasNext: returns whether or not another element exists in the iterator
        //-------------------------------------------------------------------------
        public boolean hasNext(){
            return !nodeStack.isEmpty();
        }
        
        //-------------------------------------------------------------------------
        //next: returns the next peice of data in the iterator
        //-------------------------------------------------------------------------
        public T next() {
            TernaryNode<T> leftChild,middleChild, rightChild, nextNode = null;

            // Find leftmost leaf
            while (currentNode != null) {
                nodeStack.push(currentNode);
                leftChild = currentNode.getLeftChild();
                middleChild = currentNode.getMiddleChild();
                if (leftChild == null) {
                    if(middleChild == null)
                        currentNode = currentNode.getRightChild();
                    else
                        currentNode = currentNode.getMiddleChild();
                } else 
                    currentNode = leftChild;
                
            }

            // Stack is not empty either because we just pushed a node, or
            // it wasn't empty to begin with since hasNext() is true.
            // But Iterator specifies an exception for next() in case
            // hasNext() is false.

            if (!nodeStack.isEmpty()) {
                nextNode = nodeStack.pop();
                // nextNode != null since stack was not empty before pop

                TernaryNode<T> parent = null;
                if (!nodeStack.isEmpty()) {
                    parent = nodeStack.peek();
                    if (nextNode == parent.getLeftChild()) {
                        currentNode = parent.getMiddleChild();
                    } else if(nextNode == parent.getMiddleChild()){
                        currentNode = parent.getRightChild();
                    } else
                        currentNode = null;
                } else {
                    currentNode = null;
                }
            } else {
                throw new NoSuchElementException();
            }

            return nextNode.getData();
        }
        
        //-------------------------------------------------------------------------
        //remove: unsupported operation
        //-------------------------------------------------------------------------
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    
    //-------------------------------------------------------------------------
    //PreorderIterator: private inner class that handles the creation of an 
    //iterator over the tree
    //-------------------------------------------------------------------------
    private class PreorderIterator implements Iterator<T>{
        private StackInterface<TernaryNode<T>> nodeStack;
        
        //-------------------------------------------------------------------------
        //PreorderIterator: constructor
        //-------------------------------------------------------------------------
        public PreorderIterator(){
            nodeStack = new LinkedStack<>();
            if(ternaryTreeRoot != null)
                nodeStack.push(ternaryTreeRoot);
        }
        
        //-------------------------------------------------------------------------
        //hasNext: returns whether another element exists in the iterator
        //-------------------------------------------------------------------------
        public boolean hasNext(){
            return !nodeStack.isEmpty();
        }
        
        //-------------------------------------------------------------------------
        //next: returns the next element in the iterator
        //-------------------------------------------------------------------------
        public T next(){
            TernaryNode<T> nextNode;
            
            if(hasNext()){
                nextNode = nodeStack.pop();
                
                TernaryNode<T> leftChild = nextNode.getLeftChild();
                TernaryNode<T> middleChild = nextNode.getMiddleChild();
                TernaryNode<T> rightChild = nextNode.getRightChild();
                
                // Push into stack in reverse order of recursive calls
                if (rightChild != null) {
                    nodeStack.push(rightChild);
                }
                
                if(middleChild != null)
                    nodeStack.push(middleChild);

                if (leftChild != null) {
                    nodeStack.push(leftChild);
                }
            } else {
                throw new NoSuchElementException();
            }
            
            return nextNode.getData();
        }
        
        //-------------------------------------------------------------------------
        //remove: unsupported operation
        //-------------------------------------------------------------------------
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    
    //-------------------------------------------------------------------------
    //LevelOrderIterator: private inner class handles the creation of an 
    //iterator over the tree
    //-------------------------------------------------------------------------
    private class LevelOrderIterator implements Iterator<T> {
        private QueueInterface<TernaryNode<T>> nodeQueue;
        
        //-------------------------------------------------------------------------
        //LevelOrderIterator: constructor
        //-------------------------------------------------------------------------
        public LevelOrderIterator() {
            nodeQueue = new LinkedQueue<>();
            if (ternaryTreeRoot != null) {
                nodeQueue.enqueue(ternaryTreeRoot);
            }
        }
        
        //-------------------------------------------------------------------------
        //hasNext: returns whether another element exists in the iterator
        //-------------------------------------------------------------------------
        public boolean hasNext() {
            return !nodeQueue.isEmpty();
        }

        //-------------------------------------------------------------------------
        //next: returns the next element in the iterator
        //-------------------------------------------------------------------------
        public T next() {
            TernaryNode<T> nextNode;

            if (hasNext()) {
                nextNode = nodeQueue.dequeue();
                TernaryNode<T> leftChild = nextNode.getLeftChild();
                TernaryNode<T> middleChild = nextNode.getMiddleChild();
                TernaryNode<T> rightChild = nextNode.getRightChild();

                // Add to queue in order of recursive calls
                if (leftChild != null) {
                    nodeQueue.enqueue(leftChild);
                }
                
                if(middleChild != null)
                    nodeQueue.enqueue(middleChild);

                if (rightChild != null) {
                    nodeQueue.enqueue(rightChild);
                }
            } else {
                throw new NoSuchElementException();
            }

            return nextNode.getData();
        }
        
        //-------------------------------------------------------------------------
        //remove: unsupported operation
        //-------------------------------------------------------------------------
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    
}

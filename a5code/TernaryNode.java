//Ben Aston 
//CS 445, Monday/Wednesday night lecture
//Professor Garrision
//Date last updated: 4/19/2016
//Assignment 5, TernaryNode
public class TernaryNode<T> {
    private T data;
    private TernaryNode<T> leftChild;
    private TernaryNode<T> middleChild;
    private TernaryNode<T> rightChild;
    
    public TernaryNode(){
        this(null);
    }
    
    public TernaryNode(T data){
        this(data, null, null, null);
    }
    
    public TernaryNode(T data, TernaryNode<T> leftChild, TernaryNode<T> middleChild, TernaryNode<T> rightChild){
        this.data = data;
        this.leftChild = leftChild;
        this.middleChild = middleChild;
        this.rightChild = rightChild;
    }
    
    public void setLeftChild(TernaryNode<T> leftChild){
        this.leftChild = leftChild;
    }
    
    public void setMiddleChild(TernaryNode<T> middleChild){
        this.middleChild = middleChild;
    }
    
    public void setRightChild(TernaryNode<T> rightChild){
        this.rightChild = rightChild;
    }
    
    public T getData(){
        return data;
    }
    
    public TernaryNode getLeftChild(){
        return leftChild;
    }
    
    public TernaryNode getRightChild(){
        return rightChild;
    }
    
    public TernaryNode getMiddleChild(){
        return middleChild;
    }
    
    public TernaryNode<T> copy(){
        TernaryNode<T> newRoot = new TernaryNode<>(data);
        
        if(leftChild != null){
            newRoot.setLeftChild(leftChild.copy());
        }
        if(rightChild != null){
            newRoot.setRightChild(rightChild.copy());
        }
        if(middleChild != null){
            newRoot.setMiddleChild(middleChild.copy());
        }
        
        return newRoot;
    }
    
    public int getHeight(){
        return getHeight(this);
    }
    
    private int getHeight(TernaryNode<T> node){
        int temp, height = 0; 
        if(node != null){
            temp = Math.max(getHeight(node.getLeftChild()),
                                  getHeight(node.getRightChild()));
            height = 1 + Math.max(temp, getHeight(node.getMiddleChild()));
        }
        return height;
    }
    
    public int getNumberOfNodes(){
        int leftNumber = 0;
        int middleNumber = 0;
        int rightNumber = 0;

        if (leftChild != null) {
            leftNumber = leftChild.getNumberOfNodes();
        }
        
        if(middleChild != null){
            middleNumber = middleChild.getNumberOfNodes();
        }

        if (rightChild != null) {
            rightNumber = rightChild.getNumberOfNodes();
        }

        return 1 + leftNumber + middleNumber + rightNumber;
    }
}

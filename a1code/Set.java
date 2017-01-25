//Ben Aston 
//CS 445, Monday/Wednesday night lecture
//Professor Garrision
//Date last updated: 2/7/2016

import java.io.Serializable;
import java.util.Random;

public class Set<T> implements SetInterface<T>, Serializable {
    private final int DEFAULT_SIZE = 25;
    T[] bag;
    private int numberOfElements;
    
    
    //------------------------------------------------------------------------
    //constructor
    //------------------------------------------------------------------------
    public Set(){
        bag = (T[]) new Object[DEFAULT_SIZE];
        numberOfElements = 0;
    }
    
    //------------------------------------------------------------------------
    //getCurrentSize: returns the *logical* size of the array
    //------------------------------------------------------------------------
    public int getCurrentSize(){
        return numberOfElements;
    }
    
    //------------------------------------------------------------------------
    //isEmpty: checks if the array's logical size is 0
    //------------------------------------------------------------------------
    public boolean isEmpty(){
        return numberOfElements == 0;
    }
    
    //------------------------------------------------------------------------
    //add: adds a new 
    //------------------------------------------------------------------------
    public boolean add(T newEntry) throws SetFullException, java.lang.IllegalArgumentException{
        if(newEntry == null)
            throw new java.lang.IllegalArgumentException();
        if(numberOfElements == bag.length)
            throw new SetFullException();
        if(contains(newEntry))
            return false;
        bag[numberOfElements] = newEntry;
        numberOfElements++;
        return true;
    }
    
    //------------------------------------------------------------------------
    //contains: determines whether an element (entry) exists in the set 
    //------------------------------------------------------------------------
    public boolean contains(T entry) throws java.lang.IllegalArgumentException{
        if(entry == null)
            throw new java.lang.IllegalArgumentException();
        for(T item : bag){
            if(item != null)
                if(item.equals(entry))
                    return true;
        }
        return false;
    }
    
    //------------------------------------------------------------------------
    //clear: sets all items in the set to null
    //------------------------------------------------------------------------
    public void clear(){
        if(!isEmpty())
            for(T item : bag)
                item = null;
    }
    
    //------------------------------------------------------------------------
    //toArray: returns an array of Object that contains objects of type T
    //------------------------------------------------------------------------
    public T[] toArray(){
        T [] toReturn = (T[]) new Object[numberOfElements];
        for(int i = 0; i < numberOfElements; i++)
            toReturn[i] = (T) bag[i];
        return toReturn;
    }
    
    //------------------------------------------------------------------------
    //remove: removes the last element of the set and returns it 
    //------------------------------------------------------------------------
    public T remove(){
        if(isEmpty())
            return null;
        numberOfElements--;
        return bag[numberOfElements];
    }
    
    //------------------------------------------------------------------------
    //remove: attempts to remove the element (entry) and  returns whether or
    //not it was successful
    //------------------------------------------------------------------------
    public boolean remove(T entry) throws java.lang.IllegalArgumentException{
        if(entry == null)
            throw new java.lang.IllegalArgumentException();
        if(isEmpty())
            return false;
        for(int i = 0; i < numberOfElements; i++){
            if(entry.equals(bag[i])){
                bag[i] = bag[numberOfElements - 1];
                bag[numberOfElements - 1] = null;
                return true;
            }
        }
        return false;
    }
    
    //------------------------------------------------------------------------
    //toString: returns a String representing the state of the Set
    //------------------------------------------------------------------------
    public String toString(){
        String toReturn = "";
        for(T temp : bag)
            if(temp != null)
                toReturn += temp.toString() + "\n";
        return toReturn;
    }
    
    //------------------------------------------------------------------------
    //peek: returns a random element from the set but does not remove it
    //------------------------------------------------------------------------
    public T peek(){
        Random ran = new Random();
        T result = null;
        if(!isEmpty())
            result = bag[ran.nextInt(numberOfElements)];

        return result;
    }
}

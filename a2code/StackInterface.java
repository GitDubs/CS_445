public interface StackInterface<T> {
    
    /** Adds a new entry to the top of the stack */
    public void push(T newEntry);
    
    /** Removes and returns the stack's top entry
    @throws EmptyStackException if the stack is empty */
    public T pop();
    
    /** Retrieves the stack's top entry without removing
    @throws EmptyStackException if the stack is empty */
    public T peek();
    
    /** Determines whether the stack is empty */
    public boolean isEmpty();
    
    /** Removes all entries from the stack */
    public void clear();
}

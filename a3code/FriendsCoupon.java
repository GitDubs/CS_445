//Ben Aston
//CS 445, Dr. Garrison
//Monday-Wednesday night lecture
//TA: Nannan Wen
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;

public class FriendsCoupon {
    boolean friends [][];
    boolean finished = false;
    int solution[];
    int numCoupons;
    
    //--------------------------------------------------------------------------
    //constructor: loads the file and sets up the object
    //--------------------------------------------------------------------------
    public FriendsCoupon(String fileName, int coupons){
        numCoupons = coupons;
        System.out.println("loading data");
        
        try{
            load(fileName);
        }catch(FileNotFoundException e){
            System.err.println(e);
        }
        System.out.println(this);
        
        solution = new int[friends.length];
        System.out.println("solving...");
        solve(solution);
        
    }
    
    //--------------------------------------------------------------------------
    //load: reads in the data from the file into the boolean [][]
    //--------------------------------------------------------------------------
    public void load(String inFile) throws FileNotFoundException {
        Scanner inScan = new Scanner(new File(inFile));
        String temp = inScan.nextLine();
        String [] tempArray;
        tempArray = temp.split(" ");
        friends = new boolean [tempArray.length][tempArray.length];
        //Read in the first line of the file and get the number of people in the network
        for(int i = 0; i < tempArray.length; i++)
            if(Integer.parseInt(tempArray[i]) == 1)
                friends [0][i] = true;
            else if(Integer.parseInt(tempArray[i]) == 0)
                friends [0][i] = false;
            else
                throw new NumberFormatException();
        //Read in the rest of the people in the network
        for(int i = 1; i < tempArray.length; i++){
            for(int j = 0; j < tempArray.length; j++){
                if(inScan.nextInt() == 1)
                    friends [i][j] = true;
                else
                    friends [i][j] = false;
            }
        }       
    }
    
    //--------------------------------------------------------------------------
    //checkFile: returns false if the file is not symmetrical or if a person
    //is friends with him or herself
    //--------------------------------------------------------------------------
    public boolean checkFile(){
        for(int i = 0; i < friends.length; i++)
            for(int j = 0; j < friends.length; j++)
                if(friends[i][j] != friends[j][i])
                    return false;
        for(int i = 0; i < friends.length; i++)
            if(friends[i][i])//If a person is friends with him/herself return false
                return false;
        return true;
    }
    
    //--------------------------------------------------------------------------
    //isFullSolution: determines if a particular solution fulfills all 
    //requirements
    //--------------------------------------------------------------------------
    public boolean isFullSolution(int [] partial){
        for(int temp : partial)
            if(temp == 0)
                return false;
        if(reject(partial))
            return false;
        return true;
    }
           
    //--------------------------------------------------------------------------
    //reject: determines if a solution is capable of being added onto to form
    //a full solution
    //--------------------------------------------------------------------------
    public boolean reject(int [] array){
        for(int i = 0; i < friends.length; i++)
            for(int j = 0; j < friends.length; j++)
                if(array[i] != 0 && array[j] != 0)
                    if(friends[i][j] && array[i] == array[j])
                        return true;
        return false;
    }
    
    //--------------------------------------------------------------------------
    //extend: adds one additional choice if possible
    //--------------------------------------------------------------------------
    public int [] extend(int [] partial){
        int [] temp = new int[friends.length];
        for(int i = 0; i < friends.length; i++)
            if(partial[i] != 0)
                temp[i] = partial[i];
            else{
                temp[i] = 1;
                return temp;
            }
        return null;
    }
    
    //--------------------------------------------------------------------------
    //next: modifies the most recent choice until a solution has been found
    //or all possible states of that choice have been exhausted
    //--------------------------------------------------------------------------
    public int[] next(int[] partial){
        int[] temp = new int[friends.length];
        int i = 0;
        while (i < friends.length) {
            if (i == friends.length - 1 || partial[i+1] == 0) {
           
                //The most recent coupon assigned is to the last person, or the last one before a person 
                //without a coupon
                if (partial[i] >= numCoupons) {
                    // All coupons have been tried for this person
                    return null;
                } else {
                    // Give the person the next possible coupon
                    temp[i] = partial[i] + 1;
                    break;
                }
            } else {
                //Not the last person with a coupon, copy it
                temp[i] = partial[i];
            }
            i++;
        }
        return temp;
    }
    
    //--------------------------------------------------------------------------
    //printSolution: converts the int[] to a more readable char[]
    //--------------------------------------------------------------------------
    public void printSolution(int [] solution){
        char[] chars = new char [solution.length];
        for(int i = 0; i < chars.length; i++)
            chars[i] = (char)('A' + (solution[i] - 1));
        for(char current : chars)
            System.out.print(current + " ");
        System.out.println();
    }
    
    //--------------------------------------------------------------------------
    //solve: recursivley progresses through the problem using other methods to
    //built a solution
    //--------------------------------------------------------------------------
    public void solve(int[] partial) {
        if (reject(partial)) 
            return;
        if (isFullSolution(partial)){ 
            printSolution(partial);
            finished = true;
        }
        int[] attempt = extend(partial);
        while (attempt != null && !finished) {
            solve(attempt);
            attempt = next(attempt);
        }
    }
    
    //--------------------------------------------------------------------------
    //testIsFullSoutionUnit: tests a single solution to determine if it is full
    //or not
    //--------------------------------------------------------------------------
    public void testIsFullSolutionUnit(int[] test) {
        if (isFullSolution(test)) {
            System.err.println("Full sol'n:\t" + Arrays.toString(test));
        } else {
            System.err.println("Not full sol'n:\t" + Arrays.toString(test));
        }
    }
    
    //--------------------------------------------------------------------------
    //testIsFullSolution: uses possible test cases to test the method isFullSolution
    //--------------------------------------------------------------------------
    public void testIsFullSolution(){
        System.out.println("Testing isFullSolution()");
        numCoupons = 2;
        friends = new boolean[4][4];
        friends[0] = new boolean[] {false, true, true, false};
        friends[1] = new boolean[] {true, false, false, false};
        friends[2] = new boolean[] {true, false, false, true};
        friends[3] = new boolean[] {false, false, true, false};
        
        //Full Solutions
        testIsFullSolutionUnit(new int[] {1,2,2,1});
        testIsFullSolutionUnit(new int[] {2,1,1,2});
        
        //Not Full Soluitons
        testIsFullSolutionUnit(new int[] {0,0,0,0});
        testIsFullSolutionUnit(new int[] {1,2,2,0});
        testIsFullSolutionUnit(new int[] {1,1,1,1});
    }
    
    //--------------------------------------------------------------------------
    //testRejectUnit: tests the reject method with a single solution
    //--------------------------------------------------------------------------
    public void testRejectUnit(int[] test) {
        if (reject(test)) {
            System.err.println("Rejected:\t" + Arrays.toString(test));
        } else {
            System.err.println("Not rejected:\t" + Arrays.toString(test));
        }
    }
    
    //--------------------------------------------------------------------------
    //testReject: test the Reject method with multiple cases, each of which is
    //labelled as to what its expected result is
    //--------------------------------------------------------------------------
    public void testReject(){
        System.out.println("Testing reject()");
        numCoupons = 2;
        friends[0] = new boolean[] {false, true, true, false};
        friends[1] = new boolean[] {true, false, false, false};
        friends[2] = new boolean[] {true, false, false, true};
        friends[3] = new boolean[] {false, false, true, false};
        
        System.out.println("These should not reject");
        //Should not be rejected
        testRejectUnit(new int[] {0,0,0,0});
        testRejectUnit(new int[] {1,2,0,0});
        testRejectUnit(new int[] {1,2,2,0});
        
        System.out.println("These should reject");
        //Should be rejected
        testRejectUnit(new int[] {1,1,0,0});
        testRejectUnit(new int[] {1,2,1,0});
        testRejectUnit(new int[] {1,2,1,1});
    }
    
    //--------------------------------------------------------------------------
    //testExtendUnit: tests a single solution to see if it can be extended
    //--------------------------------------------------------------------------
    public void testExtendUnit(int[] test) {
        System.err.println("Extended " + Arrays.toString(test) + " to " + Arrays.toString(extend(test)));
    }
    
    //--------------------------------------------------------------------------
    //tests the next method with multiple cases, each of which is 
    //labelled as to what its expected output is
    //--------------------------------------------------------------------------
    public void testExtend(){
        //Should Extend by 1
        System.out.println("Sould extend by 1");
        testExtendUnit(new int[] {1,2,0,0});
        testExtendUnit(new int[] {1,2,2,0});
        testExtendUnit(new int[] {0,0,0,0});
        
        //Should return null
        System.out.println("Should return null");
        testExtendUnit(new int[] {1,1,1,1});
        testExtendUnit(new int[] {1,2,1,1});
        testExtendUnit(new int[] {1,2,1,1});
    }
    
    //--------------------------------------------------------------------------
    //testNextUnit: tests a single solution for next()
    //--------------------------------------------------------------------------
    public void testNextUnit(int[] test) {
        System.err.println("Nexted " + Arrays.toString(test) + " to " + Arrays.toString(next(test)));
    }
    
    //--------------------------------------------------------------------------
    //testNext: tests the next method with multiple cases, each of which is 
    //labelled as to what its expected output is
    //--------------------------------------------------------------------------
    public void testNext(){
        //Should next successfully
        System.out.println("Should next successfully");
        testNextUnit(new int[] {1,1,1,1});
        testNextUnit(new int[] {1,1,2,1});
        testNextUnit(new int[] {1,1,0,0});
        testNextUnit(new int[] {0,0,0,0});
        
        //Should return null
        System.out.println("Should return null");
        testNextUnit(new int[] {1,2,2,2});
        testNextUnit(new int[] {1,2,0,0});
        testNextUnit(new int[] {1,1,1,2});
    }
    
    //--------------------------------------------------------------------------
    //toString: returns a String representing the state of this object
    //--------------------------------------------------------------------------
    public String toString(){
        String toReturn = "";
        for(int i = 0; i < friends.length; i++){
            for(int j = 0; j < friends.length; j++)
                toReturn += friends[i][j] + "\t";
            toReturn += "\n";   
        }   
        return toReturn;
     }
}

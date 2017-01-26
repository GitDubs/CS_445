import java.io.FileNotFoundException;

public class Test {
    public static void main(String args[]){
        FriendsCoupon test = new FriendsCoupon(args[0], Integer.parseInt(args[1]));
        test.testIsFullSolution();
        test.testReject();
        test.testExtend();
        test.testNext();
    }
}

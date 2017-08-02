package mytest.automata;

/**
 * Created by safe on 2017-05-06.
 */
/**
 * Created by safe on 2017-04-13.
 */
public class NumberParttern {
    public static void patternPrint(int rows){
        for (int i = 1; i <=rows ; i++) {
            if (i%2==0) System.out.print(i+1);
            for (int j = 1; j <rows ; j++) {
                System.out.print(i);
            }
            if (i%2!=0) System.out.print(i+1);
            System.out.print("\n");
        }
    }
    public static void main(String[] args) {
        patternPrint(6);
    }
}



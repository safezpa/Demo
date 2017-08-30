import java.util.Scanner;

public class Main{
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n=in.nextInt();
        if (n==1||n==2){
            System.out.println(1);
            return;
        }else {
            long[] arr = new long[n];
            arr[0] = arr[1] = 1;
            for (int i = 2; i < arr.length; i++) {
                arr[i] = arr[i - 1] + arr[i - 2]; }
            System.out.println(arr[n-1]);
        }


    }
}

package datastructure;

public class MatrixMultiply {

    private static int[][] multiply(int[][] A, int[][] B){
        int[][] C=new int[A.length][B[0].length];
        for (int i = 0; i <A.length ; i++) {
            for (int j = 0; j <B[0].length ; j++) {
                 C[i][j]=0;
                for (int k = 0; k <B[0].length-1 ; k++) {
                    C[i][j]=C[i][j]+A[i][k]*B[k][j];
                }
            }
        }

        return C;
    }

    public static void main(String[] args) {
        int[][] A={{1,2},{1,2}};
        int[][] B={{1,2,3},{1,2,3}};
        int[][] C=new int[A.length][B[0].length];
        System.out.println(B[0].length);
        C=multiply(A,B);

        for (int i = 0; i <C.length ; i++) {
            for (int j = 0; j <C[0].length ; j++) {
                System.out.print(C[i][j] + " ");
            }
            System.out.println();
        }
    }
}

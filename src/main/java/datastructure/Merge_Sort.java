package datastructure;

public class Merge_Sort {
    private static void merge_sort(int[] A,int p,int r){
            if (p<r){
                int q=(p+r)/2;
                merge_sort(A,p,q);
                merge_sort(A,q+1,r);
                merge2(A,p,q,r);
            }
    }

    private static void merge(int[] A, int p, int q, int r) {
        int[] L=new int[q-p+2];
        int[] R=new int[r-q+1];
        for (int i = p,j=0; i <=q ; i++,j++) {
            L[j]=A[i];
        }
        for (int i = q+1,j=0; i <=r ; i++,j++) {
            R[j]=A[i];

        }
        L[q-p+1]=Integer.MAX_VALUE;
        R[r-q]=Integer.MAX_VALUE;

        int i=0,j=0;
        for (int k = p; k <=r ; k++) {
            if (L[i]<=R[j]) {
                A[k]=L[i];
                i++;
            }else{
                A[k]=R[j];
                j++;
            }
        }

    }

    private static void merge2(int[] A, int p, int q, int r) {
        int[] L=new int[q-p+1];
        int[] R=new int[r-q];
        for (int i = p,j=0; i <=q ; i++,j++) {
            L[j]=A[i];
        }
        for (int i = q+1,j=0; i <=r ; i++,j++) {
            R[j]=A[i];

        }
        int i=0,j=0,l=p;
        int m=Integer.min(q-p,r-p-1);
        for ( ; l <=r; l++) {
            if (i==q-p+1 || j==r-p) break;
            if (L[i]<=R[j]) {
                A[l]=L[i];
                i++;
            }else{
                A[l]=R[j];
                j++;
            }


        }
        for (int k =l+1; k <=r ; k++) {
            if (i==q-p+1){
                A[k]=R[j];
                j++;
            }else {
                A[k]=L[i];
                i++;
            }
        }

    }
    public static void main(String[] args) {
        int[] A={5,2,4,7,1,3,2,6};
        merge_sort(A,0,A.length-1);
        for (int i = 0; i <A.length ; i++) {
            System.out.print(A[i]+" ");
        }

    }
}

package datastructure;

public class BinSearch {
    public static void main(String[] args) {
        int[] ordered_num={1,4,5,6,7,9,24,45,77};
        int for_search=9;
        System.out.println(search(ordered_num,for_search));
    }

    private static int search(int[] ordered_num, int for_search) {
        int start=0;
        int end =ordered_num.length-1;

        while (start<=end){
            int mid=(start+end)/2;
            if (ordered_num[mid]==for_search){ return mid;}
            else if (ordered_num[mid]<for_search){
                start=mid+1;
            }else  {
                end=mid-1;
            }

        }
        return -1;
    }
}

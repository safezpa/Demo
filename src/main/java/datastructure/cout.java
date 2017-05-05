package datastructure;

/**
 * Created by safe on 2017/3/12.
 */
public class cout {
    public boolean coutNum(int a[]){

        //1.排除反例。
        if(a.length<7)
            return false;

        //2.三个指针将数据分成四部分
        int p1=1;
        int p3=a.length-2;
        int p2=a.length>>1;

        int sum1=a[0];
        int sum4=a[a.length-1];
        int sum2=0;
        int sum3=0;

        //求sum2
        for (int i = p1+1; i <p2 ; i++) {
            sum2=sum2+a[i];
        }

        //求sum3
        for (int i = p2+1; i <p3 ; i++) {
            sum3=sum3+a[i];
        }

        while (p1<p3){

            //两个指针p1、p3向中间移动，调整sum1和sum4相等
            if(sum1<sum4){
                sum1=sum1+a[p1];
                p1++;
                sum2=sum2-a[p1];
            }else if (sum1>sum4){
                sum4=sum4+a[p3];
                p3--;
                sum3=sum3-a[p3];
            }else if (sum1==sum4){
                if (sum1>Math.min(sum2,sum3)){
                    //p2指针调整sum2和sum3相等
                    //p2指针有可能左右震荡。若震荡则退出
                    int i=0;
                    int j=0;
                    if(sum2<sum3){
                        i++;
                        sum2=sum2+a[p2];
                        p2++;
                        sum3=sum3-a[p2];
                    }else if (sum2>sum3){
                        j++;
                        sum3=sum3+a[p2];
                        p2--;
                        sum2=sum2-a[p2];
                    }else if (sum2==sum3){
                        if (sum1==sum2)
                            return true;
                        else
                            return false;
                    }else if (i>0 && j>0){
                        return false;
                    }
                }else {
                    sum1=sum1+a[p1];
                    p1++;
                    sum2=sum2-a[p1];
                }

            }
        }

        return false;
    }
}

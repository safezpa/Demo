package mytest.toutiao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by safe on 2017-03-30.
 * 词向量计算文本相似度
 */
public class Main {

    public static void main(String[] args) throws IOException {

        String path=Main.class.getResource("/").getFile();
        FileReader fin = new FileReader("input.txt" );
        BufferedReader bin = new BufferedReader( fin );
        String oneLine= bin.readLine();

        int N=Integer.parseInt(oneLine.split(" ")[0]);
        int M=Integer.parseInt(oneLine.split(" ")[1]);
        //字典
        HashMap<String, Integer>  hashMap0 = new HashMap<String, Integer>();
        //放段落单词，词频
        HashMap<String, Integer> [] hashMap1 = new HashMap[N+M];



        //int[] w=new int[N];
        String[] text=new String[N+M];
        //bin.readLine();
        for (int i = 0; i < N+M; i++) {
            text[i]=bin.readLine();
            hashMap1[i]=new HashMap<>();
            //System.out.println(i+text[i]);
            counter(text[i],hashMap0);
            counter(text[i],hashMap1[i]);
        }


        //词频统计 词向量
        //
        // 遍历HashMap,输出结果
        int col=hashMap0.keySet().size();
        int j=0;
        int[][] word2vec=new int[M+N][col];

            for (String word: hashMap0.keySet()) {
                for (int i = 0; i<M+N; i++) {
                 if (hashMap1[i].get(word)==null)
                     word2vec[i][j]=0;
                 else
                     word2vec[i][j]=hashMap1[i].get(word);
                 System.out.print(word+":"+word2vec[i][j]+"\t");
                }
                j++;
                System.out.print(" \n");
            }

        for (int i = 0; i <M+N; i++) {
            for (int k = 0; k <j ; k++) {
                System.out.print(word2vec[i][k]+" ");
            }
            System.out.print(" \n");
        }



        System.out.print("\n---------查询开始-----------------\n");
        for (int i = N; i <N+M; i++) {
            double d=Integer.MAX_VALUE;
            int m=0;
            int[] temp1=word2vec[i];

            for (int k = 0; k <j ; k++) {
                System.out.print(temp1[k]+" ");
            }
            System.out.print("---------查询"+i+"的词向量-----------------\n\n");
            for (int k = 0; k <N; k++) {

                int[] temp2=word2vec[k];

                for (int n = 0; n <j ; n++) {
                    System.out.print(temp2[n]+" ");
                }
                double dis=a_b(temp1,temp2);
                if (dis<d)
                {d=dis;m=k;}
                System.out.print("----------段落"+k+"词向量---------------");
                System.out.println("\n---------查询"+i+"--->段落"+k+"的距离  "+dis+"-----------------\n");

            }
            System.out.println("---------查询结束"+i+"-----------------");
            System.out.println("---------最佳匹配的句子是 段落"+m+"-------------\n"+text[m]+"\n\n\n\n");

        }

    }

    private static void counter(String sentence,HashMap<String,Integer> hashMap){
        String[] s=sentence.split(" ");
        for (int j = 0; j <s.length; j++) {
            String word =s[j].toLowerCase();
            if (!hashMap.containsKey(word)) {
                hashMap.put(word, new Integer(1));
            } else {
                int k = hashMap.get(word).intValue() + 1;
                hashMap.put(word, new Integer(k));
            }
        }
    }

    private static void mergeHash(String sentence,HashMap<String,Integer> hashMap){

    }

    //向量乘积
    public static long dotMuit(int[] a,int[] b) {
                if (a.length!=b.length)
                    throw new RuntimeException("长度不想等");
        else if (a.length==0)
            throw new RuntimeException("不能0");
        int c=0;
        for (int i = 0; i <a.length ; i++) {
            c+=a[i]*b[i];
        }
        return c;
    }

    //计算向量模
    public static double mo(int[] a) {
        if (a.length==0)
            throw new RuntimeException("不能0");
        int c=0;
        for (int i = 0; i <a.length ; i++) {
            c+=a[i]*a[i];
        }

        return Math.sqrt(c);
    }

    //计算向量模
    public static double cos(int[] a,int[] b) {
        if (a.length==0)
            throw new RuntimeException("不能0");

        return dotMuit(a,b)/(mo(a)*mo(b));
    }

    //|a-b|
    public static double a_b(int[] a, int[] b) {
        if (a.length!=b.length)
            throw new RuntimeException("长度不想等");
        else if (a.length==0)
            throw new RuntimeException("不能0");
        int c=0;
        int[] x=new int[a.length];
        for (int i = 0; i <a.length ; i++) {
            x[i]=a[i]-b[i];
        }
        return mo(x);
    }


}

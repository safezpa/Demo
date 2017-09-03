package mytest.Colloction;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by safe on 2017-04-01.
 * 为什么hashTable和ConcurrentHashMap 都不允许key value为null
 * 因为他们都支持并发
 *
 */
public class TestHashTable {
    public static void main(String[] args) {

        // 要选素数
        // initialCapacity 11（why？？？）
        // loadfactor 0.75（why???）
        Hashtable ht=new Hashtable(31,2);
        ht.put(2,0);
        //Neither the key nor the value can be null
        //ht.put(null,null);
        System.out.println(ht);

        HashMap hm=new HashMap();
        hm.put(null,null);
        hm.put(null,8);
        hm.put(9,null);
        System.out.println("========================");
        System.out.println(hm.get(3));
        System.out.println(hm);
        /**
         * why does ConcurrentHashMap prevent null keys and values?
         */
        ConcurrentHashMap concurrentHashMap=new ConcurrentHashMap();
        concurrentHashMap.put(null,null);
        concurrentHashMap.put(0,8);
        System.out.println(concurrentHashMap);
        //ConcurrentHashmap和Hashtable都是支持并发的，这样会有一个问题，当你通过get(k)
        // 获取对应的value时，如果获取到的是null时，你无法判断，它是put（k,v）的时候value为null，
        // 还是这个key从来没有做过映射。HashMap是非并发的，可以通过contains(key)来做这个判断。
        // 而支持并发的Map在调用m.contains（key）和m.get(key),m可能已经不同了
        System.out.println(0x7FFFFFFF);
        int m=0;
        for (int i = 1; i <= 10; i++)
        {
            for (int j = i; j >=0; j--)
            {
                j--;
                m++;
            }
        }

        System.out.println(m);
    }
}

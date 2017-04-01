package mytest.Colloction;

import java.util.Hashtable;

/**
 * Created by safe on 2017-04-01.
 */
public class TestHashTable {
    public static void main(String[] args) {

        // 要选素数
        // initialCapacity 11（why？？？）
        // loadfactor 0.75（why???）
        Hashtable ht=new Hashtable(31,2);
        ht.put(2,0);
        System.out.println(ht);
        System.out.println(0x7FFFFFFF);



    }
}

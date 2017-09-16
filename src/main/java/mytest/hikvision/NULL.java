package mytest.hikvision;

public class NULL {

    public static void print(){
        System.out.println("MM");
    }
    public static void main(String[] args) {
        //System.out.println(((String) null).length());
        boolean result=false?false:true==false?true:false;
        boolean re=false ? false : true;
        System.out.println(re);
        System.out.println(""+result+"");
        try{
            //((NULL)null).print();
            System.out.println(((String) null).length());
        }catch(NullPointerException e){
            System.out.println("NullPointerException");
        }
    }
}

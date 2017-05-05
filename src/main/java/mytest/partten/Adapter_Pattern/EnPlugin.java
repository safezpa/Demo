package mytest.partten.Adapter_Pattern;

// 实现英标插座的充电方法
public class EnPlugin implements EnPluginInterface {
    public void chargeWith3Pins() {
        System.out.println("charge with EnPlugin");
    }
}

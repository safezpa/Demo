package pattern.Adapter_Pattern;

// 国标测试类
public class CnTest {
    public static void main(String[] args) {
        CnPluginInterface cnPlugin = new CnPlugin();
        Home home = new Home(cnPlugin);
        // 会输出 “charge with CnPlugin”
        home.charge();
    }
}

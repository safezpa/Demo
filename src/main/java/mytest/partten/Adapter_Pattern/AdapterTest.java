package mytest.partten.Adapter_Pattern;

// 适配器测试类
public class AdapterTest {
    public static void main(String[] args) {
        EnPluginInterface enPlugin = new EnPlugin();
        Home home = new Home();
        PluginAdapter pluginAdapter = new PluginAdapter(enPlugin);
        home.setPlugin(pluginAdapter);
        // 会输出 “charge with EnPlugin”
        home.charge();
    }
}

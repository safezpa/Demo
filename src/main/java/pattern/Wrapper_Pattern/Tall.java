package pattern.Wrapper_Pattern;

// 加上身材高大的特性
public class Tall extends GirlDecorator {

    private Girl girl;

    public Tall(Girl g) {
        girl = g;
    }

    @Override
    public String getDescription() {
        return girl.getDescription() + "+is very tall";
    }

}



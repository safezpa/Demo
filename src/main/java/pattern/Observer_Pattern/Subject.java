package pattern.Observer_Pattern;

/**
 * Created by safe on 2017-05-05.
 */
// Subject 主题接口
public interface Subject {
    public void registerObserver(Observer o);
    public void removeObserver(Observer o);
    public void notifyAllObservers();
}

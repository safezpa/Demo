package pattern.Observer_Pattern;

import java.util.ArrayList;

// 视频网站某狐 实现 Subject 接口
public class VideoSite implements Subject{

    // 观察者列表 以及 更新了的视频列表
    private ArrayList<Observer> userList;
    private ArrayList<String> videos;

    public VideoSite(){
        userList = new ArrayList<Observer>();
        videos = new ArrayList<String>();
    }

    @Override
    public void registerObserver(Observer o) {
        userList.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        userList.remove(o);
    }

    @Override
    public void notifyAllObservers() {
        for (Observer o: userList) {
            o.update(this);
        }
    }

    public void addVideos(String video) {
        this.videos.add(video);
        notifyAllObservers();
    }

    public ArrayList<String> getVideos() {
        return videos;
    }

    public String toString(){
        return videos.toString();
    }
}

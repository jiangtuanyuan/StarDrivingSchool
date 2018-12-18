package cn.star.csxsjx.view.main.bean;

public class ImagesBean {
    private int id;
    private String url;
    private int serilanum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url.replace("\\", "/").replace(" ", "");
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSerilanum() {
        return serilanum;
    }

    public void setSerilanum(int serilanum) {
        this.serilanum = serilanum;
    }
}

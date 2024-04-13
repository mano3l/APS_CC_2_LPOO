package unip.aps.modules;

public class ReadJson {
    public static void main(String[] args){
        GetPath path = new GetPath();

        System.out.println(path.getCurrentPath() + path.getDataJson());
    }
}

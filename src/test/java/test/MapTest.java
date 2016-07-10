package test;

/**
 * Created by Zhaoan.Tan on 2016/7/10.
 */
public class MapTest {
    public static void main(String... args) {
        DataProvider dataProvider = new DataProvider();
        Id2EntityMap id2EntityMap = new Id2EntityMap("id2Entity", dataProvider);
    }
}

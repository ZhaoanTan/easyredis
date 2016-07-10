package test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhaoan.Tan on 2016/7/10.
 */
public class DataProvider {
    public List<DataEntity> provide() {
        List<DataEntity> ls = new ArrayList<>();
        DataEntity e = new DataEntity();
        e.setId("124567893");
        e.setName("Tom");
        e.setBirthday(LocalDate.of(1988, 2, 7));
        e.setAge(28);
        e.setSex(SexType.MALE);
        e.setDepartment("IT");
        ls.add(e);

        e = new DataEntity();
        e.setId("365214569");
        e.setName("Jack");
        e.setBirthday(LocalDate.of(1989, 2, 7));
        e.setAge(27);
        e.setSex(SexType.MALE);
        e.setDepartment("IT");
        ls.add(e);

        e = new DataEntity();
        e.setId("333556569");
        e.setName("Mary");
        e.setBirthday(LocalDate.of(1990, 2, 7));
        e.setAge(26);
        e.setSex(SexType.FEMAEL);
        e.setDepartment("IT");
        ls.add(e);

        return ls;
    }
}

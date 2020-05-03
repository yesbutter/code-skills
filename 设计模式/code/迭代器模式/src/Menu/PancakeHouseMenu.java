package Menu;

import Iterator.Iterator;
import Iterator.ListIterator;

import java.util.ArrayList;
import java.util.List;

public class PancakeHouseMenu {
    List<MenuItem> menuList;

    public PancakeHouseMenu() {
        this.menuList = new ArrayList<>();

        menuList.add(new MenuItem("红烧肉"));
        menuList.add(new MenuItem("梅菜扣肉"));
        menuList.add(new MenuItem("口水鸡"));
        menuList.add(new MenuItem("糖醋里脊"));
    }

    public Iterator getPancakeIterator()
    {
        return new ListIterator<MenuItem>(menuList);
    }
}

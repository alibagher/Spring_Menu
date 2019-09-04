package hello;

import java.util.ArrayList;
import java.util.List;

// This is the composite class of The Composite Design Pattern.

public class subMenu extends Menu {
    private List<Menu> menuList = new ArrayList<Menu>();

    public void addMenu(Menu m) {
        menuList.add(m);
    }

    public void removeMenu(Menu m) {
        menuList.remove(m);
    }

    public List<Menu> get() {
        return menuList;
    }
}
package hello;

import java.util.ArrayList;
import java.util.List;

// This is the Component class of The Composite Design Pattern.

public class Menu {
    private String description;
    private boolean active;
    private List<subMenu> sm = new ArrayList<subMenu>();;
    private List<item> itemList = new ArrayList<item>();



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

    //add an hello.item
    public List<item> getItems() {
        return itemList;
    }
    public void addItem(item i) {
        this.itemList.add(i);
    }


    public void addSubMenu(subMenu sm){
        this.sm.add(sm);
    }
    public List<subMenu> getSubMenu(){
        return sm;
    }
}
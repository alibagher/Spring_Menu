package hello;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;




@RestController
public class ProductServiceController{

    private static Map<String, Menu> productRepo = new HashMap<>();

    static {
        hello.Menu m = new hello.Menu();
        m.setDescription("Menu 1");
        m.setActive(true);

        hello.item i = new hello.item();
        i.name="item 1";
        i.itemDescription = "This is a new item";
        i.price = 5;
        i.days = "Monday to Friday";
        i.startDate = "20:00";
        i.endDate = "23:00";
        i.RankkingDate1 = "1st March";
        i.RankkingDate2 = "April 1st";
        i.ranking = 4;
        m.addItem(i);

        hello.subMenu sm = new hello.subMenu();
        sm.setDescription("sub1");
        sm.setActive(true);
        sm.addItem(i);
        m.addSubMenu(sm);

        productRepo.put(m.getDescription(), m);
    }

    // Delete request
    @RequestMapping(value = "/menu/{description}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("description") String description) {
        productRepo.remove(description);
        return new ResponseEntity<>("Product is deleted successfully", HttpStatus.OK);
    }

    // PUT request
    @RequestMapping(value = "/menu/{description}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateMenu(@PathVariable("description") String description, @RequestBody hello.Menu m) {
        //check that there is at least one item.
        if(!checkItems(m)){
            return new ResponseEntity<>("A menu must have at least one item.", HttpStatus.OK);
        }

        //delete and update the new menu
        productRepo.remove(description);
        m.setDescription(description);
        productRepo.put(description, m);
        return new ResponseEntity<>("Product is updated successfully", HttpStatus.OK);
    }

    // POST request
    @RequestMapping(value = "/menu", method = RequestMethod.POST)
    public ResponseEntity<Object> createMenu(@RequestBody hello.Menu m) {
        // add a new menu
        productRepo.put(m.getDescription(), m);
        return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED);
    }

    // GET request
    @RequestMapping(value = "/menu")
    public ResponseEntity<Object> getMenu() {
        //int l = productRepo.size();
        return new ResponseEntity<>(productRepo.values(), HttpStatus.OK);
    }



    // a recursive function that returns the sum of all prices of items (including submenus).
    public double getPrices(Menu m) {
        double p = 0;
        int l = m.getItems().size();
        for (int i = 0; i < l; i++) {
            p = m.getItems().get(i).price + p;
        }

        if (m.getSubMenu().size() == 0) {
            return p;
        }

        l = m.getSubMenu().size();

        for (int i = 0; i < l; i++) {
            p += getPrices(m.getSubMenu().get(i));
        }

        return p;
    }


    // a recursive function that returns false ANY submenus are without an item, true otherwise.
    public boolean checkItems(Menu m) {
        int l = m.getSubMenu().size();
        if (m.getItems().size() == 0){
            return false;
        }

        for (int i = 0; i < l; i++) {
            if (m.getSubMenu().get(i).getItems().size() == 0){
                return false;
            }
            if(!checkItems(m.getSubMenu().get(i))){
                return false;
            }
        }

        return true;
    }

}
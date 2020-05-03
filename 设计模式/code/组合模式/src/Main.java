import ComponentMode.Menu.Menu;
import ComponentMode.Menu.MenuComponent;
import ComponentMode.Menu.MenuItem;
import ComponentMode.Waitress;

public class Main {

    public static void main(String[] args) {

        //迭代器和组合一起使用
        MenuComponent pancakeHouseMenu=new Menu("PANCAKE HOUSE MENU","Breakfast");
        MenuComponent dinerMenu=new Menu("DINER MENU","Lunch");
        MenuComponent cafeMenu=new Menu("CAFE MENU","Drink");

        MenuComponent allMenus=new Menu("ALL MENUS","All menus combined");

        allMenus.add(pancakeHouseMenu);
        allMenus.add(dinerMenu);
        allMenus.add(cafeMenu);

        //String name, String description, boolean vegetarian, double price
        pancakeHouseMenu.add(new MenuItem("糖醋里脊","鸡排",false,12.5));
        pancakeHouseMenu.add(new MenuItem("红烧肉","猪肉",false,18.5));

        dinerMenu.add(new MenuItem("水果沙拉","凉拌沙拉",true,5.5));
        dinerMenu.add(new MenuItem("擀面皮","凉拌",true,7.0));

        cafeMenu.add(new MenuItem("咖啡","coffee",false,8.0));
        cafeMenu.add(new MenuItem("卡布奇诺","coffee",false,8.8));


        Waitress waitress=new Waitress(allMenus);
        waitress.printMenu();

        waitress.printVegetarianMenu();
    }
}

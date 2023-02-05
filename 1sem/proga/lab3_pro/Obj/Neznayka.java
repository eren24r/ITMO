package Obj;

import Classes.Client;
import Classes.Stock;

public class Neznayka extends Client {

    public Neznayka() {
        super("Neznayka", 50);
    }

    public boolean inScafandara(){
        if (this.getMoney() > 0) {
            this.describe("Neznayka готов к полёту!");
            return true;
        }else{
            this.describe("Neznayka не готов к полёту!");
            return false;
        }
    }

    public boolean gotoKosmos(){
        if (this.getMoney() > 0) {
            this.describe("Neznayka в Космосе!\nNeznayka в акционерное общество!\nАкционерное общество гигантских растений -- путь к богатству и процветанию.\nЦена 1 фертинг");
            return true;
        }else {
            this.describe("Neznayka не готов к полёту!");
            return false;
        }
    }
}

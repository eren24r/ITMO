package mainProgram;

import myColl.MyHashMap;

public class Tmp {
    public static void main(String[] args) {
        MyHashMap<Cat, Integer> mp = new MyHashMap<>();
        Cat a = new Cat();
        Cat b = new Cat();
        Cat c = new Cat();
        mp.put(a,2);
        System.out.println(mp.getVal(a));
        mp.put(a,4);
        System.out.println(mp.getVal(a));
        mp.put(c,5);
        System.out.println(mp.getVal(c));
    }
}

class Cat{

    @Override
    public int hashCode() {
        return 5;
    }
}
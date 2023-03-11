package collEdit;

import сlasses.Organization;

import java.util.HashMap;
import java.util.HashSet;

public class Sort {
    public HashMap<Integer, Organization> sort(HashSet<Organization> set){
        HashMap<Integer, Organization> al = new HashMap<>();
        for (Organization o: set){
            al.put(o.getId(), o);
        }
        return al;
    }
}

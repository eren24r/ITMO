package collEdit;

import сlasses.Organization;

import java.util.HashSet;

public class GetById {
    public Organization getById(HashSet<Organization> s, int i){
        Organization k = null;
        for(Organization o: s){
            if(o.getId() == i){
                k = o;
            }
        }
        return k;
    }
}

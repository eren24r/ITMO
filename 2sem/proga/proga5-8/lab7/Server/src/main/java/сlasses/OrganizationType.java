package сlasses;

/**
 * Тип Организации
 */
public enum OrganizationType {
    PUBLIC(1),
    GOVERNMENT(2),
    TRUST(3);

    private int id;
    OrganizationType(int id){
        this.id = id;
    }
    @Override
    public String toString() {
        return (String) this.name();
    }

    public int getId(){
        return this.id;
    }

    public OrganizationType getTypeById(int i){
        switch(i){
            case 1: return OrganizationType.PUBLIC;
            case 2: return OrganizationType.GOVERNMENT;
            case 3: return OrganizationType.TRUST;
        }
        return null;
    }
    public OrganizationType getTypeByName(String s){
        if(s.equals("PUBLIC")){
            return OrganizationType.PUBLIC;
        }
        else if(s.equals("GOVERNMENT")){
            return OrganizationType.GOVERNMENT;
        }
        else if(s.equals("TRUST")){
            return OrganizationType.TRUST;
        }else{
            return null;
        }
    }

    public void setId(int id) {
        this.id = id;
    }
}

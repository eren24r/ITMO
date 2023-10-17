package objectResAns;

import java.io.Serializable;

public class ObjectResAns implements Serializable {
    private String resTesxt;
    private boolean resAns;
    public String user;

    public ObjectResAns(String resTesxt, boolean resAns, String user) {
        this.resTesxt = resTesxt;
        this.resAns = resAns;
        this.user = user;
    }

    public String getResTesxt() {
        return resTesxt;
    }

    public boolean isResAns() {
        return resAns;
    }

    public void setResTesxt(String resTesxt) {
        this.resTesxt = resTesxt;
    }

    public void setResAns(boolean resAns) {
        this.resAns = resAns;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}

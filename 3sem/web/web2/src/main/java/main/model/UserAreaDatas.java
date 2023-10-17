package main.model;
import java.beans.JavaBean;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Objects;

@JavaBean
public class UserAreaDatas implements Serializable {
    private LinkedList<AreaData> areaDataList;

    public UserAreaDatas() {
        super();
    }

    public LinkedList<AreaData> getAreaDataList() {
        return areaDataList;
    }

    public void setAreaDataList(LinkedList<AreaData> areaDataList) {
        this.areaDataList = areaDataList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAreaDatas)) return false;
        UserAreaDatas that = (UserAreaDatas) o;
        return Objects.equals(getAreaDataList(), that.getAreaDataList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAreaDataList());
    }

    @Override
    public String toString() {
        return "UserAreaDatas{" +
                "areaDataList=" + areaDataList +
                '}';
    }

    public AreaData getLastResult() {
        if(!this.areaDataList.isEmpty()){
            return this.areaDataList.get(areaDataList.size() - 1);
        }else {
            return null;
        }
    }
}
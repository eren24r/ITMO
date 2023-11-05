package main.model;
import java.beans.JavaBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@JavaBean
public class UserDatas implements Serializable {
    private ArrayList<User> userList;

    public UserDatas(){
        userList = new ArrayList<>();
    }

    public boolean addUser(User user){
        user.setId(userList.size() + 1);
        userList.add(user);

        return true;
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }
}
package data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserList {

    @SerializedName("data")
    @Expose
    private ArrayList<User> users = new ArrayList<User>();

    /**
     * No args constructor for use in serialization
     *
     */
    public UserList() {
    }

    /**
     *
     * @param users
     */
    public UserList(ArrayList<User> users) {
        super();
        this.users = users;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setProduct(ArrayList<User> users) {
        this.users = users;
    }

    public void addProduct(User user) {
        this.users.add(user);
    }

}

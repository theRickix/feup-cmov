package data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;

/**
 * Created by JP on 04/11/2017.
 */

public class User {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("fiscal")
    @Expose
    private String fiscal;

    @SerializedName("cc_type")
    @Expose
    private CardType cc_type;

    @SerializedName("cc_number")
    @Expose
    private String cc_number;

    @SerializedName("cc_date")
    @Expose
    private Date cc_date;

    public User(int id, String name, String username, String email,
                String password, String fiscal, CardType cc_type, String cc_number, Date cc_date) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.fiscal = fiscal;
        this.cc_type = cc_type;
        this.cc_number = cc_number;
        this.cc_date = cc_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFiscal() {
        return fiscal;
    }

    public void setFiscal(String fiscal) {
        this.fiscal = fiscal;
    }

    public CardType getCc_type() {
        return cc_type;
    }

    public void setCc_type(CardType cc_type) {
        this.cc_type = cc_type;
    }

    public String getCc_number() {
        return cc_number;
    }

    public void setCc_number(String cc_number) {
        this.cc_number = cc_number;
    }

    public Date getCc_date() {
        return cc_date;
    }

    public void setCc_date(Date cc_date) {
        this.cc_date = cc_date;
    }
}

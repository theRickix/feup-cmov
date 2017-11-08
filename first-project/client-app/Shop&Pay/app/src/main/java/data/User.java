package data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAKey;
import java.sql.Date;

/**
 * Created by JP on 04/11/2017.
 */

public class User implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("postal_code")
    @Expose
    private String postal_code;

    @SerializedName("fiscal")
    @Expose
    private String fiscal;

    @SerializedName("cc_type")
    @Expose
    private CardType cc_type;

    @SerializedName("cc_number")
    @Expose
    private String cc_number;

    @SerializedName("cc_expiry_month")
    @Expose
    private int cc_expiry_month;

    @SerializedName("cc_expiry_year")
    @Expose
    private int cc_expiry_year;

    @SerializedName("public_key")
    @Expose
    private String public_key;

    private String private_key;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
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

    public int getCc_expiry_month() {
        return cc_expiry_month;
    }

    public void setCc_expiry_month(int cc_expiry_month) {
        this.cc_expiry_month = cc_expiry_month;
    }

    public int getCc_expiry_year() {
        return cc_expiry_year;
    }

    public void setCc_expiry_year(int cc_expiry_year) {
        this.cc_expiry_year = cc_expiry_year;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPublic_key() {
        return public_key;
    }

    public void setPublic_key(String public_key) {
        this.public_key = public_key;
    }

    public User(User copy) {

        this.id = copy.id;
        this.name = copy.name;
        this.email = copy.email;
        this.password = copy.password;
        this.address = copy.address;
        this.postal_code = copy.postal_code;
        this.fiscal = copy.fiscal;
        this.cc_type = copy.cc_type;
        this.cc_number = copy.cc_number;
        this.cc_expiry_month = copy.cc_expiry_month;
        this.cc_expiry_year = copy.cc_expiry_year;

        try {
            generatePublicAndPrivateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public User(String name, String email, String password, String address, String postal_code, String fiscal, CardType cc_type, String cc_number, int cc_expiry_month, int cc_expiry_year) {

        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.postal_code = postal_code;
        this.fiscal = fiscal;
        this.cc_type = cc_type;
        this.cc_number = cc_number;
        this.cc_expiry_month = cc_expiry_month;
        this.cc_expiry_year = cc_expiry_year;

        try {
            generatePublicAndPrivateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String getPrivate_key() {
        return private_key;
    }

    public void setPrivate_key(String private_key) {
        this.private_key = private_key;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", postal_code='" + postal_code + '\'' +
                ", fiscal='" + fiscal + '\'' +
                ", cc_type=" + cc_type +
                ", cc_number='" + cc_number + '\'' +
                ", cc_expiry_month=" + cc_expiry_month +
                ", cc_expiry_year=" + cc_expiry_year +
                ", public_key='" + public_key + '\'' +
                ", private_key='" + private_key + '\'' +
                '}';
    }

    public void generatePublicAndPrivateKey() throws NoSuchAlgorithmException {
        KeyPairGenerator generator = null;
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        try {
            generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(368, random);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        KeyPair pair =generator.generateKeyPair();
        public_key=pair.getPublic().toString();
        private_key=pair.getPrivate().toString();
    }
}
package data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;
import java.sql.Time;
import java.util.UUID;

/**
 * Created by ricardo on 09-11-2017.
 */

class Purchase {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("purchase_date")
    @Expose
    private Date purchase_date;

    @SerializedName("purchase_time")
    @Expose
    private Time purchase_time;

    @SerializedName("validation_token")
    @Expose
    private UUID validation_token;

    @SerializedName("total_price")
    @Expose
    private double total_price;

    public Purchase() {
    }

    public Purchase(int id, Date purchase_date, Time purchase_time, UUID validation_token, double total_price) {
        this.id = id;
        this.purchase_date = purchase_date;
        this.purchase_time = purchase_time;
        this.validation_token = validation_token;
        this.total_price = total_price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(Date purchase_date) {
        this.purchase_date = purchase_date;
    }

    public Time getPurchase_time() {
        return purchase_time;
    }

    public void setPurchase_time(Time purchase_time) {
        this.purchase_time = purchase_time;
    }

    public String getValidation_token() {
        return validation_token.toString();
    }

    public void setValidation_token(UUID validation_token) {
        this.validation_token = validation_token;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }
}

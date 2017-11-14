package data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.UUID;

/**
 * Created by ricardo on 09-11-2017.
 */

public class Purchase {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("purchase_timestamp")
    @Expose
    private Date purchase_timestamp;

    @SerializedName("validation_token")
    @Expose
    private UUID validation_token;

    @SerializedName("total_price")
    @Expose
    private double total_price;

    public Purchase(int id, Date purchase_timestamp, UUID validation_token, double total_price) {
        this.id = id;
        this.purchase_timestamp = purchase_timestamp;
        this.validation_token = validation_token;
        this.total_price = total_price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getPurchase_timestamp() {
        return purchase_timestamp;
    }

    public void setPurchase_timestamp(Date purchase_timestamp) {
        this.purchase_timestamp = purchase_timestamp;
    }

    public UUID getValidation_token() {
        return validation_token;
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

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", purchase_timestamp=" + purchase_timestamp +
                ", validation_token=" + validation_token +
                ", total_price=" + total_price +
                '}';
    }
}

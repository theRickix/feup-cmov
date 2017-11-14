package data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ricardo on 09-11-2017.
 */

public class ResponsePurchase {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("validation_token")
    @Expose
    private String validation_token;

    public ResponsePurchase(int id, String validation_token) {
        this.id = id;
        this.validation_token = validation_token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValidation_token() {
        return validation_token;
    }

    public void setValidation_token(String validation_token) {
        this.validation_token = validation_token;
    }
}

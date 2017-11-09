package data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ricardo on 09-11-2017.
 */

public class ResponseId {

    @SerializedName("id")
    @Expose
    private int id;

    public ResponseId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

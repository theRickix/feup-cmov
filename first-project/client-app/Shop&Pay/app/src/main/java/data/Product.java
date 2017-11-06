package data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("maker_id")
    @Expose
    private int makerId;
    @SerializedName("category_id")
    @Expose
    private int categoryId;
    @SerializedName("price")
    @Expose
    private String price;

    /**
     * No args constructor for use in serialization
     */
    public Product() {
    }

    /**
     * @param id
     * @param model
     * @param price
     * @param categoryId
     * @param makerId
     */
    public Product(int id, String model, String barcode, int makerId, int categoryId, String price) {
        super();
        this.id = id;
        this.model = model;
        this.barcode = barcode;
        this.makerId = makerId;
        this.categoryId = categoryId;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getMakerId() {
        return makerId;
    }

    public void setMakerId(int makerId) {
        this.makerId = makerId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
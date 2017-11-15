package data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("maker")
    @Expose
    private String maker;
    @SerializedName("category")
    @Expose
    private String category;
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
    public Product(int id, String model, String barcode, String makerId, String categoryId, String price) {
        super();
        this.id = id;
        this.model = model;
        this.barcode = barcode;
        this.maker = makerId;
        this.category = categoryId;
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

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", barcode='" + barcode + '\'' +
                ", maker='" + maker + '\'' +
                ", category='" + category + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.model);
        dest.writeString(this.barcode);
        dest.writeString(this.maker);
        dest.writeString(this.category);
        dest.writeString(this.price);
    }

    protected Product(Parcel in) {
        this.id = in.readInt();
        this.model = in.readString();
        this.barcode = in.readString();
        this.maker = in.readString();
        this.category = in.readString();
        this.price = in.readString();
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
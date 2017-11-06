package data;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductList {

    @SerializedName("data")
    @Expose
    private ArrayList<Product> products = new ArrayList<Product>();

    /**
     * No args constructor for use in serialization
     *
     */
    public ProductList() {
    }

    /**
     *
     * @param products
     */
    public ProductList(ArrayList<Product> products) {
        super();
        this.products = products;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProduct(ArrayList<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

}

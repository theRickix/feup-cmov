package data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ricardo on 09-11-2017.
 */

public class PurchaseList {

    @SerializedName("data")
    @Expose
    private ArrayList<Purchase> purchases = new ArrayList<Purchase>();

    public PurchaseList() {
    }

    public PurchaseList(ArrayList<Purchase> purchases) {
        this.purchases = purchases;
    }

    public void setPurchases(ArrayList<Purchase> purchases) {
        this.purchases = purchases;
    }

    public void addPurchase(Purchase purchase) {
        this.purchases.add(purchase);
    }
}

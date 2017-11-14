package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import com.cmov.feup.printer.R;
import data.Purchase;

public class MyPurchaseAdapter extends ArrayAdapter<Purchase> {

    List<Purchase> purchaseList;
    Context context;
    private LayoutInflater mInflater;

    // Constructors
    public MyPurchaseAdapter(Context context, List<Purchase> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        purchaseList = objects;
    }

    @Override
    public Purchase getItem(int position) {
        return purchaseList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.layout_purchase_history_row, parent, false);
            vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Purchase item = getItem(position);

        vh.textViewDateTime.setText(new SimpleDateFormat("yyyy:MM:dd @ HH:mm:ss").format(item.getPurchase_timestamp()));
        vh.textViewPrice.setText("Price: "+item.getTotal_price());

        return vh.rootView;
    }

    private static class ViewHolder {
        public final RelativeLayout rootView;
        public final TextView textViewDateTime;
        public final TextView textViewPrice;

        private ViewHolder(RelativeLayout rootView, TextView textViewDateTime, TextView textViewPrice) {
            this.rootView = rootView;
            this.textViewDateTime = textViewDateTime;
            this.textViewPrice = textViewPrice;
        }

        public static ViewHolder create(RelativeLayout rootView) {
            TextView textViewDateTime = (TextView) rootView.findViewById(R.id.textViewDateTime);
            TextView textViewPrice = (TextView) rootView.findViewById(R.id.textViewPrice);
            return new ViewHolder(rootView, textViewDateTime, textViewPrice);
        }
    }
}

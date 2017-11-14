package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import com.cmov.feup.printer.R;
import data.Product;

public class MyProductAdapter extends ArrayAdapter<Product> {

    List<Product> productList;
    Context context;
    private LayoutInflater mInflater;

    // Constructors
    public MyProductAdapter(Context context, List<Product> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        productList = objects;
    }

    @Override
    public Product getItem(int position) {
        return productList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.layout_row_view, parent, false);
            vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Product item = getItem(position);

        vh.textViewModel.setText(item.getModel());
        vh.textViewCategory.setText("Category: "+item.getCategory());
        vh.textViewMaker.setText("Maker: "+item.getMaker());
        vh.textViewPrice.setText(item.getPrice()+" €");

        return vh.rootView;
    }

    private static class ViewHolder {
        public final RelativeLayout rootView;
        public final TextView textViewModel;
        public final TextView textViewCategory;
        public final TextView textViewMaker;
        public final TextView textViewPrice;

        private ViewHolder(RelativeLayout rootView, TextView textViewModel, TextView textViewCategory, TextView textViewMaker,TextView textViewPrice) {
            this.rootView = rootView;
            this.textViewModel = textViewModel;
            this.textViewCategory = textViewCategory;
            this.textViewMaker = textViewMaker;
            this.textViewPrice = textViewPrice;
        }

        public static ViewHolder create(RelativeLayout rootView) {
            TextView textViewModel = (TextView) rootView.findViewById(R.id.textViewModel);
            TextView textViewCategory = (TextView) rootView.findViewById(R.id.textViewCategory);
            TextView textViewMaker = (TextView) rootView.findViewById(R.id.textViewMaker);
            TextView textViewPrice = (TextView) rootView.findViewById(R.id.textViewPrice);
            return new ViewHolder(rootView, textViewModel, textViewCategory, textViewMaker, textViewPrice);
        }
    }
}

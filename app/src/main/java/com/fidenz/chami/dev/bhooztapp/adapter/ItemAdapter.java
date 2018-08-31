package com.fidenz.chami.dev.bhooztapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fidenz.chami.dev.bhooztapp.models.Products;
import com.fidenz.chami.dev.bhooztapp.R;

import java.util.List;

/**
 * Created by fidenz on 6/19/18.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {


    private List<Products> productList;
    private Context context;
    private GetInterface getInterface;

    public ItemAdapter(List<Products> productList, Context context, GetInterface getInterface) {
        this.productList = productList;
        this.context = context;
        this.getInterface = getInterface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Products products = productList.get(position);

        holder.tvDaysText.setText(products.getDays());
        holder.tvPiecesCount.setText(products.getPiesCount());
        holder.imgProduct.setImageResource(products.getImagename());
    }

    @Override
    public int getItemCount() {
        return (productList != null ? productList.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvDaysText;
        public TextView tvPiecesCount;
        public ImageView imgProduct;
        public ImageView imgArrow;
        public RelativeLayout relViewLayout;


        public ViewHolder(View itemView) {
            super(itemView);

            tvDaysText = itemView.findViewById(R.id.iddays);
            tvPiecesCount = itemView.findViewById(R.id.idpiescount);
            imgProduct = itemView.findViewById(R.id.productimg);
            imgArrow = itemView.findViewById(R.id.imgarrow);
            relViewLayout = itemView.findViewById(R.id.idrelview);

            relViewLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int itemPosition = getAdapterPosition();
                    getInterface.mgetposition(itemPosition);

                }
            });

        }
    }

    public interface GetInterface {

        void mgetposition(int Position);

    }
}

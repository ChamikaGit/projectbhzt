package com.fidenz.chami.dev.bhooztapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fidenz.chami.dev.bhooztapp.models.Tickers;
import com.fidenz.chami.dev.bhooztapp.R;

import java.util.List;

/**
 * Created by fidenz on 6/21/18.
 */

public class TickerAdapter extends RecyclerView.Adapter<TickerAdapter.ViewHolder> {

    private List<Tickers> tickersList;

    private Context context;

    private ItemListener mItemListener;

    public TickerAdapter(List<Tickers> tickersList, Context context,ItemListener itemListener) {
        this.tickersList = tickersList;
        this.context = context;
        this.mItemListener =itemListener;
    }

    @Override
    public TickerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_tickers,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TickerAdapter.ViewHolder holder, final int position) {

        Tickers tickers = tickersList.get(position);

        holder.tvHeaderText.setText(tickers.getTickerText());
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemListener.onClickEdit(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tickersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvHeaderText;
        public ImageView imgEdit;

        public ViewHolder(View itemView) {
            super(itemView);

            tvHeaderText = itemView.findViewById(R.id.idtvTikckertext);
            imgEdit =itemView.findViewById(R.id.idedit);
        }
    }

    public interface ItemListener {

        void onClickEdit(int position);
    }
}

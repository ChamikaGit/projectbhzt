package com.fidenz.chami.dev.bhooztapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fidenz.chami.dev.bhooztapp.models.Advertisements;
import com.fidenz.chami.dev.bhooztapp.R;

import java.util.List;

/**
 * Created by fidenz on 6/21/18.
 */

public class AdvertistmnetAdapter extends RecyclerView.Adapter<AdvertistmnetAdapter.ViewHolder> {

    private List<Advertisements> advertisementsList;

    private Context context;

    private ItemListener mItemListener;

    public AdvertistmnetAdapter(List<Advertisements> advertisementsList, Context context, ItemListener itemListener) {
        this.advertisementsList = advertisementsList;
        this.context = context;
        this.mItemListener = itemListener;
    }

    @Override
    public AdvertistmnetAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_advertistmnet, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdvertistmnetAdapter.ViewHolder holder, final int position) {


        Advertisements advertisements = advertisementsList.get(position);

        holder.tvHeaderText.setText(advertisements.getAdheader());
        holder.tvBodyText.setText(advertisements.getAdbody());
        holder.tvLinkText.setText(advertisements.getAdlinkname());

        holder.imgEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemListener.onclickedit(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return advertisementsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvHeaderText;
        public TextView tvBodyText;
        public TextView tvLinkText;
        public ImageView imgEditText;

        public ViewHolder(View itemView) {
            super(itemView);

            tvHeaderText = itemView.findViewById(R.id.idadheader);
            tvBodyText = itemView.findViewById(R.id.idaddbody);
            tvLinkText = itemView.findViewById(R.id.idaddlinkname);
            imgEditText = itemView.findViewById(R.id.idedit);
        }
    }

    public interface ItemListener {

        void onclickedit(int position);


    }
}

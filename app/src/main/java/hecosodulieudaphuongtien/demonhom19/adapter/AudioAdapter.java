package hecosodulieudaphuongtien.demonhom19.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import hecosodulieudaphuongtien.demonhom19.R;
import hecosodulieudaphuongtien.demonhom19.model.Audio;

/**
 * Created by Z170A Gaming M7 on 9/19/2016.
 */
public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.ViewHolder> {
    public ArrayList<Audio> listData;
    public Context context;
    public OnClickItemRecyclerView listener;

    public AudioAdapter(Context context, OnClickItemRecyclerView listener) {
        this.listener = listener;
        this.context = context;
        listData = new ArrayList<>();

    }

    public void updateData(ArrayList<Audio> list) {
        this.listData = list;
        notifyDataSetChanged();
    }

    public void updateData() {
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_audio_online, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Audio audio = listData.get(position);
        holder.tvName.setText(audio.title);
//        holder.tvNumber.setText(position + 1 + ".");
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickItem(audio);
            }
        });
        holder.tvArtis.setText(audio.singer.name);
        if (position == getItemCount() - 1) {
            holder.line.setVisibility(View.GONE);
        } else {
            holder.line.setVisibility(View.VISIBLE);
        }
        holder.btnDownload.setVisibility(View.VISIBLE);
        holder.tvViewCount.setText(" " + audio.viewCount);
        holder.tvRateCount.setText(" " + audio.rate);
    }

    public ArrayList<Audio> getListData() {
        return listData;
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout btnDownload;
        public TextView tvName, tvNumber, tvArtis, tvViewCount, tvRateCount;
        public RelativeLayout item, line;

        public ViewHolder(View itemView) {
            super(itemView);
            item = (RelativeLayout) itemView.findViewById(R.id.btn_item);
            tvNumber = (TextView) itemView.findViewById(R.id.tv_number);
            tvArtis = (TextView) itemView.findViewById(R.id.tv_artis);
            btnDownload = (LinearLayout) itemView.findViewById(R.id.btn_sub_item);
            tvName = (TextView) itemView.findViewById(R.id.tv_title);
            line = (RelativeLayout) itemView.findViewById(R.id.line);
            tvViewCount = (TextView) itemView.findViewById(R.id.tvViewCount);
            tvRateCount = (TextView) itemView.findViewById(R.id.tvRateCount);
        }

    }

    public interface OnClickItemRecyclerView {
        public void onClickItem(Audio audio);
    }
}

package hecosodulieudaphuongtien.demonhom19.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import hecosodulieudaphuongtien.demonhom19.R;
import hecosodulieudaphuongtien.demonhom19.model.Audio;
import hecosodulieudaphuongtien.demonhom19.model.Singer;

/**
 * Created by Z170A Gaming M7 on 9/19/2016.
 */
public class SingerAdapter extends RecyclerView.Adapter<SingerAdapter.ViewHolder> {
    public ArrayList<Singer> listData;
    public Context context;
    public OnClickItemRecyclerView listener;

    public SingerAdapter(Context context, OnClickItemRecyclerView listener) {
        this.listener = listener;
        this.context = context;
        listData = new ArrayList<>();

    }

    public void updateData(ArrayList<Singer> list) {
        this.listData = list;
        notifyDataSetChanged();
    }

    public void updateData() {
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_audio, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Singer singer = listData.get(position);
        Glide.with(context).load(singer.urlAvatar).into(holder.ivAvatar);
        holder.tvName.setText(singer.name);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickItem(singer);
            }
        });
    }

    public ArrayList<Singer> getListData() {
        return listData;
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout item;
        public TextView tvName;
        public ImageView ivAvatar;

        public ViewHolder(View itemView) {
            super(itemView);
            item = (LinearLayout) itemView.findViewById(R.id.item);
            tvName = (TextView) itemView.findViewById(R.id.tv_singerName);
            ivAvatar = (ImageView) itemView.findViewById(R.id.ivAvatar);
        }

    }

    public interface OnClickItemRecyclerView {
        public void onClickItem(Singer position);
    }
}

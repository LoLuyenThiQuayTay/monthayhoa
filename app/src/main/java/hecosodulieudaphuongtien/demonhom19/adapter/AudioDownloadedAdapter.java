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
import hecosodulieudaphuongtien.demonhom19.entity.Audio;

/**
 * Created by Z170A Gaming M7 on 9/19/2016.
 */
public class AudioDownloadedAdapter extends RecyclerView.Adapter<AudioDownloadedAdapter.ViewHolder> {
    public ArrayList<Audio> listData;
    public Context context;

    public AudioDownloadedAdapter(Context context) {
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
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_audio, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Audio audio = listData.get(position);
        holder.tvName.setText(audio.title);
        holder.tvNumber.setText(position + 1 + ".");
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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
        public TextView tvName, tvNumber;
        public RelativeLayout item;

        public ViewHolder(View itemView) {
            super(itemView);
            item = (RelativeLayout) itemView.findViewById(R.id.btn_item);
            tvNumber = (TextView) itemView.findViewById(R.id.tv_number);
            btnDownload = (LinearLayout) itemView.findViewById(R.id.btn_sub_item);
            tvName = (TextView) itemView.findViewById(R.id.tv_title);
        }

    }


}

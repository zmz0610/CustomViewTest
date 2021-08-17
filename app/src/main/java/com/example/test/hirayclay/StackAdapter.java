package com.example.test.hirayclay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by CJJ on 2017/3/7.
 */

public class StackAdapter extends RecyclerView.Adapter {

    private LayoutInflater inflater;
    private List<String> datas;
    private Context context;
    private List<Integer> imageUrls = Arrays.asList(
            R.drawable.xm2,
            R.drawable.xm3,
            R.drawable.xm4,
            R.drawable.xm5,
            R.drawable.xm6,
            R.drawable.xm7,
            R.drawable.xm1,
            R.drawable.xm8,
            R.drawable.xm9,
            R.drawable.xm1,
            R.drawable.xm2,
            R.drawable.xm3,
            R.drawable.xm4,
            R.drawable.xm5,
            R.drawable.xm6
    );
    private boolean vertical;

    public StackAdapter(List<String> datas) {
        this.datas = datas;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            context = parent.getContext();
            inflater = LayoutInflater.from(parent.getContext());
        }
        if (vertical) {
            return new ViewHolder(inflater.inflate(R.layout.vertical_item_card, parent, false));
        }
        if (viewType == 0) {
            return new ViewHolder1(inflater.inflate(R.layout.item_card_first, parent, false));
        } else {
            return new ViewHolder(inflater.inflate(R.layout.item_card, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case 0:
                ViewHolder1 viewHolder1= (ViewHolder1) holder;
                viewHolder1.index.setText(datas.get(holder.getAdapterPosition()));
                break;
            default:
                ViewHolder viewHolder= (ViewHolder) holder;
                viewHolder.index.setText(datas.get(holder.getAdapterPosition()));
        }

    }

    public StackAdapter vertical() {
        this.vertical = true;
        return this;
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cover;
        TextView index;

        public ViewHolder(View itemView) {
            super(itemView);
            cover = (ImageView) itemView.findViewById(R.id.cover);
            index = (TextView) itemView.findViewById(R.id.index);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context.getApplicationContext(), String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT)
                            .show();
                }
            });
        }
    }
    class ViewHolder1 extends RecyclerView.ViewHolder {
        ImageView cover;
        TextView index;

        public ViewHolder1(View itemView) {
            super(itemView);
            cover = (ImageView) itemView.findViewById(R.id.cover);
            index = (TextView) itemView.findViewById(R.id.index);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context.getApplicationContext(), String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT)
                            .show();
                }
            });
        }
    }
}

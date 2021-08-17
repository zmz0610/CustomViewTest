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

import java.util.List;

public class TopAdapter extends RecyclerView.Adapter {
    private List<Integer> viewList;
    private LayoutInflater inflater;
    private Context context;

    public TopAdapter(List<Integer> viewList) {
        this.viewList = viewList;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            context = parent.getContext();
            inflater = LayoutInflater.from(parent.getContext());
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
                viewHolder1.index.setText(viewList.get(holder.getAdapterPosition())+"");
                break;
            default:
                ViewHolder viewHolder= (ViewHolder) holder;
                viewHolder.index.setText(viewList.get(holder.getAdapterPosition())+"");
        }
    }

    @Override
    public int getItemCount() {
        return viewList.size();
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

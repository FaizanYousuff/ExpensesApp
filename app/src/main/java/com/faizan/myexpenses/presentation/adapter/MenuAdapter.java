package com.faizan.myexpenses.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faizan.myexpenses.R;
import com.faizan.myexpenses.model.MenuItems;
import com.faizan.myexpenses.presentation.listener.OnItemClickListener;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private List<MenuItems> menuItemsList;
    private OnItemClickListener listener;

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        return new MenuViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        if (menuItemsList != null) {
            holder.menuImage.setImageResource(menuItemsList.get(position).getId());
            holder.menuText.setText(menuItemsList.get(position).getText());
        }
    }

    @Override
    public int getItemCount() {
        return menuItemsList.size();
    }

    public void setMenuItemsList(List<MenuItems> menuItemsList) {
        this.menuItemsList = menuItemsList;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {

        private ImageView menuImage;
        private TextView menuText;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            menuImage = itemView.findViewById(R.id.image_menu);
            menuText = itemView.findViewById(R.id.tv_menu);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                        listener.onItemClick(menuItemsList.get(getAdapterPosition()));
                    }
                }
            });
        }
    }
}


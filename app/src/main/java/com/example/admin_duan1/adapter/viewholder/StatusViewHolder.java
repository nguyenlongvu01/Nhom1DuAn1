package com.example.admin_duan1.adapter.viewholder;

import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin_duan1.common.Common;
import com.example.admin_duan1.R;
import com.example.admin_duan1.interfaces.ItemClick;


public class StatusViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener {

    public TextView tv_idHoaDonItem, tv_statusHoaDonItem, tv_userHoaDonItem, tv_phoneHoaDonItem, tv_addressHoaDonItem,tv_tienHoaDonItem;
    private ItemClick itemClick;

    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public StatusViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_idHoaDonItem = itemView.findViewById(R.id.tv_idHoaDonItem);
        tv_statusHoaDonItem = itemView.findViewById(R.id.tv_statusHoaDonItem);
        tv_userHoaDonItem = itemView.findViewById(R.id.tv_userHoaDonItem);
        tv_phoneHoaDonItem = itemView.findViewById(R.id.tv_phoneHoaDonItem);
        tv_addressHoaDonItem = itemView.findViewById(R.id.tv_addressHoaDonItem);
        tv_tienHoaDonItem = itemView.findViewById(R.id.tv_tienHoaDonItem);

        itemView.setOnCreateContextMenuListener(this);
   //     itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClick.itemClick(v, getLayoutPosition(), false);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select the action");
        menu.add(0,0,getAdapterPosition(),Common.CHAP_NHAN);
        menu.add(0,1,getAdapterPosition(),Common.HUY);

    }
}

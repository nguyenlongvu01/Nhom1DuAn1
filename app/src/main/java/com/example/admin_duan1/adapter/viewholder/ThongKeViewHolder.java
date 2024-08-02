package com.example.admin_duan1.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin_duan1.R;

public class ThongKeViewHolder extends RecyclerView.ViewHolder {
    public TextView tv_idHoaDonItem, tv_statusHoaDonItem, tv_userHoaDonItem, tv_phoneHoaDonItem, tv_addressHoaDonItem,tv_tienHoaDonItem;
    public  ThongKeViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_idHoaDonItem = itemView.findViewById(R.id.tv_idHoaDonItem1);
        tv_statusHoaDonItem = itemView.findViewById(R.id.tv_statusHoaDonItem1);
        tv_userHoaDonItem = itemView.findViewById(R.id.tv_userHoaDonItem1);
        tv_phoneHoaDonItem = itemView.findViewById(R.id.tv_phoneHoaDonItem1);
        tv_addressHoaDonItem = itemView.findViewById(R.id.tv_addressHoaDonItem1);
        tv_tienHoaDonItem = itemView.findViewById(R.id.tv_tienHoaDonItem1);

        //     itemView.setOnClickListener(this);
    }
}

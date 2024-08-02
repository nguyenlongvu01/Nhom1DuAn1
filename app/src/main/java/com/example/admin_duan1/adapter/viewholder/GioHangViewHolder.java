package com.example.admin_duan1.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin_duan1.R;
import com.example.admin_duan1.interfaces.ItemClick;
//
public class GioHangViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView tv_nameGioHangItem, tv_moneyGioHangItem, tv_countItem;
    public ImageView img_anhGioHangItem;
    public ItemClick itemClick;


    public RelativeLayout re_layoutDeleteGioHang;
    public ConstraintLayout cons_layoutGioHang;


    public void setTv_nameGioHangItem(TextView tv_nameGioHangItem) {
        this.tv_nameGioHangItem = tv_nameGioHangItem;
    }

    public GioHangViewHolder( View itemView) {
        super(itemView);
        //
        img_anhGioHangItem = itemView.findViewById(R.id.img_anhGioHangItem);
        tv_nameGioHangItem = itemView.findViewById(R.id.tv_nameGioHangItem);
        tv_moneyGioHangItem = itemView.findViewById(R.id.tv_moneyGioHangItem);
        tv_countItem = itemView.findViewById(R.id.tv_countItem);

        re_layoutDeleteGioHang = itemView.findViewById(R.id.re_layoutDeleteGioHang);
        cons_layoutGioHang = itemView.findViewById(R.id.cons_layoutGioHang);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
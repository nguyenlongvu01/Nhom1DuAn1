package com.example.admin_duan1.adapter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin_duan1.R;
import com.example.admin_duan1.adapter.viewholder.GioHangViewHolder;
import com.example.admin_duan1.dto.OderDetail;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangViewHolder> {


    ArrayList<OderDetail> listOderDetails;
    Context context;

    public GioHangAdapter(ArrayList<OderDetail> listOderDetails, Context context) {
        this.listOderDetails = listOderDetails;
        this.context = context;
    }

    public void setListOderDetails(ArrayList<OderDetail> listOderDetails) {
        this.listOderDetails = listOderDetails;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GioHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_giohang, parent, false);
        return new GioHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GioHangViewHolder holder, int position) {

        OderDetail oderDetail = listOderDetails.get(position);

        Picasso.get().load(oderDetail.getImage()).into(holder.img_anhGioHangItem);


        holder.tv_nameGioHangItem.setText(oderDetail.getProductName());


        Locale locale = new Locale("vi", "VN");
        NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
        int price = (Integer.parseInt(oderDetail.getPrice())) * (Integer.parseInt(oderDetail.getQuanTity()));
        holder.tv_moneyGioHangItem.setText(nf.format(price));

        holder.tv_countItem.setText(oderDetail.getQuanTity());


    }

    @Override
    public int getItemCount() {
        if (listOderDetails != null) return listOderDetails.size();
        return 0;
    }



    //Lấy tên;
    public OderDetail getItem(int id) {
        return listOderDetails.get(id);
    }


    //Xoá theo id;
    public void xoa(int id) {
        listOderDetails.remove(id);
        notifyItemRemoved(id);
        notifyDataSetChanged();
    }

    //Hoàn lại;
    public void hoantac(OderDetail oderDetail, int id) {
        listOderDetails.add(id, oderDetail);
        notifyItemInserted(id);
        notifyDataSetChanged();
    }
}

package com.example.admin_duan1.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin_duan1.common.Common;
import com.example.admin_duan1.R;
import com.example.admin_duan1.adapter.viewholder.StatusViewHolder;
import com.example.admin_duan1.dto.Request;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HoaDonActivity extends AppCompatActivity {


    private Toolbar tool_barHoaDon;
    private RecyclerView re_ViewListHoaDon;
    private TextView tv_TbTitle;
    private BottomNavigationView bottom_NaView;
    //
    FirebaseRecyclerOptions options;
    FirebaseRecyclerAdapter<Request, StatusViewHolder> adapter;
    private DatabaseReference table_GioHangDbr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoadon);


        re_ViewListHoaDon = findViewById(R.id.re_ViewListHoaDon);


        //
        tool_barHoaDon = findViewById(R.id.tool_barHoaDon);
        setSupportActionBar(tool_barHoaDon);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //
        tv_TbTitle = findViewById(R.id.tv_TbTitle);
        tv_TbTitle.setText("Hoá Đơn");
        //Lấy bảng Requests;
        table_GioHangDbr = FirebaseDatabase.getInstance().getReference("Requests");
        //
        bottom_NaView = findViewById(R.id.bottom_NaView);
        bottom_NaView.setSelectedItemId(R.id.menu_HoaDon);
        bottom_NaView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.menu_Home:
                        startActivity(new Intent(getApplicationContext(), CuaHangActivity.class));
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.menu_Menu:
                        startActivity(new Intent(getApplicationContext(), Category_Activity.class));
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.menu_HoaDon:
                        break;

                    case R.id.menu_CaNhan:
                        startActivity(new Intent(getApplicationContext(), CaNhanActivity.class));
                        overridePendingTransition(0, 0);
                        break;
                }
                return true;
            }
        });


        loadHoaDon();
    }

    private void loadHoaDon() {
        LinearLayoutManager manager = new LinearLayoutManager(HoaDonActivity.this, LinearLayoutManager.VERTICAL, false);
        re_ViewListHoaDon.setLayoutManager(manager);

        options = new FirebaseRecyclerOptions.Builder<Request>().setQuery(table_GioHangDbr, Request.class).build();
        adapter = new FirebaseRecyclerAdapter<Request, StatusViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull StatusViewHolder holder, int position, @NonNull Request model) {
                holder.tv_idHoaDonItem.setText(adapter.getRef(position).getKey());
                holder.tv_statusHoaDonItem.setText(coverStatus(model.getStatus()));
                holder.tv_userHoaDonItem.setText(model.getUser());
                holder.tv_addressHoaDonItem.setText(model.getAddress());
                holder.tv_phoneHoaDonItem.setText(model.getPhone());
                holder.tv_tienHoaDonItem.setText(model.getTotal());

            }

            @NonNull
            @Override
            public StatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_hoadon, parent, false);
                return new StatusViewHolder(view);
            }
        };


        adapter.startListening();
        re_ViewListHoaDon.setAdapter(adapter);
    }

    private String coverStatus(String status) {
        if (status.equals("0")) {
            return "Đặt hàng";
        } else if (status.equals("1")) {

            return "Vận chuyển thành công";
        } else {
            return "Đã Hủy";
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getTitle().equals(Common.CHAP_NHAN)) {
            Chap_nhan(adapter.getRef(item.getOrder()).getKey(), adapter.getItem(item.getOrder()));
        } else if ((item.getTitle().equals(Common.HUY))) {
            Kh_Chap_nhan(adapter.getRef(item.getOrder()).getKey(), adapter.getItem(item.getOrder()));

        }
        return super.onContextItemSelected(item);
    }

    private void Kh_Chap_nhan(String key, Request item) {
        item.setStatus("2");
        table_GioHangDbr.child(key).setValue(item);
    }

    private void Chap_nhan(String key, Request item) {
        item.setStatus("1");
        table_GioHangDbr.child(key).setValue(item);
    }


}
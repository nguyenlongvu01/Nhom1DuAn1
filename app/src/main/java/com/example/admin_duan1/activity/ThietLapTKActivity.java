package com.example.admin_duan1.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.admin_duan1.R;
import com.example.admin_duan1.common.Common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class ThietLapTKActivity extends AppCompatActivity implements View.OnClickListener {


    private Toolbar tool_barTlTk;
    private TextView tv_TbTitleCn, tv_CnDoiAvatar, tv_CnDoiPass, tv_CnDangXuat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thiet_lap_tkactivity);
        tool_barTlTk = findViewById(R.id.tool_barTlTk);
        tv_TbTitleCn = findViewById(R.id.tv_TbTitleCn);
        tv_CnDoiAvatar = findViewById(R.id.tv_CnDoiAvatar);
        tv_CnDoiPass = findViewById(R.id.tv_CnDoiPass);
        tv_CnDangXuat = findViewById(R.id.tv_CnDangXuat);

        setSupportActionBar(tool_barTlTk);
        tv_TbTitleCn.setText("Thiết lập tài khoản");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tool_barTlTk.setNavigationIcon(R.drawable.ic_quaylai);
        tool_barTlTk.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        tv_CnDoiAvatar.setOnClickListener(this);
        tv_CnDangXuat.setOnClickListener(this);
        tv_CnDoiPass.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_CnDoiAvatar:

                break;
            case R.id.tv_CnDoiPass:
                DoiMk();
                break;
            case R.id.tv_CnDangXuat:
                DangXuat();
                break;
        }
    }


    //Đổi mật khẩu;
    private void DoiMk() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ThietLapTKActivity.this);
        builder.setTitle("Đổi mật khẩu");
        builder.setMessage("Xin nhập đủ thông tin !");

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_changepass, null);

        EditText edt_CnMkCu = view.findViewById(R.id.edt_CnMkCu);
        EditText edt_CnMkNew = view.findViewById(R.id.edt_CnMkNew);
        EditText edt_CnMkNew2 = view.findViewById(R.id.edt_CnMkNew2);

        builder.setView(view);

        builder.setPositiveButton("Đổi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String mkCu = edt_CnMkCu.getText().toString().trim();
                String mkNew = edt_CnMkNew.getText().toString().trim();
                String mkNew2 = edt_CnMkNew2.getText().toString().trim();


                //Kiểm tra mật khẩu hiện tại;
                if (mkCu.equals(Common.user.getPass())) {
                    //Kiểm tra mật khẩu mới và nhập lại mật khẩu;
                    if (mkNew.equals(mkNew2)) {
                        Map<String, Object> passUpdate = new HashMap<>();
                        passUpdate.put("pass", mkNew);

                        //Bắt đầu Cập nhật;
                        //Lấy bảng User;
                        DatabaseReference table_userDbr = FirebaseDatabase.getInstance().getReference("User");
                        table_userDbr.child(Common.user.getUser()).updateChildren(passUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                Toast.makeText(ThietLapTKActivity.this, "Đổi thành công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ThietLapTKActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(ThietLapTKActivity.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ThietLapTKActivity.this, "Mật khẩu hiện tại sai", Toast.LENGTH_SHORT).show();
                }

            }


        });

        builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });


        builder.show();


    }


    //Đăng xuất;
    private void DangXuat() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ThietLapTKActivity.this);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn muốn đăng xuất ?");
        builder.setNegativeButton("Đăng xuất", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(ThietLapTKActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Toast.makeText(ThietLapTKActivity.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setPositiveButton("Ở lại", null);
        builder.show();
    }
}
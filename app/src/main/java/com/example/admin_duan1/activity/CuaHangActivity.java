package com.example.admin_duan1.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin_duan1.R;
import com.example.admin_duan1.adapter.viewholder.CategoryViewHolder;
import com.example.admin_duan1.common.Common;
import com.example.admin_duan1.dto.Banner;
import com.example.admin_duan1.dto.Category;
import com.example.admin_duan1.interfaces.ItemClick;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.fpt.library.SliderLayout;
import com.fpt.library.animations.DescriptionAnimation;
import com.fpt.library.slidertypes.BaseSliderView;
import com.fpt.library.slidertypes.TextSliderView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.UUID;

public class CuaHangActivity extends AppCompatActivity {
    private RecyclerView re_Cate;
    private TextView tv_User, tv_wCome, tv_ThucDon, tv_searchView;
    private BottomNavigationView bottom_NaView;
    //
    FirebaseRecyclerOptions options;
    FirebaseRecyclerAdapter<Category, CategoryViewHolder> adapter;
    DatabaseReference cateDbr;

    FirebaseStorage storage;
    StorageReference storageReference;
    Category category;
    TextInputEditText edt_name;
    Button btnthemanh, btnUpload;
    Uri saveUri;
    private final int PICK_IMAGE_REQUEST = 71;
    //
    DatabaseReference bannerDbr;
    SliderLayout slider_Layout;
    HashMap<String, String> img_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cua_hang);
        re_Cate = findViewById(R.id.re_Cate);

        // firebase storage
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        //Chọn item;
        bottom_NaView = findViewById(R.id.bottom_NaView);
        bottom_NaView.setSelectedItemId(R.id.menu_Home);
        bottom_NaView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.menu_Home:
                        break;

                    case R.id.menu_Menu:
                        startActivity(new Intent(getApplicationContext(), Category_Activity.class));
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.menu_HoaDon:
                        startActivity(new Intent(getApplicationContext(), HoaDonActivity.class));
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.menu_CaNhan:
                        startActivity(new Intent(getApplicationContext(), CaNhanActivity.class));
                        overridePendingTransition(0, 0);
                        break;
                }
                return true;
            }
        });

        //Tạo bảng Category;
        cateDbr = FirebaseDatabase.getInstance().getReference("Category");
        loadCate();

//        Lấy tên đăng nhập;
        tv_User = findViewById(R.id.tv_User);
        tv_User.setText(Common.user.getUser());

//        //Từng chữ chạy;
//        tv_wCome = findViewById(R.id.tv_wCome);
//        //  setText("Welcome");

        //Set fonts;
        tv_ThucDon = findViewById(R.id.tv_ThucDon);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/NABILA.TTF");
        tv_ThucDon.setTypeface(typeface);


        //Banner;
        setupBanner();
    }

    private void setupBanner() {
        slider_Layout = findViewById(R.id.slider_Layout);
        img_list = new HashMap<>();
        bannerDbr = FirebaseDatabase.getInstance().getReference("Banner");

        bannerDbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot a : snapshot.getChildren()) {
                    Banner banner = a.getValue(Banner.class);
                    img_list.put(banner.getName() + "_" + banner.getId(), banner.getImage());
                }
                for (String key : img_list.keySet()) {
                    String[] keyList = key.split("_");
                    String nameOf = keyList[0];
                    String idOf = keyList[1];


                    //Tạo slider;
                    TextSliderView textSliderView = new TextSliderView(CuaHangActivity.this);
                    textSliderView.description(nameOf).image(img_list.get(key)).setScaleType(BaseSliderView.ScaleType.Fit).setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                        }
                    });

                    //Thêm extra vào Bundle;
                    textSliderView.bundle(new Bundle());
                    textSliderView.getBundle().putString("FoodId", idOf);


                    slider_Layout.addSlider(textSliderView);
                    bannerDbr.removeEventListener(this);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        slider_Layout.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
        slider_Layout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        slider_Layout.setCustomAnimation(new DescriptionAnimation());
        slider_Layout.setDuration(3000);

    }

    @Override
    protected void onStop() {
        super.onStop();
        slider_Layout.stopAutoCycle();
    }


    private void loadCate() {
        GridLayoutManager manager = new GridLayoutManager(CuaHangActivity.this, 2);
        re_Cate.setLayoutManager(manager);

        options = new FirebaseRecyclerOptions.Builder<Category>().setQuery(cateDbr, Category.class).build();
        adapter = new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CategoryViewHolder holder, int position, @NonNull Category model) {
                holder.tv_CateItem.setText(model.getName());
                Picasso.get().load(model.getImage()).into(holder.img_CateItem);
                Category category = model;
                holder.setItemClick(new ItemClick() {
                    @Override
                    public void itemClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(CuaHangActivity.this, "" + category.getName(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CuaHangActivity.this, FoodActivity.class);
//                            Tạo key Category;
                        intent.putExtra("CategoryId", adapter.getRef(position).getKey());
                        startActivity(intent);

                    }
                });
            }

            @NonNull
            @Override
            public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_cate, parent, false);
                return new CategoryViewHolder(view);
            }
        };
        adapter.startListening();
        re_Cate.setAdapter(adapter);
    }

    //menu click  update and delete
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getTitle().equals(Common.UPDATE)) {
            showUpdateDialog(adapter.getRef(item.getOrder()).getKey(), adapter.getItem(item.getOrder()));
        } else if ((item.getTitle().equals(Common.DELETE))) {
            DeleteCatehory(adapter.getRef(item.getOrder()).getKey());

        }
        return super.onContextItemSelected(item);

    }

    // xóa đồ ăn
    private void DeleteCatehory(String key) {
        cateDbr.child(key).removeValue();
    }
// show dialog update
    private void showUpdateDialog(String key, Category item) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CuaHangActivity.this);
        alertDialog.setTitle("Cập nhật Category");
        alertDialog.setMessage("Xin mời nhập đủ !");
        LayoutInflater inflater = this.getLayoutInflater();
        View add_menu_layout = inflater.inflate(R.layout.dialog_them_menu, null);
        edt_name = add_menu_layout.findViewById(R.id.edt_tenmonan);
        btnthemanh = add_menu_layout.findViewById(R.id.btn_themanh);
        btnUpload = add_menu_layout.findViewById(R.id.btn_upload);
        edt_name.setText(item.getName());
        alertDialog.setView(add_menu_layout);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImage(item);
            }
        });
        btnthemanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                item.setName(edt_name.getText().toString());
                cateDbr.child(key).setValue(item);
            }
        });
        alertDialog.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    // update lai anh
    private void changeImage(Category item) {
        if (saveUri != null) {
            ProgressDialog mDialog = new ProgressDialog(CuaHangActivity.this);
            mDialog.setMessage("Uploading...");
            mDialog.show();
            String imageName = UUID.randomUUID().toString();
            StorageReference imageFolder = storageReference.child("image/" + imageName);
            imageFolder.putFile(saveUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mDialog.dismiss();
                    Toast.makeText(CuaHangActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                    imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            item.setImage(uri.toString());
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mDialog.dismiss();
                    Toast.makeText(CuaHangActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    mDialog.setMessage("Upload" + progress + "%");
                }
            });

        }
    }
// chon ảnh trong thư viện
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
// set text đã chọn ảnh
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && data.getData() != null && resultCode == RESULT_OK && data != null) {
            saveUri = data.getData();
            btnthemanh.setText("Image selected");
        }
    }
}


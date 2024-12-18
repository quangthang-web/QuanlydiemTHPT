package com.example.sinhvienapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sinhvienapp.dao.ChuyenNganhDao;
import com.example.sinhvienapp.dao.LopDao;
import com.example.sinhvienapp.dao.SinhVienDao;
import com.example.sinhvienapp.model.ChuyenNganh;
import com.example.sinhvienapp.model.Lop;
import com.example.sinhvienapp.model.SinhVien;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ThemSinhVienActivity extends AppCompatActivity {
    EditText edtTensv, edtMasv, edtemail, edtHinh;
    Spinner spMaLop,spMaNganh;
    Button btnThem, btnNhapLai, btnDanhSach, btnReview;
    SinhVienDao daoSach;
    LopDao lsDao;
    ChuyenNganhDao lsChuyennganhDao;
    LinearLayout linearLayout;
    CircleImageView imgAvata;
    ArrayList<Lop> lsList = new ArrayList<>();
    ArrayList<ChuyenNganh> lsListCN = new ArrayList<>();
    Animation animation;
    ImageView btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_sinh_vien);

        lsDao = new LopDao(ThemSinhVienActivity.this);
        lsChuyennganhDao = new ChuyenNganhDao(ThemSinhVienActivity.this);

        linearLayout = findViewById(R.id.linearLayout);
        edtMasv = findViewById(R.id.txtMaSV);
        edtTensv = findViewById(R.id.txtTenSV);
        edtHinh = findViewById(R.id.txtHinh);
        edtemail = findViewById(R.id.txtemail);

        spMaLop = findViewById(R.id.txtMalop);
        spMaNganh = findViewById(R.id.txtMaNganh);


        btnThem = findViewById(R.id.btntThem);
        btnNhapLai = findViewById(R.id.btnNhapLai);
        btnDanhSach = findViewById(R.id.btnDanhSach);
        btnReview = findViewById(R.id.btnReviewThem);
        imgAvata = findViewById(R.id.imgAvata);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> {
            onBackPressed();
        });
        daoSach = new SinhVienDao(ThemSinhVienActivity.this);
        animation = AnimationUtils.loadAnimation(this, R.anim.uptodowndiagonal);
        linearLayout.setAnimation(animation);
        btnNhapLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtMasv.setText("");
                edtTensv.setText("");
                edtemail.setText("");
            }
        });


        btnDanhSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThemSinhVienActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.ani_intent, R.anim.ani_intenexit);
            }
        });
        lsList = lsDao.getAll();
        ArrayAdapter adapter = new ArrayAdapter(ThemSinhVienActivity.this, android.R.layout.simple_spinner_item, lsList);

        lsListCN = lsChuyennganhDao.getAll();
        ArrayAdapter adapterNganh = new ArrayAdapter(ThemSinhVienActivity.this, android.R.layout.simple_spinner_item, lsListCN);
        spMaLop.setAdapter(adapter);

        spMaNganh.setAdapter(adapterNganh);
        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if(edtHinh.getText().toString().equalsIgnoreCase("")){
                  imgAvata.setImageResource(R.drawable.avatasinhvien);
              }else if(edtHinh.getText().toString()!=""){
                  int id_hinh = ((Activity)ThemSinhVienActivity.this).getResources().getIdentifier(edtHinh.getText().toString(), "drawable", ((Activity) ThemSinhVienActivity.this).getPackageName());
                  imgAvata.setImageResource(id_hinh);
                }
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                    String ma = edtMasv.getText().toString();
                    String ten = edtTensv.getText().toString();
                    String email = edtemail.getText().toString();
                    String hinh = edtHinh.getText().toString();
                    Lop ls = (Lop) spMaLop.getSelectedItem();
                    String maLop = ls.getMaLop();
                    ChuyenNganh chuyenNganh = (ChuyenNganh) spMaNganh.getSelectedItem();
                    String manganh = chuyenNganh.getMaChuyenNganh();
                    if (ma.equals("")) {
                        Toast.makeText(ThemSinhVienActivity.this, "Mã sinh viên không được để trống", Toast.LENGTH_LONG).show();
                    } else if (ten.equals("")) {
                        Toast.makeText(ThemSinhVienActivity.this, "Tên sinh viên không được để trống", Toast.LENGTH_LONG).show();
                    } else if (ten.matches((".*[0-9].*"))) {
                        Toast.makeText(ThemSinhVienActivity.this, "Tên chỉ được nhập chuỗi", Toast.LENGTH_LONG).show();
                    } else if (email.equals("")) {
                        Toast.makeText(ThemSinhVienActivity.this, "Email sinh viên không được để trống", Toast.LENGTH_LONG).show();
                    } else if (email.matches(pattern) == false) {
                        Toast.makeText(ThemSinhVienActivity.this, "Bạn nhập sai định dạng email", Toast.LENGTH_SHORT).show();
                    } else if (hinh.equals("")) {
                        edtHinh.setText("avatamacdinh");
                    } else {
                        SinhVien s = new SinhVien(ma, ten, email, hinh, maLop,manganh);
                        if (daoSach.insert(s)) {
                            Toast.makeText(ThemSinhVienActivity.this, "Them thanh cong", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ThemSinhVienActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(ThemSinhVienActivity.this, " Không được trùng mã sinh viên ", Toast.LENGTH_LONG).show();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(ThemSinhVienActivity.this, "Lỗi : " + e, Toast.LENGTH_LONG).show();
                }

            }
        });

    }


}

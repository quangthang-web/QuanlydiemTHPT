package com.example.sinhvienapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHeplper extends SQLiteOpenHelper {
    public DBHeplper(@Nullable Context context) {
        super(context, "QUANLYSINHVIENFPTDB.sqlite", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //tạo table lớp
        String sql = " CREATE TABLE LOP(maLop TEXT PRIMARY KEY, tenLop TEXT)";
        db.execSQL(sql);
        sql = " INSERT INTO LOP VALUES ('1','10A1')";
        db.execSQL(sql);
        sql = " INSERT INTO LOP VALUES ('2','11A1')";
        db.execSQL(sql);
        sql = " INSERT INTO LOP VALUES ('3','12A2')";
        db.execSQL(sql);

        //tạo table chuyên ngành
        sql = " CREATE TABLE CHUYENNGANH(maChuyenNganh TEXT PRIMARY KEY, tenChuyenNganh TEXT)";
        db.execSQL(sql);
        sql = " INSERT INTO CHUYENNGANH VALUES ('10','Khoi 10')";
        db.execSQL(sql);
        sql = " INSERT INTO CHUYENNGANH VALUES ('11','Khoi 11')";
        db.execSQL(sql);
        sql = " INSERT INTO CHUYENNGANH VALUES ('12','Khoi 12')";
        db.execSQL(sql);
        //tạo table môn học
        sql = " CREATE TABLE MONHOC(maMH TEXT PRIMARY KEY, tenmonhoc TEXT)";
        db.execSQL(sql);
        sql = " INSERT INTO MONHOC VALUES ('T','Toan')";
        db.execSQL(sql);
        sql = " INSERT INTO MONHOC VALUES ('V','Van')";
        db.execSQL(sql);
        sql = " INSERT INTO MONHOC VALUES ('A','Anh')";
        db.execSQL(sql);
        //tao table calendar event
        sql = "CREATE TABLE EventCalendar(Date TEXT, Event TEXT)";
        db.execSQL(sql);
        //tạo table sinh viên
        sql = " CREATE TABLE SINHVIEN(maSv TEXT PRIMARY KEY, tenSV TEXT ," +
                " email TEXT ,hinh TEXT, maLop TEXT REFERENCES LOP(maLop), maChuyenNganh TEXT REFERENCES CHUYENNGANH(maChuyenNganh)) ";
        db.execSQL(sql);
        sql = " INSERT INTO SINHVIEN VALUES ('2013483','Doan Van Duc','doanvandusc@gmail.com','avatasinhvien','3','12')";
        db.execSQL(sql);
        sql = " INSERT INTO SINHVIEN VALUES ('2098765432','Nguyen Van A','nguyenvanb@gmail.com','avatasinhvien','2','11')";
        db.execSQL(sql);
        sql = " INSERT INTO SINHVIEN VALUES ('234567890','Nguyen Thi C','c@gmail.com','avatamacdinh','1','10')";
        db.execSQL(sql);
        //tạo table taikhoan
        sql = "CREATE TABLE taiKhoan(tenTaiKhoan text primary key, matKhau text)";
        db.execSQL(sql);
        //tai khoan mac dinh admin
        sql = "INSERT INTO taiKhoan VALUES('admin','admin')";
        db.execSQL(sql);


        //tạo table Diem
        sql = "CREATE TABLE DIEM(maSv TEXT REFERENCES SINHVIEN(maSv),maMH TEXT REFERENCES MONHOC(maMH)," +
                "diem REAL, PRIMARY KEY (maSv, maMH))";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

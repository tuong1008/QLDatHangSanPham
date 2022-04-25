package com.example.qldathangsanpham;

import androidx.lifecycle.ViewModelProvider;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qldathangsanpham.data.EmployeeDataSoure;
import com.example.qldathangsanpham.data.EmployeeRepository;
import com.example.qldathangsanpham.data.model.EmployeeModel;
import com.example.qldathangsanpham.databinding.ActivityProfileBinding;
import com.example.qldathangsanpham.databinding.ProfileFragmentBinding;
import com.example.qldathangsanpham.ui.login.EmployeeViewModel;

public class ProfileFragment extends Fragment {

    private EmployeeViewModel mViewModel;
    private ProfileFragmentBinding binding;

    @Override
    public void onStart() {

        super.onStart();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        binding=ProfileFragmentBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding=ProfileFragmentBinding.inflate(inflater, container, false);
        mViewModel=new EmployeeViewModel(new EmployeeRepository(new EmployeeDataSoure(getContext())));
        final TextView textView_hoTen=binding.textViewHoTen;
        final TextView textView_TaiKhoan=binding.textViewtaiKhoan;
        final ImageView imageView_employee=binding.imageViewEmployee;
        int id= (int) this.getArguments().getInt("id");

        String userName= (String) this.getArguments().getString("username");
        EmployeeModel employeeModel=mViewModel.findByTaiKhoanID(4);
        textView_hoTen.setText(employeeModel.getHoVaTen());
        textView_TaiKhoan.setText(userName);
        Bitmap bitmap= BitmapFactory.decodeByteArray(employeeModel.getImage(),0,employeeModel.getImage().length);
        imageView_employee.setImageBitmap(bitmap);
        View view =binding.getRoot();
        return view;
    }



}
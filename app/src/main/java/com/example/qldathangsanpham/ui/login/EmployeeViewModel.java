package com.example.qldathangsanpham.ui.login;

import androidx.lifecycle.ViewModel;

import com.example.qldathangsanpham.data.EmployeeRepository;
import com.example.qldathangsanpham.data.model.EmployeeModel;

public class EmployeeViewModel extends ViewModel {


    private EmployeeRepository employeeRepository;

    public EmployeeViewModel(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public EmployeeModel findByTaiKhoanID(int id) {
        EmployeeModel employeeModel = employeeRepository.findbyTaiKhoanID(id);

        return employeeModel;
    }

}

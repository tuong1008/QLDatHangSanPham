package com.example.qldathangsanpham.data;

import com.example.qldathangsanpham.data.model.EmployeeModel;

public class EmployeeRepository {

    private EmployeeDataSoure employeeDatasoure;
    private static volatile EmployeeRepository employeeRepository;

    public EmployeeRepository(EmployeeDataSoure employeeDatasoure) {
        this.employeeDatasoure = employeeDatasoure;
    }

    public static EmployeeRepository getInstance(EmployeeDataSoure employeeDatasoure) {
        if (employeeRepository == null) {
            return new EmployeeRepository(employeeDatasoure);
        } else {
            return employeeRepository;
        }
    }

    EmployeeModel employeeModelListt;

    public EmployeeModel findbyTaiKhoanID(int id) {
        return employeeDatasoure.findbyTaiKhoanID(id);
    }


}

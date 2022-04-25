package com.example.qldathangsanpham.ui.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qldathangsanpham.R;
import com.example.qldathangsanpham.data.RegisterRepository;
import com.example.qldathangsanpham.data.Result;
import com.example.qldathangsanpham.data.model.RegisterUser;

import java.util.regex.Pattern;

@SuppressLint("StaticFieldLeak")
public class RegisterViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private MutableLiveData<RegisterFromState> registerFromState=new MutableLiveData<>();
    private MutableLiveData<RegisterResult> registerResult=new MutableLiveData<>();
    private RegisterRepository registerRepository;

    public RegisterViewModel(RegisterRepository registerRepository) {
        this.registerRepository = registerRepository;
    }

    LiveData<RegisterFromState> getRegisterFromState(){
        return registerFromState;
    }
    LiveData<RegisterResult> getRegisterResult(){
        return registerResult;
    }

    public void register(String username,String password,byte[] avatar,String hovaten)
    {
        Result<RegisterUser> registerUser=registerRepository.register(username,password,avatar,hovaten);

        if(registerUser instanceof Result.Success)
        {
                RegisterUser data= ((Result.Success<RegisterUser>) registerUser).getData();
                registerResult.setValue(new RegisterResult(data));
        }
        else{
            registerResult.setValue(new RegisterResult(R.string.login_failed));
        }
    }
    public void registerDataChanged(String username,String password,byte[] avatar,String hovaten)
    {
            if(!isValidUsername(username)){
                registerFromState.setValue(new RegisterFromState(R.string.invalid_username,null,null,null));
            }
            else if(!isValidPassword(password))
            {
                registerFromState.setValue(new RegisterFromState(null,R.string.invalid_password,null,null));

            }
            else if(hovaten.isEmpty())
            {
                registerFromState.setValue(new RegisterFromState(null,null,R.string.invalid_fullname,null));
            }
    }
    public boolean isValidUsername(String username)
    {
        if(username==null)
        {
            return false;
        }
        if(username.contains("@"))
        {
           return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        }
        else{
            return false;
        }
    }
    public boolean isValidPassword(String password)
    {
        if(password==null)
        {
            return false;
        }
        else{
            return password.length()>8;
        }
    }





}
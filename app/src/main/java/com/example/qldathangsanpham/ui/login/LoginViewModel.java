package com.example.qldathangsanpham.ui.login;

import android.content.Context;
import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qldathangsanpham.R;
import com.example.qldathangsanpham.data.LoginRepository;
import com.example.qldathangsanpham.data.Result;
import com.example.qldathangsanpham.data.model.LoggedInUser;

public class LoginViewModel extends ViewModel {

    private Context context;
    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;
    private MutableLiveData<ChangePasswordFromState> changePasswordFromState = new MutableLiveData<>();
    private MutableLiveData<ChangePasswordResult> changePasswordResult = new MutableLiveData<>();

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public LoginViewModel(MutableLiveData<ChangePasswordResult> changePasswordResult) {
        this.changePasswordResult = changePasswordResult;
    }

    public LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    public LiveData<LoginResult> getLoginResult() {

        return loginResult;
    }

    public LiveData<ChangePasswordFromState> getChangePasswordFromState() {
        return changePasswordFromState;
    }

    public LiveData<ChangePasswordResult> getChangePasswordResult() {
        return changePasswordResult;
    }

    public boolean isLogged() {
        return loginRepository.isLoggedIn();

    }

    public void logout() {

        loginRepository.logout();
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = loginRepository.login(username, password);
        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName(), data.getUserId())));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    public void changePassword(String username, String password, String newPassword) {
        Boolean isChanged = loginRepository.changePassword(username, password, newPassword);
        if (isChanged == true) {
            changePasswordResult.setValue(new ChangePasswordResult("Password changed"));
        } else {
            changePasswordResult.setValue(new ChangePasswordResult(R.string.change_password_failed));
        }

    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    public void changePasswordDataChanged(String password, String newPassword) {
        if (!isPasswordValid(password)) {
            changePasswordFromState.setValue(new ChangePasswordFromState(R.string.invalid_password, null));
        } else if (!isPasswordValid(newPassword)) {
            changePasswordFromState.setValue(new ChangePasswordFromState(null, R.string.invalid_password));
        } else {
            changePasswordFromState.setValue(new ChangePasswordFromState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return false;
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
package com.example.qldathangsanpham.ui.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.qldathangsanpham.databinding.SecurityFragmentBinding;
import com.example.qldathangsanpham.ui.login.ChangePasswordFromState;
import com.example.qldathangsanpham.ui.login.ChangePasswordResult;
import com.example.qldathangsanpham.ui.login.LoginActivity;
import com.example.qldathangsanpham.ui.login.LoginViewModel;
import com.example.qldathangsanpham.ui.login.LoginViewModelFactory;
import com.example.qldathangsanpham.ui.login.SaveSharedPreference;

public class SecurityFragment extends Fragment {

    private LoginViewModel loginViewModel;
    private SecurityFragmentBinding binding;

    public SecurityFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = SecurityFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory(getContext())).get(LoginViewModel.class);
        final EditText editTextUserName = binding.editTextUsernameSecurity;
        final EditText editTextPassword = binding.editTextPasswordSecurity;
        final EditText editTextPasswordNew = binding.editTextPasswordNewSecurity;
        final Button buttonChangePassword = binding.buttonChangePassword;
        Bundle bundle = getArguments();
        String userName = bundle.getString("username");
        editTextUserName.setText(userName);

        loginViewModel.getChangePasswordFromState().observe(getActivity(), new Observer<ChangePasswordFromState>() {
            @Override
            public void onChanged(ChangePasswordFromState changePasswordFromState) {
                if (changePasswordFromState == null) {
                    return;
                }
                if (changePasswordFromState.getPasswordError() != null) {
                    editTextPassword.setError(getString(changePasswordFromState.getPasswordError()));
                }
                if (changePasswordFromState.getNewPasswordError() != null) {
                    editTextPasswordNew.setError(getString(changePasswordFromState.getNewPasswordError()));
                }
            }
        });
        loginViewModel.getChangePasswordResult().observe(getActivity(), new Observer<ChangePasswordResult>() {
            @Override
            public void onChanged(ChangePasswordResult changePasswordResult) {
                if (changePasswordResult == null) {
                    return;
                }
                if (changePasswordResult.getError() != null) {
                    showLoginFailed(changePasswordResult.getError());
                }
                if (changePasswordResult.getSussess() != null) {
                    updateUI(changePasswordResult.getSussess());
                    SaveSharedPreference.logout(getActivity());
                    loginViewModel.logout();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(intent);
                    getActivity().finish();

                }
            }
        });
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                loginViewModel.changePasswordDataChanged(editTextPassword.getText().toString()
                        , editTextPasswordNew.getText().toString()
                );
            }
        };

        editTextPasswordNew.addTextChangedListener(textWatcher);
        editTextPassword.addTextChangedListener(textWatcher);
        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginViewModel.changePassword(editTextUserName.getText().toString(),
                        editTextPassword.getText().toString(), editTextPasswordNew.getText().toString());
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //   loginViewModel=new ViewModelProvider(getActivity(), new LoginViewModelFactory(getContext())).get(LoginViewModel.class)
        // TODO: Use the ViewModel
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private void updateUI(String string) {
        Toast.makeText(getContext(), string, Toast.LENGTH_SHORT).show();
    }

}
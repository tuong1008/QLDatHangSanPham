package com.example.qldathangsanpham.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qldathangsanpham.AngDoDatabaseHelper;
import com.example.qldathangsanpham.FullscreenActivity;
import com.example.qldathangsanpham.R;
import com.example.qldathangsanpham.ui.login.LoginViewModel;
import com.example.qldathangsanpham.ui.login.LoginViewModelFactory;
import com.example.qldathangsanpham.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        boolean finish = getIntent().getBooleanExtra("logout", false);
        //AUTO LOGIN
        String userName=SaveSharedPreference.getUserName(LoginActivity.this);
        Integer id=SaveSharedPreference.getId(LoginActivity.this);
        if(!userName.equals("")&&id!=-1){
            Intent intent=new Intent(LoginActivity.this, FullscreenActivity.class);
            intent.putExtra("username",userName);
            intent.putExtra("id",id);
            finish();
            startActivity(intent);
        }
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory(this))
                .get(LoginViewModel.class);


        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;
        final ProgressBar loadingProgressBar = binding.loading;
        final TextView forgotPassword=binding.forgotPassword;
        final TextView registerNơw=binding.registerNow;


        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());

                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                    // set SharedPreferenece
                    SaveSharedPreference.setUserName(LoginActivity.this,loginViewModel.getLoginResult().getValue().getSuccess().getDisplayName(),
                            Integer.valueOf(loginViewModel.getLoginResult().getValue().getSuccess().getId()));

                    //Show home
                    Intent intent=new Intent(LoginActivity.this, FullscreenActivity.class);
                    intent.putExtra("username",loginViewModel.getLoginResult().getValue().getSuccess().getDisplayName());
                    intent.putExtra("id",Integer.valueOf(loginViewModel.getLoginResult().getValue().getSuccess().getId()));
                    finish();
                    startActivity(intent);
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                //finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        registerNơw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
              //  finish(); //no history
               // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
               startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        /*if(loginViewModel.isLogged())
        {
            Intent intent=new Intent(LoginActivity.this, FullscreenActivity.class);
            intent.putExtra("username",loginViewModel.getLoginResult().getValue().getSuccess().getDisplayName());
            intent.putExtra("id",loginViewModel.getLoginResult().getValue().getSuccess().getId());
            startActivity(intent);
        }*/
        super.onStart();
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
package com.example.qldathangsanpham.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.qldathangsanpham.R;
import com.example.qldathangsanpham.databinding.RegisterFragmentBinding;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class RegisterActivity extends AppCompatActivity {

    private RegisterViewModel registerViewModel;
    private RegisterFragmentBinding registerActivityBinding;


    public static RegisterActivity newInstance() {
        return new RegisterActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        registerActivityBinding = RegisterFragmentBinding.inflate(getLayoutInflater());
        setContentView(registerActivityBinding.getRoot());

        registerViewModel = new ViewModelProvider(this, new RegisterViewModelFactory(this)).get(RegisterViewModel.class);

        final EditText editTextFullName = registerActivityBinding.editTextFullname;
        final EditText editTextPassword = registerActivityBinding.editTextPassword;
        final EditText editTextUserName = registerActivityBinding.editTextUsername;
        final ImageView imageView = registerActivityBinding.imageView;
        final Button selectImageButton = registerActivityBinding.selectImageButton;
        final Button registerButton = registerActivityBinding.registerButton;

        registerViewModel.getRegisterFromState().observe(this, new Observer<RegisterFromState>() {
            @Override
            public void onChanged(RegisterFromState registerFromState) {
                if (registerFromState == null) {
                    return;
                }
                if (registerFromState.getUsernameError() != null) {
                    editTextUserName.setError(getString(registerFromState.getUsernameError()));
                }
                if (registerFromState.getPasswordError() != null) {
                    editTextPassword.setError(getString(registerFromState.getPasswordError()));

                }
                if (registerFromState.getHovatenError() != null) {
                    editTextFullName.setError(getString(registerFromState.getHovatenError()));
                }
            }
        });
        registerViewModel.getRegisterResult().observe(this, new Observer<RegisterResult>() {
            @Override
            public void onChanged(RegisterResult registerResult) {
                if (registerResult == null) {
                    return;
                }
                if (registerResult.getSuccess() != null) {
                    updateUiWithUser(registerResult);
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                if (registerResult.getError() != null) {
                    showRegisterFailed(registerResult.getError());
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

                /*Bitmap bitmap=((BitmapDrawable)imageView.getDrawable()).getBitmap();
                ByteArrayOutputStream stream=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                byte[] byteArr=stream.toByteArray();*/
                registerViewModel.registerDataChanged(editTextUserName.getText().toString(),
                        editTextPassword.getText().toString(), new byte[]{}, editTextFullName.getText().toString());
            }
        };

        editTextUserName.addTextChangedListener(textWatcher);
        editTextFullName.addTextChangedListener(textWatcher);
        editTextPassword.addTextChangedListener(textWatcher);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            //get data
                            Intent intent = result.getData();
                            final Uri uri = intent.getData();
                            try {
                                final InputStream imageStream = getContentResolver().openInputStream(uri);
                                final Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                                imageView.setImageBitmap(bitmap);
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                byte[] byteArr = stream.toByteArray();
                                registerViewModel.registerDataChanged(editTextUserName.getText().toString(),
                                        editTextPassword.getText().toString(), byteArr, editTextFullName.getText().toString());
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                                Toast.makeText(RegisterActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();

                            }

                        } else {
                            Toast.makeText(RegisterActivity.this, "You haven't picked Image", Toast.LENGTH_LONG).show();
                        }

                    }
                });
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                String chooserTitle = "Select Picture";

                Intent intentChooser = Intent.createChooser(intent, chooserTitle);

                activityResultLauncher.launch(intentChooser);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (registerViewModel.getRegisterFromState().getValue() != null && registerViewModel.getRegisterFromState().getValue().isDataValid()) {
                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArr = stream.toByteArray();
                    registerViewModel.register(editTextUserName.getText().toString()
                            , editTextPassword.getText().toString(),
                            byteArr,
                            editTextFullName.getText().toString());
                } else {
                    showRegisterFailed(R.string.register_failed);
                }
            }
        });

    }

    private void updateUiWithUser(RegisterResult model) {
        String welcome = getString(R.string.welcome) + model.getSuccess().getEmail();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showRegisterFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
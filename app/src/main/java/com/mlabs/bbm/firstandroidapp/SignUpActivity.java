package com.mlabs.bbm.firstandroidapp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends android.app.Activity {
    TextView member, show1, show2;
    LoginDatabaseAdapter LoginDatabaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        LoginDatabaseAdapter=new LoginDatabaseAdapter(this);
        LoginDatabaseAdapter=LoginDatabaseAdapter.open();

        final EditText FirstName= (EditText) findViewById(R.id.txt_FName);
        final EditText LastName= (EditText) findViewById(R.id.txt_LName);
        final EditText Username= (EditText) findViewById(R.id.txt_UName);
        final EditText NewEmail= (EditText) findViewById(R.id.txt_Email);
        final EditText NewPassword= (EditText) findViewById(R.id.txt_Password);
        final EditText ConfirmPassword= (EditText) findViewById(R.id.txt_CPassword);

        show1 = (TextView) findViewById(R.id.showCPW);
        show2 = (TextView) findViewById(R.id.textView2);
        member = (TextView) findViewById(R.id.link_member);

        member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class );
                startActivity(intent);
            }
        });

        show1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int cursor= NewPassword.getSelectionStart();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        NewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        NewPassword.setSelection(cursor);
                        break;

                    case MotionEvent.ACTION_UP:
                        NewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        NewPassword.setSelection(cursor);
                        break;
                }
                return true;
            }
        });

        show2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int cursor= ConfirmPassword.getSelectionStart();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        ConfirmPassword.setSelection(cursor);
                        break;

                    case MotionEvent.ACTION_UP:
                        ConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        ConfirmPassword.setSelection(cursor);
                        break;
                }
                return true;
            }
        });
        final Button CreateAcct = (Button) findViewById(R.id.btn_CreateAcct);

        assert CreateAcct != null;
        CreateAcct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String frName = FirstName.getText().toString();
                String lsName = LastName.getText().toString();
                String usName = Username.getText().toString();
                String nEmail = NewEmail.getText().toString();
                String nPass = NewPassword.getText().toString();
                String conPass = ConfirmPassword.getText().toString();

                String checkSUUname = LoginDatabaseAdapter.getSignUpUsername(usName);
                String checkSUEmail = LoginDatabaseAdapter.getSignUpEmail(nEmail);
                if(frName.equals("")||lsName.equals("")||usName.equals("")||nEmail.equals("")||nPass.equals("")||conPass.equals(""))
                {Toast.makeText(getApplicationContext(), "No Empty Fields", Toast.LENGTH_SHORT).show();
                    return;}


                if (!validateEmail(nEmail))
                {NewEmail.setError("Invalid Email");
                    NewEmail.requestFocus();}

                if(!validatePassword(NewPassword.getText().toString()))
                {NewPassword.setError("Invalid Password");
                    NewPassword.requestFocus();}

                if (!NewPassword.getText().toString().equals(ConfirmPassword.getText().toString()))
                {Toast.makeText(SignUpActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();}


                if (!validateFirstName(frName))
                {   FirstName.setError("Invalid=");
                    FirstName.requestFocus();
                    FirstName.getSelectionStart();}

                else if (!validateLastName(lsName))
                {LastName.setError("Invalid");
                    LastName.requestFocus();
                    LastName.getSelectionStart();}

                else if(usName.equals(checkSUUname)){
                    Username.setError("Username already exists");
                    Username.requestFocus();
                }
                else if(nEmail.equals(checkSUEmail)){
                    NewEmail.setError("Email already exists");
                    NewEmail.requestFocus();
                }


                else
                {Toast.makeText(SignUpActivity.this, "Processing....", Toast.LENGTH_SHORT).show();
                    LoginDatabaseAdapter.insertEntry(frName,lsName,usName,nEmail,nPass);
                    Toast.makeText(getApplicationContext(), "Account Created", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this,LoginActivity.class );
                    startActivity(intent);finish();}

            }
        });



    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        LoginDatabaseAdapter.close();
    }
    private boolean validateEmail(String email) {
        String emailRegex;
        Pattern pattern;

        emailRegex = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";
        pattern = Pattern.compile(emailRegex);

        Matcher matcher = pattern.matcher(email);
        if (!matcher.find()) {
            return false;
        }
        return true;
    }
    private boolean validateFirstName(String fName) {
        String fNameRegex;
        Pattern pattern;

        fNameRegex = "^([A-Za-z]*)+$";
        pattern = Pattern.compile(fNameRegex);

        Matcher matcher = pattern.matcher(fName);
        if (!matcher.find()) {
            return false;
        }
        return true;
    }
    private boolean validateLastName(String fName) {
        String fNameRegex;
        Pattern pattern;

        fNameRegex = "^([A-Za-z]*)+$";
        pattern = Pattern.compile(fNameRegex);

        Matcher matcher = pattern.matcher(fName);
        if (!matcher.find()) {
            return false;
        }
        return true;
    }
    private boolean validatePassword(String password){
        if(password!=null && password.length()>7){
            return true;
        }
        else {
            return false;
        }
    }
}
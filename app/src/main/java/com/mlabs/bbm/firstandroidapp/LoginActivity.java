package com.mlabs.bbm.firstandroidapp;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;


import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import android.view.View.OnTouchListener;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    TextView show, SignUp;
    LoginDatabaseAdapter LoginDatabaseAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R .layout.activity_login);

        LoginDatabaseAdapter=new LoginDatabaseAdapter(this);
        LoginDatabaseAdapter=LoginDatabaseAdapter.open();

        final EditText email_ad = (EditText) findViewById(R.id.editText);
        final EditText password_tu = (EditText) findViewById(R.id.editText2);
        show = (TextView) findViewById(R.id.show);

        SignUp = (TextView) findViewById(R.id.SignUp);
        Button go = (Button) findViewById(R.id.buttonok);


        assert go != null;
        go.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String userName = email_ad.getText().toString();
                String password = password_tu.getText().toString();

                String storedPassword=LoginDatabaseAdapter.getSingleEntry(userName);
                String storedPassword2=LoginDatabaseAdapter.getSingleEntry2(userName);

                if(password.equals(storedPassword) || password.equals(storedPassword2))
                {
                    Toast.makeText(LoginActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                    Intent intentSignUP=new Intent(getApplicationContext(),Home.class);
                    startActivity(intentSignUP);
                    finish();
                }


                else if(password.equals(storedPassword2))
                {
                    Toast.makeText(LoginActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                    Intent intentSignUP=new Intent(getApplicationContext(),Home.class);
                    startActivity(intentSignUP);
                    email_ad.setText("");
                    password_tu.setText("");finish();
                }


                else
                {
                    Toast.makeText(LoginActivity.this, "Account does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        });

        show.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int cursor= password_tu.getSelectionStart();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        password_tu.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        password_tu.setSelection(cursor);
                        break;

                    case MotionEvent.ACTION_UP:
                        password_tu.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        password_tu.setSelection(cursor);
                        break;
                }
                return true;
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class );
                startActivity(intent);
                finish();
      


            }
        }); }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoginDatabaseAdapter.close();
    }
}

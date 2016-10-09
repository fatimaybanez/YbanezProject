package com.mlabs.bbm.firstandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;



public class Home extends AppCompatActivity {
    float x1, y1 , x2, y2, a, b;
    String msg1 = "", msg2="";
    ImageView imageLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        final EditText t1 = (EditText) findViewById(R.id.editText3);
        final EditText t2 = (EditText) findViewById(R.id.editText4);
        final EditText t3 = (EditText) findViewById(R.id.editText5);
        final EditText t4 = (EditText) findViewById(R.id.editText6);
        final EditText t5 = (EditText) findViewById(R.id.editText7);
        t1.setKeyListener(null);
        t2.setKeyListener(null);
        t3.setKeyListener(null);
        t4.setKeyListener(null);
        t5.setKeyListener(null);

        imageLogo = (ImageView) findViewById(R.id.imageView);
        imageLogo.setOnTouchListener(new View.OnTouchListener() {
            float x,y,x1,y1;

            @Override
            public boolean onTouch(View view, MotionEvent e) {

                String actionX = "";
                String actionY = "";
                String quadrant = "";

                switch (e.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = e.getX();
                        y = e.getY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        float X = imageLogo.getRight()/2;
                        float Y = imageLogo.getBottom()/2;

                        x1=e.getX();
                        y1=e.getY();

                        actionX = "";
                        actionY = "";
                        quadrant = "";

                        if (x<x1){
                            actionX = "Swiped Right ";
                        }
                        if (x>x1){
                            actionX = "Swiped Left ";
                        }
                        if (y<y1){
                            actionY = "Swiped Down ";
                        }
                        if (y>y1)
                        {
                            actionY = "Swiped Up ";
                        }

                        if(x1>X && y1>Y){
                            quadrant = "Quadrant 4";
                        }
                        if(x1<X && y1>Y){
                            quadrant = "Quadrant 3";
                        }
                        if(x1<X && y1<Y){
                            quadrant = "Quadrant 2";
                        }
                        if(x1>X && y1<Y){
                            quadrant = "Quadrant 1";
                        }

                        t1.setText(x + " , " + y);
                        t2.setText(x1 + " , " + y1);
                        t3.setText(  (Math.abs(x1-x))+" , "+ (Math.abs(y1-y)) );

                        t5.setText(actionX + actionY);
                        t4.setText(quadrant);

                }
                return  false;
            }

        });
    }

}
package com.example.chand0048.demoapps;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import android.graphics.Color;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView randomResult;
    Button randomButton;
    Random randNum;

    List<Button> buttonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //Test Commit
        randomResult = findViewById(R.id.randomResult);
        randomButton = findViewById(R.id.randomBtn);
        randNum = new Random();

        buttonList = new ArrayList<Button>();

        buttonList.add((Button) findViewById(R.id.btn1));
        buttonList.add((Button) findViewById(R.id.btn2));
        buttonList.add((Button) findViewById(R.id.btn3));
        buttonList.add((Button) findViewById(R.id.btn4));

        for (Button btn: buttonList)
        {
            btn.setOnClickListener(this);
            btn.setBackgroundColor(Color.WHITE);
        }

        changeButtonColor();

        // Greet User
        Toast.makeText(getApplicationContext(), "Welcome to Demo Apps", Toast.LENGTH_SHORT).show();
    }

    public void getRandom(View view)
    {
        int num = randNum.nextInt(6);

        randomResult.setText("" + num);
    }

    public void changeButtonColor()
    {
        int randint = randNum.nextInt(4);
        buttonList.get(randint).setBackgroundColor(Color.RED);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.btn1:
                if (v.getBackground().equals(Color.RED))
                {
                    v.setBackgroundColor(Color.WHITE);
                    changeButtonColor();
                }
                break;

            case R.id.btn2:
                if (v.getBackground().equals(Color.RED))
                {
                    v.setBackgroundColor(Color.WHITE);
                    changeButtonColor();
                }
                break;

            case R.id.btn3:
                if (v.getBackground().equals(Color.RED))
                {
                    v.setBackgroundColor(Color.WHITE);
                    changeButtonColor();
                }
                break;

            case R.id.btn4:
                if (v.getBackground().equals(Color.RED))
                {
                    v.setBackgroundColor(Color.WHITE);
                    changeButtonColor();
                }
                break;
        }
    }
}

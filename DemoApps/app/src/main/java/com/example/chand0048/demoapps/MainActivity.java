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

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    TextView randomResult, scoreResult, passwordResult;
    Button randomButton, generatePasswordButton;
    Random randNum;
    int score;

    List<Button> buttonList;
    List<Integer> buttonColorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        randomResult = findViewById(R.id.randomResult);
        randomButton = findViewById(R.id.randomBtn);
        randNum = new Random();

        score = 0;
        scoreResult = findViewById(R.id.scoreResult);

        buttonList = new ArrayList<Button>();

        buttonList.add((Button) findViewById(R.id.btn1));
        buttonList.add((Button) findViewById(R.id.btn2));
        buttonList.add((Button) findViewById(R.id.btn3));
        buttonList.add((Button) findViewById(R.id.btn4));

        for (Button btn: buttonList)
        {
            btn.setBackgroundColor(Color.WHITE);
        }

        buttonColorList = new ArrayList<Integer>();

        buttonColorList.add(Color.WHITE);
        buttonColorList.add(Color.WHITE);
        buttonColorList.add(Color.WHITE);
        buttonColorList.add(Color.WHITE);


        passwordResult = findViewById(R.id.passwordResult);
        generatePasswordButton = findViewById(R.id.generatePasswordBtn);

        changeButtonColor();

        // Greet User
        Toast.makeText(getApplicationContext(), "Welcome to Demo Apps", Toast.LENGTH_SHORT).show();
    }

    public void getRandom(View view)
    {
        int num = randNum.nextInt(6);

        randomResult.setText("" + num);
    }

    public void generateRandomPassword(View view)
    {
        String password = "";
        boolean containsSpecial = false;

        while (containsSpecial == false)
        {
            password = "";

            for (int num = 0; num < 8; num++)
            {
                char c = (char) (randNum.nextInt(89) + 33);
                if (c >= 33 && c <= 47)
                {
                    containsSpecial = true;
                }
                password += c;
            }
        }

        passwordResult.setText(password);
    }

    public void changeButtonColor()
    {
        int randint = randNum.nextInt(4);
        buttonList.get(randint).setBackgroundColor(Color.RED);
        buttonColorList.set(randint, Color.RED);
        scoreResult.setText("" + score);
    }

    public void testBtn1(View view)
    {
        if (buttonColorList.get(0).equals(Color.RED))
        {
            buttonList.get(0).setBackgroundColor(Color.WHITE);
            buttonColorList.set(0, Color.WHITE);
            score++;
        }
        else
        {
            for (Button btn: buttonList)
            {
                btn.setBackgroundColor(Color.WHITE);
            }
            for (Integer color: buttonColorList)
            {
                color = Color.WHITE;
            }
            score = 0;
        }
        scoreResult.setText("" + score);
        changeButtonColor();
    }
    public void testBtn2(View view)
    {
        if (buttonColorList.get(1).equals(Color.RED))
        {
            buttonList.get(1).setBackgroundColor(Color.WHITE);
            buttonColorList.set(1, Color.WHITE);
            score++;
        }
        else
        {

            for (Button btn: buttonList)
            {
                btn.setBackgroundColor(Color.WHITE);
            }
            for (Integer color: buttonColorList)
            {
                color = Color.WHITE;
            }
            score = 0;
        }
        scoreResult.setText("" + score);
        changeButtonColor();
    }
    public void testBtn3(View view)
    {
        if (buttonColorList.get(2).equals(Color.RED))
        {
            buttonList.get(2).setBackgroundColor(Color.WHITE);
            buttonColorList.set(2, Color.WHITE);
            score++;
        }
        else
        {

            for (Button btn: buttonList)
            {
                btn.setBackgroundColor(Color.WHITE);
            }
            for (Integer color: buttonColorList)
            {
                color = Color.WHITE;
            }
            score = 0;
        }
        scoreResult.setText("" + score);
        changeButtonColor();
    }
    public void testBtn4(View view)
    {
        if (buttonColorList.get(3).equals(Color.RED))
        {
            buttonList.get(3).setBackgroundColor(Color.WHITE);
            buttonColorList.set(3, Color.WHITE);
            score++;
        }
        else
        {

            for (Button btn: buttonList)
            {
                btn.setBackgroundColor(Color.WHITE);
            }
            for (Integer color: buttonColorList)
            {
                color = Color.WHITE;
            }
            score = 0;
        }
        scoreResult.setText("" + score);
        changeButtonColor();
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
}

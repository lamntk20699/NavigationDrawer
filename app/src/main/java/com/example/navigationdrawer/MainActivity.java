package com.example.navigationdrawer;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.navigationdrawer.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    //Declare Calculator variable
    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    Button btnAdd, btnSub, btnMulti, btnDivide, btnResult, btnMinus, btnCE, btnC;
    ImageButton btnBack;

    TextView mathDisplay;
    TextView resultDisplay;

    String mathBuffer;
    String resultBuffer;
    String checkMath = "";

    Double buff1, buff2,result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Calculator Programming
        initTextView();

        // Converting Programming

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    // Calculator Fragment
    private void initTextView() {
        btn0 = findViewById(R.id.button_0);
        btn1 = findViewById(R.id.button_1);
        btn2 = findViewById(R.id.button_2);
        btn3 = findViewById(R.id.button_3);
        btn4 = findViewById(R.id.button_4);
        btn5 = findViewById(R.id.button_5);
        btn6 = findViewById(R.id.button_6);
        btn7 = findViewById(R.id.button_7);
        btn8 = findViewById(R.id.button_8);
        btn9 = findViewById(R.id.button_9);
        btnAdd = findViewById(R.id.button_add);
        btnSub = findViewById(R.id.button_sub);
        btnMulti = findViewById(R.id.button_multi);
        btnDivide = findViewById(R.id.button_divide);
        btnCE = findViewById(R.id.button_ce);
        btnC = findViewById(R.id.button_c);
        btnBack = findViewById(R.id.button_back);
        btnMinus = findViewById(R.id.button_minus);
        btnResult = findViewById(R.id.button_result);
        mathDisplay = findViewById(R.id.math);
        resultDisplay = findViewById(R.id.result);

        mathDisplay.setText("");
        resultDisplay.setText("0");
        mathBuffer = "";
        resultBuffer = "0";
    }

    private void setMathSpace(String givenValue) {
        mathBuffer = mathDisplay.getText().toString();
        mathBuffer = mathBuffer + givenValue;
        mathDisplay.setText(mathBuffer);
    }

    private void  setResultSpace(String givenValue) {
        /*Enable Button*/
        if (checkMath.equals("=")) {
            btnAdd.setEnabled(true);
            btnSub.setEnabled(true);
            btnMulti.setEnabled(true);
            btnMinus.setEnabled(true);
            btnDivide.setEnabled(true);
            btnResult.setEnabled(true);
            mathDisplay.setText("");
            resultDisplay.setText("0");
            resultDisplay.setTextSize(60);
            checkMath = "";
        }

        resultBuffer = resultDisplay.getText().toString();
        if (!resultBuffer.equals("0")) {
            resultBuffer = resultBuffer + givenValue;
        } else {
            resultBuffer = givenValue;
        }
        resultDisplay.setText(resultBuffer);
    }

    public void minusOnClick(View view) {
        resultBuffer = resultDisplay.getText().toString();
        resultDisplay.setText("-" + resultBuffer);
    }

    public void resultOnClick(View view) {
        resultBuffer = resultDisplay.getText().toString();
        setMathSpace(resultBuffer);
        setMathSpace(" =");
        if (resultBuffer.equals("0")) {
            if (checkMath.equals("/")) {
                resultDisplay.setTextSize(28);
                resultDisplay.setText(R.string.divide_by_zero);
                /*Enable button*/
                btnAdd.setEnabled(false);
                btnSub.setEnabled(false);
                btnMulti.setEnabled(false);
                btnMinus.setEnabled(false);
                btnDivide.setEnabled(false);
                btnResult.setEnabled(false);
            }
            else {
                switch (checkMath) {
                    case "+": case "-":
                        result = buff1;
                        break;
                    case "*":
                        result = 0.0;
                        break;
                    default:
                        //Do nothing
                }
                resultDisplay.setText(result+"");
            }
        }
        else {
            buff2 = Double.parseDouble(resultBuffer+"");
            switch (checkMath) {
                case "+" :
                    result = buff1 + buff2;
                    break;
                case "-" :
                    result = buff1 - buff2;
                    break;
                case "*" :
                    result = buff1 * buff2;
                    break;
                case "/" :
                    result = buff1 / buff2;
                    break;
                default:
                    result = buff1;
                    break;
            }
            resultDisplay.setText(result+"");
        }
        checkMath = "=";
    }

    public void clearOnClick(View view) {
        mathDisplay.setText("");
        resultDisplay.setText("0");
    }

    public void backOnClick(View view) {
        resultBuffer = resultDisplay.getText().toString();
        int temp = resultBuffer.length() - 1;
        resultBuffer = resultBuffer.substring(0, temp);
        if ( !resultBuffer.isEmpty() ) {
            resultDisplay.setText(resultBuffer);
        }
        else {
            resultDisplay.setText("0");
        }
    }

    public void resetOnClick(View view) {
        resultDisplay.setText("0");
    }

    public void addOnClick(View view) {
        resultBuffer = resultDisplay.getText().toString();
        mathDisplay.setText("");
        setMathSpace(resultBuffer);
        setMathSpace(" + ");
        buff1 = Double.parseDouble(resultBuffer+"");
        resultDisplay.setText("0");
        checkMath = "+";
        mathBuffer = "";
        resultBuffer = "0";
    }

    public void subOnClick(View view) {
        resultBuffer = resultDisplay.getText().toString();
        mathDisplay.setText("");
        setMathSpace(resultBuffer);
        setMathSpace(" - ");
        buff1 = Double.parseDouble(resultBuffer+"");
        resultDisplay.setText("0");
        checkMath = "-";
        mathBuffer = "";
        resultBuffer = "0";
    }

    public void multiOnClick(View view) {
        resultBuffer = resultDisplay.getText().toString();
        mathDisplay.setText("");
        setMathSpace(resultBuffer);
        setMathSpace(" * ");
        buff1 = Double.parseDouble(resultBuffer+"");
        resultDisplay.setText("0");
        checkMath = "*";
        mathBuffer = "";
        resultBuffer = "0";
    }

    public void divideOnClick(View view) {
        resultBuffer = resultDisplay.getText().toString();
        mathDisplay.setText("");
        setMathSpace(resultBuffer);
        setMathSpace(" / ");
        buff1 = Double.parseDouble(resultBuffer+"");
        resultDisplay.setText("0");
        checkMath = "/";
        mathBuffer = "";
        resultBuffer = "0";
    }

    public void zeroOnClick(View view) {
        setResultSpace("0");
    }

    public void oneOnClick(View view) {
        setResultSpace("1");
    }

    public void twoOnClick(View view) {
        setResultSpace("2");
    }

    public void threeOnClick(View view) {
        setResultSpace("3");
    }

    public void fourOnClick(View view) {
        setResultSpace("4");
    }

    public void fiveOnClick(View view) {
        setResultSpace("5");
    }

    public void sixOnClick(View view) {
        setResultSpace("6");
    }

    public void sevenOnClick(View view) {
        setResultSpace("7");
    }

    public void eightOnClick(View view) {
        setResultSpace("8");
    }

    public void nineOnClick(View view) {
        setResultSpace("9");
    }
}
package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView displayText;
    private String currentNumber = "";
    private String operator = "";
    private double firstNumber = 0;
    private boolean isNewOperation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        displayText = findViewById(R.id.displayText);
        
        // Set click listeners for number buttons
        setNumberButtonClickListener(R.id.btn0, "0");
        setNumberButtonClickListener(R.id.btn1, "1");
        setNumberButtonClickListener(R.id.btn2, "2");
        setNumberButtonClickListener(R.id.btn3, "3");
        setNumberButtonClickListener(R.id.btn4, "4");
        setNumberButtonClickListener(R.id.btn5, "5");
        setNumberButtonClickListener(R.id.btn6, "6");
        setNumberButtonClickListener(R.id.btn7, "7");
        setNumberButtonClickListener(R.id.btn8, "8");
        setNumberButtonClickListener(R.id.btn9, "9");
        setNumberButtonClickListener(R.id.btnDot, ".");
        
        // Set click listeners for operator buttons
        setOperatorButtonClickListener(R.id.btnPlus, "+");
        setOperatorButtonClickListener(R.id.btnMinus, "-");
        setOperatorButtonClickListener(R.id.btnMultiply, "×");
        setOperatorButtonClickListener(R.id.btnDivide, "÷");
        
        // Equal button
        findViewById(R.id.btnEqual).setOnClickListener(v -> calculateResult());
        
        // Clear button
        findViewById(R.id.btnClear).setOnClickListener(v -> clearCalculator());
        
        // Add Backspace button
        findViewById(R.id.btnBackspace).setOnClickListener(v -> handleBackspace());
    }

    private void setNumberButtonClickListener(int buttonId, String number) {
        findViewById(buttonId).setOnClickListener(v -> {
            if (isNewOperation) {
                currentNumber = number;
                isNewOperation = false;
            } else {
                currentNumber += number;
            }
            displayText.setText(currentNumber);
        });
    }

    private void setOperatorButtonClickListener(int buttonId, String op) {
        findViewById(buttonId).setOnClickListener(v -> {
            if (!currentNumber.isEmpty()) {
                firstNumber = Double.parseDouble(currentNumber);
                operator = op;
                currentNumber = "";
            }
        });
    }

    private void calculateResult() {
        if (!currentNumber.isEmpty() && !operator.isEmpty()) {
            double secondNumber = Double.parseDouble(currentNumber);
            double result = 0;
            
            switch (operator) {
                case "+":
                    result = firstNumber + secondNumber;
                    break;
                case "-":
                    result = firstNumber - secondNumber;
                    break;
                case "×":
                    result = firstNumber * secondNumber;
                    break;
                case "÷":
                    if (secondNumber != 0) {
                        result = firstNumber / secondNumber;
                    } else {
                        displayText.setText("Error");
                        return;
                    }
                    break;
            }
            
            // Format result to remove unnecessary decimal places
            String resultStr = String.valueOf(result);
            if (resultStr.endsWith(".0")) {
                resultStr = resultStr.substring(0, resultStr.length() - 2);
            }
            
            displayText.setText(resultStr);
            currentNumber = resultStr;
            operator = "";
            isNewOperation = true;
        }
    }

    private void clearCalculator() {
        currentNumber = "";
        operator = "";
        firstNumber = 0;
        isNewOperation = true;
        displayText.setText("0");
    }

    private void handleBackspace() {
        if (!currentNumber.isEmpty()) {
            // Remove the last character from currentNumber
            currentNumber = currentNumber.substring(0, currentNumber.length() - 1);
            
            // If all digits are removed, show 0
            if (currentNumber.isEmpty()) {
                displayText.setText("0");
            } else {
                displayText.setText(currentNumber);
            }
        }
    }
}
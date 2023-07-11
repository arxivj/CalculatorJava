package com.arxivj.calculatorjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tvInput = null;
    private Boolean lastNumeric = false;
    private Boolean lastDot = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvInput = findViewById(R.id.tvInput);
    }

    public void onDigit(View view) {
        if (tvInput != null) {
            tvInput.append(((Button) view).getText());
            lastNumeric = true;
            lastDot = false;
        }
    }

    public void onClear(View view) {
        if (tvInput != null) {
            tvInput.setText("");
        }
    }

    public void onDecimalPoint(View view) {
        if (lastNumeric && !lastDot)
            if (tvInput != null) {
                tvInput.append(".");
                lastNumeric = false;
                lastDot = true;
            }
    }

    public void onOperator(View view) {
        if (tvInput != null && tvInput.getText() != null) {
            String currentInput = tvInput.getText().toString();
            if (lastNumeric && !isOperatorAdded(currentInput)) {
                tvInput.append(((Button) view).getText());
                lastNumeric = false;
                lastDot = false;
            }
        }
    }

    public Boolean isOperatorAdded(String value) {
        if (value.startsWith("-")) return false;
        else return (value.contains("/")
                || value.contains("*")
                || value.contains("+")
                || value.contains("-"));
    }

    public void onEqual(View view) {
        if (lastNumeric) {
            String tvValue = "";
            String prefix = "";
            if (tvInput != null) tvValue = tvInput.getText().toString();
            try {
                if (tvValue.startsWith("-")) {
                    prefix = "-";
                    tvValue = tvValue.substring(1);
                }
                if (tvValue.contains("-")) {
                    String[] splitValue = tvValue.split("-");
                    String one = splitValue[0];
                    String two = splitValue[1];
                    if (!prefix.isEmpty()) {
                        one = prefix + one;
                    }
                    if (tvInput != null) {
                        tvInput.setText(removeZeroAfterDot(String.valueOf(Double.parseDouble(one) - Double.parseDouble(two))));
                    }
                } else if (tvValue.contains("+")) {
                    String[] splitValue = tvValue.split("\\+");
                    String one = splitValue[0];
                    String two = splitValue[1];

                    if (!prefix.isEmpty()) {
                        one = prefix + one;
                    }
                    tvInput.setText(removeZeroAfterDot(String.valueOf(Double.parseDouble(one) + Double.parseDouble(two))));
                } else if (tvValue.contains("/")) {
                    String[] splitValue = tvValue.split("/");
                    String one = splitValue[0];
                    String two = splitValue[1];

                    if (!prefix.isEmpty()) {
                        one = prefix + one;
                    }
                    tvInput.setText(removeZeroAfterDot(String.valueOf(Double.parseDouble(one) / Double.parseDouble(two))));
                } else if (tvValue.contains("*")) {
                    String[] splitValue = tvValue.split("\\*");
                    String one = splitValue[0];
                    String two = splitValue[1];

                    if (!prefix.isEmpty()) {
                        one = prefix + one;
                    }
                    tvInput.setText(removeZeroAfterDot(String.valueOf(Double.parseDouble(one) * Double.parseDouble(two))));
                }
            } catch (ArithmeticException event) {
                event.printStackTrace();
            }
        }
    }


    private String removeZeroAfterDot(String result) {
        String value = result;
        if (result.contains(".0")) value = result.substring(0, result.length() - 2);
        return value;
    }

}

package com.example.dillydally;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CalculatorFragment extends Fragment {

    private TextView display;
    private String currentNumber = "";
    private String operator = "";
    private double result = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator, container, false);

        display = view.findViewById(R.id.calculator_display);

        int[] numberButtonIds = {R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4, R.id.button_5, R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9, R.id.button_dot};
        for (int id : numberButtonIds) {
            view.findViewById(id).setOnClickListener(this::onNumberClick);
        }

        int[] operatorButtonIds = {R.id.button_add, R.id.button_subtract, R.id.button_multiply, R.id.button_divide};
        for (int id : operatorButtonIds) {
            view.findViewById(id).setOnClickListener(this::onOperatorClick);
        }

        view.findViewById(R.id.button_equals).setOnClickListener(this::onEqualsClick);
        view.findViewById(R.id.button_clear).setOnClickListener(this::onClearClick);

        return view;
    }

    private void onNumberClick(View view) {
        Button button = (Button) view;
        currentNumber += button.getText().toString();
        display.setText(currentNumber);
    }

    private void onOperatorClick(View view) {
        Button button = (Button) view;
        if (!currentNumber.isEmpty()) {
            calculate();
            operator = button.getText().toString();
            currentNumber = "";
        }
    }

    private void onEqualsClick(View view) {
        if (!currentNumber.isEmpty() && !operator.isEmpty()) {
            calculate();
            operator = "";
        }
    }

    private void onClearClick(View view) {
        currentNumber = "";
        operator = "";
        result = 0;
        display.setText("0");
    }

    private void calculate() {
        if (currentNumber.isEmpty()) return;

        double newNumber = Double.parseDouble(currentNumber);
        switch (operator) {
            case "+":
                result += newNumber;
                break;
            case "-":
                result -= newNumber;
                break;
            case "*":
                result *= newNumber;
                break;
            case "/":
                result /= newNumber;
                break;
            default:
                result = newNumber;
                break;
        }
        display.setText(String.valueOf(result));
    }
}

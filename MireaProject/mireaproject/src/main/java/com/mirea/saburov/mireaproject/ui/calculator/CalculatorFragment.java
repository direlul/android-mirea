package com.mirea.saburov.mireaproject.ui.calculator;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mirea.saburov.mireaproject.R;

public class CalculatorFragment extends Fragment implements View.OnClickListener {

    public static CalculatorFragment newInstance() {
        return new CalculatorFragment();
    }

    TextView operation;
    TextView result;
    EditText firstNumber;
    EditText secondNumber;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculcator, container, false);

        Button buttonPlus = view.findViewById(R.id.plus);
        Button buttonMinus = view.findViewById(R.id.minus);
        Button buttonMultiPly = view.findViewById(R.id.multiply);
        Button buttonDivision = view.findViewById(R.id.division);
        Button buttonEnter = view.findViewById(R.id.enter);

        buttonPlus.setOnClickListener(this);
        buttonMinus.setOnClickListener(this);
        buttonMultiPly.setOnClickListener(this);
        buttonDivision.setOnClickListener(this);
        buttonEnter.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        operation = view.findViewById(R.id.operationView);
        result = view.findViewById(R.id.resultView);
        firstNumber = view.findViewById(R.id.firstNumberEdit);
        secondNumber = view.findViewById(R.id.secondNumberEdit);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.plus:
                operation.setText("+");
                break;
            case R.id.minus:
                operation.setText("-");
                break;
            case R.id.multiply:
                operation.setText("*");
                break;
            case R.id.division:
                operation.setText("/");
                break;
            case R.id.enter:
                calculate();
                break;
        }
    }

    public void calculate() {
        double first = parseDouble(firstNumber.getText().toString());
        double second = parseDouble(secondNumber.getText().toString());

        switch (operation.getText().toString()) {
            case "+":
                result.setText(String.valueOf(first + second));
                break;
            case "-":
                result.setText(String.valueOf(first - second));
                break;
            case "*":
                result.setText(String.valueOf(first * second));
                break;
            case "/":
                result.setText(String.valueOf(first / second));
                break;
            default:
                result.setText("Требуется выбрать операцию!");
        }
    }

    double parseDouble(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Double.parseDouble(strNumber);
            } catch (Exception e) {
                result.setText("Требуется заполнить формы!");
            }
        }

        return 0;

    }
}
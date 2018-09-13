package edu.gatech.seclass.sdpsalarycomp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;


public class SDPSalaryCompActivity extends AppCompatActivity {

    EditText baseSalaryText;
    TextView targetSalaryText;

    double baseSalaryValue;
    double targetSalaryValue;
    String target;

    Spinner spinnerCurrentCity;
    Spinner spinnerNewCity;
    ArrayList<String> cityList;
    ArrayList<String> indexList;
    ArrayAdapter<String> adapter;
    String selectedIndexCurrent;
    String selectedIndexNew;
    int indexCurrent;
    int indexNew;

    public String calculate(double baseSalaryValue, int indexNew, int indexCurrent) {
        targetSalaryValue = Math.round((baseSalaryValue * indexNew) / indexCurrent);
        int targetSalary = (int) targetSalaryValue;
        return String.valueOf(targetSalary);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdpsalary_comp);

        baseSalaryText = (EditText)findViewById(R.id.baseSalary);
        targetSalaryText = (TextView) findViewById(R.id.targetSalary);

        baseSalaryText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (baseSalaryText.getText().toString().trim().isEmpty()) {
                    baseSalaryText.setError("Invalid salary");

                    targetSalaryText.setText("");
                }

                if (!baseSalaryText.getText().toString().trim().isEmpty()) {
                    baseSalaryValue = Double.parseDouble(baseSalaryText.getText().toString());
                    target = calculate(baseSalaryValue, indexNew, indexCurrent);
                    targetSalaryText.setText(target);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        spinnerCurrentCity = findViewById(R.id.currentCity);
        spinnerNewCity = findViewById(R.id.newCity);

        cityList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.city)));
        indexList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.costIndex)));

        adapter = new ArrayAdapter<>(SDPSalaryCompActivity.this, android.R.layout.simple_list_item_1, cityList);

        spinnerCurrentCity.setAdapter(adapter);
        spinnerNewCity.setAdapter(adapter);

        spinnerCurrentCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedIndexCurrent = indexList.get(i);
                indexCurrent = Integer.parseInt(selectedIndexCurrent);
                Toast.makeText(SDPSalaryCompActivity.this, selectedIndexCurrent, Toast.LENGTH_SHORT).show();
                targetSalaryText.setText(calculate(baseSalaryValue, indexNew, indexCurrent));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerNewCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedIndexNew = indexList.get(i);
                indexNew = Integer.parseInt(selectedIndexNew);
                Toast.makeText(SDPSalaryCompActivity.this, selectedIndexNew, Toast.LENGTH_SHORT).show();
                targetSalaryText.setText(calculate(baseSalaryValue, indexNew, indexCurrent));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
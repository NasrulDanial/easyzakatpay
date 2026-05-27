package com.example.easyzakatpay;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.net.Uri;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    EditText edtWeight, edtPrice;
    RadioGroup radioGroupType;
    RadioButton radioKeep, radioWear;
    Button btnCalculate, btnReset;
    TextView txtTotalValue, txtZakatWeight, txtZakatPayable, txtTotalZakat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edtWeight = findViewById(R.id.edtWeight);
        edtPrice = findViewById(R.id.edtPrice);

        radioGroupType = findViewById(R.id.radioGroupType);
        radioKeep = findViewById(R.id.radioKeep);
        radioWear = findViewById(R.id.radioWear);

        btnCalculate = findViewById(R.id.btnCalculate);
        btnReset = findViewById(R.id.btnReset);
        Animation buttonAnim = AnimationUtils.loadAnimation(this, R.anim.button_animation);

        txtTotalValue = findViewById(R.id.txtTotalValue);
        txtZakatWeight = findViewById(R.id.txtZakatWeight);
        txtZakatPayable = findViewById(R.id.txtZakatPayable);
        txtTotalZakat = findViewById(R.id.txtTotalZakat);

        btnCalculate.startAnimation(buttonAnim);
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edtWeight.getText().toString().isEmpty()) {
                    edtWeight.setError("Please enter gold weight");
                    edtWeight.requestFocus();
                    return;
                }

                if (edtPrice.getText().toString().isEmpty()) {
                    edtPrice.setError("Please enter gold price");
                    edtPrice.requestFocus();
                    return;
                }

                if (radioGroupType.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(MainActivity.this, "Please select gold type", Toast.LENGTH_SHORT).show();
                    return;
                }

                double weight = Double.parseDouble(edtWeight.getText().toString());
                double price = Double.parseDouble(edtPrice.getText().toString());
                double uruf;
                double zakatWeight;
                double zakatPayable;
                double totalValue;
                double totalZakat;

                if (radioKeep.isChecked()) {
                    uruf = 85;
                } else {
                    uruf = 200;
                }

                totalValue = weight * price;
                zakatWeight = weight - uruf;
                if (zakatWeight < 0) {
                    zakatWeight = 0;
                }

                zakatPayable = zakatWeight * price;
                totalZakat = zakatPayable * 0.025;

                txtTotalValue.setText("Total Gold Value: RM " + String.format("%.2f", totalValue));
                txtZakatWeight.setText("Zakat Weight: " + String.format("%.2f", zakatWeight) + " gram");
                txtZakatPayable.setText("Zakat Payable: RM " + String.format("%.2f", zakatPayable));
                txtTotalZakat.setText("Total Zakat: RM " + String.format("%.2f", totalZakat));

                Toast.makeText(MainActivity.this, "Zakat calculated successfully", Toast.LENGTH_SHORT).show();
            }
        });

        btnReset.startAnimation(buttonAnim);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                edtWeight.setText("");
                edtPrice.setText("");

                radioGroupType.clearCheck();

                txtTotalValue.setText("Total Gold Value: RM 0.00");
                txtZakatWeight.setText("Zakat Weight: 0 gram");
                txtZakatPayable.setText("Zakat Payable: RM 0.00");
                txtTotalZakat.setText("Total Zakat: RM 0.00");

                Toast.makeText(MainActivity.this, "All fields reset", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuAbout) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;
        }

        if (item.getItemId() == R.id.menuShare) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out EasyZakat Pay:\nhttps://github.com/nasrul/easyzakatpay");
            startActivity(Intent.createChooser(shareIntent, "Share via"));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

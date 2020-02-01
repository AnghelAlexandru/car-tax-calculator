package clcvam.elitiv.com.calculatorvamal.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import clcvam.elitiv.calculatorvamal.analytics.AnalyticsTrackers;
import clcvam.elitiv.calculatorvamal.analytics.CalcVamAnalytics;
import clcvam.elitiv.com.calculatorvamal.R;
import uk.me.lewisdeane.ldialogs.BaseDialog;
import uk.me.lewisdeane.ldialogs.CustomListDialog;


public class Testare extends android.support.v4.app.Fragment implements View.OnClickListener {
    Button btnCalcTaxe,btnSelectVehicle;
    TextView txtResult,txtTaxa;
    EditText insertWeight;
    int indicatorPos=-1;
    RelativeLayout testareLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_testare, container, false);
        testareLayout= (RelativeLayout) v.findViewById(R.id.testareLayout);
        testareLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);
                return false;
            }
        });
        Typeface BikoRegular = Typeface.createFromAsset(getActivity().getAssets(),"BikoRegular.otf");
        Typeface BikoBold = Typeface.createFromAsset(getActivity().getAssets(), "BikoBold.otf");
        btnSelectVehicle= (Button) v.findViewById(R.id.btnSelectTestare);
        btnSelectVehicle.setTypeface(BikoRegular);
        btnSelectVehicle.setOnClickListener(this);
        btnCalcTaxe= (Button) v.findViewById(R.id.btnCalcTestare);
        btnCalcTaxe.setTypeface(BikoRegular);
        btnCalcTaxe.setOnClickListener(this);
        insertWeight = (EditText) v.findViewById(R.id.inserValueTestare);
        insertWeight.setTypeface(BikoRegular);
        txtTaxa= (TextView) v.findViewById(R.id.textView3);
        txtTaxa.setTypeface(BikoRegular);
        txtResult= (TextView) v.findViewById(R.id.txtResultTestare);
        txtResult.setTypeface(BikoBold);

        return v;

    }

    @Override
    public void onResume() {
        insertWeight.setText("");
        indicatorPos=-1;
        super.onResume();
        AnalyticsTrackers.initialize(getActivity());
        CalcVamAnalytics.getInstance().trackScreenView("Testarea Tehnica");
    }

    protected void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
    public void defineDialog(){
        CustomListDialog.Builder builder=new CustomListDialog.Builder(getActivity(),getResources().getString(R.string.vehicleType),getResources().getStringArray(R.array.testare));
        builder.titleAlignment(BaseDialog.Alignment.CENTER);
        builder.itemAlignment(BaseDialog.Alignment.CENTER);
        CustomListDialog customListDialog =builder.build();
        customListDialog.show();
        customListDialog.setListClickListener(new CustomListDialog.ListClickListener() {
            @Override
            public void onListItemSelected(int i, String[] strings, String s) {
                if (i != 0) {
                    insertWeight.setVisibility(View.VISIBLE);
                    btnCalcTaxe.setVisibility(View.VISIBLE);
                    txtResult.setText("0"+" MDL");
                } else {
                    insertWeight.setVisibility(View.INVISIBLE);
                    btnCalcTaxe.setVisibility(View.INVISIBLE);
                    txtResult.setText("50" + " MDL");
                }
                insertWeight.setText("");
                indicatorPos = i;
                btnSelectVehicle.setText(s);
                insertWeight.setHint("Kg");
            }
        });
    }
    public void setResult(int i,int editTextValue) {
        if (indicatorPos <= -1) {
            Toast.makeText(getActivity(), getResources().getString(R.string.vehicleType), Toast.LENGTH_SHORT).show();
        } else {
            double price = 0;
            switch (i) {
                case 1:
                    if (editTextValue <= 1000) {
                        price = 50;
                    }
                    if (editTextValue >= 1000) {
                        price = 150;
                    }
                    break;
                case 2:
                    if (editTextValue <= 2000) {
                        price = 150;
                    }
                    if (editTextValue >= 2001 && editTextValue <= 3500) {
                        price = 200;
                    }
                    if (editTextValue >= 3501 && editTextValue <= 10000) {
                        price = 250;
                    }
                    if (editTextValue >= 10001 && editTextValue <= 20000) {
                        price = 300;
                    }
                    if (editTextValue >= 20001) {
                        price = 350;
                    }
                    break;


            }
            txtResult.setText(String.valueOf(price) + " MDL");
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSelectTestare:
                defineDialog();
                break;
            case R.id.btnCalcTestare:
                CalcVamAnalytics.getInstance().trackEvent("Testarea Tehnica", "Utilizatorul a calculat Testarea Tehnica", "Taxe Drumuri");
                try {
                    setResult(indicatorPos, Integer.valueOf(insertWeight.getText().toString()));
                }catch (NumberFormatException e){
                    if(indicatorPos!=4) {
                        Toast.makeText(getActivity(), getResources().getString(R.string.otherToast), Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }
}

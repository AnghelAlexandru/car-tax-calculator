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


public class Taxe extends android.support.v4.app.Fragment implements View.OnClickListener {

    Button btnCalcTaxe,btnSelectVehicle;
    TextView txtResult,txtTaxa;
    EditText insertWeight;
    String indicator;
    int indicatorPos;
    Devamare devClass;
    RelativeLayout taxeLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_taxe, container, false);
        taxeLayout = (RelativeLayout) v.findViewById(R.id.taxeLayout);
        taxeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);
                return false;
            }
        });
        Typeface BikoRegular = Typeface.createFromAsset(getActivity().getAssets(),"BikoRegular.otf");
        Typeface BikoBold = Typeface.createFromAsset(getActivity().getAssets(), "BikoBold.otf");
        btnSelectVehicle= (Button) v.findViewById(R.id.btnSelectTaxa);
        btnSelectVehicle.setTypeface(BikoRegular);
        btnSelectVehicle.setOnClickListener(this);
        btnCalcTaxe= (Button) v.findViewById(R.id.btnCalcTaxa);
        btnCalcTaxe.setTypeface(BikoRegular);
        btnCalcTaxe.setOnClickListener(this);
        insertWeight = (EditText) v.findViewById(R.id.inserValue);
        insertWeight.setTypeface(BikoRegular);
        txtTaxa= (TextView) v.findViewById(R.id.textView);
        txtTaxa.setTypeface(BikoRegular);
        txtResult= (TextView) v.findViewById(R.id.txtResultTaxa);
        txtResult.setTypeface(BikoBold);

        return v;
    }

    @Override
    public void onResume() {
        insertWeight.setText("");
        indicatorPos=-1;
        super.onResume();
        AnalyticsTrackers.initialize(getActivity());
        CalcVamAnalytics.getInstance().trackScreenView("Taxe Drumuri");
    }

    protected void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
    public void defineDialog(){
        CustomListDialog.Builder builder=new CustomListDialog.Builder(getActivity(),getResources().getString(R.string.vehicleType), getResources().getStringArray(R.array.taxe));
        builder.titleAlignment(BaseDialog.Alignment.CENTER);
        builder.itemAlignment(BaseDialog.Alignment.CENTER);
        CustomListDialog customListDialog =builder.build();
        customListDialog.show();
        final String tone=getResources().getString(R.string.tonaj);
        customListDialog.setListClickListener(new CustomListDialog.ListClickListener() {
            @Override
            public void onListItemSelected(int i, String[] strings, String s) {
                insertWeight.setText("");
                if(i!=4){
                    insertWeight.setVisibility(View.VISIBLE);
                    btnCalcTaxe.setVisibility(View.VISIBLE);
                    txtResult.setText("0"+" MDL");
                }else{
                    insertWeight.setVisibility(View.INVISIBLE);
                    btnCalcTaxe.setVisibility(View.INVISIBLE);
                    txtResult.setText("2250" + " MDL");
                }

                indicatorPos=i;

                btnSelectVehicle.setText(s);
                switch (i){
                    case 0:
                    case 1:
                        indicator="cm3";
                        break;
                    case 2:
                    case 3:
                        indicator=tone;
                        break;
                    case 4:

                        break;
                    case 5:
                        indicator=tone;
                        break;
                    case 6:
                        indicator=getResources().getString(R.string.locuri);
                        break;
                }
                insertWeight.setHint(indicator);
            }
        });
    }
    public void setResult(int i,int editTextValue){
        if(indicatorPos<=-1){
            Toast.makeText(getActivity(),getResources().getString(R.string.vehicleType),Toast.LENGTH_SHORT).show();
        }else {
            double price = 0;
            switch (i) {
                case 0:

                    if (editTextValue <= 500) {
                        price = 300;
                    } else {
                        price = 600;
                    }
                    break;
                case 1:
                    if (editTextValue <= 2000) {
                        price = editTextValue * 0.60;
                    }
                    if (editTextValue >= 2001 && editTextValue <= 3000) {
                        price = editTextValue * 0.90;
                    }
                    if (editTextValue >= 3001 && editTextValue <= 4000) {
                        price = editTextValue * 1.20;
                    }
                    if (editTextValue >= 4001 && editTextValue <= 5000) {
                        price = editTextValue * 1.50;
                    }
                    if (editTextValue >= 5001) {
                        price = editTextValue * 1.80;
                    }
                    break;
                case 2:
                    price = editTextValue * 270;
                    break;
                case 3:
                    if (editTextValue <= 20) {
                        price = editTextValue * 225;
                    } else {
                        price = 4500;
                    }
                    break;
                case 4:
                    insertWeight.setVisibility(View.INVISIBLE);
                    price = 4500;
                    break;
                case 5:

                    if (editTextValue <= 1600) {
                        price = 1200;
                    }
                    if (editTextValue >= 1600 && editTextValue >= 5000) {
                        price = 2250;
                    }
                    if (editTextValue >= 5001 && editTextValue >= 10000) {
                        price = 3000;
                    }
                    if (editTextValue >= 10001) {
                        price = 4500;
                    }

                    break;
                case 6:
                    if (editTextValue <= 11) {
                        price = 2925;
                    }
                    if (editTextValue >= 12 && editTextValue >= 17) {
                        price = 3600;
                    }
                    if (editTextValue >= 18 && editTextValue >= 24) {
                        price = 4275;
                    }
                    if (editTextValue >= 25 && editTextValue >= 40) {
                        price = 4725;
                    }
                    if (editTextValue >= 41) {
                        price = 5400;
                    }
                    break;

            }
            txtResult.setText(String.valueOf(price) + " MDL");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSelectTaxa:
                defineDialog();
                break;
            case R.id.btnCalcTaxa:
                CalcVamAnalytics.getInstance().trackEvent("Taxe Drumuri", "Utilizatorul a calculat taxa pe drumuri", "Taxe Drumuri");
                try {
                    setResult(indicatorPos, Integer.valueOf(insertWeight.getText().toString()));
                }catch (NumberFormatException e){
                    if(indicatorPos!=4) {
                        Toast.makeText(getActivity(),  getResources().getString(R.string.otherToast), Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }
}
//@todo x delete button edittext
//
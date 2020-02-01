package clcvam.elitiv.com.calculatorvamal.fragments;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import clcvam.elitiv.calculatorvamal.analytics.AnalyticsTrackers;
import clcvam.elitiv.calculatorvamal.analytics.CalcVamAnalytics;
import clcvam.elitiv.com.calculatorvamal.R;
import uk.me.lewisdeane.ldialogs.BaseDialog;
import uk.me.lewisdeane.ldialogs.CustomListDialog;


public class Devamare extends android.support.v4.app.Fragment implements View.OnClickListener{
    private static Button btnClcVam,btnSelectYear;
    private static ToggleButton btnBenzin,btnMotorina;
    private static TextView txtVamResult,txtTaxa;
    private static EditText editCapacity;
    int year=-1;
    int capacity;
    TextView testTxt;
    Button testBTN;
    RelativeLayout devLayout;
    String[] adj=new String[]{"2006","2007","2008","2009","2010","2011","2012","2013","2014","2015","2016"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_devamare, container, false);

        devLayout= (RelativeLayout) v.findViewById(R.id.devLayout);
        devLayout.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);
                return false;
            }
        });
        DefineButtonsAndText(v);
        return v;

    }
    protected void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
    //Buttons
    public void DefineButtonsAndText(View v){
        //Buttons

        btnBenzin= (ToggleButton) v.findViewById(R.id.btnBenzin);
        btnBenzin.setChecked(true);
        btnBenzin.setOnClickListener(this);
        setFont(btnBenzin, testTxt, 0);
        btnMotorina= (ToggleButton) v.findViewById(R.id.btnDiesel);
        btnMotorina.setOnClickListener(this);
        setFont(btnMotorina,testTxt,0);
        btnClcVam= (Button) v.findViewById(R.id.btnCalcVam);
        btnClcVam.setOnClickListener(this);
        setFont(btnClcVam,testTxt,1);
        btnSelectYear= (Button) v.findViewById(R.id.btnSelectYear);
        btnSelectYear.setOnClickListener(this);
        setFont(btnSelectYear, testTxt, 1);
        btnMotorina.setTextColor(getResources().getColor(R.color.darkBlue));
        btnBenzin.setTextColor(getResources().getColor(R.color.whiteBlue));

        //Text
        txtTaxa= (TextView) v.findViewById(R.id.textView2);
        setFont(testBTN, txtTaxa, 3);
        txtVamResult= (TextView) v.findViewById(R.id.rezultDevTxt);
        setFont(testBTN,txtVamResult,4);
        editCapacity= (EditText) v.findViewById(R.id.editCapacity);
        setFont(testBTN, testTxt, 5);
        editCapacity.setHint("cm3");

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBenzin:
                Log.d("Decision", " Benzin");
                btnMotorina.setChecked(false);
                btnMotorina.setTextColor(getResources().getColor(R.color.darkBlue));
                btnBenzin.setTextColor(getResources().getColor(R.color.whiteBlue));
                btnBenzin.setChecked(true);
                CalcVamAnalytics.getInstance().trackEvent("Devamare", "Utilizatorul a ales Benzina", "Benzina");
                break;
            case R.id.btnDiesel:
                Log.d("Decision", " Diesel");
                btnBenzin.setChecked(false);
                btnBenzin.setTextColor(getResources().getColor(R.color.darkBlue));
                btnMotorina.setTextColor(getResources().getColor(R.color.whiteBlue));
                btnMotorina.setChecked(true);
                CalcVamAnalytics.getInstance().trackEvent("Devamare", "Utilizatorul a ales Motorina", "Motorina");
                break;
            case R.id.btnCalcVam:
                txtVamResult.setText(doDevamare());
                CalcVamAnalytics.getInstance().trackEvent("Devamare","Calculul Devamarii Auto","Calculul sumei");
                break;
            case R.id.btnSelectYear:
                defineDialog();
                break;
        }
    }

    @Override
    public void onResume() {
        editCapacity.setText("");
        year=-1;
        btnMotorina.setChecked(false);
        btnMotorina.setTextColor(getResources().getColor(R.color.darkBlue));
        btnBenzin.setTextColor(getResources().getColor(R.color.whiteBlue));
        btnBenzin.setChecked(true);
        AnalyticsTrackers.initialize(getActivity());
        CalcVamAnalytics.getInstance().trackScreenView("Devamarea Autovehiculelor");
        super.onResume();
    }

    //FirstMainFunction
    public String doDevamare(){
        if(year<=-1){
            Toast.makeText(getActivity(), getResources().getString(R.string.devToast), Toast.LENGTH_SHORT).show();
        }
        try {
            capacity = Integer.parseInt(editCapacity.getText().toString());
        }catch (NumberFormatException e){
            capacity=0;
            Toast.makeText(getActivity(), getResources().getString(R.string.devToast), Toast.LENGTH_SHORT).show();
        }
        String x="";
        if(btnBenzin.isChecked()){

            x=String.valueOf(selectYear(year,0,capacity)+" "+"\u20ac");

        }else if(btnMotorina.isChecked()){
            x= String.valueOf(selectYear(year,1,capacity)+" "+"\u20ac");
        }
        return x;
    }
    //Seekbar
    public void defineDialog(){
        CustomListDialog.Builder builder=new CustomListDialog.Builder(getActivity(),getResources().getString(R.string.devYear),adj);
        builder.titleAlignment(BaseDialog.Alignment.CENTER);
        builder.itemAlignment(BaseDialog.Alignment.CENTER);
        CustomListDialog customListDialog =builder.build();
        customListDialog.show();
        customListDialog.setListClickListener(new CustomListDialog.ListClickListener() {
            @Override
            public void onListItemSelected(int i, String[] strings, String s) {
                    year=i;
                    btnSelectYear.setText(s);
                    txtVamResult.setText("0.0"+" "+"\u20ac");
            }
        });

    }
    //Main Calc Function
    public double selectYear(int x,int i,int capacity){
        double k=0;
        if(i==0) {
            switch (x) {
                case 0://2005
                    if (capacity > 0 && capacity <= 1000) {
                        k = capacity * 0.56;
                    }if (capacity >= 1001 && capacity <= 1500) {
                        k = capacity * 0.71;
                    }if (capacity >= 1501 && capacity <= 2000) {
                        k = capacity * 1.06;
                    }if (capacity >= 2001 && capacity <= 3000) {
                        k = capacity * 1.69;
                    }if (capacity >= 3001) {
                        k = capacity * 3.79;
                    }
                    break;
                case 1://2006
                    if (capacity > 0 && capacity <= 1000) {
                        k = capacity * 0.53;
                    }if (capacity >= 1001 &&  capacity <= 1500) {
                        k = capacity * 0.66;
                    }if (capacity >= 1501 && capacity <= 2000) {
                        k = capacity * 1.01;
                    }if (capacity >= 2001 && capacity <= 3000) {
                        k = capacity * 1.61;
                    }if (capacity >= 3001) {
                        k = capacity * 3.78;
                    }
                    break;
                case 2://2007
                    if (capacity > 0 && capacity <= 1000) {
                        k = capacity * 0.52;
                    }if (capacity > 1001 && capacity <= 1500) {
                        k = capacity * 0.63;
                    }if (capacity >= 1501 && capacity <= 2000) {
                        k = capacity * 0.94;
                    }if (capacity >= 2001 && capacity <= 3000) {
                        k = capacity * 1.53;
                    }if (capacity >= 3001) {
                        k = capacity * 3.77;
                    }
                    break;
                case 3://2008
                    if (capacity > 0 && capacity <= 1000) {
                        k = capacity * 0.49;
                    }if (capacity > 1001 && capacity <= 1500) {
                        k = capacity * 0.59;
                    }if (capacity >= 1501 && capacity <= 2000) {
                        k = capacity * 0.89;
                    }if (capacity >= 2001 && capacity <= 3000) {
                        k = capacity * 1.44;
                    }if (capacity >= 3001) {
                        k = capacity * 3.76;
                    }
                    break;
                case 4://2009-2010
                case 5:
                    if (capacity > 0 && capacity <= 1000) {
                        k = capacity * 0.46;
                    }if (capacity > 1001 && capacity <= 1500) {
                        k = capacity * 0.58;
                    }if (capacity >= 1501 && capacity <= 2000) {
                        k = capacity * 0.88;
                    }if (capacity >= 2001 && capacity <= 3000) {
                        k = capacity * 1.43;
                    }if (capacity >= 3001) {
                        k = capacity * 3.75;
                    }
                    break;
                case 6://2010
                case 7:
                    if (capacity > 0 && capacity <= 1000) {
                        k = capacity * 0.45;
                    }if (capacity > 1001 && capacity <= 1500) {
                        k = capacity * 0.57;
                    }if (capacity >= 1501 && capacity <= 2000) {
                        k = capacity * 0.87;
                    }if (capacity >= 2001 && capacity <= 3000) {
                        k = capacity * 1.42;
                    }if (capacity >= 3001) {
                        k = capacity * 3.74;
                    }
                    break;
                case 8://2010
                case 9:
                case 10:
                    if (capacity > 0 && capacity <= 1000) {
                        k = capacity * 0.43;
                    }if (capacity > 1001 && capacity <= 1500) {
                        k = capacity * 0.55;
                    }if (capacity >= 1501 && capacity <= 2000) {
                        k = capacity * 0.85;
                    }if (capacity >= 2001 && capacity <= 3000) {
                        k = capacity * 1.40;
                    }if (capacity >= 3001) {
                        k = capacity * 3.72;
                    }
                    break;

            }
        }if(i==1){
            switch (x) {
                case 0://2005
                    if (capacity <= 1500) {
                        k = capacity * 0.71;
                    }if(capacity >= 1501 && capacity <= 2500){
                        k = capacity * 1.69;
                    }if(capacity >= 2501){
                        k = capacity * 3.79;
                    }
                    break;
                case 1://2006
                    if (capacity <= 1500) {
                        k = capacity * 0.66;
                    }if(capacity >= 1501 && capacity <= 2500){
                        k = capacity * 1.61;
                    }if(capacity >= 2501){
                        k = capacity * 3.78;
                    }
                    break;
                case 2://2007
                    if (capacity <= 1500) {
                        k = capacity * 0.63;
                    }if(capacity >= 1501 && capacity <= 2500){
                        k = capacity * 1.53;
                    }if(capacity >= 2501){
                        k = capacity * 3.77;
                    }
                    break;
                case 3://2008
                    if (capacity <= 1500) {
                        k = capacity * 0.59;
                    }if(capacity >= 1501 && capacity <= 2500){
                        k = capacity * 1.44;
                    }if(capacity >= 2501){
                        k = capacity * 3.76;
                    }
                    break;
                case 4://2009-2010
                case 5:
                    if (capacity <= 1500) {
                        k = capacity * 0.58;
                    }if(capacity >= 1501 && capacity <= 2500){
                        k = capacity * 1.43;
                    }if(capacity >= 2501){
                        k = capacity * 3.75;
                    }
                    break;
                case 6://2012
                case 7:
                    if (capacity <= 1500) {
                        k = capacity * 0.57;
                    }if(capacity >= 1501 && capacity <= 2500){
                        k = capacity * 1.42;
                    }if(capacity >= 2501){
                        k = capacity * 3.74;
                    }
                    break;
                case 8://2015
                case 9:
                case 10:
                    if (capacity <= 1500) {
                        k = capacity * 0.55;
                    }if(capacity >= 1501 && capacity <= 2500){
                        k = capacity * 1.40;
                    }if(capacity >= 2501){
                        k = capacity * 3.72;
                    }
                    break;

            }
        }
        return Math.round(k);
    }
    public void setFont(Button btnExample,TextView x,int i){
        Typeface BikoRegular = Typeface.createFromAsset(getActivity().getAssets(),"BikoRegular.otf");
        Typeface BikoBold = Typeface.createFromAsset(getActivity().getAssets(),"BikoBold.otf");
        Typeface CenturyGothic = Typeface.createFromAsset(getActivity().getAssets(),"GothicRegular.TTF");
        switch (i){
            case 0:
                btnExample.setTypeface(CenturyGothic);
                break;
            case 1:
                btnExample.setTypeface(BikoRegular);
                break;
            case 3:
                x.setTypeface(BikoRegular);
                break;
            case 4:
                x.setTypeface(BikoBold);
                break;
            case 5:
                editCapacity.setTypeface(BikoRegular);
                break;


        }
    }

}

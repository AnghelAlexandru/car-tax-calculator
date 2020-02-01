package weownit.io.autotaxcalculator;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.widget.TextView;

import weownit.io.autotaxcalculator.fragments.Devamare;
import weownit.io.autotaxcalculator.fragments.Taxe;
import weownit.io.autotaxcalculator.fragments.Testare;


public class MainActivity extends FragmentActivity  {


    private FragmentTabHost mTabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        Typeface BikoRegular = Typeface.createFromAsset(this.getAssets(),"BikoRegular.otf");
        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator(getResources().getString(R.string.tabDev)),
                Devamare.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator(getResources().getString(R.string.tabTaxe)),
                Taxe.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab3").setIndicator(getResources().getString(R.string.tabTest)),
                Testare.class, null);
        TextView first = (TextView)  mTabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
        first.setTypeface(BikoRegular);
        TextView second = (TextView)  mTabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
        second.setTypeface(BikoRegular);

        TextView third = (TextView)  mTabHost.getTabWidget().getChildAt(2).findViewById(android.R.id.title);
        third.setTypeface(BikoRegular);

    }


}

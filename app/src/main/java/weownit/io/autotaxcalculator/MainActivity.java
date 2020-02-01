package weownit.io.autotaxcalculator;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import clcvam.elitiv.com.calculatorvamal.R;
import weownit.io.autotaxcalculator.adapters.PagerAdapter;


public class MainActivity extends FragmentActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ((ViewPager) findViewById(R.id.pager))
            .setAdapter(new PagerAdapter(getSupportFragmentManager()));
  }
}

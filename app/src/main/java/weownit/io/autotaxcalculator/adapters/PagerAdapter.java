package weownit.io.autotaxcalculator.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import weownit.io.autotaxcalculator.fragments.PageFragment;

public class PagerAdapter extends FragmentPagerAdapter {
  private final int PAGE_COUNT = 3;

  public PagerAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override
  public int getCount() {
    return PAGE_COUNT;
  }

  @Override
  public Fragment getItem(int position) {
    return PageFragment.getInstance(position + 1);
  }
}
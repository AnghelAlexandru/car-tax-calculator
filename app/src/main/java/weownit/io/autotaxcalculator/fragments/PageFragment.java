package weownit.io.autotaxcalculator.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import clcvam.elitiv.com.calculatorvamal.R;

public class PageFragment extends Fragment {
  public static final String ARG_PAGE = "ARG_PAGE";

  public static PageFragment getInstance(int page) {
    Bundle args = new Bundle();
    args.putInt(ARG_PAGE, page);
    PageFragment fragment = new PageFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.p_container, container, false);
    getFragmentManager()
            .beginTransaction()
            .add(R.id.pager_container, getFragment(
                    getArguments().getInt(ARG_PAGE))
            ).commit();

    return view;
  }

  private Fragment getFragment(int page) {
    switch (page) {
      case 2:
        return CarRoadTax.newInstance();
      case 1:
        return CarTesting.newInstance();
      case 0:
      default:
        return CarImport.newInstance();
    }
  }
}

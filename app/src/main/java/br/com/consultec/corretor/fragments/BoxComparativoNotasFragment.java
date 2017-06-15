package br.com.consultec.corretor.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.consultec.corretor.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoxComparativoNotasFragment extends Fragment {


    public BoxComparativoNotasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_box_comparativo_notas, container, false);
    }

}

package br.com.consultec.corretor.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.consultec.corretor.R;
import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * A simple {@link Fragment} subclass.
 */
public class GraficoNotasPessoalFragment extends Fragment
{
    private PieChartView grafico;
    private PieChartData data;

    private boolean hasLabels           = false;
    private boolean hasLabelsOutside    = false;
    private boolean hasCenterCircle     = false;
    private boolean hasCenterText1      = false;
    private boolean hasCenterText2      = false;
    private boolean isExploded          = false;
    private boolean hasLabelForSelected = false;

    public GraficoNotasPessoalFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grafico_notas_pessoal, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        bindActivity();
        configuraGrafico();
    }

    private void bindActivity()
    {
        grafico = (PieChartView) getView().findViewById(R.id.grafico);
    }

    private void configuraGrafico()
    {
        hasCenterCircle = !hasCenterCircle;
        if (!hasCenterCircle)
        {
            hasCenterText1 = true;
            hasCenterText2 = true;
        }/**/
        hasCenterText1 = !hasCenterText1;

        if (hasCenterText1)
        {
            hasCenterCircle = true;
        }

        hasCenterText2 = true;

        hasCenterText2 = !hasCenterText2;

        if (hasCenterText2)
        {
            hasCenterText1 = true;// text 2 need text 1 to by also drawn.
            hasCenterCircle = true;
        }

        toggleLabels();
        //toggleLabelsOutside();
        grafico.setOnValueTouchListener(new GraficoNotasPessoalFragment.ValueTouchListener());
    }

    private void reset()
    {
        grafico.setCircleFillRatio(1.0f);
        hasLabels           = false;
        hasLabelsOutside    = false;
        hasCenterCircle     = false;
        hasCenterText1      = false;
        hasCenterText2      = false;
        isExploded          = false;
        hasLabelForSelected = false;
    }

    private void generateData()
    {
        int numValues = 6;

        List<SliceValue> values = new ArrayList<SliceValue>();
        /*for (int i = 0; i < numValues; ++i) {
            SliceValue sliceValue = new SliceValue((float) Math.random() * 30 + 15, ChartUtils.pickColor());
            values.add(sliceValue);
        }*/

        SliceValue sliceValue1 = new SliceValue(6, ChartUtils.pickColor());
        sliceValue1.setLabel("1");
        values.add(sliceValue1);

        SliceValue sliceValue2 = new SliceValue(14, ChartUtils.pickColor());
        sliceValue2.setLabel("2");
        values.add(sliceValue2);

        SliceValue sliceValue3 = new SliceValue(27, ChartUtils.pickColor());
        sliceValue3.setLabel("3");
        values.add(sliceValue3);

        SliceValue sliceValue4 = new SliceValue(3, ChartUtils.pickColor());
        sliceValue4.setLabel("4");
        values.add(sliceValue4);

        SliceValue sliceValue5 = new SliceValue(16, ChartUtils.pickColor());
        sliceValue5.setLabel("5");
        values.add(sliceValue5);

        SliceValue sliceValue6 = new SliceValue(4, ChartUtils.pickColor());
        sliceValue6.setLabel("6");
        values.add(sliceValue6);

        SliceValue sliceValue7 = new SliceValue(10, ChartUtils.pickColor());
        sliceValue7.setLabel("7");
        values.add(sliceValue7);

        SliceValue sliceValue8 = new SliceValue(13, ChartUtils.pickColor());
        sliceValue8.setLabel("8");
        values.add(sliceValue8);

        SliceValue sliceValue9 = new SliceValue(7, ChartUtils.pickColor());
        sliceValue9.setLabel("9");
        values.add(sliceValue9);

        SliceValue sliceValue10 = new SliceValue(2, ChartUtils.pickColor());
        sliceValue10.setLabel("10");
        values.add(sliceValue10);

        data = new PieChartData(values);
        data.setHasLabels(hasLabels);
        data.setHasLabelsOnlyForSelected(hasLabelForSelected);
        data.setHasLabelsOutside(hasLabelsOutside);
        data.setHasCenterCircle(hasCenterCircle);

        if (isExploded)
        {
            data.setSlicesSpacing(24);
        }

        if (hasCenterText1)
        {
            data.setCenterText1("Notas");

            // Get roboto-italic font.
            Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Italic.ttf");
            data.setCenterText1Typeface(tf);

            // Get font size from dimens.xml and convert it to sp(library uses sp values).
            data.setCenterText1FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
                    (int) getResources().getDimension(R.dimen.pie_chart_text1_size)));
        }

        if (hasCenterText2)
        {
            data.setCenterText2("Charts (Roboto Italic)");

            Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Italic.ttf");

            data.setCenterText2Typeface(tf);
            data.setCenterText2FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
                    (int) getResources().getDimension(R.dimen.pie_chart_text2_size)));
        }

        grafico.setPieChartData(data);
    }

    private void explodeChart()
    {
        isExploded = !isExploded;
        generateData();
    }

    private void toggleLabelsOutside()
    {
        // has labels have to be true:P
        hasLabelsOutside = !hasLabelsOutside;
        if (hasLabelsOutside)
        {
            hasLabels = true;
            hasLabelForSelected = false;
            grafico.setValueSelectionEnabled(hasLabelForSelected);
        }

        if (hasLabelsOutside)
        {
            grafico.setCircleFillRatio(0.7f);
        }
        else
        {
            grafico.setCircleFillRatio(1.0f);
        }

        generateData();
    }

    private void toggleLabels()
    {
        hasLabels = !hasLabels;

        if (hasLabels)
        {
            hasLabelForSelected = false;
            grafico.setValueSelectionEnabled(hasLabelForSelected);

            if (hasLabelsOutside)
            {
                grafico.setCircleFillRatio(0.7f);
            }
            else
            {
                grafico.setCircleFillRatio(1.0f);
            }
        }

        generateData();
    }

    private void toggleLabelForSelected()
    {
        hasLabelForSelected = !hasLabelForSelected;

        grafico.setValueSelectionEnabled(hasLabelForSelected);

        if (hasLabelForSelected)
        {
            hasLabels = false;
            hasLabelsOutside = false;

            if (hasLabelsOutside)
            {
                grafico.setCircleFillRatio(0.7f);
            }
            else
            {
                grafico.setCircleFillRatio(1.0f);
            }
        }

        generateData();
    }

    /**
     * To animate values you have to change targets values and then call {@link //Chart#startDataAnimation()}
     * method(don't confuse with View.animate()).
     */
    private void prepareDataAnimation()
    {
        for (SliceValue value : data.getValues())
        {
            value.setTarget((float) Math.random() * 30 + 15);
        }
    }

    private class ValueTouchListener implements PieChartOnValueSelectListener
    {
        @Override
        public void onValueSelected(int arcIndex, SliceValue value)
        {
            Toast.makeText(getActivity(), "Valor: " + value, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected()
        {
            // TODO Auto-generated method stub
        }
    }

}

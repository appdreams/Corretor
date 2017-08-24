package br.com.consultec.corretor.fragments;


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
import lecho.lib.hellocharts.listener.ComboLineColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.ComboLineColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.ComboLineColumnChartView;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * A simple {@link Fragment} subclass.
 */
public class GraficoProdutividadeFragment extends Fragment
{

    private ColumnChartView graficoPessoal;
    private ColumnChartView graficoGeral;
    private ColumnChartData columnData;

    private int numberOfLines           = 1;
    private int maxNumberOfLines        = 4;
    private int numberOfPoints          = 12;

    float[][] randomNumbersTab          = new float[maxNumberOfLines][numberOfPoints];

    private boolean hasAxes             = true;
    private boolean hasAxesNames        = false;
    private boolean hasPoints           = true;
    private boolean hasLines            = true;
    private boolean isCubic             = false;
    private boolean hasLabels           = false;
    private boolean hasLabelForSelected = false;

    public final static String[] dias = new String[]{"10/08", "11/08", "12/08", "13/08", "14/08", "15/08", "16/08", "17/08"};

    public GraficoProdutividadeFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grafico_produtividade, container, false);
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
        graficoPessoal = (ColumnChartView) getView().findViewById(R.id.graficoPessoal);
        graficoGeral   = (ColumnChartView) getView().findViewById(R.id.graficoGeral);
    }

    private void configuraGrafico()
    {
        //graficoPessoal.setOnValueTouchListener(new ValueTouchListener());
        //graficoGeral.setOnValueTouchListener(new ValueTouchListener());

        configuraGraficoPessoal();
        configuraGraficoGeral();
    }

    private void configuraGraficoPessoal()
    {
        int numColumns = dias.length;

        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;

        for (int i = 0; i < numColumns; ++i)
        {

            values = new ArrayList<SubcolumnValue>();

            values.add(new SubcolumnValue((float) Math.random() * 50f + 5, ChartUtils.pickColor()));

            axisValues.add(new AxisValue(i).setLabel(dias[i]));

            Column column = new Column(values);
            column.setHasLabels(true);
            column.setHasLabelsOnlyForSelected(false);
            columns.add(column);

        }

        columnData = new ColumnChartData(columns);

        columnData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
        columnData.setAxisXBottom(new Axis(axisValues).setName("PESSOAL"));
        columnData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(2));

        graficoPessoal.setColumnChartData(columnData);
    }

    private void configuraGraficoGeral()
    {

        int numColumns = dias.length;

        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;

        for (int i = 0; i < numColumns; ++i)
        {

            values = new ArrayList<SubcolumnValue>();

            values.add(new SubcolumnValue((float) Math.random() * 50f + 5, ChartUtils.pickColor()));

            axisValues.add(new AxisValue(i).setLabel(dias[i]));

            Column column = new Column(values);
            column.setHasLabels(true);
            column.setHasLabelsOnlyForSelected(false);
            columns.add(column);

        }

        columnData = new ColumnChartData(columns);

        columnData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
        columnData.setAxisXBottom(new Axis(axisValues).setName("GERAL"));
        columnData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(2));

        graficoGeral.setColumnChartData(columnData);

    }

    private class ValueTouchListener implements ComboLineColumnChartOnValueSelectListener
    {

        @Override
        public void onValueDeselected()
        {
            // TODO Auto-generated method stub

        }

        @Override
        public void onColumnValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value)
        {
            Toast.makeText(getActivity(), "Selected column: " + value, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPointValueSelected(int lineIndex, int pointIndex, PointValue value)
        {
            Toast.makeText(getActivity(), "Selected line point: " + value, Toast.LENGTH_SHORT).show();
        }

    }

}

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
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.ComboLineColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ComboLineColumnChartView;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * A simple {@link Fragment} subclass.
 */
public class GraficoProdutividadeFragment extends Fragment
{

    private ComboLineColumnChartView grafico;
    private ComboLineColumnChartData data;

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
        grafico = (ComboLineColumnChartView) getView().findViewById(R.id.grafico);
    }

    private void configuraGrafico()
    {
        grafico = (ComboLineColumnChartView) getView().findViewById(R.id.grafico);
        grafico.setOnValueTouchListener(new ValueTouchListener());

        generateValues();
        //generateData();
        toggleLabels();
    }

    private void generateValues() {
        for (int i = 0; i < maxNumberOfLines; ++i) {
            for (int j = 0; j < numberOfPoints; ++j) {
                randomNumbersTab[i][j] = (float) Math.random() * 50f + 5;
            }
        }
    }

    private void reset()
    {
        numberOfLines = 1;

        hasAxes = true;
        hasAxesNames = true;
        hasLines = true;
        hasPoints = true;
        hasLabels = false;
        isCubic = false;

    }

    private void generateData()
    {
        // Chart looks the best when line data and column data have similar maximum viewports.
        data = new ComboLineColumnChartData(generateColumnData(), generateLineData());

        if (hasAxes)
        {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames)
            {
                axisX.setName("Axis X");
                axisY.setName("Axis Y");
            }
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
        }
        else
        {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }

        grafico.setComboLineColumnChartData(data);
    }

    private LineChartData generateLineData()
    {

        List<Line> lines = new ArrayList<Line>();

        for (int i = 0; i < numberOfLines; ++i)
        {

            List<PointValue> values = new ArrayList<PointValue>();

            for (int j = 0; j < numberOfPoints; ++j)
            {
                values.add(new PointValue(j, randomNumbersTab[i][j]));
            }

            Line line = new Line(values);
            line.setColor(ChartUtils.COLORS[i]);
            line.setCubic(isCubic);
            //line.setHasLabels(hasLabels);
            line.setHasLines(hasLines);
            line.setHasPoints(hasPoints);
            lines.add(line);
        }

        LineChartData lineChartData = new LineChartData(lines);

        return lineChartData;

    }

    private ColumnChartData generateColumnData()
    {
        int numSubcolumns = 1;
        int numColumns = 12;
        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numColumns; ++i)
        {

            values = new ArrayList<SubcolumnValue>();

            for (int j = 0; j < numSubcolumns; ++j)
            {
                values.add(new SubcolumnValue((float) Math.random() * 50 + 5, ChartUtils.pickColor()));
            }

            Column column = new Column(values);
            column.setHasLabels(hasLabels);
            column.setHasLabelsOnlyForSelected(hasLabelForSelected);
            columns.add(column);
            //columns.add(new Column(values));
        }

        ColumnChartData columnChartData = new ColumnChartData(columns);
        return columnChartData;
    }

    private void addLineToData()
    {
        if (data.getLineChartData().getLines().size() >= maxNumberOfLines)
        {
            Toast.makeText(getActivity(), "Samples app uses max 4 lines!", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            ++numberOfLines;
        }

        generateData();
    }

    private void toggleLines()
    {
        hasLines = !hasLines;

        generateData();
    }

    private void togglePoints()
    {
        hasPoints = !hasPoints;

        generateData();
    }

    private void toggleCubic()
    {
        isCubic = !isCubic;

        generateData();
    }

    private void toggleLabels()
    {
        hasLabels = !hasLabels;

        if (hasLabels)
        {
            hasLabelForSelected = false;
            grafico.setValueSelectionEnabled(hasLabelForSelected);
        }
        generateData();
    }

    private void toggleAxes()
    {
        hasAxes = !hasAxes;

        generateData();
    }

    private void toggleAxesNames()
    {
        hasAxesNames = !hasAxesNames;

        generateData();
    }

    private void prepareDataAnimation()
    {

        // Line animations
        for (Line line : data.getLineChartData().getLines())
        {
            for (PointValue value : line.getValues())
            {
                // Here I modify target only for Y values but it is OK to modify X targets as well.
                value.setTarget(value.getX(), (float) Math.random() * 50 + 5);
            }
        }

        // Columns animations
        for (Column column : data.getColumnChartData().getColumns())
        {
            for (SubcolumnValue value : column.getValues())
            {
                value.setTarget((float) Math.random() * 50 + 5);
            }
        }
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

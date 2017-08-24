package br.com.consultec.corretor.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.consultec.corretor.R;
import br.com.consultec.corretor.fragments.GraficoProdutividadeFragment;
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

public class TesteActivity extends BaseActivity
{
    private ColumnChartView grafico;
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

    private ColumnChartData columnData;
    private ColumnChartView chartBottom;

    public final static String[] months = new String[]{"10/08", "11/08", "12/08", "13/08", "14/08", "15/08", "16/08", "17/08"};


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);

        setUpToolbar("CORRETOR");

        bindActivity();
        configuraGrafico();
    }

    private void bindActivity()
    {
        grafico = (ColumnChartView) findViewById(R.id.grafico);
    }

    private void configuraGrafico()
    {

        generateColumnData();
    }

    private void generateColumnData() {

        int numSubcolumns = 1;
        int numColumns = months.length;

        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            //for (int j = 0; j < numSubcolumns; ++j) {
            values.add(new SubcolumnValue((float) Math.random() * 50f + 5, ChartUtils.pickColor()));
            //}

            axisValues.add(new AxisValue(i).setLabel(months[i]));


            Column column = new Column(values);
            column.setHasLabels(true);
            column.setHasLabelsOnlyForSelected(false);
            columns.add(column);

        }

        columnData = new ColumnChartData(columns);

        columnData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
        columnData.setAxisXBottom(new Axis(axisValues).setName("Pessoal"));
        columnData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(2));


        //Axis axisX = new Axis();
        //axisX.setName("Pessoal");

        //columnData.setAxisXBottom(axisX);

        grafico.setColumnChartData(columnData);

        // Set value touch listener that will trigger changes for chartTop.
        //chartBottom.setOnValueTouchListener(new ValueTouchListener());

        // Set selection mode to keep selected month column highlighted.
        //grafico.setValueSelectionEnabled(true);

        //chartBottom.setZoomType(ZoomType.HORIZONTAL);

        // chartBottom.setOnClickListener(new View.OnClickListener() {
        //
        // @Override
        // public void onClick(View v) {
        // SelectedValue sv = chartBottom.getSelectedValue();
        // if (!sv.isSet()) {
        // generateInitialLineData();
        // }
        //
        // }
        // });

    }

}

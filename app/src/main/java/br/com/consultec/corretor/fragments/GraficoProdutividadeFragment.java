package br.com.consultec.corretor.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.consultec.corretor.R;
import br.com.consultec.corretor.model.ReferenciaDaBusca;
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
    private String idCorretor           =   "";
    private String idProcessoSeletivo   =   "";

    private ColumnChartView graficoPessoal;
    private ColumnChartView graficoGeral;
    private ColumnChartData columnData;

    private int numberOfLines           = 1;
    private int maxNumberOfLines        = 4;
    private int numberOfPoints          = 12;

    public final static String[] dias = new String[]{"10/08", "11/08", "12/08", "13/08", "14/08", "15/08", "16/08", "17/08"};

    public GraficoProdutividadeFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        ReferenciaDaBusca referenciaDaBusca = getArguments().getParcelable("dadosBusca");

        idCorretor          =   referenciaDaBusca.getIdCorretor().toString();
        idProcessoSeletivo  =   referenciaDaBusca.getIdProcessoSeletivo().toString();

        //Log.i("PAULO","IDC -> "+referenciaDaBusca.getIdCorretor().toString());
        //Log.i("PAULO","IDPS -> "+referenciaDaBusca.getIdProcessoSeletivo().toString());
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
        carregaDadosVolley();

        configuraGraficoGeral();
    }

    public void carregaDadosVolley()
    {
        //progressBar.setVisibility(View.VISIBLE);

        String URL = "http://consultec.com.br/APPS/CORRETOR/getDadosProdutividade.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            Log.i("PAULO", "RESP -> "+response.toString());
                            JSONObject jsonObject= new JSONObject(response.toString());
                            separaDadosJSON(jsonObject);
                            //progressBar.setVisibility(View.INVISIBLE);
                        }
                        catch (JSONException e)
                        {

                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        //progressBar.setVisibility(View.INVISIBLE);
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String,String> parameters = new HashMap<String,String>();
                parameters.put("idCorretor", idCorretor);
                parameters.put("idProcessoSeletivo", idProcessoSeletivo);
                return parameters;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);/**/

    }

    private void separaDadosJSON(JSONObject json)
    {
        //Log.i("PAULO", json.toString());
        try {

            JSONObject totalGeralLotesJsonObject    =   null;
            JSONObject totalLoteAtualJsonObject     =   null;

            JSONObject jsonObject                   =   json;
            JSONArray jsonArray                     =   jsonObject.getJSONArray("dados");

            JSONArray totalProdutividadePessoal     =   jsonArray.getJSONObject((int)0).optJSONArray("total_produtividade_pessoal");
            JSONArray totalProdutividadeGeral       =   jsonArray.getJSONObject((int)0).optJSONArray("total_produtividade_geral");

            Log.i("PAULO", "totalProdutividadePessoal -> "+totalProdutividadePessoal);
            Log.i("PAULO", "totalProdutividadeGeral -> "+totalProdutividadeGeral);

            configuraGraficoPessoal(totalProdutividadePessoal);

        }
        catch (JSONException e)
        {
            //alert(e.getMessage());
        }
    }

    private void configuraGraficoPessoal(JSONArray dados)
    {
        int numColumns = dados.length();

        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;

        for (int i = 0; i < numColumns; ++i)
        {

            values = new ArrayList<SubcolumnValue>();

            try {
                values.add(new SubcolumnValue(Float.parseFloat(dados.getJSONObject(i).getString("quantidade")), ChartUtils.pickColor()));
                axisValues.add(new AxisValue(i).setLabel(dados.getJSONObject(i).getString("data").substring(0, 5)));
            } catch (JSONException e) {
                e.printStackTrace();
            }

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

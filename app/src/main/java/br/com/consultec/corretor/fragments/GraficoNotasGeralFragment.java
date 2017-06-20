package br.com.consultec.corretor.fragments;


import android.graphics.Typeface;
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
import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * A simple {@link Fragment} subclass.
 */
public class GraficoNotasGeralFragment extends Fragment
{
    private String idCorretor           =   "";
    private String idProcessoSeletivo   =   "";

    private PieChartView grafico;
    private PieChartData data;

    private boolean hasLabels           = false;
    private boolean hasLabelsOutside    = false;
    private boolean hasCenterCircle     = false;
    private boolean hasCenterText1      = false;
    private boolean hasCenterText2      = false;
    private boolean isExploded          = false;
    private boolean hasLabelForSelected = false;

    private String totalNota0           =   "";
    private String totalNota1           =   "";
    private String totalNota2           =   "";
    private String totalNota3           =   "";
    private String totalNota4           =   "";
    private String totalNota5           =   "";
    private String totalNota6           =   "";
    private String totalNota7           =   "";
    private String totalNota8           =   "";
    private String totalNota9           =   "";
    private String totalNota10          =   "";

    public GraficoNotasGeralFragment() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grafico_notas_geral, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        bindActivity();
        configuraGrafico();

        carregaDadosVolley();
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
        grafico.setOnValueTouchListener(new GraficoNotasGeralFragment.ValueTouchListener());
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

    private void generateData(String nota0, String nota1, String nota2, String nota3, String nota4, String nota5, String nota6, String nota7, String nota8, String nota9, String nota10)
    {
        int numValues = 6;

        List<SliceValue> values = new ArrayList<SliceValue>();
        /*for (int i = 0; i < numValues; ++i) {
            SliceValue sliceValue = new SliceValue((float) Math.random() * 30 + 15, ChartUtils.pickColor());
            values.add(sliceValue);
        }*/

        if(!nota0.equals("0"))
        {
            SliceValue sliceValue0 = new SliceValue(Float.parseFloat(nota0), ChartUtils.pickColor());
            sliceValue0.setLabel("0");
            values.add(sliceValue0);/**/
            //Log.i("PAULO", "ADD 0");
        }

        if(!nota1.equals("0"))
        {
            SliceValue sliceValue1 = new SliceValue(Float.parseFloat(nota1), ChartUtils.pickColor());
            sliceValue1.setLabel("1");
            values.add(sliceValue1);
            //Log.i("PAULO", "ADD 1");
        }

        if(!nota2.equals("0"))
        {
            SliceValue sliceValue2 = new SliceValue(Float.parseFloat(nota2), ChartUtils.pickColor());
            sliceValue2.setLabel("2");
            values.add(sliceValue2);
            //Log.i("PAULO", "ADD 2");
        }

        if(!nota3.equals("0"))
        {
            SliceValue sliceValue3 = new SliceValue(Float.parseFloat(nota3), ChartUtils.pickColor());
            sliceValue3.setLabel("3");
            values.add(sliceValue3);
            //Log.i("PAULO", "ADD 3");
        }

        if(!nota4.equals("0"))
        {
            SliceValue sliceValue4 = new SliceValue(Float.parseFloat(nota4), ChartUtils.pickColor());
            sliceValue4.setLabel("4");
            values.add(sliceValue4);
            //Log.i("PAULO", "ADD 4");
        }

        if(!nota5.equals("0"))
        {
            SliceValue sliceValue5 = new SliceValue(Float.parseFloat(nota5), ChartUtils.pickColor());
            sliceValue5.setLabel("5");
            values.add(sliceValue5);
            //Log.i("PAULO", "ADD 5");
        }

        if(!nota6.equals("0"))
        {
            SliceValue sliceValue6 = new SliceValue(Float.parseFloat(nota6), ChartUtils.pickColor());
            sliceValue6.setLabel("6");
            values.add(sliceValue6);
            //Log.i("PAULO", "ADD 6");
        }

        if(!nota7.equals("0"))
        {
            SliceValue sliceValue7 = new SliceValue(Float.parseFloat(nota7), ChartUtils.pickColor());
            sliceValue7.setLabel("7");
            values.add(sliceValue7);
            //Log.i("PAULO", "ADD 7");
        }

        if(!nota8.equals("0"))
        {
            SliceValue sliceValue8 = new SliceValue(Float.parseFloat(nota8), ChartUtils.pickColor());
            sliceValue8.setLabel("8");
            values.add(sliceValue8);
            //Log.i("PAULO", "ADD 8");
        }

        if(!nota9.equals("0"))
        {
            SliceValue sliceValue9 = new SliceValue(Float.parseFloat(nota9), ChartUtils.pickColor());
            sliceValue9.setLabel("9");
            values.add(sliceValue9);
            //Log.i("PAULO", "ADD 9");
        }

        if(!nota10.equals("0"))
        {
            SliceValue sliceValue10 = new SliceValue(Float.parseFloat(nota10), ChartUtils.pickColor());
            sliceValue10.setLabel("10");
            values.add(sliceValue10);
            //Log.i("PAULO", "ADD 10");
        }

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
        //generateData();
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

        //generateData();
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

        //generateData();
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

        //generateData();
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
            String strAux = new String(value.toString());
            strAux = strAux.replace("SliceValue [value=","");
            strAux = strAux.replace(".0]","");
            Toast.makeText(getActivity(), "Quantidade: " + strAux, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected()
        {
            // TODO Auto-generated method stub
        }
    }


    public void carregaDadosVolley()
    {
        //progressBar.setVisibility(View.VISIBLE);

        String URL = "http://consultec.com.br/APPS/CORRETOR/getDadosTotalNotasGeral.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            //Log.i("PAULO", "RESP G -> "+response.toString());
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

            JSONArray totalNotasPessoalJsonArray    =   jsonArray.getJSONObject((int)0).optJSONArray("total_notas_geral");

            totalNota0                              =   totalNotasPessoalJsonArray.getJSONObject((int)0).getString("nota").toString();
            totalNota1                              =   totalNotasPessoalJsonArray.getJSONObject((int)1).getString("nota").toString();
            totalNota2                              =   totalNotasPessoalJsonArray.getJSONObject((int)2).getString("nota").toString();
            totalNota3                              =   totalNotasPessoalJsonArray.getJSONObject((int)3).getString("nota").toString();
            totalNota4                              =   totalNotasPessoalJsonArray.getJSONObject((int)4).getString("nota").toString();
            totalNota5                              =   totalNotasPessoalJsonArray.getJSONObject((int)5).getString("nota").toString();
            totalNota6                              =   totalNotasPessoalJsonArray.getJSONObject((int)6).getString("nota").toString();
            totalNota7                              =   totalNotasPessoalJsonArray.getJSONObject((int)7).getString("nota").toString();
            totalNota8                              =   totalNotasPessoalJsonArray.getJSONObject((int)8).getString("nota").toString();
            totalNota9                              =   totalNotasPessoalJsonArray.getJSONObject((int)9).getString("nota").toString();
            totalNota10                             =   totalNotasPessoalJsonArray.getJSONObject((int)10).getString("nota").toString();

            //Log.i("PAULO", "totalNota0 -> "+totalNota0);
            //Log.i("PAULO", "totalNota1 -> "+totalNota1);
            //Log.i("PAULO", "totalNota2 -> "+totalNota2);
            //Log.i("PAULO", "totalNota3 -> "+totalNota3);
            //Log.i("PAULO", "totalNota4 -> "+totalNota4);
            //Log.i("PAULO", "totalNota5 -> "+totalNota5);
            //Log.i("PAULO", "totalNota6 -> "+totalNota6);
            //Log.i("PAULO", "totalNota7 -> "+totalNota7);
            //Log.i("PAULO", "totalNota8 -> "+totalNota8);
            //Log.i("PAULO", "totalNota9 -> "+totalNota9);
            //Log.i("PAULO", "totalNota10 -> "+totalNota10);

            generateData(totalNota0, totalNota1, totalNota2, totalNota3, totalNota4, totalNota5, totalNota6, totalNota7, totalNota8, totalNota9, totalNota10);

        }
        catch (JSONException e)
        {
            //alert(e.getMessage());
        }
    }

}

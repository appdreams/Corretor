package br.com.consultec.corretor.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

import java.util.HashMap;
import java.util.Map;

import br.com.consultec.corretor.R;
import br.com.consultec.corretor.model.ReferenciaDaBusca;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoxComparativoNotasFragment extends Fragment
{

    private String idCorretor           =   "";
    private String idProcessoSeletivo   =   "";

    private TextView txtNota0;
    private TextView txtNota1;
    private TextView txtNota2;
    private TextView txtNota3;
    private TextView txtNota4;
    private TextView txtNota5;
    private TextView txtNota6;
    private TextView txtNota7;
    private TextView txtNota8;
    private TextView txtNota9;
    private TextView txtNota10;

    private TextView txtNotaP0;
    private TextView txtNotaP1;
    private TextView txtNotaP2;
    private TextView txtNotaP3;
    private TextView txtNotaP4;
    private TextView txtNotaP5;
    private TextView txtNotaP6;
    private TextView txtNotaP7;
    private TextView txtNotaP8;
    private TextView txtNotaP9;
    private TextView txtNotaP10;

    private TextView txtNotaMP;
    private TextView txtNotaG0;
    private TextView txtNotaG1;
    private TextView txtNotaG2;
    private TextView txtNotaG3;
    private TextView txtNotaG4;
    private TextView txtNotaG5;
    private TextView txtNotaG6;
    private TextView txtNotaG7;
    private TextView txtNotaG8;
    private TextView txtNotaG9;
    private TextView txtNotaG10;

    private TextView txtNotaMG;

    private String notaP0;
    private String notaP1;
    private String notaP2;
    private String notaP3;
    private String notaP4;
    private String notaP5;
    private String notaP6;
    private String notaP7;
    private String notaP8;
    private String notaP9;
    private String notaP10;

    private String notaMP;
    private String notaG0;
    private String notaG1;
    private String notaG2;
    private String notaG3;
    private String notaG4;
    private String notaG5;
    private String notaG6;
    private String notaG7;
    private String notaG8;
    private String notaG9;
    private String notaG10;

    private String notaMG;

    private String modaP0;
    private String modaP1;
    private String modaP2;
    private String modaP3;
    private String modaP4;
    private String modaP5;
    private String modaP6;
    private String modaP7;
    private String modaP8;
    private String modaP9;
    private String modaP10;

    private String modaG0;
    private String modaG1;
    private String modaG2;
    private String modaG3;
    private String modaG4;
    private String modaG5;
    private String modaG6;
    private String modaG7;
    private String modaG8;
    private String modaG9;
    private String modaG10;



    public BoxComparativoNotasFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_box_comparativo_notas, container, false);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        ReferenciaDaBusca referenciaDaBusca = getArguments().getParcelable("dadosBusca");

        idCorretor          =   referenciaDaBusca.getIdCorretor().toString();
        idProcessoSeletivo  =   referenciaDaBusca.getIdProcessoSeletivo().toString();

        Log.i("PAULO","IDC -> "+referenciaDaBusca.getIdCorretor().toString());
        Log.i("PAULO","IDPS -> "+referenciaDaBusca.getIdProcessoSeletivo().toString());
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        bindActivity();

        carregaDadosVolley();
    }


    private void bindActivity()
    {
        txtNota0    = (TextView) getView().findViewById(R.id.txtNota0);
        txtNota1    = (TextView) getView().findViewById(R.id.txtNota1);
        txtNota2    = (TextView) getView().findViewById(R.id.txtNota2);
        txtNota3    = (TextView) getView().findViewById(R.id.txtNota3);
        txtNota4    = (TextView) getView().findViewById(R.id.txtNota4);
        txtNota5    = (TextView) getView().findViewById(R.id.txtNota5);
        txtNota6    = (TextView) getView().findViewById(R.id.txtNota6);
        txtNota7    = (TextView) getView().findViewById(R.id.txtNota7);
        txtNota8    = (TextView) getView().findViewById(R.id.txtNota8);
        txtNota9    = (TextView) getView().findViewById(R.id.txtNota9);
        txtNota10   = (TextView) getView().findViewById(R.id.txtNota10);

        txtNotaP0   = (TextView) getView().findViewById(R.id.txtNotaP0);
        txtNotaP1   = (TextView) getView().findViewById(R.id.txtNotaP1);
        txtNotaP2   = (TextView) getView().findViewById(R.id.txtNotaP2);
        txtNotaP3   = (TextView) getView().findViewById(R.id.txtNotaP3);
        txtNotaP4   = (TextView) getView().findViewById(R.id.txtNotaP4);
        txtNotaP5   = (TextView) getView().findViewById(R.id.txtNotaP5);
        txtNotaP6   = (TextView) getView().findViewById(R.id.txtNotaP6);
        txtNotaP7   = (TextView) getView().findViewById(R.id.txtNotaP7);
        txtNotaP8   = (TextView) getView().findViewById(R.id.txtNotaP8);
        txtNotaP9   = (TextView) getView().findViewById(R.id.txtNotaP9);
        txtNotaP10  = (TextView) getView().findViewById(R.id.txtNotaP10);

        txtNotaMP   = (TextView) getView().findViewById(R.id.txtNotaMP);
        txtNotaG0   = (TextView) getView().findViewById(R.id.txtNotaG0);
        txtNotaG1   = (TextView) getView().findViewById(R.id.txtNotaG1);
        txtNotaG2   = (TextView) getView().findViewById(R.id.txtNotaG2);
        txtNotaG3   = (TextView) getView().findViewById(R.id.txtNotaG3);
        txtNotaG4   = (TextView) getView().findViewById(R.id.txtNotaG4);
        txtNotaG5   = (TextView) getView().findViewById(R.id.txtNotaG5);
        txtNotaG6   = (TextView) getView().findViewById(R.id.txtNotaG6);
        txtNotaG7   = (TextView) getView().findViewById(R.id.txtNotaG7);
        txtNotaG8   = (TextView) getView().findViewById(R.id.txtNotaG8);
        txtNotaG9   = (TextView) getView().findViewById(R.id.txtNotaG9);
        txtNotaG10  = (TextView) getView().findViewById(R.id.txtNotaG10);

        txtNotaMG   = (TextView) getView().findViewById(R.id.txtNotaMG);
    }


    public void carregaDadosVolley()
    {
        carregaDadosVolleyPessoal();
        carregaDadosVolleyGeral();
    }

    public void carregaDadosVolleyPessoal()
    {
        //progressBar.setVisibility(View.VISIBLE);

        String URL = "http://consultec.com.br/APPS/CORRETOR/getDadosTotalNotasPessoal.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            //Log.i("PAULO", "RESP -> "+response.toString());
                            JSONObject jsonObject= new JSONObject(response.toString());
                            separaDadosPessoalJSON(jsonObject);
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


    private void separaDadosPessoalJSON(JSONObject json)
    {
        //Log.i("PAULO", json.toString());
        try {

            JSONObject jsonObject                   =   json;
            JSONArray jsonArray                     =   jsonObject.getJSONArray("dados");

            JSONArray totalNotasPessoalJsonArray    =   jsonArray.getJSONObject((int)0).optJSONArray("total_notas_pessoal");

            notaP0                              =   totalNotasPessoalJsonArray.getJSONObject((int)0).getString("nota").toString();
            modaP0                              =   totalNotasPessoalJsonArray.getJSONObject((int)0).getString("moda").toString();
            notaP1                              =   totalNotasPessoalJsonArray.getJSONObject((int)1).getString("nota").toString();
            modaP1                              =   totalNotasPessoalJsonArray.getJSONObject((int)1).getString("moda").toString();
            notaP2                              =   totalNotasPessoalJsonArray.getJSONObject((int)2).getString("nota").toString();
            modaP2                              =   totalNotasPessoalJsonArray.getJSONObject((int)2).getString("moda").toString();
            notaP3                              =   totalNotasPessoalJsonArray.getJSONObject((int)3).getString("nota").toString();
            modaP3                              =   totalNotasPessoalJsonArray.getJSONObject((int)3).getString("moda").toString();
            notaP4                              =   totalNotasPessoalJsonArray.getJSONObject((int)4).getString("nota").toString();
            modaP4                              =   totalNotasPessoalJsonArray.getJSONObject((int)4).getString("moda").toString();
            notaP5                              =   totalNotasPessoalJsonArray.getJSONObject((int)5).getString("nota").toString();
            modaP5                              =   totalNotasPessoalJsonArray.getJSONObject((int)5).getString("moda").toString();
            notaP6                              =   totalNotasPessoalJsonArray.getJSONObject((int)6).getString("nota").toString();
            modaP6                              =   totalNotasPessoalJsonArray.getJSONObject((int)6).getString("moda").toString();
            notaP7                              =   totalNotasPessoalJsonArray.getJSONObject((int)7).getString("nota").toString();
            modaP7                              =   totalNotasPessoalJsonArray.getJSONObject((int)7).getString("moda").toString();
            notaP8                              =   totalNotasPessoalJsonArray.getJSONObject((int)8).getString("nota").toString();
            modaP8                              =   totalNotasPessoalJsonArray.getJSONObject((int)8).getString("moda").toString();
            notaP9                              =   totalNotasPessoalJsonArray.getJSONObject((int)9).getString("nota").toString();
            modaP9                              =   totalNotasPessoalJsonArray.getJSONObject((int)9).getString("moda").toString();
            notaP10                             =   totalNotasPessoalJsonArray.getJSONObject((int)10).getString("nota").toString();
            modaP10                             =   totalNotasPessoalJsonArray.getJSONObject((int)10).getString("moda").toString();
            notaMP                              =   totalNotasPessoalJsonArray.getJSONObject((int)11).getString("media").toString();

            setDadosNotasPessoal(notaP0, notaP1, notaP2, notaP3, notaP4, notaP5, notaP6, notaP7, notaP8, notaP9, notaP10, notaMP);
            setDestaqueNotasPessoal(modaP0, modaP1, modaP2, modaP3, modaP4, modaP5, modaP6, modaP7, modaP8, modaP9, modaP10);

            /*Log.i("PAULO", "notaP0 -> "+notaP0);
            Log.i("PAULO", "notaP1 -> "+notaP1);
            Log.i("PAULO", "notaP2 -> "+notaP2);
            Log.i("PAULO", "notaP3 -> "+notaP3);
            Log.i("PAULO", "notaP4 -> "+notaP4);
            Log.i("PAULO", "notaP5 -> "+notaP5);
            Log.i("PAULO", "notaP6 -> "+notaP6);
            Log.i("PAULO", "notaP7 -> "+notaP7);
            Log.i("PAULO", "notaP8 -> "+notaP8);
            Log.i("PAULO", "notaP9 -> "+notaP9);
            Log.i("PAULO", "notaP10 -> "+notaP10);
            Log.i("PAULO", "notaMP -> "+notaMP);

            Log.i("PAULO", "modaP0 -> "+modaP0);
            Log.i("PAULO", "modaP1 -> "+modaP1);
            Log.i("PAULO", "modaP2 -> "+modaP2);
            Log.i("PAULO", "modaP3 -> "+modaP3);
            Log.i("PAULO", "modaP4 -> "+modaP4);
            Log.i("PAULO", "modaP5 -> "+modaP5);
            Log.i("PAULO", "modaP6 -> "+modaP6);
            Log.i("PAULO", "modaP7 -> "+modaP7);
            Log.i("PAULO", "modaP8 -> "+modaP8);
            Log.i("PAULO", "modaP9 -> "+modaP9);
            Log.i("PAULO", "modaP10 -> "+modaP10);*/

        }
        catch (JSONException e)
        {
            //alert(e.getMessage());
        }
    }


    private void setDadosNotasPessoal(String notaP0, String notaP1, String notaP2, String notaP3, String notaP4, String notaP5, String notaP6, String notaP7, String notaP8, String notaP9, String notaP10, String notaMP)
    {
        txtNotaP0.setText(notaP0);
        txtNotaP1.setText(notaP1);
        txtNotaP2.setText(notaP2);
        txtNotaP3.setText(notaP3);
        txtNotaP4.setText(notaP4);
        txtNotaP5.setText(notaP5);
        txtNotaP6.setText(notaP6);
        txtNotaP7.setText(notaP7);
        txtNotaP8.setText(notaP8);
        txtNotaP9.setText(notaP9);
        txtNotaP10.setText(notaP10);
        txtNotaMP.setText(notaMP);
    }

    private void setDestaqueNotasPessoal(String modaP0, String modaP1, String modaP2, String modaP3, String modaP4, String modaP5, String modaP6, String modaP7, String modaP8, String modaP9, String modaP10)
    {
        if(modaP0.equals("S"))
        {
            txtNotaP0.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_novo));
            txtNotaP0.setTypeface(null, Typeface.BOLD);
        }
        else
        {
            txtNotaP0.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape));
            txtNotaP0.setTypeface(null, Typeface.NORMAL);
        }

        if(modaP1.equals("S"))
        {
            txtNotaP1.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_novo));
            txtNotaP1.setTypeface(null, Typeface.BOLD);
        }
        else
        {
            txtNotaP1.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape));
            txtNotaP1.setTypeface(null, Typeface.NORMAL);
        }

        if(modaP2.equals("S"))
        {
            txtNotaP2.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_novo));
            txtNotaP2.setTypeface(null, Typeface.BOLD);
        }
        else
        {
            txtNotaP2.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape));
            txtNotaP2.setTypeface(null, Typeface.NORMAL);
        }

        if(modaP3.equals("S"))
        {
            txtNotaP3.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_novo));
            txtNotaP3.setTypeface(null, Typeface.BOLD);
        }
        else
        {
            txtNotaP3.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape));
            txtNotaP3.setTypeface(null, Typeface.NORMAL);
        }

        if(modaP4.equals("S"))
        {
            txtNotaP4.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_novo));
            txtNotaP4.setTypeface(null, Typeface.BOLD);
        }
        else
        {
            txtNotaP4.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape));
            txtNotaP4.setTypeface(null, Typeface.NORMAL);
        }

        if(modaP5.equals("S"))
        {
            txtNotaP5.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_novo));
            txtNotaP5.setTypeface(null, Typeface.BOLD);
        }
        else
        {
            txtNotaP5.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape));
            txtNotaP5.setTypeface(null, Typeface.NORMAL);
        }

        if(modaP6.equals("S"))
        {
            txtNotaP6.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_novo));
            txtNotaP6.setTypeface(null, Typeface.BOLD);
        }
        else
        {
            txtNotaP6.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape));
            txtNotaP6.setTypeface(null, Typeface.NORMAL);
        }

        if(modaP7.equals("S"))
        {
            txtNotaP7.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_novo));
            txtNotaP7.setTypeface(null, Typeface.BOLD);
        }
        else
        {
            txtNotaP7.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape));
            txtNotaP7.setTypeface(null, Typeface.NORMAL);
        }

        if(modaP8.equals("S"))
        {
            txtNotaP8.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_novo));
            txtNotaP8.setTypeface(null, Typeface.BOLD);
        }
        else
        {
            txtNotaP8.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape));
            txtNotaP8.setTypeface(null, Typeface.NORMAL);
        }

        if(modaP9.equals("S"))
        {
            txtNotaP9.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_novo));
            txtNotaP9.setTypeface(null, Typeface.BOLD);
        }
        else
        {
            txtNotaP9.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape));
            txtNotaP9.setTypeface(null, Typeface.NORMAL);
        }

        if(modaP10.equals("S"))
        {
            txtNotaP10.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_novo));
            txtNotaP10.setTypeface(null, Typeface.BOLD);
        }
        else
        {
            txtNotaP10.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape));
            txtNotaP10.setTypeface(null, Typeface.NORMAL);
        }

    }


    public void carregaDadosVolleyGeral()
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
                            //Log.i("PAULO", "RESP -> "+response.toString());
                            JSONObject jsonObject= new JSONObject(response.toString());
                            separaDadosGeralJSON(jsonObject);
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


    private void separaDadosGeralJSON(JSONObject json)
    {
        //Log.i("PAULO", json.toString());
        try {

            JSONObject jsonObject                   =   json;
            JSONArray jsonArray                     =   jsonObject.getJSONArray("dados");

            JSONArray totalNotasGeralJsonArray    =   jsonArray.getJSONObject((int)0).optJSONArray("total_notas_geral");

            notaG0                              =   totalNotasGeralJsonArray.getJSONObject((int)0).getString("nota").toString();
            modaG0                              =   totalNotasGeralJsonArray.getJSONObject((int)0).getString("moda").toString();
            notaG1                              =   totalNotasGeralJsonArray.getJSONObject((int)1).getString("nota").toString();
            modaG1                              =   totalNotasGeralJsonArray.getJSONObject((int)1).getString("moda").toString();
            notaG2                              =   totalNotasGeralJsonArray.getJSONObject((int)2).getString("nota").toString();
            modaG2                              =   totalNotasGeralJsonArray.getJSONObject((int)2).getString("moda").toString();
            notaG3                              =   totalNotasGeralJsonArray.getJSONObject((int)3).getString("nota").toString();
            modaG3                              =   totalNotasGeralJsonArray.getJSONObject((int)3).getString("moda").toString();
            notaG4                              =   totalNotasGeralJsonArray.getJSONObject((int)4).getString("nota").toString();
            modaG4                              =   totalNotasGeralJsonArray.getJSONObject((int)4).getString("moda").toString();
            notaG5                              =   totalNotasGeralJsonArray.getJSONObject((int)5).getString("nota").toString();
            modaG5                              =   totalNotasGeralJsonArray.getJSONObject((int)5).getString("moda").toString();
            notaG6                              =   totalNotasGeralJsonArray.getJSONObject((int)6).getString("nota").toString();
            modaG6                              =   totalNotasGeralJsonArray.getJSONObject((int)6).getString("moda").toString();
            notaG7                              =   totalNotasGeralJsonArray.getJSONObject((int)7).getString("nota").toString();
            modaG7                              =   totalNotasGeralJsonArray.getJSONObject((int)7).getString("moda").toString();
            notaG8                              =   totalNotasGeralJsonArray.getJSONObject((int)8).getString("nota").toString();
            modaG8                              =   totalNotasGeralJsonArray.getJSONObject((int)8).getString("moda").toString();
            notaG9                              =   totalNotasGeralJsonArray.getJSONObject((int)9).getString("nota").toString();
            modaG9                              =   totalNotasGeralJsonArray.getJSONObject((int)9).getString("moda").toString();
            notaG10                             =   totalNotasGeralJsonArray.getJSONObject((int)10).getString("nota").toString();
            modaG10                             =   totalNotasGeralJsonArray.getJSONObject((int)10).getString("moda").toString();
            notaMG                              =   totalNotasGeralJsonArray.getJSONObject((int)11).getString("media").toString();

            setDadosNotasGeral(notaG0, notaG1, notaG2, notaG3, notaG4, notaG5, notaG6, notaG7, notaG8, notaG9, notaG10, notaMG);
            setDestaqueNotasGeral(modaG0, modaG1, modaG2, modaG3, modaG4, modaG5, modaG6, modaG7, modaG8, modaG9, modaG10);

            /*Log.i("PAULO", "notaG0 -> "+notaG0);
            Log.i("PAULO", "notaG1 -> "+notaG1);
            Log.i("PAULO", "notaG2 -> "+notaG2);
            Log.i("PAULO", "notaG3 -> "+notaG3);
            Log.i("PAULO", "notaG4 -> "+notaG4);
            Log.i("PAULO", "notaG5 -> "+notaG5);
            Log.i("PAULO", "notaG6 -> "+notaG6);
            Log.i("PAULO", "notaG7 -> "+notaG7);
            Log.i("PAULO", "notaG8 -> "+notaG8);
            Log.i("PAULO", "notaG9 -> "+notaG9);
            Log.i("PAULO", "notaG10 -> "+notaG10);
            Log.i("PAULO", "notaMG -> "+notaMG);

            Log.i("PAULO", "modaG0 -> "+modaG0);
            Log.i("PAULO", "modaG1 -> "+modaG1);
            Log.i("PAULO", "modaG2 -> "+modaG2);
            Log.i("PAULO", "modaG3 -> "+modaG3);
            Log.i("PAULO", "modaG4 -> "+modaG4);
            Log.i("PAULO", "modaG5 -> "+modaG5);
            Log.i("PAULO", "modaG6 -> "+modaG6);
            Log.i("PAULO", "modaG7 -> "+modaG7);
            Log.i("PAULO", "modaG8 -> "+modaG8);
            Log.i("PAULO", "modaG9 -> "+modaG9);
            Log.i("PAULO", "modaG10 -> "+modaG10);*/

        }
        catch (JSONException e)
        {
            //alert(e.getMessage());
        }
    }


    private void setDadosNotasGeral(String notaG0, String notaG1, String notaG2, String notaG3, String notaG4, String notaG5, String notaG6, String notaG7, String notaG8, String notaG9, String notaG10, String notaMG)
    {
        txtNotaG0.setText(notaG0);
        txtNotaG1.setText(notaG1);
        txtNotaG2.setText(notaG2);
        txtNotaG3.setText(notaG3);
        txtNotaG4.setText(notaG4);
        txtNotaG5.setText(notaG5);
        txtNotaG6.setText(notaG6);
        txtNotaG7.setText(notaG7);
        txtNotaG8.setText(notaG8);
        txtNotaG9.setText(notaG9);
        txtNotaG10.setText(notaG10);
        txtNotaMG.setText(notaMG);
    }

    private void setDestaqueNotasGeral(String modaG0, String modaG1, String modaG2, String modaG3, String modaG4, String modaG5, String modaG6, String modaG7, String modaG8, String modaG9, String modaG10)
    {
        if(modaG0.equals("S"))
        {
            txtNotaG0.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_novo));
            txtNotaG0.setTypeface(null, Typeface.BOLD);
        }
        else
        {
            txtNotaG0.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape));
            txtNotaG0.setTypeface(null, Typeface.NORMAL);
        }

        if(modaG1.equals("S"))
        {
            txtNotaG1.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_novo));
            txtNotaG1.setTypeface(null, Typeface.BOLD);
        }
        else
        {
            txtNotaG1.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape));
            txtNotaG1.setTypeface(null, Typeface.NORMAL);
        }

        if(modaG2.equals("S"))
        {
            txtNotaG2.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_novo));
            txtNotaG2.setTypeface(null, Typeface.BOLD);
        }
        else
        {
            txtNotaG2.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape));
            txtNotaG2.setTypeface(null, Typeface.NORMAL);
        }

        if(modaG3.equals("S"))
        {
            txtNotaG3.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_novo));
            txtNotaG3.setTypeface(null, Typeface.BOLD);
        }
        else
        {
            txtNotaG3.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape));
            txtNotaG3.setTypeface(null, Typeface.NORMAL);
        }

        if(modaG4.equals("S"))
        {
            txtNotaG4.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_novo));
            txtNotaG4.setTypeface(null, Typeface.BOLD);
        }
        else
        {
            txtNotaG4.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape));
            txtNotaG4.setTypeface(null, Typeface.NORMAL);
        }

        if(modaG5.equals("S"))
        {
            txtNotaG5.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_novo));
            txtNotaG5.setTypeface(null, Typeface.BOLD);
        }
        else
        {
            txtNotaG5.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape));
            txtNotaG5.setTypeface(null, Typeface.NORMAL);
        }

        if(modaG6.equals("S"))
        {
            txtNotaG6.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_novo));
            txtNotaG6.setTypeface(null, Typeface.BOLD);
        }
        else
        {
            txtNotaG6.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape));
            txtNotaG6.setTypeface(null, Typeface.NORMAL);
        }

        if(modaG7.equals("S"))
        {
            txtNotaG7.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_novo));
            txtNotaG7.setTypeface(null, Typeface.BOLD);
        }
        else
        {
            txtNotaG7.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape));
            txtNotaG7.setTypeface(null, Typeface.NORMAL);
        }

        if(modaG8.equals("S"))
        {
            txtNotaG8.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_novo));
            txtNotaG8.setTypeface(null, Typeface.BOLD);
        }
        else
        {
            txtNotaG8.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape));
            txtNotaG8.setTypeface(null, Typeface.NORMAL);
        }

        if(modaG9.equals("S"))
        {
            txtNotaG9.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_novo));
            txtNotaG9.setTypeface(null, Typeface.BOLD);
        }
        else
        {
            txtNotaG9.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape));
            txtNotaG9.setTypeface(null, Typeface.NORMAL);
        }

        if(modaG10.equals("S"))
        {
            txtNotaG10.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_novo));
            txtNotaG10.setTypeface(null, Typeface.BOLD);
        }
        else
        {
            txtNotaG10.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape));
            txtNotaG10.setTypeface(null, Typeface.NORMAL);
        }

    }

}

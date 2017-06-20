package br.com.consultec.corretor.fragments;


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
public class BoxTempoMedioFragment extends Fragment
{

    private String idCorretor           =   "";
    private String idProcessoSeletivo   =   "";

    private TextView txtTempoMedioPessoal;
    private TextView txtTempoMedioGeral;

    private String tempoMedioPessoal;
    private String tempoMedioGeral;


    public BoxTempoMedioFragment()
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_box_tempo_medio, container, false);
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
        txtTempoMedioPessoal = (TextView) getView().findViewById(R.id.txtTempoMedioPessoal);
        txtTempoMedioGeral   = (TextView) getView().findViewById(R.id.txtTempoMedioGeral);
    }


    public void carregaDadosVolley()
    {
        //progressBar.setVisibility(View.VISIBLE);

        String URL = "http://consultec.com.br/APPS/CORRETOR/getDadosTempoMedio.php";

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
            JSONArray tempoMediaJsonArray           =   jsonArray.getJSONObject((int)0).optJSONArray("tempo_medio");

            tempoMedioPessoal    =   tempoMediaJsonArray.getJSONObject((int)0).getString("pessoal").toString();
            tempoMedioGeral      =   tempoMediaJsonArray.getJSONObject((int)0).getString("geral").toString();

            //Log.i("PAULO", " -> "+tempoMediaJsonArray.getJSONObject((int)0).getString("pessoal").toString());
            //Log.i("PAULO", " -> "+tempoMediaJsonArray.getJSONObject((int)0).getString("geral").toString());

            setDadosTempoMedia(tempoMedioPessoal, tempoMedioGeral);

        }
        catch (JSONException e)
        {
            //alert(e.getMessage());
        }
    }


    private void setDadosTempoMedia(String tempoMedioPessoal, String tempoMedioGeral)
    {
        txtTempoMedioPessoal.setText(tempoMedioPessoal);
        txtTempoMedioGeral.setText(tempoMedioGeral);
    }

}

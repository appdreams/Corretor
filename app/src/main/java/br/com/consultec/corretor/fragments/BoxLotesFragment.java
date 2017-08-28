package br.com.consultec.corretor.fragments;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
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
import lecho.lib.hellocharts.view.ColumnChartView;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoxLotesFragment extends Fragment {

    private String idCorretor           =   "";
    private String idProcessoSeletivo   =   "";

    private TableLayout tabelaLote;
    private TableRow linhaLote;


    public BoxLotesFragment()
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
        return inflater.inflate(R.layout.fragment_box_lotes, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        bindActivity();
        configuraTabela();
    }


    private void bindActivity()
    {
        tabelaLote = (TableLayout) getView().findViewById(R.id.tabelaLotes);
        linhaLote  = (TableRow) getView().findViewById(R.id.linhaLotes);
    }


    private void configuraTabela()
    {

        carregaDadosVolley();
    }


    public void carregaDadosVolley()
    {
        //progressBar.setVisibility(View.VISIBLE);

        String URL = "http://consultec.com.br/APPS/CORRETOR/getDadosLotes.php";

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
        Log.i("PAULO", json.toString());
        try {

            JSONArray dadosLotes     =   null;

            JSONObject jsonObject    =   json;
            JSONArray jsonArray      =   jsonObject.getJSONArray("dados");

            dadosLotes               =   jsonArray.getJSONObject((int)0).optJSONArray("lotes");

            //Log.i("PAULO", "$--> "+dadosLotes.length()+"");

            if(dadosLotes != null)
            {
                montaTabela(dadosLotes);
            }

        }
        catch (JSONException e)
        {
            //alert(e.getMessage());
        }
    }



    private void montaTabela(JSONArray dados)
    {
        Resources resource = getContext().getResources();

        for (int i = 0; i < dados.length(); ++i) {

            TableRow linha = (TableRow) View.inflate(getActivity(), R.layout.row_box_lotes, null);

            try {

                TextView txtNome         = (TextView) linha.findViewById(R.id.txtNome);
                TextView txtQuantidade   = (TextView) linha.findViewById(R.id.txtQuantidade);
                TextView txtInicializado = (TextView) linha.findViewById(R.id.txtInicializado);
                TextView txtFinalizado   = (TextView) linha.findViewById(R.id.txtFinalizado);
                TextView txtStatus       = (TextView) linha.findViewById(R.id.txtStatus);

                txtNome.setText(dados.getJSONObject(i).getString("nome"));
                txtQuantidade.setText(dados.getJSONObject(i).getString("quantidade"));
                txtStatus.setText(dados.getJSONObject(i).getString("status"));

                if(dados.getJSONObject(i).getString("inicio").equals("null"))
                {
                    txtInicializado.setText("-------");
                }
                else
                {
                    txtInicializado.setText(dados.getJSONObject(i).getString("inicio"));
                }

                if(dados.getJSONObject(i).getString("fim").equals("null"))
                {
                    txtFinalizado.setText("-------");
                }
                else
                {
                    txtFinalizado.setText(dados.getJSONObject(i).getString("fim"));
                }

                linha.setBackground(resource.getDrawable(R.drawable.cell_shape_selected));

                switch (dados.getJSONObject(i).getString("status")) {
                    case "NOVO":
                        txtNome.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_novo));
                        txtQuantidade.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_novo));
                        txtInicializado.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_novo));
                        txtFinalizado.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_novo));
                        txtStatus.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_novo));
                        break;
                    case "ABERTO":
                        txtNome.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_aberto));
                        txtQuantidade.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_aberto));
                        txtInicializado.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_aberto));
                        txtFinalizado.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_aberto));
                        txtStatus.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_aberto));
                        break;
                    case "FECHADO":
                        txtNome.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_fechado));
                        txtQuantidade.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_fechado));
                        txtInicializado.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_fechado));
                        txtFinalizado.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_fechado));
                        txtStatus.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_fechado));
                        break;
                    case "CANCELADO":
                        txtNome.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_cancelado));
                        txtQuantidade.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_cancelado));
                        txtInicializado.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_cancelado));
                        txtFinalizado.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_cancelado));
                        txtStatus.setBackgroundDrawable(getView().getResources().getDrawable(R.drawable.cell_shape_cancelado));
                        break;
                    default:
                        //System.out.println("Este não é um dia válido!");
                }

                tabelaLote.addView(linha);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}

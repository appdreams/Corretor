package br.com.consultec.corretor.fragments;


import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import at.grabner.circleprogress.CircleProgressView;
import br.com.consultec.corretor.R;
import br.com.consultec.corretor.activity.MainActivity;
import br.com.consultec.corretor.model.ReferenciaDaBusca;

/**
 * A simple {@link Fragment} subclass.
 */
public class GraficoPrincipalFragment extends Fragment
{
    private String tlTotalAtribuido;
    private String tlTotalCorrigido;
    private String tlTotalNaoCorrigido;
    private String laTotalAtribuido;
    private String laTotalCorrigido;
    private String laTotalNaoCorrigido;
    private String laLoteAberto;
    private ProgressBar progressBar;
    private Button btnTotalLotes;
    private Button btnTotalLoteAtual;
    private TextView txtStatusInfo;
    private TextView txtTotalCorrigidas;
    private TextView txtTotalNaoCorrigidas;
    private CircleProgressView circleProgressView;

    private CardView boxNaoExisteLoteDisponivel;
    private CardView boxNaoExisteLoteAberto;

    private Boolean flagLoteDisponivel  = false;
    private Boolean flagLoteAberto      = false;
    private Boolean flagBtnTotalLotes   = true;
    private Boolean flagBtnLoteAtual    = false;

    private String idCorretor           = "";
    private String idProcessoSeletivo   = "";


    public GraficoPrincipalFragment()
    {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grafico_principal, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        bindActivity();
        addListenerOnButtonTotalLotes();
        addListenerOnButtonTotalLoteAtual();

        carregaDadosVolley();
    }


    private void bindActivity()
    {
        circleProgressView       =   (CircleProgressView) getView().findViewById(R.id.circleProgressView);
        progressBar              =   (ProgressBar) getView().findViewById(R.id.progressBar);
        btnTotalLotes            =   (Button) getView().findViewById(R.id.btnTotalLotes);
        btnTotalLoteAtual        =   (Button) getView().findViewById(R.id.btnTotalLoteAtual);
        txtStatusInfo            =   (TextView) getView().findViewById(R.id.txtStatusInfo);
        txtTotalCorrigidas       =   (TextView) getView().findViewById(R.id.txtTotalCorrigidas);
        txtTotalNaoCorrigidas    =   (TextView) getView().findViewById(R.id.txtTotalNaoCorrigidas);

        boxNaoExisteLoteDisponivel  = (CardView) getView().findViewById(R.id.boxNaoExisteLoteDisponivel);
        boxNaoExisteLoteAberto      = (CardView) getView().findViewById(R.id.boxNaoExisteLoteAberto);
    }


    private void addListenerOnButtonTotalLotes()
    {
        btnTotalLotes.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                flagBtnTotalLotes = true;
                flagBtnLoteAtual  = false;
                //setDadosTotalLotes();
                validaLotes(tlTotalAtribuido, tlTotalCorrigido, tlTotalNaoCorrigido, laTotalAtribuido, laTotalCorrigido, laTotalNaoCorrigido, laLoteAberto);
            }
        });
    }


    private void addListenerOnButtonTotalLoteAtual()
    {
        btnTotalLoteAtual.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                flagBtnTotalLotes = false;
                flagBtnLoteAtual  = true;
                //setDadosLoteAtual();
                validaLotes(tlTotalAtribuido, tlTotalCorrigido, tlTotalNaoCorrigido, laTotalAtribuido, laTotalCorrigido, laTotalNaoCorrigido, laLoteAberto);
            }
        });
    }


    public void carregaDadosVolley()
    {
        progressBar.setVisibility(View.VISIBLE);

        String JSON_URL = "http://consultec.com.br/APPS/CORRETOR/getDadosGraficoPrincipal.php";
        JSONObject dadosObj = new JSONObject();

        try
        {
            dadosObj.put("idCorretor", idCorretor);
            dadosObj.put("idProcessoSeletivo", idProcessoSeletivo);
        }
        catch (Exception e)
        {

        }

        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET,JSON_URL,dadosObj,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        separaDadosJSON(response);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        //alert(error.getMessage());
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(jsonObjRequest);/**/

    }


    private void separaDadosJSON(JSONObject json)
    {
        Log.i("PAULO", json.toString());
        try {

            JSONObject totalGeralLotesJsonObject    =   null;
            JSONObject totalLoteAtualJsonObject     =   null;

            JSONObject jsonObject                   =   json;
            JSONArray jsonArray                     =   jsonObject.getJSONArray("dados");

            JSONArray totalGeralLotesJsonArray      =   jsonArray.getJSONObject((int)0).optJSONArray("total_lotes");
            JSONArray totalLoteAtualJsonArray       =   jsonArray.getJSONObject((int)0).optJSONArray("total_lote_atual");

            tlTotalAtribuido                        =   totalGeralLotesJsonArray.getJSONObject((int)0).getString("total_atribuido").toString();
            tlTotalCorrigido                        =   totalGeralLotesJsonArray.getJSONObject((int)0).getString("total_corrigido").toString();
            tlTotalNaoCorrigido                     =   totalGeralLotesJsonArray.getJSONObject((int)0).getString("total_nao_corrigido").toString();

            laTotalAtribuido                        =   totalLoteAtualJsonArray.getJSONObject((int)0).getString("total_atribuido").toString();
            laTotalCorrigido                        =   totalLoteAtualJsonArray.getJSONObject((int)0).getString("total_corrigido").toString();
            laTotalNaoCorrigido                     =   totalLoteAtualJsonArray.getJSONObject((int)0).getString("total_nao_corrigido").toString();
            laLoteAberto                            =   totalLoteAtualJsonArray.getJSONObject((int)0).getString("lote_aberto").toString();

            Log.i("PAULO", totalGeralLotesJsonArray.toString());
            Log.i("PAULO", totalLoteAtualJsonArray.toString());

            Log.i("PAULO", tlTotalAtribuido);
            Log.i("PAULO", tlTotalCorrigido);
            Log.i("PAULO", tlTotalNaoCorrigido);
            Log.i("PAULO", laTotalAtribuido);
            Log.i("PAULO", laTotalCorrigido);
            Log.i("PAULO", laTotalNaoCorrigido);
            Log.i("PAULO", laLoteAberto);

            validaLotes(tlTotalAtribuido, tlTotalCorrigido, tlTotalNaoCorrigido, laTotalAtribuido, laTotalCorrigido, laTotalNaoCorrigido, laLoteAberto);

        } catch (JSONException e) {
            //alert(e.getMessage());
        }/**/
    }


    private void validaLotes(String tlTotalAtribuido, String tlTotalCorrigido, String tlTotalNaoCorrigido, String laTotalAtribuido, String laTotalCorrigido, String laTotalNaoCorrigido, String laLoteAberto)
    {
        if((tlTotalAtribuido.equals("0"))&&(tlTotalCorrigido.equals("0"))&&(tlTotalNaoCorrigido.equals("0"))&&(laTotalAtribuido.equals("0"))&&(laTotalCorrigido.equals("0"))&&(laTotalNaoCorrigido.equals("0")))
        {
            //Não existe lotes
            Log.i("PAULO", "Não existe lotes");
            showBoxNaoExisteLoteAberto(false);
            showBoxNaoExisteLoteDisponivel(true);

            circleProgressView.setValueAnimated(0);
            txtTotalCorrigidas.setText("0");
            txtTotalNaoCorrigidas.setText("0");
            txtStatusInfo.setText("Não existe Lotes disponíveis.");
        }
        else
        {
            if((flagBtnTotalLotes == true)&&(flagBtnLoteAtual == false))
            {
                showBoxNaoExisteLoteAberto(false);
                showBoxNaoExisteLoteDisponivel(false);
                setDadosTotalLotes();
            }
            else if((flagBtnTotalLotes == false)&&(flagBtnLoteAtual == true))
            {
                if(laLoteAberto.equals("N"))
                {
                    //Não existe lote aberto
                    Log.i("PAULO", "Não existe lote aberto");
                    showBoxNaoExisteLoteAberto(true);
                    showBoxNaoExisteLoteDisponivel(false);

                    circleProgressView.setValueAnimated(0);
                    txtTotalCorrigidas.setText("0");
                    txtTotalNaoCorrigidas.setText("0");
                    txtStatusInfo.setText("Não existe Lote aberto.");
                }
                else
                {
                    Log.i("PAULO", "Foi aqui!");
                    showBoxNaoExisteLoteAberto(false);
                    showBoxNaoExisteLoteDisponivel(false);
                    setDadosLoteAtual();
                }

            }

        }
    }


    private void setDadosTotalLotes()
    {
        if(tlTotalAtribuido != "0")
        {
            Integer porcentagem = (Integer.parseInt(tlTotalCorrigido)*100)/Integer.parseInt(tlTotalAtribuido);

            circleProgressView.setValueAnimated(porcentagem);
            txtTotalCorrigidas.setText(tlTotalCorrigido);
            txtTotalNaoCorrigidas.setText(tlTotalNaoCorrigido);
            txtStatusInfo.setText("Total geral de redações corrigidas.");
        }
    }


    private void setDadosLoteAtual()
    {
        if(tlTotalAtribuido != "0") {
            Integer porcentagem = (Integer.parseInt(laTotalCorrigido) * 100) / Integer.parseInt(laTotalAtribuido);

            circleProgressView.setValueAnimated(porcentagem);
            txtTotalCorrigidas.setText(laTotalCorrigido);
            txtTotalNaoCorrigidas.setText(laTotalNaoCorrigido);
            txtStatusInfo.setText("Total de redações corrigidas do lote atual.");
        }
    }


    public void showBoxNaoExisteLoteDisponivel(Boolean valor)
    {
        if(valor == true)
        {
            ((MainActivity) getActivity()).desabilitaScroll();
            boxNaoExisteLoteDisponivel.setVisibility(android.view.View.VISIBLE);
        }
        else
        {
            ((MainActivity) getActivity()).habilitaScroll();
            boxNaoExisteLoteDisponivel.setVisibility(android.view.View.INVISIBLE);
        }
    }


    public void showBoxNaoExisteLoteAberto(Boolean valor)
    {
        if(valor == true)
        {
            boxNaoExisteLoteAberto.setVisibility(android.view.View.VISIBLE);
        }
        else
        {
            boxNaoExisteLoteAberto.setVisibility(android.view.View.INVISIBLE);
        }
    }

}

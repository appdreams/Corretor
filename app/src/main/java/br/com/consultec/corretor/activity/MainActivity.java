package br.com.consultec.corretor.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.transfuse.annotations.View;

import java.util.HashMap;

import br.com.consultec.corretor.R;
import br.com.consultec.corretor.domain.RequestHandler;
import br.com.consultec.corretor.fragments.BoxComparativoNotasFragment;
import br.com.consultec.corretor.fragments.BoxHojeFragment;
import br.com.consultec.corretor.fragments.BoxImagemRuimFragment;
import br.com.consultec.corretor.fragments.BoxLotesFragment;
import br.com.consultec.corretor.fragments.BoxNotaMediaFragment;
import br.com.consultec.corretor.fragments.BoxTempoMedioFragment;
import br.com.consultec.corretor.fragments.Grafico1Fragment;
import br.com.consultec.corretor.fragments.Grafico2Fragment;
import br.com.consultec.corretor.fragments.GraficoNotasGeralFragment;
import br.com.consultec.corretor.fragments.GraficoNotasPessoalFragment;
import br.com.consultec.corretor.fragments.GraficoPrincipalFragment;
import br.com.consultec.corretor.fragments.GraficoProdutividadeFragment;
import br.com.consultec.corretor.model.ReferenciaDaBusca;
import br.com.consultec.corretor.utils.CustomScrollView;

public class MainActivity extends BaseActivity
{
    private CustomScrollView scrollView;
    private TextView txtUltimaAtualizacao;
    private LinearLayout alvoGraficoPrincipal;
    private LinearLayout alvoGraficoNotasPessoal;
    private LinearLayout alvoGraficoNotasGeral;
    private LinearLayout alvoGraficoProdutividade;
    private LinearLayout alvoBoxNotaMedia;
    private LinearLayout alvoBoxTempoMedio;
    private LinearLayout alvoBoxHoje;
    private LinearLayout alvoBoxImagemRuim;
    private LinearLayout alvoBoxComparativoNotas;
    private LinearLayout alvoBoxLotes;
    private SwipeRefreshLayout swipeContainer;

    private GraficoPrincipalFragment graficoPrincipalFragment;
    private GraficoNotasPessoalFragment graficoNotasPessoalFragment;
    private GraficoNotasGeralFragment graficoNotasGeralFragment;
    private BoxNotaMediaFragment boxNotaMediaFragment;
    private GraficoProdutividadeFragment graficoProdutividadeFragment;
    private BoxTempoMedioFragment boxTempoMedioFragment;
    private BoxHojeFragment boxHojeFragment;
    private BoxImagemRuimFragment boxImagemRuimFragment;
    private BoxComparativoNotasFragment boxComparativoNotasFragment;

    private Bundle dadosDaBusca;

    //private String url                  = "http://www.openturismo.com.br";
    private String idCorretor           = "127";
    private String idProcessoSeletivo   = "915017";
    //private String idCorretor           = "1";
    //private String idProcessoSeletivo   = "201700";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpToolbar("CORRETOR");
        //setUpNavDrawer();

        bindActivity();

        setReferenciaDaBusca(idCorretor,idProcessoSeletivo);

        addGraficoPrincipal();
        addGraficoNotasPessoal();
        addGraficoNotasGeral();
        addGraficoProdutividade();
        addBoxNotaMedia();
        addBoxTempoMedio();
        addBoxHoje();
        addBoxImagemRuim();
        addBoxComparativoNotas();
        addBoxLotes();

        configuraSwipRefresh();

        carregaDadosVolley(true);

        desabilitaScroll();
        habilitaScroll();
    }


    private void bindActivity()
    {
        scrollView                  = (CustomScrollView) findViewById(R.id.scrollView);
        txtUltimaAtualizacao        = (TextView) findViewById(R.id.txtUltimaAtualizacao);
        alvoGraficoPrincipal        = (LinearLayout) findViewById(R.id.alvo_grafico_principal);
        alvoGraficoNotasPessoal     = (LinearLayout) findViewById(R.id.alvo_grafico_notas_pessoal);
        alvoGraficoNotasGeral       = (LinearLayout) findViewById(R.id.alvo_grafico_notas_geral);
        alvoGraficoProdutividade    = (LinearLayout) findViewById(R.id.alvo_grafico_produtividade);
        alvoBoxNotaMedia            = (LinearLayout) findViewById(R.id.alvo_box_nota_media);
        alvoBoxTempoMedio           = (LinearLayout) findViewById(R.id.alvo_box_tempo_medio);
        alvoBoxHoje                 = (LinearLayout) findViewById(R.id.alvo_box_hoje);
        alvoBoxImagemRuim           = (LinearLayout) findViewById(R.id.alvo_box_imagem_ruim);
        alvoBoxComparativoNotas     = (LinearLayout) findViewById(R.id.alvo_box_comparativo_notas);
        alvoBoxLotes                = (LinearLayout) findViewById(R.id.alvo_box_lotes);
        swipeContainer              = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        dadosDaBusca                = new Bundle();

        graficoPrincipalFragment    = new GraficoPrincipalFragment();
        graficoNotasPessoalFragment = new GraficoNotasPessoalFragment();
        graficoNotasGeralFragment   = new GraficoNotasGeralFragment();
        boxNotaMediaFragment        = new BoxNotaMediaFragment();
        graficoProdutividadeFragment= new GraficoProdutividadeFragment();
        boxTempoMedioFragment       = new BoxTempoMedioFragment();
        boxHojeFragment             = new BoxHojeFragment();
        boxImagemRuimFragment       = new BoxImagemRuimFragment();
        boxComparativoNotasFragment = new BoxComparativoNotasFragment();

    }


    private void setValorUltimaAtualizacao(String valorUltimaAtualizacao)
    {
        txtUltimaAtualizacao.setText("Última atualização foi "+valorUltimaAtualizacao);
    }


    private void addGraficoPrincipal()
    {
        //GraficoPrincipalFragment graficoPrincipalFragment = new GraficoPrincipalFragment();
        graficoPrincipalFragment.setArguments(getReferenciaDaBusca());
        FragmentManager fragmentManagerGraficoPrincipal = getSupportFragmentManager();
        fragmentManagerGraficoPrincipal.beginTransaction().replace(R.id.alvo_grafico_principal, graficoPrincipalFragment).commit();
        //graficoPrincipalFragment.carregaDadosVolley();
    }


    private void addGraficoNotasPessoal()
    {
        graficoNotasPessoalFragment.setArguments(getReferenciaDaBusca());
        FragmentManager fragmentManagerGraficoNotasPessoal = getSupportFragmentManager();
        fragmentManagerGraficoNotasPessoal.beginTransaction().replace(R.id.alvo_grafico_notas_pessoal, graficoNotasPessoalFragment).commit();
    }

    private void addGraficoNotasGeral()
    {
        graficoNotasGeralFragment.setArguments(getReferenciaDaBusca());
        FragmentManager fragmentManagerGraficoNotasPessoal = getSupportFragmentManager();
        fragmentManagerGraficoNotasPessoal.beginTransaction().replace(R.id.alvo_grafico_notas_geral, graficoNotasGeralFragment).commit();
    }

    private void addGraficoProdutividade()
    {
        //graficoProdutividadeFragment.carregaDadosVolley();
        FragmentManager fragmentManagerGraficoProdutividade = getSupportFragmentManager();
        fragmentManagerGraficoProdutividade.beginTransaction().replace(R.id.alvo_grafico_produtividade, graficoProdutividadeFragment).commit();
    }

    private void addBoxNotaMedia()
    {
        boxNotaMediaFragment.setArguments(getReferenciaDaBusca());
        FragmentManager fragmentManagerBoxNotaMedia = getSupportFragmentManager();
        fragmentManagerBoxNotaMedia.beginTransaction().replace(R.id.alvo_box_nota_media, boxNotaMediaFragment).commit();
    }

    private void addBoxTempoMedio()
    {
        boxTempoMedioFragment.setArguments(getReferenciaDaBusca());
        FragmentManager fragmentManagerBoxTempoMedio = getSupportFragmentManager();
        fragmentManagerBoxTempoMedio.beginTransaction().replace(R.id.alvo_box_tempo_medio, boxTempoMedioFragment).commit();
    }

    private void addBoxHoje()
    {
        boxHojeFragment.setArguments(getReferenciaDaBusca());
        FragmentManager fragmentManagerBoxHoje = getSupportFragmentManager();
        fragmentManagerBoxHoje.beginTransaction().replace(R.id.alvo_box_hoje, boxHojeFragment).commit();
    }

    private void addBoxImagemRuim()
    {
        boxImagemRuimFragment.setArguments(getReferenciaDaBusca());
        FragmentManager fragmentManagerBoxImagemRuim = getSupportFragmentManager();
        fragmentManagerBoxImagemRuim.beginTransaction().replace(R.id.alvo_box_imagem_ruim, boxImagemRuimFragment).commit();
    }


    private void addBoxComparativoNotas()
    {
        boxComparativoNotasFragment.setArguments(getReferenciaDaBusca());
        FragmentManager fragmentManagerBoxComparativoNotas = getSupportFragmentManager();
        fragmentManagerBoxComparativoNotas.beginTransaction().replace(R.id.alvo_box_comparativo_notas, boxComparativoNotasFragment).commit();
    }


    private void addBoxLotes()
    {
        FragmentManager fragmentManagerBoxLotes = getSupportFragmentManager();
        fragmentManagerBoxLotes.beginTransaction().replace(R.id.alvo_box_lotes, new BoxLotesFragment()).commit();
    }


    private void configuraSwipRefresh()
    {
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                carregaDadosVolley(false);
                graficoPrincipalFragment.carregaDadosVolley();
                graficoNotasPessoalFragment.carregaDadosVolley();
                graficoNotasGeralFragment.carregaDadosVolley();
                boxNotaMediaFragment.carregaDadosVolley();
                boxTempoMedioFragment.carregaDadosVolley();
                boxHojeFragment.carregaDadosVolley();
                boxImagemRuimFragment.carregaDadosVolley();
                boxComparativoNotasFragment.carregaDadosVolley();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }


    public void carregaDadosVolley(Boolean showLoading)
    {

        String JSON_URL = "http://consultec.com.br/APPS/CORRETOR/getDadosUltimaAtualizacao.php";

        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.POST,JSON_URL,null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        separaDadosJSON(response);
                        swipeContainer.setRefreshing(false);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        //alert(error.getMessage());
                        swipeContainer.setRefreshing(false);
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjRequest);

    }


    private void separaDadosJSON(JSONObject json)
    {
        try
        {
            String utimaAtualizacao = "";
            JSONArray totalDosLotes = new JSONArray();

            JSONObject jsonObject   = json;
            JSONArray jsonArray     = jsonObject.getJSONArray("dados");

            utimaAtualizacao        =   jsonArray.getJSONObject((int)0).getString("ultima_atualizacao");

            setValorUltimaAtualizacao(utimaAtualizacao);
        }
        catch (JSONException e)
        {
            alert(e.getMessage());
        }
    }


    public void desabilitaScroll()
    {
        scrollView.scrollTo(0,0);
        swipeContainer.setEnabled(false);
        scrollView.setEnableScrolling(false);
    }


    public void habilitaScroll()
    {
        swipeContainer.setEnabled(true);
        scrollView.setEnableScrolling(true);
    }

    public void setReferenciaDaBusca(String idCorretor, String idProcessoSeletivo)
    {
        ReferenciaDaBusca referenciaDaBusca = new ReferenciaDaBusca();
        referenciaDaBusca.setIdCorretor(idCorretor);
        referenciaDaBusca.setIdProcessoSeletivo(idProcessoSeletivo);

        dadosDaBusca.putParcelable("dadosBusca", referenciaDaBusca);
    }

    public Bundle getReferenciaDaBusca()
    {
        return dadosDaBusca;
    }
}
package br.com.formacaopadrao.WiFiStrength;

import android.app.ActionBar;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.formacaopadrao.model.Wifi;
import br.com.formacaopadrao.model.WifiList;

public class MainActivity extends AppCompatActivity {

    private WifiList wl;
    private WifiManager wifiManager;
    private List<TextView> txIds;
    private LinearLayout LL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        LL = ((LinearLayout)findViewById(R.id.LinearLayoutMain));
        wl = new WifiList();
        txIds = new ArrayList<TextView>();
        wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        incluirWifiConfiguradas();
        Button myButton = new Button(this);
        myButton.setText("Conectar na maior");
        myButton.setLayoutParams(caberConteudo());
        myButton.setOnClickListener(conectarMaior());
        LL.addView(myButton);

    }

    public void incluirWifiConfiguradas(){
        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        for( WifiConfiguration i : list ) {
            if(i.SSID != null) {
                wl.addWifi(i.SSID);
            }
        }
        criarTextViews();
    }

    public void criarTextViews(){
        for (Wifi w : wl.getList()){
            TextView tx = new TextView(this);
            txIds.add(tx);
            Logar("criarTextViews: "+w.getNome()+" = id: "+tx.getId());
            tx.setText(w.getNome());
            LL.addView(tx);
        }
    }

    public void atualizarTextViews(){
        for (TextView tx : txIds){
            Logar("atualizarTextViews: "+" = tx: "+tx.getText().toString());
            String nome = tx.getText().toString().split(" ")[0];
            int forca = wl.getForcaByNome(nome);
            if (forca != -1){
                tx.setText(nome+" "+forca);
            }
        }
    }

    public void atualizarWifiForcas(){
        List<ScanResult> list = wifiManager.getScanResults();
        for( ScanResult i : list ) {
            if(i.SSID != null) {
                int forca = WifiManager.calculateSignalLevel(i.level, 5);
                wl.atualizar("\"" + i.SSID + "\"", forca);
            }
        }
        atualizarTextViews();
    }

    public View.OnClickListener conectarMaior(){
        View.OnClickListener o = new View.OnClickListener() {
            public void onClick(View view) {
                atualizarWifiForcas();
                String maior = wl.getMaior();
                if (maior.equals("null"))
                    return;
                List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
                for( WifiConfiguration i : list ) {
                    if(i.SSID != null && i.SSID.equals(maior)) {
                        wifiManager.disconnect();
                        wifiManager.enableNetwork(i.networkId, true);
                        wifiManager.reconnect();
                        break;
                    }
                }
            }
        };
        return o;
    }

    public void Logar(String s){
        Log.i("chocoflex", s);
    }

    public LinearLayout.LayoutParams caberConteudo(){
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
    }


}

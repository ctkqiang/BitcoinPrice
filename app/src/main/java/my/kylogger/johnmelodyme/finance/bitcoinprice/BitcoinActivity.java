package my.kylogger.johnmelodyme.finance.bitcoinprice;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BitcoinActivity extends AppCompatActivity {
    public static final String TAG = "BTC_PRICE";
    public static final String URL_API_BITCOIN = "https://api.coindesk.com/v1/bpi/currentprice.json";
    private static OkHttpClient okHttpClient;
    private static ProgressDialog progressDialog;
    private Button GET_BITCOIN_PRICE;
    private TextView PRICE;

    // TODO DeclarationInit()
    private void DeclarationInit() {
        progressDialog = new ProgressDialog(BitcoinActivity.this);
        okHttpClient = new OkHttpClient();
        GET_BITCOIN_PRICE = findViewById(R.id.geBTCprice);
        PRICE = findViewById(R.id.USD);
    }

    @Override
    // TODO onCreate
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "Staring " + BitcoinActivity.class.getSimpleName());
        DeclarationInit();
        progressDialog.setMessage(getResources().getString(R.string.getprice_dialog));

        // TODO GET_BITCOIN_PRICE
        GET_BITCOIN_PRICE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Request request = new Request
                        .Builder()
                        .url(URL_API_BITCOIN)
                        .build();
                progressDialog.show();

                // TODO okHttpCLient:
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Toast.makeText(BitcoinActivity.this,
                                getResources()
                                        .getString(R.string.onFailure),
                                Toast.LENGTH_SHORT)
                                .show();
                    }

                    @Override
                    // TODO onResponse:
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        final String body = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                parseBpiResponse(body);
                            }
                        });
                    }
                });
            }
        });
    }

    // TODO  parseBpiResponse()
    public void parseBpiResponse(String body) {
        try {
            StringBuilder builder = new StringBuilder();
            JSONObject jsonObject = new JSONObject(body);
            JSONObject timeObject = jsonObject.getJSONObject("time");
            JSONObject bpiObject = jsonObject.getJSONObject("bpi");
            JSONObject usdObject = bpiObject.getJSONObject("USD");
            JSONObject gbpObject = bpiObject.getJSONObject("GBP");
            JSONObject euroObject = bpiObject.getJSONObject("EUR");
            builder.append(timeObject.getString("updated")).append("\n\n");
            builder.append(usdObject.getString("rate")).append(" $USD").append("\n");
            builder.append(gbpObject.getString("rate")).append(" £GBP").append("\n");
            builder.append(euroObject.getString("rate")).append(" €EURO").append("\n");
            PRICE.setText(builder.toString());
            Log.d(TAG, "parseBpiResponse:" + builder.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "parseBpiResponse: " + e);
        }
    }

    @Override
    // TODO  onCreateOptionsMenu()
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    // TODO onOptionsItemSelected()
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.about) {
            new SweetAlertDialog(BitcoinActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                    .setTitleText(getResources().getString(R.string.about))
                    .setContentText(getResources().getString(R.string.message))
                    .setCustomImage(R.mipmap.applogo)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }
}

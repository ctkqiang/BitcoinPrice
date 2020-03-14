package my.kylogger.johnmelodyme.finance.bitcoinprice;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import okhttp3.OkHttpClient;

public class BitcoinActivity extends AppCompatActivity {
    public static final String TAG = "BTC_PRICE";
    public static final String URL_API_BITCOIN = "https://api.coindesk.com/v1/bpi/currentprice.json";
    private OkHttpClient okHttpClient;

    private void DeclarationInit() {
        okHttpClient = new OkHttpClient();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "Staring " + BitcoinActivity.class.getSimpleName());
        DeclarationInit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       // if (item.getItemId() == R.id.action_load) {
       // }
        return super.onOptionsItemSelected(item);
    }



}

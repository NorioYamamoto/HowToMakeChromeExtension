package jp.co.yamamoto.norio.howtomakechromeextension2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity {

    private AdView mAdView;
    WebView myWebView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Release ca-app-pub-5927681014749576/2105354204
        // Debug ca-app-pub-3940256099942544/6300978111
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        myWebView = (WebView) findViewById(R.id.webView1);
        myWebView.setWebViewClient(new WebViewClient());

        myWebView.setWebViewClient(new WebViewClient() {
            // 新しいURLが指定されたときの処理を定義
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri u = Uri.parse(url);
                String h = u.getHost();

                if (u.toString().startsWith("http")) {
                    // 標準ブラウザで表示する
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }

                // WebView内で表示する
                return false;
            }
        });

        // myWebView.setWebChromeClient(new WebChromeClient());

        String index_html = getString(R.string.index_html);

        myWebView.loadUrl(index_html); // ローカルのhtmlファイルを指定

        myWebView.getSettings().setBuiltInZoomControls(true);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        WebView  myWebView = (WebView)findViewById(R.id.webView1);
        // 端末のBACKキーで一つ前のページヘ戻る
        if(keyCode == KeyEvent.KEYCODE_BACK && myWebView.canGoBack()) {
            myWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode,  event);
    }
}

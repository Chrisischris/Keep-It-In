package com.chrishaen.keepitin;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class AndroidLauncher extends AndroidApplication implements IActivityRequestHandler{
	AdView bannerAd;

	//	 Interstitial Ad
	InterstitialAd interstitialAd;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Create the libgdx View
		View gameView = initializeForView(new KeepItIn(this));

		setupAds();

		// Define the layout
		RelativeLayout layout = new RelativeLayout(this);
		layout.addView(gameView);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		layout.addView(bannerAd, params);

		bannerAd.setAdListener(new AdListener() {
			public void onAdLoaded(){
				Log.i("AndroidLauncher", "Ad Loaded...");
			}
		});
		AdRequest.Builder builder = new AdRequest.Builder();
		bannerAd.loadAd(builder.build());

		setContentView(layout);
		/*AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new KeepItIn(), config);*/
	}
	public void setupAds() {
		//	Banner Ad
		bannerAd = new AdView(this);
		bannerAd.setVisibility(View.VISIBLE);
		bannerAd.setBackgroundColor(0xff000000); // black
		bannerAd.setAdUnitId("ca-app-pub-5470650884230497/4163904043");
		bannerAd.setAdSize(AdSize.SMART_BANNER);

		// Interstitial Ad
		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

		AdRequest.Builder builder = new AdRequest.Builder();
		AdRequest ad = builder.build();
		interstitialAd.loadAd(ad);
	}

	@Override
	public void hideBanner() {

	}

	@Override
	public void showBanner() {

	}

	@Override
	public void showInterstitial() {

	}

	@Override
	public void showInterstitial(final Runnable then) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if (then != null) {
						interstitialAd.setAdListener(new AdListener() {
							@Override
							public void onAdClosed() {
								Gdx.app.postRunnable(then);
								AdRequest.Builder builder = new AdRequest.Builder();
								AdRequest ad = builder.build();
								interstitialAd.loadAd(ad);
							}
						});
					}
					interstitialAd.show();
				}
			});
	}

	@Override
	public void showAds(boolean show) {

	}

	@Override
	public void initAds() {

	}
}

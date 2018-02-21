package com.chrishaen.keepitin;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdSize;


import com.badlogic.gdx.backends.android.AndroidApplication;

public class AndroidLauncher extends AndroidApplication {
	AdView bannerAd;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Create the libgdx View
		View gameView = initializeForView(new KeepItIn(null));

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
		bannerAd = new AdView(this);
		bannerAd.setVisibility(View.VISIBLE);
		bannerAd.setBackgroundColor(0xff000000); // black
		bannerAd.setAdUnitId("ca-app-pub-5470650884230497/4163904043");
		bannerAd.setAdSize(AdSize.SMART_BANNER);
	}
}

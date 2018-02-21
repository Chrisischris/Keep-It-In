package com.chrishaen.keepitin;

public interface IActivityRequestHandler {
	void hideBanner();
    void showBanner();
    void showInterstitial();
    void showAds(boolean show);
    void initAds();
}

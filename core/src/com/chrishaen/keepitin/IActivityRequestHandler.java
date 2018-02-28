package com.chrishaen.keepitin;

public interface IActivityRequestHandler {
	void hideBanner();
    void showBanner();
    void showInterstitial();
    void showInterstitial(final Runnable then);
    void showAds(boolean show);
    void initAds();
}

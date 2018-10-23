package jp.ac.doshisha.mikilab.androiddownlightcontrollerforkc101;

import LightingClient.Light;
import android.app.Activity;
import android.support.v7.widget.GridLayout;

import java.util.ArrayList;

public class LightController {
    Activity mActivity;
    ArrayList<LightView> lightViews = new ArrayList<>();
    ArrayList<Light> lights = new ArrayList<>();

    public LightController(Activity activity) {
        mActivity = activity;

        GridLayout lightArea = mActivity.findViewById(R.id.light_area);

        // Load Lights information from server


        // Generate LightView
        for (int i=0; i<36; i++) {
            LightView l = new LightView(mActivity);
            lightViews.add(l);
            lightArea.addView(l);
        }
    }
}

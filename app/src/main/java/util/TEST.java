package util;

import android.widget.TextView;

/**
 * Created by Foster on 8/4/15.
 */
public class TEST {
    public static TEST GetInstance() {return instance;}


    private static TEST instance = new TEST();
    private TEST(){}
}

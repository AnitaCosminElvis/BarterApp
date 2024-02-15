package com.example.barterapp.utility;

import static com.example.barterapp.utility.DefinesUtility.*;

import java.text.DecimalFormat;

/**
 * The OperationsUtility utility.
 */
public class OperationsUtility {

    public static float inverseFloatValueSign(float value){
        return (0 - value);
    }


    public static String getFormatedFloatText(float value){
        return new DecimalFormat(DEC_FORMAT).format(value);
    }
}

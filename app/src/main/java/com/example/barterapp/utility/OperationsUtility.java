package com.example.barterapp.utility;

import java.text.DecimalFormat;

import static com.example.barterapp.utility.DefinesUtility.*;

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

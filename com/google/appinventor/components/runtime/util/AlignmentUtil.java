package com.google.appinventor.components.runtime.util;

import com.google.appinventor.components.runtime.LinearLayout;
import gnu.expr.SetExp;
import gnu.kawa.functions.ParseFormat;
import gnu.kawa.xml.XDataType;

public class AlignmentUtil {
    LinearLayout viewLayout;

    public AlignmentUtil(LinearLayout viewLayout) {
        this.viewLayout = viewLayout;
    }

    public void setHorizontalAlignment(int alignment) throws IllegalArgumentException {
        switch (alignment) {
            case ParseFormat.SEEN_MINUS /*1*/:
                this.viewLayout.setHorizontalGravity(3);
            case SetExp.DEFINING_FLAG /*2*/:
                this.viewLayout.setHorizontalGravity(5);
            case XDataType.ANY_ATOMIC_TYPE_CODE /*3*/:
                this.viewLayout.setHorizontalGravity(1);
            default:
                throw new IllegalArgumentException("Bad value to setHorizontalAlignment: " + alignment);
        }
    }

    public void setVerticalAlignment(int alignment) throws IllegalArgumentException {
        switch (alignment) {
            case ParseFormat.SEEN_MINUS /*1*/:
                this.viewLayout.setVerticalGravity(48);
            case SetExp.DEFINING_FLAG /*2*/:
                this.viewLayout.setVerticalGravity(16);
            case XDataType.ANY_ATOMIC_TYPE_CODE /*3*/:
                this.viewLayout.setVerticalGravity(80);
            default:
                throw new IllegalArgumentException("Bad value to setVerticalAlignment: " + alignment);
        }
    }
}

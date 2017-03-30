package com.example.collinyu.customcolors;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceView;

import java.util.ArrayList;

import static android.graphics.Color.rgb;

/**
 * Created by CollinYu on 3/29/17.
 */


public class mySurfaceView extends SurfaceView {

    ArrayList<CustomElement> ce = new ArrayList<>();
    public mySurfaceView(Context context){super(context);}
    public mySurfaceView(Context context, AttributeSet attrs){super(context,attrs);}
    public mySurfaceView(Context context, AttributeSet attrs, int defStyleAttr){super(context,attrs,defStyleAttr);}
    public mySurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){super(context,attrs,defStyleAttr,defStyleRes);}

    @Override
    public void onDraw(Canvas c){
        for(int i = 0; i< ce.size(); i++) {
            ce.get(i).drawMe(c);
        }
    }

    public ArrayList<CustomElement> getElementList(){return ce;}

    public void addElement(CustomElement ele){
        ce.add(ele);
    }
}

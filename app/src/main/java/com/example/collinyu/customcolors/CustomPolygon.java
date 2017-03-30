package com.example.collinyu.customcolors;

import android.graphics.Canvas;
import android.graphics.Path;

import static com.example.collinyu.customcolors.InPolygonTester.isInside;

/**
 * Created by CollinYu on 3/28/17.
 */

public class CustomPolygon extends CustomElement{

    protected float[] poly;
    protected  Point polyTester[];

    public CustomPolygon(String name, int color, float[] points){
        super(name, color);
        this.poly = new float[points.length];
            for(int i = 0; i < points.length; i++) {this.poly[i] = points[i];}

        this.polyTester = new Point[points.length/2];
            for (int i =0; i < this.polyTester.length; i++) {
                this.polyTester[i]= new Point(
                        (int)points[i*2],
                        (int)points[i*2+1]);

            }
    }


    @Override
    // don't need
    public int getSize() {
    return -1;
    }

    @Override
    public void drawHighlight(Canvas c) {
        Path p = new Path();
        p.moveTo(this.poly[0], this.poly[1]);
        for (int i = 2; i<poly.length; i = i +2){p.lineTo(this.poly[i], this.poly[i+1]);}
        c.drawPath(p, this.highlightPaint);
    }

    // don't need
    public void drawMe(Canvas c){
        Path p = new Path();
        p.moveTo(this.poly[0], this.poly[1]);
        for (int i = 2; i<poly.length; i = i +2){p.lineTo(this.poly[i], this.poly[i+1]);}
        c.drawPath(p, this.myPaint);
    }

    public boolean containsPoint(int x, int y){
        Point p = new Point(x,y);
        return isInside(this.polyTester, this.polyTester.length, p);
        }
    }



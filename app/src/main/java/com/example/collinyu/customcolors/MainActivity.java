package com.example.collinyu.customcolors;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import static android.graphics.Color.rgb;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener, SeekBar.OnSeekBarChangeListener{

    private TextView element;
    private mySurfaceView theDrawing;
    private SeekBar seekRed;
    private SeekBar seekGreen;
    private SeekBar seekBlue;
    private boolean joints;
    private boolean legsArms;
    private boolean hammers;
    private boolean torso;
    private boolean shoulders;
    private boolean feet;
    private int[] rProg;
    private int[] gProg;
    private int[] bProg;

    // initialize picture dimensions in terms of CustomElement and children
    CustomCircle raJoint;
    private int[] rightArmJointDim = {768 -55, 210, 60};

    CustomCircle laJoint;
    private int[] leftArmJointDim = {55, 210, 60};

    CustomCircle llJoint;
    private int[] llJointDim = {225, 600, 60};

    CustomCircle rlJoint;
    private int[] rlJointDim = {768 - 225, 600, 60};

    CustomPolygon leg;
    private float[] legDim = {
            (768 - 256), 450,
            256, 450,
            175, 600,
            175, 750,
            275, 750,
            275, 600,
            384, 450,

            (768 - 275), 600,
            (768 - 275), 750,
            (768 - 175), 750,
            (768 - 175), 600,
            (768 - 256), 450
    };

    CustomPolygon lArm;
    private float[] leftArmDim = {
            90, 64,
            20, 210,
            20, 450,
            90, 450,
            90, 210,
            150, 100
    };

    CustomPolygon rArm;
    private float[] rightArmDim = {
            (768 - 90), 64,
            (768 - 20), 210,
            (768 - 20), 450,
            (768 - 90), 450,
            (768 - 90), 210,
            (768 - 150), 100
    };

    CustomRect lHammer;
    private int[] leftHammerDim = {
            0,450,
            110, 520
    };

    // the containsPoint method would not function when it was a rect
    CustomPolygon rHammer;
    private float[] rightHammerDim = {
            768,450,
            768,520,
            657, 520,
            657,450,
            768,450
    };

    CustomPolygon chest;
    private float[] torsoDim = {
            256, 30,
            (768 - 256), 30,
            (768 - 200), 130,
            (768 - 256), 450,
            256, 450,
            200, 130,
            256, 30

    };


    CustomPolygon lShoulder;
    private float[] leftShoulderDim = {
            30, 30,
            256, 30,
            200, 130,
            30, 30
    };

    CustomPolygon rShoulder;
    private float[] rightShoulderDim = {
            (768 - 30), 30,
            (768 - 256), 30,
            (768 - 200), 130,
            (768 - 30), 30
    };

    // the containsPoint method would not function when it was a rect
    CustomPolygon lFoot;
    private float[] leftFootDim = {
            275,750,
            125,750,
            125,800,
            275,800,
            275,750
    };
    CustomRect rFoot;
    private int[] rightFootDim = {
            (768 - 275), 750,
            (768 - 125), 800,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Attach variables to xml objects
        element = (TextView) findViewById(R.id.elements);
        theDrawing = (mySurfaceView) findViewById(R.id.theDrawing);
        seekRed = (SeekBar) findViewById(R.id.seekRed);
        seekGreen = (SeekBar) findViewById(R.id.seekGreen);
        seekBlue = (SeekBar) findViewById(R.id.seekBlue);

        // Set listeners
        theDrawing.setOnTouchListener(this);
        seekRed.setOnSeekBarChangeListener(this);
        seekGreen.setOnSeekBarChangeListener(this);
        seekBlue.setOnSeekBarChangeListener(this);

        // Initialize color array
        rProg = new int[6];
        gProg = new int[6];
        bProg = new int[6];

        for (int i = 0; i < 6; i++) {
            rProg[i] = i*40;
            gProg[i] = i*40;
            bProg[i] = i*40;
        }

        // Initialize CustomElement children objects
        leg = new CustomPolygon("leg",Color.argb(255,rProg[1],gProg[1],bProg[1]), legDim);
        theDrawing.addElement(leg);
        lArm = new CustomPolygon("la", Color.argb(255,rProg[2],gProg[2],bProg[2]), leftArmDim);
        theDrawing.addElement(lArm);
        rArm = new CustomPolygon("ra", Color.argb(255,rProg[2],gProg[2],bProg[2]), rightArmDim);
        theDrawing.addElement(rArm);
        lHammer = new CustomRect("lh", Color.argb(255,rProg[3],gProg[3],bProg[3]),
                                leftHammerDim[0], leftHammerDim[1], leftHammerDim[2], leftHammerDim[3]);
        theDrawing.addElement(lHammer);
        rHammer = new CustomPolygon("rh", Color.argb(255,rProg[3],gProg[3],bProg[3]), rightHammerDim);
        theDrawing.addElement(rHammer);
        chest = new CustomPolygon("torso", Color.argb(255,rProg[4],gProg[4],bProg[4]), torsoDim);
        theDrawing.addElement(chest);
        lShoulder = new CustomPolygon("ls", Color.argb(255,rProg[5],gProg[5],bProg[5]), leftShoulderDim);
        theDrawing.addElement(lShoulder);
        rShoulder = new CustomPolygon("rs", Color.argb(255,rProg[5],gProg[5],bProg[5]), rightShoulderDim);
        theDrawing.addElement(rShoulder);
        lFoot = new CustomPolygon("lf", Color.argb(255,rProg[5],gProg[5],bProg[5]), leftFootDim);
        theDrawing.addElement(lFoot);
        rFoot = new CustomRect("lf", Color.argb(255,rProg[5],gProg[5],bProg[5]), rightFootDim[0],rightFootDim[1],rightFootDim[2],rightFootDim[3]);
        theDrawing.addElement(rFoot);
        laJoint = new CustomCircle("laj", Color.argb(255,rProg[0],gProg[0],bProg[0]), rightArmJointDim[0], rightArmJointDim[1], rightArmJointDim[2]);
        theDrawing.addElement(laJoint);
        raJoint = new CustomCircle("raj", Color.argb(255, rProg[0],gProg[0],bProg[0]), leftArmJointDim[0], leftArmJointDim[1], leftArmJointDim[2]);
        theDrawing.addElement(raJoint);
        llJoint = new CustomCircle("llj", rgb(rProg[0],gProg[0],bProg[0]), llJointDim[0],llJointDim[1],llJointDim[2]);
        theDrawing.addElement(llJoint);
        rlJoint = new CustomCircle("llj", rgb(rProg[0],gProg[0],bProg[0]), rlJointDim[0],rlJointDim[1],rlJointDim[2]);
        theDrawing.addElement(rlJoint);

        // These Booleans refer to which element is currently selected, start with torso as the initial selection
        // (textview reflects this as well initially)
        joints = false;
        legsArms = false;
        hammers = false;
        torso = true;
        shoulders = false;
        feet = false;

    }

    @Override
    // Detects whether the point of contact is within one of the CustomElements
        public boolean onTouch(View v, MotionEvent me) {
                //Check if any of the corresponding elements contain the point
                if (laJoint.containsPoint((int) me.getX(), (int) me.getY()) ||
                        raJoint.containsPoint((int) me.getX(), (int) me.getY()) ||
                        llJoint.containsPoint((int) me.getX(), (int) me.getY()) ||
                        rlJoint.containsPoint((int) me.getX(), (int) me.getY())) {
                            // Change progress bars to reflect the current color state of the selected element
                            seekRed.setProgress(rProg[0]);
                            seekGreen.setProgress(gProg[0]);
                            seekBlue.setProgress(bProg[0]);

                            // Change selected element to the correct one
                            joints = true;
                            legsArms = false;
                            hammers = false;
                            torso = false;
                            shoulders = false;
                            feet = false;

                            // Change the textview to reflect the selected element
                            element.setText("Joints");

                            // return so that if another element is underneath this one,
                            // it does not switch to that one, i.e. legs is under leg joints
                            return true;
                        }
                // the rest follow the same pattern
                if (lHammer.containsPoint((int) me.getX(), (int) me.getY()) ||
                        rHammer.containsPoint((int) me.getX(), (int) me.getY())) {
                            seekRed.setProgress(rProg[2]);
                            seekGreen.setProgress(gProg[2]);
                            seekBlue.setProgress(bProg[2]);
                            joints = false;
                            legsArms = false;
                            hammers = true;
                            torso = false;
                            shoulders = false;
                            feet = false;
                            element.setText("Hammers");
                            return true;
                        }
                if (lArm.containsPoint((int) me.getX(), (int) me.getY()) ||
                        rArm.containsPoint((int) me.getX(), (int) me.getY()) ||
                        leg.containsPoint((int) me.getX(), (int) me.getY())) {
                            seekRed.setProgress(rProg[1]);
                            seekGreen.setProgress(gProg[1]);
                            seekBlue.setProgress(bProg[1]);
                            joints = false;
                            legsArms = true;
                            hammers = false;
                            torso = false;
                            shoulders = false;
                            feet = false;
                            element.setText("Arms and Legs");
                            return true;
                        }

                if (chest.containsPoint((int) me.getX(), (int) me.getY())) {
                            seekRed.setProgress(rProg[3]);
                            seekGreen.setProgress(gProg[3]);
                            seekBlue.setProgress(bProg[3]);
                            joints = false;
                            legsArms = false;
                            hammers = false;
                            torso = true;
                            shoulders = false;
                            feet = false;
                            element.setText("Torso");
                }
                if (lShoulder.containsPoint((int) me.getX(), (int) me.getY()) ||
                        rShoulder.containsPoint((int) me.getX(), (int) me.getY())) {
                            seekRed.setProgress(rProg[4]);
                            seekGreen.setProgress(gProg[4]);
                            seekBlue.setProgress(bProg[4]);
                            joints = false;
                            legsArms = false;
                            hammers = false;
                            torso = false;
                            shoulders = true;
                            feet = false;
                            element.setText("Shoulders");
                            return true;
                        }
                if (lFoot.containsPoint((int) me.getX(), (int) me.getY()) ||
                        rFoot.containsPoint((int) me.getX(), (int) me.getY())) {
                            seekRed.setProgress(rProg[5]);
                            seekGreen.setProgress(gProg[5]);
                            seekBlue.setProgress(bProg[5]);
                            joints = false;
                            legsArms = false;
                            hammers = false;
                            torso = false;
                            shoulders = false;
                            feet = true;
                            element.setText("Feet");
                            return true;
                        }
                return true;

        }

        public void onProgressChanged(SeekBar sb, int progress, boolean fromUser) {
            if(fromUser) {
                if (joints) {
                    if (sb == seekRed) {
                        rProg[0] = progress;
                    }
                    if (sb == seekGreen) {
                        gProg[0] = progress;
                    }
                    if (sb == seekBlue) {
                        bProg[0] = progress;
                    }
                    laJoint.setColor(rgb(rProg[0], gProg[0], bProg[0]));
                    llJoint.setColor(rgb(rProg[0], gProg[0], bProg[0]));
                    raJoint.setColor(rgb(rProg[0], gProg[0], bProg[0]));
                    rlJoint.setColor(rgb(rProg[0], gProg[0], bProg[0]));
                    theDrawing.invalidate();

                }
                if (legsArms) {
                    if (sb == seekRed) {
                        rProg[1] = progress;
                    }
                    if (sb == seekGreen) {
                        gProg[1] = progress;
                    }
                    if (sb == seekBlue) {
                        bProg[1] = progress;
                    }
                    lArm.setColor(rgb(rProg[1], gProg[1], bProg[1]));
                    rArm.setColor(rgb(rProg[1], gProg[1], bProg[1]));
                    leg.setColor(rgb(rProg[1], gProg[1], bProg[1]));
                    theDrawing.invalidate();


                }
                if (hammers) {
                    if (sb == seekRed) {
                        rProg[2] = progress;
                    }
                    if (sb == seekGreen) {
                        gProg[2] = progress;
                    }
                    if (sb == seekBlue) {
                        bProg[2] = progress;
                    }
                    lHammer.setColor(rgb(rProg[2], gProg[2], bProg[2]));
                    rHammer.setColor(rgb(rProg[2], gProg[2], bProg[2]));
                    theDrawing.invalidate();


                }

                if (torso) {
                    if (sb == seekRed) {
                        rProg[3] = progress;
                    }
                    if (sb == seekGreen) {
                        gProg[3] = progress;
                    }
                    if (sb == seekBlue) {
                        bProg[3] = progress;
                    }
                    chest.setColor(rgb(rProg[3], gProg[3], bProg[3]));
                    theDrawing.invalidate();

                }

                if (shoulders) {
                    if (sb == seekRed) {
                        rProg[4] = progress;
                    }
                    if (sb == seekGreen) {
                        gProg[4] = progress;
                    }
                    if (sb == seekBlue) {
                        bProg[4] = progress;
                    }
                    lShoulder.setColor(rgb(rProg[4], gProg[4], bProg[4]));
                    rShoulder.setColor(rgb(rProg[4], gProg[4], bProg[4]));
                    theDrawing.invalidate();

                }

                if (feet) {
                    if (sb == seekRed) {
                        rProg[5] = progress;
                    }
                    if (sb == seekGreen) {
                        gProg[5] = progress;
                    }
                    if (sb == seekBlue) {
                        bProg[5] = progress;
                    }
                    lFoot.setColor(rgb(rProg[5], gProg[5], bProg[5]));
                    rFoot.setColor(rgb(rProg[5], gProg[5], bProg[5]));
                    theDrawing.invalidate();

                }
            }
        }

        public void onStartTrackingTouch(SeekBar sb) {
        }

        public void onStopTrackingTouch(SeekBar sb) {
        }
    }


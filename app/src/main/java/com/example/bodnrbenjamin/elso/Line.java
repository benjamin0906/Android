package com.example.bodnrbenjamin.elso;

/**
 * Created by BodnárBenjamin on 2018. 02. 28..
 */

public class Line
{
    public int dX;
    public int dY;
    public int StartY;
    public int StartX;
    public int LineColor;
    public float m;
    public float Invm;
    public int ID;
    public Line(int InputStartX, int InputStartY, int InputStopX, int InputStopY,int color, int ID)
    {
        StartX=InputStartX;
        dY=InputStopY-InputStartY;
        dX=InputStopX-InputStartX;
        StartY = InputStartY;
        m = (float)(dY)/dX;
        Invm = (float) dX/dY;
        LineColor = color;
        this.ID=ID;
    }
}

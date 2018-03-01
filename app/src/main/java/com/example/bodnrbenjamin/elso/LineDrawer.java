package com.example.bodnrbenjamin.elso;

/**
 * Created by BodnárBenjamin on 2018. 02. 25..
 */
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.ImageView;

public class LineDrawer {
    private Bitmap bitmap;
    private ImageView mImageView;
    private int StartX;
    private int StartY;
    private int StopX;
    private int StopY;
    private int BackGroundColor;
    private int looper;
    private int ArraySize=100;
    private Line[] LineArray = new Line[ArraySize];
    private int[] FreeSpacesInLineArray = new int[ArraySize];
    public LineDrawer(Bitmap InputBitmap, ImageView InputImageView)
    {
        bitmap = InputBitmap;
        mImageView = InputImageView;
        BackGroundColor = Color.WHITE;
        for(int looper=0;looper<ArraySize;looper++){
            FreeSpacesInLineArray[looper]=looper;
            LineArray[looper] = null;
        }
    }
    public void setLine(int InputStartX, int InputStartY, int InputStopX, int InputStopY)
    {
        StartX=InputStartX;
        StartY=InputStartY;
        StopX=InputStopX;
        StopY=InputStopY;
        int dX=StopX-StartX;
        int dY=StopY-StartY;
        float m = (float)dX/dY;
        int y;
        for(looper = 0; looper<dX;looper++)
        {
            y=((int)((float)m*looper))+StartY;
            //bitmap.setPixel(looper,y, color);
        }

        mImageView.setImageBitmap(bitmap);
    }
    private int RoundToInt(float num)
    {
        int ret = (int) num;
        if(num>=0)
        {
            if((num - (float) ret) >= 0.5) ret++;
        }
        else
        {
            if((num - (float) ret) <= -0.5) ret--;
        }
        return ret;
    }
    private void setPixels(int index)
    {
        int looper;
        int y;
        int x;
        for(looper = 0; looper < LineArray[index].dX; looper++)
        {
            y=RoundToInt( (float) LineArray[index].m*looper+LineArray[index].StartY);
            bitmap.setPixel(LineArray[index].StartX+looper,y, LineArray[index].LineColor);
        }
        for(looper = 0; looper < LineArray[index].dY; looper++)
        {
            x=RoundToInt((float) LineArray[index].Invm*looper+LineArray[index].StartX);
            bitmap.setPixel(x,LineArray[index].StartY+looper, LineArray[index].LineColor);
        }
    }
    public void addLine(int InputStartX, int InputStartY, int InputStopX, int InputStopY, int color, int ID)
    {
        int looper = 0;
        boolean sign= true;
        while(sign && looper <ArraySize-1)
        {
            if(LineArray[looper] == null)
            {
                sign= false;
            }
            else looper++;
        }
        if(sign == true) return;
        LineArray[looper]= new Line(InputStartX, InputStartY, InputStopX, InputStopY, color, ID);
        setPixels(looper);
        mImageView.setImageBitmap(bitmap);
    }
    /*This method can both the position and the color of the line modify*/
    public void modifyLine(int LineId, int newStartX, int newStartY, int newStopX, int newStopY, int newColor)
    {
        bitmap = Bitmap.createBitmap(100,100,Bitmap.Config.ARGB_8888);
        int looper = 0;
        boolean sign = true;
        /*The following cycle looks for the desired line in the array of lines*/
        while(sign && (looper < ArraySize))
        {
            if(LineArray[looper] != null) sign= !(LineArray[looper].ID == LineId);
            looper++;
        }
        looper--;
        if(sign) return;
        LineArray[looper].StartY=newStartY;
        LineArray[looper].dX=newStopX-newStartX;
        LineArray[looper].dY=newStopY-newStartY;
        LineArray[looper].m=(float)LineArray[looper].dY/LineArray[looper].dX;
        LineArray[looper].Invm=(float)LineArray[looper].dX/LineArray[looper].dY;
        LineArray[looper].LineColor=newColor;

        for(looper=0;looper<ArraySize;looper++) // the whole array of lines will be checked
        {
            /* If the actual line object is not null, it can be check the ID, and if it is correct we can set the pixels */
            if((LineArray[looper] != null) && (LineArray[looper].ID != 0)) setPixels(looper);
        }
        mImageView.setImageBitmap(bitmap);
    }
    /*This method can only the pozition of the line modify*/
    public void modifyLine(int LineId, int newStartX, int newStartY, int newStopX, int newStopY)
    {
        bitmap = Bitmap.createBitmap(100,100,Bitmap.Config.ARGB_8888);
        int looper;
        boolean sign= true;
        looper=0;
        /*The following cycle looks for the desired line in the array of lines*/
        while(sign && (looper < ArraySize))
        {
            if(LineArray[looper] != null) sign= !(LineArray[looper].ID == LineId);
            looper++;
        }
        looper--;
        if(sign) return;
        LineArray[looper].StartX=newStartX;
        LineArray[looper].StartY=newStartY;
        LineArray[looper].dX=newStopX-newStartX;
        LineArray[looper].dY=newStopY-newStartY;
        LineArray[looper].m=(float)LineArray[looper].dY/LineArray[looper].dX;
        LineArray[looper].Invm=(float)LineArray[looper].dX/LineArray[looper].dY;

        for(looper=0;looper<ArraySize;looper++) // the whole array of lines will be checked
        {
            /* If the actual line object is not null, it can be check the ID, and if it is correct we can set the pixels */
            if((LineArray[looper]!= null) && (LineArray[looper].ID != 0)) setPixels(looper);
        }
        mImageView.setImageBitmap(bitmap);
    }
    /*This method can only the color of line modify*/
    public void modifyLine(int LineId, int newColor)
    {
        bitmap = Bitmap.createBitmap(100,100,Bitmap.Config.ARGB_8888);
        int looper;
        boolean sign= true;
        looper = 0;
        /*The following cycle looks for the desired line in the array of lines*/
        while(sign && (looper < ArraySize))
        {
            if(LineArray[looper] != null) sign= !(LineArray[looper].ID == LineId);
            looper++;
        }
        looper--;
        if(sign) return;
        LineArray[looper].LineColor=newColor;

        for(looper=0;looper<ArraySize;looper++) // the whole array of lines will be checked
        {
            /* If the actual line object is not null, it can be check the ID, and if it is correct we can set the pixels */
            if((LineArray[looper]!= null) && (LineArray[looper].ID != 0)) setPixels(looper);
        }
        mImageView.setImageBitmap(bitmap);
    }
    /*This method deletes the line from the array of lines*/
    public void deleteLine(int LineId)
    {
        bitmap = Bitmap.createBitmap(100,100,Bitmap.Config.ARGB_8888);
        int looper = 0;
        boolean sign = true;
        /*The following cycle looks for the desired line in the array of lines*/
        while(sign && (looper < ArraySize))
        {
            if(LineArray[looper] != null) sign= !(LineArray[looper].ID == LineId);
            looper++;
        }
        looper--;
        if(sign) return;
        LineArray[looper]=null;

        for(looper=0;looper<ArraySize;looper++) // the whole array of lines will be checked
        {
            /* If the actual line object is not null, it can be check the ID, and if it is correct we can set the pixels */
            if((LineArray[looper]!= null) && (LineArray[looper].ID != 0)) setPixels(looper);
        }
        mImageView.setImageBitmap(bitmap);
    }
}



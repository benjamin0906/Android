package com.example.bodnrbenjamin.elso;

import com.example.usbseriallib.usbserial.driver.UsbSerialDriver;
import com.example.usbseriallib.usbserial.driver.UsbSerialPort;
import com.example.usbseriallib.usbserial.util.HexDump;
import com.example.usbseriallib.usbserial.driver.UsbSerialProber;

import android.app.PendingIntent;
import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbDevice;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TwoLineListItem;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import java.lang.String;
import java.io.IOException;
import java.lang.Math.*;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private Button button2;
    private EditText textbox;
    private TextView textView;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private ListView ListView;

    private UsbManager mUsbManager;

    private List<UsbSerialPort> mEntries = new ArrayList<UsbSerialPort>();
    private ArrayAdapter<UsbSerialPort> mAdapter;

    private final String TAG = "ize";

    private static String ACTION_USB_PERMISSION = "com.example.bodnrbenjamin.elso";

    private Paint paint = new Paint();
    private Paint BackgroundPaint = new Paint();

    private ImageView mImageView;

    private Bitmap bitmap = Bitmap.createBitmap(100,100,Bitmap.Config.ARGB_8888);

    private Canvas canvas = new Canvas(bitmap);

    float[] sinus = new float[100];
    private int lineID=1;
    private LineDrawer line1;
private float num = (float) 1.6;
    public int[] asd = new int[1];
    private LineDrawer Lines;
    private int x;
    private int y;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        textbox = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        //ListView = findViewById(R.id.ListView);
        //ListView.setAdapter(mAdapter);
        mImageView = (ImageView) findViewById(R.id.imageView);
        int mul=10;
        for(int looper=0;looper<sinus.length;looper++)
        {
            sinus[looper] = (float) Math.sin((double) Math.PI/(sinus.length/2)*looper)*mul+mul;
        }
        BackgroundPaint.setColor(Color.WHITE);
        paint.setColor(Color.BLUE);
        BackgroundPaint.setColor(Color.WHITE);
        asd[0]=Color.RED;
        line1 = new LineDrawer(bitmap,mImageView);
        textView.setText(Integer.toString(bitmap.getPixel(20,60)));
        x=0;
        y=0;
    }

    public void button2OnClick(View v)
    {

        line1.addLine(x,20,60,70,Color.RED, lineID);
        lineID++;
        x+=5;
        //canvas.drawLine(0,0,20,20,paint);
        //canvas.drawLine(20,30,60,80,paint);
        textView.setText("Height: "+Integer.toString(bitmap.getHeight()));
        textView2.setText("Width: "+Integer.toString(bitmap.getWidth()));
        textView4.setText(Integer.toString(bitmap.getByteCount()));
        //line1.setLine(0,0,100,100);
        UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
        List<UsbSerialDriver> availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(manager);
        HashMap usbDevices = manager.getDeviceList();
        textView.setText(Integer.toString(bitmap.getPixel(20,60)));
        if(usbDevices.isEmpty())
        {
            textView3.setText("ide belépett igen");
            return;
        }
        else
            textView3.setText("ide belépett");

        /*if (availableDrivers.isEmpty()) {
            textView3.setText("ide belépett");
            textView.setText("Available drivers: "+Integer.toString(availableDrivers.size()));
            textView2.setText("No serial device");
            return;
        }
        textView.setText("Available drivers: "+Integer.toString(availableDrivers.size()));
        UsbSerialDriver driver = availableDrivers.get(0);
        textView2.setText(driver.toString());
        PendingIntent mPermissionIntent = PendingIntent.getBroadcast(this,0,new Intent(ACTION_USB_PERMISSION),0);
        manager.requestPermission(driver.getDevice(),mPermissionIntent);
        UsbDeviceConnection connection = manager.openDevice(driver.getDevice());
        if (connection == null) {
            // You probably need to call UsbManager.requestPermission(driver.getDevice(), ..)
            return;
        }
        UsbSerialPort port = driver.getPorts().get(0);
        try {
            port.open(connection);
            port.setParameters(115200, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);

            byte buffer[] = new byte[16];
            int numBytesRead = port.read(buffer, 1000);
            buffer[0]='a';
            buffer[1]='b';
            buffer[2]='c';
            port.write(buffer, 1000);
            Log.d(TAG, "Read " + numBytesRead + " bytes.");
        } catch (IOException e) {
            // Deal with error.
        } finally {
            //port.close();
        }*/
        /*Context context = this;
        UsbManager manager = (UsbManager)getSystemService(Context.USB_SERVICE);
        UsbInterface Interface = null;
        HashMap usbDevices = manager.getDeviceList();
        YOUR_DEVICE_NAME+=textbox.getText();*/


        /*if(!usbDevices.isEmpty())
        {
            textbox.setText("true");
            UsbDevice mDevice = (UsbDevice) usbDevices.get(YOUR_DEVICE_NAME);

            /*lekérem és kiírom a VID és PID számokat*//*
            int VendorId = mDevice.getVendorId();
            int ProductId = mDevice.getProductId();
            textView.setText("VID: "+Integer.toString(VendorId));
            textView2.setText("PID: "+ Integer.toString(ProductId));
            /*****************************************/

            /*UsbDeviceConnection connection = manager.openDevice(mDevice);
            int InterfaceCount = mDevice.getInterfaceCount();
            Interface = mDevice.getInterface(InterfaceCount-1);

            UsbEndpoint endpoint = mDevice.getInterface(0).getEndpoint(0);
            textView3.setText("Endpoint Count: "+Integer.toString(Interface.getEndpointCount()));

            //connection.claimInterface(Interface, true);


            //if(connection.claimInterface(Interface, true))
            {
                textView4.setText("Interface succesfully claimed");
            }
            //
            {

                textView4.setText("Interface could not be claimed");
                textView4.setText(Integer.toString(Interface.getInterfaceClass()));
            }

        }
        else
        {
            textView4.setText("Nincs USB bedugva!");
            while(true);
        }*/

    }
    public void buttonOnClick(View v)
    {
        long t1 = System.currentTimeMillis();
        asd[0] = Color.YELLOW;
        line1.modifyLine(1, 0,50,70,70,Color.BLUE);
        line1.modifyLine(2,0,70,70,70);
        line1.modifyLine(3, Color.GREEN);
        line1.deleteLine(5);
        long t2 = System.currentTimeMillis();
        long dt=t2-t1;
        textView.setText("t1= "+Long.toString(t1));
        textView2.setText("t2= "+Long.toString(t2));
        textView3.setText("dt= "+Long.toString(dt));
        num-=0.1;
        //canvas.drawColor(Color.WHITE);
        //mImageView.setImageBitmap(bitmap);
        //line1.clearLine();
    }
}


package com.example.bodnrbenjamin.elso;

import com.example.usbseriallib.usbserial.driver.UsbSerialDriver;
import com.example.usbseriallib.usbserial.driver.UsbSerialPort;
import com.example.usbseriallib.usbserial.util.HexDump;
import com.example.usbseriallib.usbserial.driver.UsbSerialProber;

import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import android.content.Context;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDeviceConnection;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Button;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbDevice;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.ViewGroup;
import android.widget.TwoLineListItem;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import java.lang.String;
import java.io.IOException;

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
        ListView = findViewById(R.id.ListView);




        textView.setText("ide belépett");
        ListView.setAdapter(mAdapter);
    }

    public void buttonOnClick(View v)
    {

        button.setText("006");

        String YOUR_DEVICE_NAME= "/dev/bus/usb/001/";
        UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
        List<UsbSerialDriver> availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(manager);
        if (availableDrivers.isEmpty()) {
            textView4.setText("ide belépett 1");
            return;
        }
        textView.setText(Integer.toString(availableDrivers.size()));
        UsbSerialDriver driver = availableDrivers.get(0);
        textView.setText(driver.toString());
        PendingIntent mPermissionIntent = PendingIntent.getBroadcast(this,0,new Intent(ACTION_USB_PERMISSION),0);
        manager.requestPermission(driver.getDevice(),mPermissionIntent);
        UsbDeviceConnection connection = manager.openDevice(driver.getDevice());
        if (connection == null) {
            // You probably need to call UsbManager.requestPermission(driver.getDevice(), ..)
            textView4.setText("ide belépett 2");
            return;
        }
        UsbSerialPort port = driver.getPorts().get(0);
        textView2.setText("ide belépett");
        try {
            textView3.setText("ide belépett");
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
        }
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
}

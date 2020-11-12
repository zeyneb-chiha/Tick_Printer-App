package com.example.tickp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.UUID;


public class Main5Activity extends AppCompatActivity {
    BluetoothAdapter bluetoothAdapter;
    BluetoothSocket bluetoothSocket;
    BluetoothDevice bluetoothDevice;
    InputStream inputStream;
    OutputStream outputStream;
    byte[] ReadBuffer;
    int ReadBufferPosition;
    volatile boolean StopWorker;
    Button btnPrint;
    EditText name,prename,serv,analy,mat;
    Button btnconnect;
    Button btnDisconnect;
    Button btnAjouter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);


        try {
            //create objects
            btnPrint = findViewById(R.id.button6);
            btnconnect = findViewById(R.id.button7);
            btnDisconnect = findViewById(R.id.button8);
            btnAjouter = findViewById(R.id.button9);
            name=findViewById(R.id.name);
            prename=findViewById(R.id.prename);
            serv=findViewById(R.id.serv);
            analy=findViewById(R.id.analy);
            mat=findViewById(R.id.mat);







            btnconnect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        FindBluetoothDevice();
                        openBluetoothPrinter();


                    } catch (Exception ex) {
                        ex.printStackTrace();

                    }
                }

            });
            btnPrint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {

                        printData();

                    } catch (Exception ex) {
                        ex.printStackTrace();

                    }
                }

            });
            btnDisconnect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {

                        data();

                    } catch (Exception ex) {
                        ex.printStackTrace();

                    }
                }


            });

            btnAjouter.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {

                Ajouter();


            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        });



            public void Ajouter() {
                PatientModel patientmodel;
            }
         try {

             PatientModel patientmodel = new PatientModel(name.getText().toString(), Integer.parseInt(mat).getText().toString(), prename.getText().toString(), serv.getText().toString(), analy.getText().toString());



         } catch (NullPointerException e) {
             e.printStackTrace();

         } catch (Exception ex) {
             ex.printStackTrace();

         }
                DataBaseHelper databasehelper = new DataBaseHelper(Main5Activity.this);
                boolean b = databasehelper.addOne(patientmodel);



            }

    //fin bluetooth device******************************************
    @SuppressLint("Assert")
    public void FindBluetoothDevice() {
        try {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (bluetoothAdapter == null) {
                Toast.makeText(this, "aucun bluetoothAdapter trouvé ;)", Toast.LENGTH_SHORT).show();

            }
            if (bluetoothAdapter.isEnabled()) {
                Intent enableBtn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtn, 0);
            }
            Set<BluetoothDevice> pairedDevice = bluetoothAdapter.getBondedDevices();
            if (0 < pairedDevice.size()) {
                for (BluetoothDevice device : pairedDevice)
                    if (device.getName().equals("MP006")) {
                        bluetoothDevice = device;


                        Toast.makeText(this, "Bluetooth attaché ;)", Toast.LENGTH_SHORT).show();
                        break;
                    }

            }
        } catch (NullPointerException e) {
            e.printStackTrace();

        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    //open bluetooth printer************************************************
    public void openBluetoothPrinter() throws IOException {
        if (bluetoothDevice != null)
            try {
                UUID uuidSting = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
                bluetoothSocket = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(uuidSting);
                bluetoothSocket.connect();
                outputStream = bluetoothSocket.getOutputStream();
                inputStream = bluetoothSocket.getInputStream();
                beginListenData();

            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (InterruptedIOException e) {
                e.printStackTrace();
            }
    }

    public void beginListenData() {
        try {
            final Handler handler = new Handler();
            final byte delimiter = 10;
            StopWorker = false;
            ReadBufferPosition = 0;
            ReadBuffer = new byte[1024];
            Thread thread = new Thread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void run() {
                    while (!Thread.currentThread().isInterrupted() && !StopWorker) {
                        try {
                            int byteAvailable = inputStream.available();
                            if (byteAvailable > 0) {
                                byte[] packetByte = new byte[byteAvailable];
                                inputStream.read(packetByte);
                                for (int i = 0; i < byteAvailable; i++) {
                                    byte b = packetByte[i];
                                    if (b == delimiter) {
                                        byte[] encodeByte = new byte[ReadBufferPosition];
                                        System.arraycopy(
                                                ReadBuffer, 0,
                                                encodeByte, 0,
                                                encodeByte.length
                                        );
                                        final String Data = new String(encodeByte, StandardCharsets.US_ASCII);
                                        ReadBufferPosition = 0;
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                btnPrint.setText(Data);
                                            }
                                        });
                                    } else {
                                        ReadBuffer[ReadBufferPosition++] = b;
                                    }
                                }
                            }
                        } catch (IOException ex) {
                            StopWorker = true;

                        }
                    }
                }
            });
            thread.start();

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //printing text to bluetooth printer***************************************
    @SuppressLint("SetTextI18n")

    public void printData() throws IOException {

        try { // the text typed by the user
            String msg = textBox.getText().toString();
            msg += "\n";

            if (bluetoothSocket.isConnected()) {
                outputStream.write(1);
            }


            outputStream.write(msg.getBytes());

            btnPrinterName.setText("print text");

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (InterruptedIOException e) {
            e.printStackTrace();
        }


    }

    @SuppressLint("SetTextI18n")
    private void closeBT() throws IOException {

        try {
            StopWorker = true;
            if (bluetoothSocket.isConnected()) {
                outputStream.write(1);
            }

            outputStream.close();
            inputStream.close();
            bluetoothSocket.close();
            btnPrinterName.setText("Bluetooth Closed");

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (InterruptedIOException e) {
            e.printStackTrace();
        }
    }


}




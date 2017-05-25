package com.wms.github.aidl.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

/**
 * Created by 王梦思 on 2017/5/25.
 */

public class CodeActivity extends MainActivity {
    private EditText mEditText;
    private IBinder mBinder;
    private static final String DESCRIPTOR = "com.wms.MyBinder";

    private ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //当绑定成功后调用
            mBinder = service;
            Log.e("MainActivity", "onServiceConnected...");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBinder = null;
            Log.e("MainActivity", "onServiceDisconnected...");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = (EditText) findViewById(R.id.id_edittext);

        findViewById(R.id.bind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService();
            }
        });

        findViewById(R.id.unbind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unBindService();
            }
        });

        findViewById(R.id.invokeServer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invokeServer();
            }
        });
    }

    /**
     * 绑定服务
     */
    public void bindService() {
        Intent intent = new Intent();
        intent.setAction("com.wms.github.aidl.server.MyCodeService");
        //Android 5.0以上必须要加这句代码，不然报错
        intent.setPackage("com.wms.github.aidl.server");
        bindService(intent, mServiceConn, Context.BIND_AUTO_CREATE);
    }

    public void unBindService() {
        unbindService(mServiceConn);
    }

    public void invokeServer() {
        String inputStr = mEditText.getText().toString().trim();
        try {
            android.os.Parcel _data = android.os.Parcel.obtain();
            android.os.Parcel _reply = android.os.Parcel.obtain();
            java.lang.String result = "";
            try {
                _data.writeInterfaceToken(DESCRIPTOR);
                _data.writeString(inputStr);
                mBinder.transact(0x001, _data, _reply, 0);
                _reply.readException();
                result = _reply.readString();
            } finally {
                _reply.recycle();
                _data.recycle();
            }
            //重新显示到Edittext
            mEditText.setText(result);
        } catch (RemoteException e) {
            //这里会抛出远程异常
            e.printStackTrace();
        }
    }
}

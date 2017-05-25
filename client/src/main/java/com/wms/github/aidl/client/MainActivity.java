package com.wms.github.aidl.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.wms.github.aidl.server.String2UpperCase;

/**
 * Created by 王梦思 on 2017/5/25.
 */
public class MainActivity extends AppCompatActivity {

    private EditText mEditText;
    private String2UpperCase mString2UpperCase;
    private ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //当绑定成功后调用
            mString2UpperCase = String2UpperCase.Stub.asInterface(service);
            Log.e("MainActivity","onServiceConnected...");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mString2UpperCase = null;
            Log.e("MainActivity","onServiceDisconnected...");
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
        intent.setAction("com.wms.github.aidl.server.MyService");
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
            String result = mString2UpperCase.toUpperCase(inputStr);
            //重新显示到Edittext
            mEditText.setText(result);
        } catch (RemoteException e) {
            //这里会抛出远程异常
            e.printStackTrace();
        }
    }

}

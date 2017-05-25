package com.wms.github.aidl.server;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

/**
 * Created by 王梦思 on 2017/5/25.
 */

public class MyCodeService extends Service {

    private static final String TAG = "MyService";

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind...");
        //这里不能返回null，必须要返回我们创建的Binder对象
        return new MyBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "onUnbind...");
        return super.onUnbind(intent);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.e(TAG, "onStart...");
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand...");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate...");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy...");
        super.onDestroy();
    }

    /**
     * 这里一定要继承自String2UpperCase.Stub
     */
    class MyBinder extends Binder {
        private static final String DESCRIPTOR = "com.wms.MyBinder";

        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            //code是和操作对应,这个code和客户端code对应
            switch (code) {
                case 0x001:
                    data.enforceInterface(DESCRIPTOR);
                    java.lang.String _arg0;
                    _arg0 = data.readString();
                    java.lang.String _result = _arg0.toUpperCase();
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
            }
            return super.onTransact(code, data, reply, flags);
        }
    }
}

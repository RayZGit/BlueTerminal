package com.ruizhou.blueterminal.Utils;

        import android.app.Activity;
        import android.bluetooth.BluetoothAdapter;
        import android.content.Context;
        import android.content.Intent;
        import android.widget.Toast;

public class Utils_functions {
    public static boolean checkBluetooth(BluetoothAdapter bluetoothAdapter){
        if(bluetoothAdapter == null || !bluetoothAdapter.isEnabled()){
            return false;
        }
        else{
            return true;
        }
    }

    public static void requestUserBluetooth(Activity activity){
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//        activity.startActivityForResult(enableBtIntent, MainActivity.REQUEST_ENABLE_BT);

    }

    public static void toast(Context context, String string){
        Toast toast = Toast.makeText(context, string, Toast.LENGTH_SHORT);
        toast.show();
    }

}

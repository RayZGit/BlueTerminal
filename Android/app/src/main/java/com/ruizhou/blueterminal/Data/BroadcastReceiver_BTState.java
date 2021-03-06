package com.ruizhou.blueterminal.Data;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ruizhou.blueterminal.Utils.Utils_functions;

public class BroadcastReceiver_BTState extends BroadcastReceiver
{
    Context activityContext;

    public BroadcastReceiver_BTState(Context activityContext){
        this.activityContext = activityContext;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();

        if(action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)){
            final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);

            switch (state){
                case BluetoothAdapter.STATE_OFF:
                    Utils_functions.toast(activityContext, "Bluetooth is off");
                    break;
                case BluetoothAdapter.STATE_TURNING_OFF:
                    Utils_functions.toast(activityContext, "Bluetooth is turning off");
                    break;
                case BluetoothAdapter.STATE_ON:
                    Utils_functions.toast(activityContext, "Bluetooth is on");
                    break;
                case BluetoothAdapter.STATE_TURNING_ON:
                    Utils_functions.toast(activityContext, "Bluetooth is turning on");
                    break;

            }
        }

    }
}

/**
 * Funf: Open Sensing Framework
 * Copyright (C) 2010-2011 Nadav Aharony, Wei Pan, Alex Pentland.
 * Acknowledgments: Alan Gardner
 * Contact: nadav@media.mit.edu
 *
 * This file is part of Funf.
 *
 * Funf is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * Funf is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Funf. If not, see <http://www.gnu.org/licenses/>.
 */
package edu.quantifiedself.callstressindicator;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import edu.mit.media.funf.probe.Probe;

public class CustomCallProbe extends Probe{

        public static final long DEFAULT_PERIOD = 60L * 5L;
        private static final String TAG = CustomCallProbe.class.getName();
       
        private TelephonyManager mTelephonyManager;
        private PhoneStateListener mPhoneStateListener ;
        
        @Override
        public Parameter[] getAvailableParameters() {
                return new Parameter[] {
                                new Parameter(Parameter.Builtin.PERIOD, DEFAULT_PERIOD),
                                new Parameter(Parameter.Builtin.START, 0L),
                                new Parameter(Parameter.Builtin.END, 0L)
                };
        }

        @Override
        public String[] getRequiredPermissions() {
                return new String[] {
                        Manifest.permission.ACCESS_WIFI_STATE,
                        Manifest.permission.CHANGE_WIFI_STATE,
                };
        }
       

        @Override
        public String[] getRequiredFeatures() {
                return new String[] {
                                "android.hardware.wifi"
                };
        }
       
        @Override
        protected String getDisplayName() {
                return "Nearby Wifi Devices Probe";
        }

        @Override
        public void sendProbeData() {
              /*  Bundle data = new Bundle();
                List<ScanResult> results = wifiManager.getScanResults();
                ArrayList<ScanResult> nonNullResults = new ArrayList<ScanResult>();
                if (results != null) {
                        nonNullResults.addAll(results);
                }
                data.putParcelableArrayList(SCAN_RESULTS, nonNullResults);
                sendProbeData(Utils.getTimestamp(), data);*/
        }
       
        @Override
        protected void onEnable() {
        	mTelephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        	mPhoneStateListener = new TelephonyListener(mTelephonyManager.getCallState(), this);
        	mTelephonyManager.listen(mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
            Log.i(TAG, "Service created...");

        }
       
        @Override
        protected void onDisable() {
        	Log.i(TAG, "Service stopped...");
        }

        @Override
        public void onRun(Bundle params) {
        	Log.i(TAG, "ON run"); 
//                acquireWifiLock();
//                saveWifiStateAndRunScan();
        }
       
       
       

        @Override
        public void onStop() {
        }

}


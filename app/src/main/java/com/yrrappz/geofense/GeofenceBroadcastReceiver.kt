package com.yrrappz.geofense

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

class GeofenceBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(p0: Context, intent: Intent) {

        Log.e("GeoFenceTripLoad", "START : ENTER GEOFANCING")
        val pendingResult = goAsync()
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        if (geofencingEvent.hasError()) {
            val errorMessage = "Error"
            Log.e("GeoFenceTripLoad", errorMessage)
            pendingResult.finish()
            return
        }
        // Get the transition type.
        var geofenceTransition = geofencingEvent.geofenceTransition
        var triggeringGeofences = geofencingEvent.triggeringGeofences

        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            Log.e("GeoFenceTripLoad", "ENTER GEOFANCING")
        }else if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            Log.e("GeoFenceTripLoad", "EXIT GEOFANCING")
        }

    }
}
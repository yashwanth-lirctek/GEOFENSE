package com.yrrappz.geofense

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {

    var geoFencingClient: GeofencingClient? = null

    private var mGeofenceList = ArrayList<Geofence>()
    private val geofencingRequest: GeofencingRequest
        get() {
            val builder = GeofencingRequest.Builder()
            builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            builder.addGeofences(mGeofenceList)
            return builder.build()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        geoFencingClient = LocationServices.getGeofencingClient(this)

        mGeofenceList.add(
            Geofence.Builder()
                .setRequestId("3423")

                .setCircularRegion(
                    java.lang.Double.parseDouble(12.8237187.toString()),
                    java.lang.Double.parseDouble(77.7658424.toString()),
                    2000.0f
                )
                // Set the expiration duration of the GeoFence. This GeoFence gets automatically removed after this period of time.
                .setExpirationDuration(Geofence.NEVER_EXPIRE)

                // Set the transition types of interest. Alerts are only generated for these transition. We track entry and exit transitions in this sample.
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)

                // Set the LoiteringDelay of the GeoFence.
                .setLoiteringDelay(1)

                // Create the GeoFence.
                .build()
        )

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        geoFencingClient!!.addGeofences(geofencingRequest, getGeofencePendingIntent)
            .addOnFailureListener {
                Log.e("GeoFenceTripLoad", it.localizedMessage)
            }
            .addOnCompleteListener {
                Log.e("GeoFenceTripLoad", it.toString())
            }
            .addOnSuccessListener {
            }
    }

    private val getGeofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(this, GeofenceBroadcastReceiver::class.java)
        PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun registerBroadcastReceiver() {
        val intent = Intent(this, GeofenceBroadcastReceiver::class.java)
        this.sendBroadcast(intent)
    }
}
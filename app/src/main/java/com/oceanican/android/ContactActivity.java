package com.oceanican.android;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class ContactActivity extends SherlockFragmentActivity implements GoogleMap.OnMarkerClickListener,LocationListener {

    private Location uLocation;
    View infoCard;
    boolean cardVisible=false;
    private Context context;
    GoogleMap map;
    private RequestQueue mqQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_oceanican_map);
        context = this;
        mqQueue= Volley.newRequestQueue(context);

        infoCard = findViewById(R.id.info_oceanican);
        TextView infoTtitle = (TextView) infoCard.findViewById(R.id.contact_title);
        TextView infoBody = (TextView) infoCard.findViewById(R.id.contact_body);
        ImageView closeImage = (ImageView) infoCard.findViewById(R.id.contact_close);
        infoTtitle.setText(R.string.oceanican);

        infoBody.setText(getString(R.string.direction));
        infoBody.append("\n" + getString(R.string.contactOceanican));
        infoBody.setMovementMethod(LinkMovementMethod.getInstance());
        Linkify.addLinks(infoBody, Linkify.ALL);
        map = ((SupportMapFragment) this.getSupportFragmentManager().findFragmentById(
                R.id.group_map)).getMap();
        map.setMyLocationEnabled(true);

        map.addMarker(new MarkerOptions()
                .position(new LatLng(19.2103404,-99.2102756)));

        map.setOnMarkerClickListener(this);
        closeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoCard.setVisibility(View.GONE);
            }
        });

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,this);
        uLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        locationManager.removeUpdates(this);


        ImageView routeButton = (ImageView) infoCard.findViewById(R.id.imageButton);
        routeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uLocation!=null) {
                    getRoute();
                }
            }
        });
    }




    public void getRoute(){
        String origin = uLocation.getLatitude() + ","
                + uLocation.getLongitude();

        String URL = String.format(getString(R.string.linkDirections), origin);

        Toast.makeText(context, "Obteniendo Ruta", Toast.LENGTH_SHORT).show();

        JsonObjectRequest routeJson = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject responseJson) {

                infoCard.setVisibility(View.GONE);
                cardVisible=false;

                int distancia=0;
                int tiempo =0;
                String polyline;
                try {
                    JSONArray jsonArray = responseJson.getJSONArray("routes");
                    JSONObject poly = jsonArray.getJSONObject(0).getJSONObject(
                            "overview_polyline");

                    JSONArray legs = jsonArray.getJSONObject(0).getJSONArray("legs");

                    for (int i = 0; i < legs.length(); i++) {
                        JSONObject leg = legs.getJSONObject(i);
                        distancia = distancia
                                + leg.getJSONObject("distance").getInt("value");
                        tiempo = tiempo + leg.getJSONObject("duration").getInt("value");
                    }

                    System.out.println("Distancia: " + distancia);
                    System.out.println("Tiempo: " + tiempo);

                    polyline = poly.getString("points");

                    if (polyline != null) {
                        List<LatLng> locations = DecodePoly.decodePoly(polyline);
                        map.addPolyline(new PolylineOptions()
                                .addAll(locations).width(8)
                                .color(Color.rgb(34, 140, 210)));
                       addRouteInfo(distancia, tiempo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, getString(R.string.errorLine), Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context, getString(R.string.errorLine), Toast.LENGTH_LONG).show();

            }
        }
        );

        mqQueue.add(routeJson);

    }

    @Override
    public void onBackPressed() {
        handlerCard();
    }

    public void handlerCard(){
        if(cardVisible) {
            infoCard.setVisibility(View.GONE);
            cardVisible=false;
        }
        else
            finish();
    }


    public void addRouteInfo(int distancia,int tiempo){
        LinearLayout infoRoute = (LinearLayout) findViewById(R.id.routeInfo);

        infoRoute.setVisibility(View.VISIBLE);


    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        cardVisible=true;
        infoCard.setVisibility(View.VISIBLE);
        infoCard.bringToFront();

        return true;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}

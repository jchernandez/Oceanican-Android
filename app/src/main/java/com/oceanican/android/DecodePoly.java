package com.oceanican.android;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;

public class DecodePoly{
	
public static List<LatLng> decodePoly(String encoded) {

	  List<LatLng> poly = new ArrayList<LatLng>();
	  int index = 0, len = encoded.length();
	  int lat = 0, lng = 0;

	  while (index < len) {
	      int b, shift = 0, result = 0;
	      do {
	          b = encoded.charAt(index++) - 63;
	          result |= (b & 0x1f) << shift;
	          shift += 5;
	      } while (b >= 0x20);
	      int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
	      lat += dlat;

	      shift = 0;
	      result = 0;
	      do {
	          b = encoded.charAt(index++) - 63;
	          result |= (b & 0x1f) << shift;
	          shift += 5;
	      } while (b >= 0x20);
	      int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
	      lng += dlng;

	      LatLng p = new LatLng(lat/1E5, lng/1E5);
//	      System.out.println("LatLng: "+p.toString());
	      poly.add(p);
	  }

	  return poly;
	}
}
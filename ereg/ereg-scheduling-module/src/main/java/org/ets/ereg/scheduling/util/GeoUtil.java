package org.ets.ereg.scheduling.util;

public class GeoUtil {
	public static double greatCircleDistance(double originLatitude, double originLongitude, double destinationLatitude, double destinationLongitude)
	{
		return Math.acos(Math.sin(originLatitude/57.2958279)*Math.sin(destinationLatitude/57.2958279) + Math.cos(originLatitude/57.2958279)*Math.cos(destinationLatitude/57.2958279)*Math.cos((destinationLongitude-originLongitude)/57.2958279))*3959;
	}
}

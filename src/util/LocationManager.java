package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class LocationManager {
	
	private final String FILENAME = "GeoNotes.ser";
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final String TAG = LocationManager.class.getSimpleName();
	private StringBuilder logMessage = new StringBuilder();
	public final int LOC_NUMBER = 3;
	
	public synchronized String nextLocationsTo(NoteData refObj)
	{
		
		ArrayList<NoteData> noteLocations =
		initialize(deserialize());
		
		for(int i=0; i< noteLocations.size(); i++)
		{
			NoteData loc = noteLocations.get(i);
			loc.distance = (int) Math.sqrt(Math.pow((refObj.latitude- loc.latitude), 2)
			+ Math.pow((refObj.longitude - loc.longitude), 2));
		}
		Object[] locs = noteLocations.toArray();
		Arrays.sort(locs);
		int length= (locs.length < LOC_NUMBER)?
		locs.length : LOC_NUMBER;
		StringBuffer sb = new StringBuffer();
		int counter = 0;
		while(counter < length) {
			NoteData loc = (NoteData) locs[counter];
			sb.append(loc.latitude);
			sb.append(";");
			sb.append(loc.longitude);
			sb.append(";");
			sb.append(loc.altitude);
			sb.append(";");
			sb.append(loc.time);
			sb.append(";");
			sb.append(loc.subject);
			sb.append(";");
			sb.append(loc.note);
			sb.append(";");
			sb.append(loc.id);
			sb.append(";");
			sb.append(loc.distance);
			sb.append(";#");
			counter++;
		}
		return sb.toString();
	}
	
	
	
	private boolean serialize(ArrayList<NoteData> obj)
	{
		try {
			ObjectOutputStream out =
			new ObjectOutputStream(
			new BufferedOutputStream(
			new FileOutputStream(FILENAME)));
			out.writeObject(obj);
			out.flush();
			out.close();
		return true;
		} catch (Exception e) {
			log(e.toString());
		}
		return false;
	}
	private ArrayList<NoteData> deserialize()
	{
		ArrayList<NoteData> retList = null;
		try {
				ObjectInputStream in = new ObjectInputStream(
				new BufferedInputStream(
				new FileInputStream(FILENAME)));
				retList = (ArrayList<NoteData>) in.readObject();
				in.close();
			} catch (Exception e) {
				log(e.toString());
			}
			return retList;
	 }
	 private void log(String message)
			{
				logMessage.append(sdf.format(new Date()));
				logMessage.append(" ");
				logMessage.append(TAG);
				logMessage.append(": ");
				logMessage.append(message);
				logMessage.append("\n");
		}
		// Bei Abruf von "logMessage" wird das
		// StringBuilder-Objekt zurückgesetzt:
		public String getLogMessage() {
			String temp = logMessage.toString();
			logMessage = new StringBuilder();
			return temp;
		}

		public synchronized ArrayList<NoteData> getLocations()
		{
			return initialize(deserialize());
		}
		private ArrayList<NoteData> initialize(
		ArrayList<NoteData> noteLocations)
		{
			if(noteLocations == null) {
			noteLocations = new ArrayList<NoteData>();
			noteLocations.add(new NoteData(52514366, 13350141,
			250, "1327142615386", "Berlin",
			"Siegessäule im Tiergarten", "1234567890"));
			noteLocations.add(new NoteData(52519366, 13355541,
			230, "1327142615387", "Lüneburger Straße",
			"Kreuzung Paulstraße/S-Bahn", "1234567890"));
			noteLocations.add(new NoteData(52518966, 13323841,
					250, "1327142615387", "TU Berlin",
					"im Spreebogen", "1234567890"));
			// weitere Testdaten
			}
			return noteLocations;
		}
		
		public synchronized boolean saveLocation
		(NoteData location)
		{
			ArrayList<NoteData> noteLocations =
			initialize(deserialize());
			// optionales Logging:
			log("Anzahl der verfügbaren Lokalitäten: "
			+ noteLocations.size());
			noteLocations.add(location);
			if(this.serialize(noteLocations))
			return true;
			else
			return false;
		}
}

package util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteData implements Serializable, Comparable<NoteData>{
    private static final long serialVersionUID = 1L;
	public int latitude, longitude, altitude;
	public transient int distance;
	public String time, subject, note, id;
	private static SimpleDateFormat sdf =
		new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public String getTimeStamp() {
		return sdf.format(new Date(Long.parseLong(time)));
	}
	
	public NoteData(int latitude, int longitude, int altitude, 
			String time, String subjece, String note, String id)
	{
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
		this.time = time;
		this.subject = subject;
		this.note = note;
		this.id = id;
	}

	@Override
	public int compareTo(NoteData obj) {
		// TODO Auto-generated method stub
		if(this.distance > obj.distance)
			return 1;
			else if(this.distance < obj.distance)
			return -1;
		else
			return 0;
	}
}

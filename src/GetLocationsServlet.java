

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.LocationManager;
import util.NoteData;

/**
 * Servlet implementation class for Servlet: GetLocationsServlet
 *
 */
 public class GetLocationsServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   private LocationManager locManager;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public GetLocationsServlet() {
		super();
		locManager = new LocationManager();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		//response.setContentType("text/html");
		//respondTable(out);
		response.setContentType("text/plain");
		respondCSV(request, out);
		
		this.log(locManager.getLogMessage());
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}   	 
public String [] header = new String [] {
			"Latitude", "Longitude", "Time", "Subject",
			"Note", "ID" };
			// Titel für den Browser:
private String title = "Geopositionen";

private void respondCSV(HttpServletRequest request, PrintWriter out)
		{
		try {
			NoteData refObj = new NoteData(
			Integer.parseInt(
			request.getParameter("latitude")),
			Integer.parseInt(
			request.getParameter("longitude")),
			Integer.parseInt(
			request.getParameter("altitude")),
			request.getParameter("time"),
			request.getParameter("subject"),
			request.getParameter("note"),
			request.getParameter("id"));
			out.println(
			locManager.nextLocationsTo(refObj));
		} catch(Exception exc) {
		// Logging???
		}
}
			
private void respondTable(PrintWriter out)
			{
				ArrayList<NoteData> noteLocations =
				locManager.getLocations();
				out.println("<html>");
				out.println("<head>");
				out.println("<title>" + title + "</title>");
				out.println("</head>");
				out.println("<body>");
				out.println("<h3>" + title + "</h3>");
				out.println("<table border=0>");
				out.println("<tr>");
				for(int i=0; i< header.length; i++) {
					out.println("<td bgcolor=\"#CCCCCC\">");
					out.println(header [i]);
					out.println("</td>");
				}
				out.println("</tr>");
				for(int i=0; i<noteLocations.size(); i++)
				{
					NoteData loc = noteLocations.get(i);
					out.println("<tr>");
					out.println("<td>" + loc.latitude + "</td>");
					out.println("<td>" + loc.longitude + "</td>");
					out.println("<td>" + loc.getTimeStamp() + "</td>");
					out.println("<td>" + loc.subject + "</td>");
					out.println("<td>" + loc.note + "</td>");
					out.println("<td>" + loc.id + "</td>");
					out.println("</tr>");
				}
				out.println("</table></body></html>");
				
			}
}
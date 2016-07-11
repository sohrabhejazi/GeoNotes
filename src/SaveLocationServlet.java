

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.LocationManager;
import util.NoteData;

/**
 * Servlet implementation class for Servlet: SaveLocationServlet
 *
 */
 public class SaveLocationServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
   private LocationManager locManager;
   private String firstHtml = "<html><head><title>Feedback</title><style><!-- h3 {font-family:Tahoma,Arial,sans-serif;color:white; background-color:#";
   private String secondHtml = "; font-size:18px;} p{fontfamily:Tahoma,Arial,sans-serif; background:white; color:black; font-size:14px;} --></style></head><body><h3>Bearbeitung des Speichern-Requests von Benutzer ";
   private String thirdHtml = "</h3><p>";
   private String fourthHtml = "</p></body></html>";
   //private String newLine =System.getProperty("line.separator");

   private String responseHtml(String color, String message,String id)
	   {
	   StringBuilder sb = new StringBuilder(firstHtml);
	   sb.append(color);
	   sb.append(secondHtml);
	   sb.append(id);
	   sb.append(thirdHtml);
	   sb.append(message);
	   sb.append(fourthHtml);
	   return sb.toString();
	   }
   public SaveLocationServlet() {
	   super();
	   locManager = new LocationManager();
   }
   protected void doGet(HttpServletRequest request,	HttpServletResponse response)
   throws ServletException, IOException
   {
	   response.setContentType("text/plain");
	   response.setCharacterEncoding("UTF-8");
	   PrintWriter out = response.getWriter();
	   Enumeration e = request.getParameterNames();
	   if(!e.hasMoreElements())
		   out.println(responseHtml("525222", "Kein Parameter angegeben!", "ID nich verfügbar"));
	   else {
	   try {
	   NoteData saveObj = new NoteData(
	   Integer.parseInt(request.getParameter("latitude")),
	   Integer.parseInt(request.getParameter("longitude")),
	   Integer.parseInt(request.getParameter("altitude")),
	   request.getParameter("time"),
	   request.getParameter("subject"),
	   request.getParameter("note"),
	   request.getParameter("id"));
	   if(locManager.saveLocation(saveObj))
		   out.println(responseHtml("525D76", "Location auf Server gespeichert, ergänzender Meldung: " + "<br>" 
				   + locManager.getLogMessage()+ "</br>" ,saveObj.id));
	   else
		   out.println(responseHtml("FE0A04", "Location n i c h t gespeichert, ergänzender Hinweis: " + locManager.getLogMessage(), saveObj.id));
	   } catch(Exception exc) {
		    out.println(responseHtml("FE0A04",exc.toString(), "ID nich verfügbar"));
	   }
	   }
   }
   protected void doPost(HttpServletRequest request,
   HttpServletResponse response)
   throws ServletException, IOException
   {
	   doGet(request, response);
   }
   }
//Shubham Patel
//1001376052
package com;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import javax.xml.parsers.*;
import org.xml.sax.InputSource;
import org.w3c.dom.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import gov.weather.graphical.xml.DWMLgen.wsdl.ndfdXML_wsdl.*;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Reached");
		//Using NDFDgen function to retrieve weather details
		//here creating an object of weather parameters which we need to set to true
		WeatherParametersType weatherParams = new WeatherParametersType();
		//get the latitude from the form
		String latitude=request.getParameter("latitude");
		//get the longitude from the form
		String longitude=request.getParameter("longitude");
		weatherParams.setMint(true);//Set minimum temperature to true
		weatherParams.setWspd(true);//Set Wind Speed to true
		weatherParams.setWdir(true);//Set Wind direction to true
		weatherParams.setPop12(true);//Set Probability of precipitation to true
		
		//Create an object of porttypeproxy to make a request to SOAP API of NWS
		NdfdXMLPortTypeProxy ndfdstub=new NdfdXMLPortTypeProxy();
		Calendar a =Calendar.getInstance();//This is for start and end time
        a.set(Calendar.MONTH, 4);
        a.set(Calendar.YEAR, 2017);
        a.set(Calendar.DAY_OF_MONTH, 24);
        a.setTime(new Date());
        //This is calling the SOAP client which will make a request to SOAP API using SOAP client generated through eclipse web API client
		String res=ndfdstub.NDFDgen(new BigDecimal(latitude), new BigDecimal(longitude), "time-series", a, a,"e", weatherParams);
		//Prints the response of server for particular latitude and longitude on 24th of march 2017
		System.out.println(res);
		outputVariables var=new outputVariables();
		var=parse(res);//send the response to parse method
		var.setLat(latitude);
		var.setLon(longitude);
		
		//If server give the result as ? changing it to NIL for better display
		if(var.getMint()=="?")
		{
			var.setMint("NIL");
		}
		if(var.getWdir()=="?")
		{
			var.setWdir("NIL");
		}
		if(var.getWspd()=="?")
		{
			var.setWspd("NIL");
		}
		if(var.getPop12()=="?")
		{
			var.setPop12("NIL");
		}
		
		//Send the details of four parameters to output.jsp which are stored in var object of outputvariables
		RequestDispatcher rd=request.getRequestDispatcher("output.jsp");
		request.setAttribute("variables", var);
		request.setAttribute("reached", "reached");
		rd.forward(request, response);
	}

	public outputVariables parse(String response)
	{
		//This method parses the response to retrieve the details of minimum temperature, wind speed, wind direction and probability of precipitation
		outputVariables var=new outputVariables();
		 try {
			 
			 
		        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		        DocumentBuilder db = dbf.newDocumentBuilder();
		        InputSource is = new InputSource();
		        is.setCharacterStream(new StringReader(response));
		        
		        //create a doc of response to be parsed
		        Document doc = db.parse(is);
		        	
		        	//Search for the element named temperature and retrieve the value of the same
		           NodeList tname = doc.getElementsByTagName("temperature");
		           if(tname.getLength()!=0)
		           {
		           Element tline = (Element) tname.item(0);
		           NodeList value=tline.getElementsByTagName("value");
		           Element tline2=(Element) value.item(0);
		           var.setMint(getCharacterDataFromElement(tline2));
		           }
		           
		         //Search for the element named direction and retrieve the value of the same
		           NodeList dname = doc.getElementsByTagName("direction");
		           if(tname.getLength()!=0)
		           {
		        	   Element dline = (Element) dname.item(0);
		        	   NodeList dvalue=dline.getElementsByTagName("value");
		        	   Element dline2=(Element) dvalue.item(0);
		        	   var.setWdir(getCharacterDataFromElement(dline2));
		           }
		           
		         //Search for the element named wind-speed and retrieve the value of the same
		           NodeList wname = doc.getElementsByTagName("wind-speed");        
		           if(tname.getLength()!=0)
		           {
		        	   Element wline = (Element) wname.item(0);
		        	   NodeList wvalue=wline.getElementsByTagName("value");
		        	   Element wline2=(Element) wvalue.item(0);
		        	   var.setWspd(getCharacterDataFromElement(wline2));
		           }
		           
		         //Search for the element named probability-of-precipitation and retrieve the value of the same
		           NodeList popname = doc.getElementsByTagName("probability-of-precipitation");        
		           if(tname.getLength()!=0)
		           {
		        	   	Element popline = (Element) popname.item(0);
		           		NodeList popvalue=popline.getElementsByTagName("value");
		           		Element popline2=(Element) popvalue.item(0);
		           		var.setPop12(getCharacterDataFromElement(popline2));
		           }
		           	 System.out.println("Minimum Temp: "+var.getMint());
		             System.out.println("Wind Direction: "+var.getWdir());
		             System.out.println("Wind Speed: "+var.getWspd());
		             System.out.println("Probability Of Precipitation: "+var.getPop12());
		 }
		    catch (Exception e) {
		        e.printStackTrace();
		    }
		 return var;
		 
	}
	//this function is to get the value which resides in value element
	public static String getCharacterDataFromElement(Element e) {
	    Node child = e.getFirstChild();
	    if (child instanceof CharacterData) {
	       CharacterData cd = (CharacterData) child;
	       return cd.getData();
	    }
	    return "?";
	  }
}

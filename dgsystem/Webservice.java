package com.example.dgsystem;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class Webservice {

	private static String NAMESPACE = "http://webservice.com/";
	//Webservice URL - WSDL File location	
	
	private static String URL = "http://192.168.0.30:8083/SecureMobileVotingSystem/NewWebService?wsdl";
	//SOAP Action URI again Namespace + Web method name
	private static String SOAP_ACTION = "http://webservice.com/";
	
	public static String invokelogin(String name,String password,String webMethName) {
		String resTxt = null;
		
		// Create request
		
		SoapObject request = new SoapObject(NAMESPACE, webMethName);
		// Property which holds input parameters
		request.addProperty("email",name);
		request.addProperty("pass",password);

		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		// Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			// Invoke web service
			androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
			// Get the response
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			// Assign it to resTxt variable static variable
			resTxt = response.toString();

		} catch (Exception e) {
			//Print error
			e.printStackTrace();
			//Assign error message to resTxt
			resTxt = "Error occured"+e;
		} 
		//Return resTxt to calling object
		return resTxt;
	}
}

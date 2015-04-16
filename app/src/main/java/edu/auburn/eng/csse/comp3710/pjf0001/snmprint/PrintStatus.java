package edu.auburn.eng.csse.comp3710.pjf0001.snmprint;
/*
 * http://hosteddocs.ittoolbox.com/KA110807.pdf
 * http://www.oidview.com/mibs/0/Printer-MIB.html
 */

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.net.InetAddress;
import snmp.*;

public class PrintStatus extends Activity{
	public static final String OID_BASE_LEVEL = "1.3.6.1.2.1.43.11.1.1.9.";
	public static final String OID_BLACK = "1.1";
	public static final String OID_YELLOW = "1.2";
	public static final String OID_CYAN = "1.3";
	public static final String OID_MAGENTA = "1.4";
	TextView outText;


		/*
         * This method process the request and Get the Value on the device
         * @returns String. community=READ_COMMUNITY
         */
	public String snmpGet(String strIPAddress, String community, int iSNMPVersion, String strOID){
		String str="";
		String printValues = new String();
		try{
			InetAddress hostAddress = InetAddress.getByName(strIPAddress);
			
			SNMPv1CommunicationInterface comInterface = new SNMPv1CommunicationInterface(iSNMPVersion, hostAddress, community);
			
			// returned Vector has just one pair inside it.
			SNMPVarBindList newVars = comInterface.getMIBEntry(strOID);
			
			// extract the (OID,value) pair from the SNMPVarBindList; the pair is just a two-element SNMPSequence
			SNMPSequence pair = (SNMPSequence)(newVars.getSNMPObjectAt(0));
			
			// extract the object identifier from the pair; it's the first element in the sequence
			//SNMPObjectIdentifier snmpOID = (SNMPObjectIdentifier)pair.getSNMPObjectAt(0);
			
			//extract the corresponding value from the pair; it's the second element in the sequence
			SNMPObject snmpValue = pair.getSNMPObjectAt(1);

			str = snmpValue.toString();
			if(strOID == OID_BASE_LEVEL+OID_BLACK){
                printValues += ("BLACK" + str + "%\n");
			}
			else if(strOID == OID_BASE_LEVEL+OID_YELLOW){
				printValues += ("Yellow" + str + "%\n");
			}
			if(strOID == OID_BASE_LEVEL+OID_CYAN){
				printValues += ("CYAN" + str + "%\n");
			}
			if(strOID == OID_BASE_LEVEL+OID_MAGENTA) {
				printValues += ("MAGENTA" + str + "%\n");
			}
		}
		catch(Exception e){
			System.out.println("Exception during SNMP operation: " + e + "\n");
		}
		return printValues;
	}
} 


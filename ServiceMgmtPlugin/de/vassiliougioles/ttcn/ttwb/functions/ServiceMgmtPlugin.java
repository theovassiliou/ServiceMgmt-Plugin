package de.vassiliougioles.ttcn.ttwb.functions;

import org.etsi.ttcn.tci.CharstringValue;
import org.etsi.ttcn.tci.RecordValue;
import org.etsi.ttcn.tci.TciTypeClass;
import org.etsi.ttcn.tci.Value;

import com.testingtech.ttcn.annotation.ExternalFunction;
import com.testingtech.ttcn.tri.AnnotationsExternalFunctionPlugin;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.util.Properties;
import java.io.InputStream;

// Dieser Befehl ermöglicht die Verwendung von den externen Methoden aus der TTCN-Datei
@ExternalFunction.Definitions(ServiceMgmtPlugin.class)
public class ServiceMgmtPlugin extends AnnotationsExternalFunctionPlugin {
		/* Da wird auf die Methode aus der Datei Lib_ServiceManagement verwiesen, damit diese Methode
			die richtige Eingabe bekommen kann.
	*/
	
	 @ExternalFunction(name = "startService", module = "Lib_ServiceManagement")
	 
	 /*
	  * Die folgende Methode ist dafür da, um einen Service zu starten. 
	  * Als Eingabe bekommt sie die Struktur eines Services mit allen erforderlichen Infos zum Aufbau der SSH-Verbindung.
	  */
	  public CharstringValue startService(RecordValue serviceSpec) {

		 
		 String[] fieldNames = serviceSpec.getFieldNames();
		 String output="";
		 
		 /*
			 * Anbei werden alle Infos zum Starten der SSH-Verbindung eingelesen. 
			 * Da die Authentifizierung in diesem Projekt über SSH-Key erfolgt, wird hier nur das PrivateKey geholt und kein Kennwort.
			 */
		 
			try {
				
				 String user= ((CharstringValue) serviceSpec.getField("user")).getString();
				 String host= ((CharstringValue) serviceSpec.getField("hostname")).getString();
				 RecordValue authlist = (RecordValue) serviceSpec.getField(fieldNames[3]);
				 String[] auths= authlist.getFieldNames();
				 String privateKey=  ((CharstringValue) authlist.getField("idrsa_filename")).getString();
				 RecordValue commandlist = (RecordValue) serviceSpec.getField(fieldNames[4]);
				 String[] commands= commandlist.getFieldNames();
				 // An dieser Stelle wird festgelegt, dass der Befehl StartCommand durchgeführt wird.
				 String command= ((CharstringValue) commandlist.getField("startCommand")).getString();
				 
		            JSch jsch = new JSch();

		            System.out.println("host ist "+ host);
			         // Port für SSH-Verbindung
			            int port = 22;

	
		            jsch.addIdentity(privateKey);
		            System.out.println("identity added ");
		            // Da wird die Session mit den richtigen Daten gestartet.
		            Session session = jsch.getSession(user, host, port);
		            System.out.println("session created.");
	
		            java.util.Properties config = new java.util.Properties();
	
		            config.put("StrictHostKeyChecking", "no");
		            session.setConfig(config);
		            session.connect();
		            System.out.println("session connected.....");
		            ChannelExec channel = (ChannelExec) session.openChannel("exec");
		            channel.setCommand(command);
		            channel.setPty(true);
		            channel.setErrStream(System.err);
		            InputStream in = channel.getInputStream();
		            channel.connect();
		            System.out.println("Connected...");
		            byte[] tmp = new byte[1024];
		            while (true) {
		                while (in.available() > 0) {
		                    int i = in.read(tmp, 0, 1024);
		                    if (i < 0) {
		                        break;
		                    }
		                    output=(new String(tmp, 0, i));
			                System.out.print(output);
		                }
		                if (channel.isClosed()) {
		                    System.out.println("Exit Status: "
		                            + channel.getExitStatus());
		                    break;
		                }
		                Thread.sleep(1000);
		            }
		            channel.disconnect();
		            session.disconnect();
		            System.out.println("DONE!!!");
	
	
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
			// An der Stelle wird der Status "started" erwartet und gespeichert bzw. zurückgegeben.
			 String[] parts = output.split("d");
			 String status = parts[0] +"d";
			 System.out.print(status);
			 return newCharstringValue(status); 
		  
	 }
	 
	 /*
	  *  Der Unterschied zwischen dieser Methode und der Methode "startService" besteht nur darin,
	  *  dass die Methode in der Datei Lib_ServiceManagement anders heißt. 
	  *  ein anderer wichtiger Unterschied ist, dass der Kommandbefehl, der ausgeführt wird, ist Stop.
	  *  
	  */
	
	 @ExternalFunction(name = "stopService", module = "Lib_ServiceManagement")
	  public CharstringValue stopService(RecordValue serviceSpec) {
		 String[] fieldNames = serviceSpec.getFieldNames();
		 String output="";
			 
			 // Der Aufbau der Verbindung erfolgt im Analog zur Methode startService
			 try {
				 String user= ((CharstringValue) serviceSpec.getField("user")).getString();
				 String host= ((CharstringValue) serviceSpec.getField("hostname")).getString();
				 RecordValue authlist = (RecordValue) serviceSpec.getField(fieldNames[3]);
				 String[] auths= authlist.getFieldNames();
				 String privateKey=  ((CharstringValue) authlist.getField("idrsa_filename")).getString();
				 RecordValue commandlist = (RecordValue) serviceSpec.getField(fieldNames[4]);
				 String[] commands= commandlist.getFieldNames();
				// An dieser Stelle wird festgelegt, dass der Befehl StopCommand durchgeführt wird
				 String command= ((CharstringValue) commandlist.getField("stopCommand")).getString();
		            JSch jsch = new JSch();



		            System.out.println("host ist "+ host);
			         //  System.out.println("Vergleich ist "+ an);
			            int port = 22;
			           
		            jsch.addIdentity(privateKey);
		            System.out.println("identity added ");

		            Session session = jsch.getSession(user, host, port);
		            System.out.println("session created.");

		            java.util.Properties config = new java.util.Properties();

		            config.put("StrictHostKeyChecking", "no");
		            session.setConfig(config);
		            session.connect();
		            System.out.println("session connected.....");
		            ChannelExec channel = (ChannelExec) session.openChannel("exec");
		            channel.setCommand(command);
		            channel.setPty(true);
		            channel.setErrStream(System.err);
		            InputStream in = channel.getInputStream();
		            channel.connect();
		            System.out.println("Connected...");
		            byte[] tmp = new byte[1024];
		            while (true) {
		                while (in.available() > 0) {
		                    int i = in.read(tmp, 0, 1024);
		                    if (i < 0) {
		                        break;
		                    }
		     
		                    output=(new String(tmp, 0, i));
			                System.out.print(output);
		                }
		                if (channel.isClosed()) {
		                    System.out.println("Exit Status: "
		                            + channel.getExitStatus());
		                    break;
		                }
		                Thread.sleep(1000);
		            }
		            channel.disconnect();
		            session.disconnect();
		            System.out.println("DONE!!!");


		        } catch (Exception e) {
		            e.printStackTrace();
		        }
			// An der Stelle wird der Status "stopped" erwartet und gespeichert bzw. zurückgegeben.
			 String[] parts = output.split("d");
			 String status = parts[0] +"d";
			 System.out.print(status);
			 return newCharstringValue(status); 
			
			
		 
		 
	 }
	 
	 /*
	  *  Der Unterschied zwischen dieser Methode und der Methode "startService" besteht nur darin,
	  *  dass die Methode in der Datei Lib_ServiceManagement anders heißt. 
	  *  ein anderer wichtiger Unterschied ist, dass der Kommandbefehl, der ausgeführt wird, ist Status.
	  *  
	  */
	 @ExternalFunction(name = "getServiceStatus", module = "Lib_ServiceManagement")
	  public CharstringValue getServiceStatus(RecordValue serviceSpec) {
		 
		 String[] fieldNames = serviceSpec.getFieldNames();
		
		 String output="";
		 
		 
		 try {
			
			 String user= ((CharstringValue) serviceSpec.getField("user")).getString();
			 String host= ((CharstringValue) serviceSpec.getField("hostname")).getString();
			 RecordValue authlist = (RecordValue) serviceSpec.getField(fieldNames[3]);
			 String[] auths= authlist.getFieldNames();
			 String privateKey=  ((CharstringValue) authlist.getField("idrsa_filename")).getString();
			 RecordValue commandlist = (RecordValue) serviceSpec.getField(fieldNames[4]);
			 String[] commands= commandlist.getFieldNames();
			// An dieser Stelle wird festgelegt, dass der Befehl Status durchgeführt wird
			 String command= ((CharstringValue) commandlist.getField("statusCommand")).getString();
	            JSch jsch = new JSch();   
	            int port = 22;
	            jsch.addIdentity(privateKey);
	            System.out.println("identity added ");

	            Session session = jsch.getSession(user, host, port);
	            System.out.println("session created.");

	            java.util.Properties config = new java.util.Properties();

	            config.put("StrictHostKeyChecking", "no");
	            session.setConfig(config);
	            session.connect();
	            System.out.println("session connected.....");
	            ChannelExec channel = (ChannelExec) session.openChannel("exec");
	            channel.setCommand(command);
	            channel.setPty(true);
	            channel.setErrStream(System.err);
	            InputStream in = channel.getInputStream();
	            channel.connect();
	            System.out.println("Connected...");
	            byte[] tmp = new byte[1024];
	            
	            while (true) {
	                while (in.available() > 0) {
	                    int i = in.read(tmp, 0, 512);
	                    if (i < 0) {
	                        break;
	                    }
	                    output=(new String(tmp, 0, i));
	                   System.out.print(output);
	                }
	                if (channel.isClosed()) {
	                    System.out.println("Exit Status: "
	                            + channel.getExitStatus());
	                    break;
	                }
	                Thread.sleep(1000);
	            }
	            channel.disconnect();
	            session.disconnect();
	            System.out.println("DONE!!!");


	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		// An der Stelle wird der Status "Running" erwartet und gespeichert bzw. zurückgegeben.
		 String[] parts = output.split("g");
		 String status = parts[0] +"g";
		 System.out.print(status);
		 return newCharstringValue(status); 
	 }
}

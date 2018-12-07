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

@ExternalFunction.Definitions(ServiceMgmtPlugin.class)
public class ServiceMgmtPlugin extends AnnotationsExternalFunctionPlugin {

	 @ExternalFunction(name = "startService", module = "Lib_ServiceManagement")
	  public CharstringValue startService(RecordValue serviceSpec) {

		 
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
				 
				 String command= ((CharstringValue) commandlist.getField("startCommand")).getString();
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
			 String[] parts = output.split("d");
			 String status = parts[0] +"d";
			 System.out.print(status);
			 return newCharstringValue(status); 
		  
	 }
	 
	 
	 @ExternalFunction(name = "stopService", module = "Lib_ServiceManagement")
	  public CharstringValue stopService(RecordValue serviceSpec) {
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
			 String[] parts = output.split("d");
			 String status = parts[0] +"d";
			 System.out.print(status);
			 return newCharstringValue(status); 
			
			
		 
		 
	 }
	 
	 
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
			 
			 String command= ((CharstringValue) commandlist.getField("statusCommand")).getString();
	            JSch jsch = new JSch();

	           
	           //System.out.println(command);
	        
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
		 String[] parts = output.split("g");
		 String status = parts[0] +"g";
		 System.out.print(status);
		 return newCharstringValue(status); 
	 }
}

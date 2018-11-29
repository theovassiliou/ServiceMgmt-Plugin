package de.vassiliougioles.ttcn.ttwb.functions;

import org.etsi.ttcn.tci.CharstringValue;
import org.etsi.ttcn.tci.RecordValue;

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
		 
		 for (String string : fieldNames) {
			 System.out.println("The field name is " + string + " and the values are " + serviceSpec.getField(string));
		}

		 return newCharstringValue("This is a RESULT"); 
	 }
	 
	 
	 @ExternalFunction(name = "stopService", module = "Lib_ServiceManagement")
	  public CharstringValue stopService(RecordValue serviceSpec) {
		 
		 System.out.println("WE ARE IN EXTERNAL FUNCTIONS STOP");
		 //logInfo("WE ARE IN EXTERNAL FUNCTIONS STOP");
		 


		 return newCharstringValue("This is a RESULT");  
	 }
	 
	 
	 @ExternalFunction(name = "getServiceStatus", module = "Lib_ServiceManagement")
	  public CharstringValue getServiceStatus(RecordValue serviceSpec) {
		 
		 logInfo("WE ARE IN EXTERNAL FUNCTIONS GETSERVICESTATUS");
		 System.out.println("WE ARE IN EXTERNAL FUNCTIONS GETSERVICESTATUS");
		 try {
	            JSch jsch = new JSch();

	            String user = "wowi";
	            String host = "solv116";
	            int port = 22;
	            String privateKey = "H:\\ssh/id_rsa";
	            String command = " sudo  /etc/init.d/mywowi-login-gateway status";



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
	                    System.out.print(new String(tmp, 0, i));
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
		 return newCharstringValue("This is a RESULT"); 
	 }
}

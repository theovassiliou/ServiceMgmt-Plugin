package de.vassiliougioles.ttcn.ttwb.functions;

import org.etsi.ttcn.tci.CharstringValue;
import org.etsi.ttcn.tci.RecordValue;

import com.testingtech.ttcn.annotation.ExternalFunction;
import com.testingtech.ttcn.tri.AnnotationsExternalFunctionPlugin;


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
		 
		 logInfo("WE ARE IN EXTERNAL FUNCTIONS STOP");
		 return null; 
	 }
}

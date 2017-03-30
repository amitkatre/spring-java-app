package org.example;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.example.resources.InvoicePdfMergeController;

import com.sun.grizzly.http.SelectorThread;
import com.sun.jersey.api.container.grizzly.GrizzlyWebContainerFactory;

public class Main {
    public static void main(String[] args) throws Exception {
        /*
        final String baseUri = "http://localhost:"+(System.getenv("PORT")!=null?System.getenv("PORT"):"9998")+"/";
        final Map<String, String> initParams = new HashMap<String, String>();
        initParams.put("com.sun.jersey.config.property.packages","org.example.resources");

        System.out.println("Starting grizzly...");
        SelectorThread threadSelector = GrizzlyWebContainerFactory.create(baseUri, initParams);
        System.out.println(String.format("Jersey started with WADL available at %sapplication.wadl.",baseUri, baseUri));
    	*/
    	String pId = "a7x3C000000L0Jr";
		String woId = "a1r3C000000L1hz";
		InvoicePdfMergeController.mergeAttachmentControl(pId,woId);
    }
}

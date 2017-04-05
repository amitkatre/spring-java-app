package com.varian.pdf.resources;

import java.io.*;

import javax.print.PrintService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.StreamingOutput;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/blob")
public class PdfMergeResource {
	
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("text/plain")
    public String handleUpload(@FormDataParam("file") InputStream in,
                               @FormDataParam("file") FormDataContentDisposition fileDetail) throws Exception {
        return "No Post Opertaion Available";
    }
    
    @GET
    @Path("/{file}")
    @Produces("application/pdf")
    public File handleMerge(@PathParam("file") final String file) throws Exception {    	
    	System.out.println("File "+file+" requested");
		try {
			
			SalesforcePdfMergeController obj = new SalesforcePdfMergeController();
			if(obj.isAuthenticated()){
				File outputfile = obj.mergeAttchments(file);
				System.out.println("Documents merged succesfully");
				return outputfile;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("File "+e.getMessage());
		}
		System.out.println("File");
    	return null;
    }
    
    @GET
    @Path("/")
    @Produces("application/pdf")
    public String getOnly(@PathParam("file") final String file) throws Exception {    	
    	return "success";
    }
    
}

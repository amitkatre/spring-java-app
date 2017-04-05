package com.varian.pdf.resources;

/*
import java.io.*;

import javax.print.PrintService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.StreamingOutput;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

*/
/*@Path("/blob") */
public class BlobResourceOldBackup {
	/*
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("text/plain")
    public String handleUpload(@FormDataParam("file") InputStream in,
                               @FormDataParam("file") FormDataContentDisposition fileDetail) throws Exception {

        /*if(fileDetail==null || fileDetail.getFileName()==null) {
            return "No filename";
        }
        System.out.println("Receiving file "+fileDetail.getFileName());
        File f = new File(fileDetail.getFileName());
        long ts = System.currentTimeMillis();
        FileOutputStream out = new FileOutputStream(f);

        byte[] buf = new byte[16384];
        int len = in.read(buf);
        while(len!=-1) {
            out.write(buf,0,len);
            len = in.read(buf);
        }
        out.close();
        System.out.println("Received file "+f.getName()+" in "+(System.currentTimeMillis()-ts)/1000+"s");
        
    	String pId = "a7x3C000000L0Jr";
		String woId = "a1r3C000000L1hz";
		InvoicePdfMergeController.mergeAttachmentControl(pId,woId);
        return "Success";
        
    }
    /*
    @GET
    @Path("/{file}")
    @Produces("application/pdf")
    public File handleDownload(@PathParam("file") final String file) throws Exception {    	
    	System.out.println("File "+file+" requested");
        String[] value_split = file.split("\\|");
        System.out.println("File "+file+" successfully downloaded in1");
        String pId = value_split[0];
		String woId = value_split[1].split("\\.")[0];
		try {
			InvoicePdfMergeController.mergeAttachmentControl(pId,woId);
			System.out.println("File "+file+" successfully downloaded in2");
            File outputfile = new File("pdf/merge-pdf-result.pdf");
            return outputfile;
           
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("File "+e.getMessage());
		}
		System.out.println("File");
    	return null;
    }
    
    
    
    @GET
    @Path("/{file}")
    @Produces("application/pdf")
    public File handleDownload(@PathParam("file") final String file) throws Exception {    	
    	System.out.println("File "+file+" requested");
		try {
			InvoicePdfMergeController.mergeAttachmentControl(file);
			System.out.println("File "+file+" successfully downloaded in2");
            File outputfile = new File("pdf/merge-pdf-result.pdf");
            return outputfile;
            
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("File "+e.getMessage());
		}
		System.out.println("File");
    	return null;
    }
    
    */
    
}

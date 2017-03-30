package org.example.resources;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.sforce.soap.enterprise.Connector;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.Error;
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.SaveResult;
import com.sforce.soap.enterprise.sobject.Attachment;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

public class InvoicePdfMergeController {

	private static final String USERNAME = "vms-akatre@varian.com.sfdev";
	private static final String PASSWORD = "@thumsup2zT5K0sEjOSUJtmXG23sp8LEeB";
	private static final String endpoint = "https://test.salesforce.com/services/Soap/c/39.0/00D3C0000008iNt";
	private static EnterpriseConnection connection;
	private static InputStream is;
	
	public static void test() throws Exception{
		String pId = "a7x3C000000L0Jr";
		String woId = "a1r3C000000L1hz";
		mergeAttachmentControl(pId,woId);
	}
	
	public static void mergeAttachmentControl(String pId, String woId) throws Exception {
		
		ConnectorConfig config = new ConnectorConfig();
		config.setUsername(USERNAME);
		config.setPassword(PASSWORD);
		config.setAuthEndpoint(endpoint);

		try {
			connection = Connector.newConnection(config);
			// display some current settings
			System.out.println("Auth EndPoint: " + config.getAuthEndpoint());
			System.out.println("Service EndPoint: " + config.getServiceEndpoint());
			System.out.println("Username: " + config.getUsername());
			System.out.println("SessionId: " + config.getSessionId());
			mergeAttchments(pId,woId);

		} catch (ConnectionException e1) {
			e1.printStackTrace();
		}

	}
	
	private static void uploadAttachment(String parentid) throws IOException, ConnectionException {
		File f = new File("pdf/merge-pdf-result.pdf");
		is = new FileInputStream(f);
		byte[] Body = getBytesFromInputStream(is);
		Attachment acc = new Attachment();
		acc.setBody(Body);
		acc.setContentType("pdf");
		acc.setParentId(parentid);
		acc.setName("MergedInvandWoFSR.pdf"); 
		Attachment[] records = new Attachment[1];
		records[0] = acc;
		SaveResult[] saveResults = connection.create(records);
		for (int i = 0; i < saveResults.length; i++) {
			if (saveResults[i].isSuccess()) {
				System.out.println(i + ". Successfully created record - Id: " + saveResults[i].getId());
			} else {
				Error[] errors = saveResults[i].getErrors();
				for (int j = 0; j < errors.length; j++) {
					System.out.println("ERROR creating record: " + errors[j].getMessage());
				}
			}
		}
	}
	
	private static void mergeAttchments(String pId, String woId) {
		try {
			List<InputStream> inputPdfList = new ArrayList<InputStream>();
			QueryResult queryResultsAttachment;

			queryResultsAttachment = connection
					.query("SELECT Body " + "FROM Attachment " + "where ParentId ='" + pId + "'");
			if (queryResultsAttachment.getSize() > 0) {
				Attachment aObj = (Attachment) queryResultsAttachment.getRecords()[0];
				System.out.println(aObj.getBody());
				InputStream myInputStream = new ByteArrayInputStream(aObj.getBody());
				inputPdfList.add(myInputStream);
			}
			
			queryResultsAttachment = connection
					.query("SELECT Body " + "FROM Attachment " + "where ParentId ='" + woId + "'");
			if (queryResultsAttachment.getSize() > 0) {
				Attachment aObj = (Attachment) queryResultsAttachment.getRecords()[0];
				System.out.println(aObj.getBody());
				InputStream myInputStream = new ByteArrayInputStream(aObj.getBody());
				inputPdfList.add(myInputStream);
			}

			createDocument(inputPdfList);
			uploadAttachment(pId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static byte[] getBytesFromInputStream(InputStream is) throws IOException {
		try (ByteArrayOutputStream os = new ByteArrayOutputStream();) {
			byte[] buffer = new byte[0xFFFF];
			for (int len; (len = is.read(buffer)) != -1;)
				os.write(buffer, 0, len);
			os.flush();
			return os.toByteArray();
		}
	}
	
	public static void createDocument(List<InputStream> inputPdfList) throws DocumentException, IOException{
		Document document = new Document();
        PdfCopy copy = new PdfCopy(document, new FileOutputStream("pdf/merge-pdf-result.pdf"));
        document.open();
        Iterator<InputStream> pdfIterator = inputPdfList.iterator();		
		while (pdfIterator.hasNext()) {
            PdfReader reader = new PdfReader(pdfIterator.next());
            copy.addDocument(reader);
            copy.freeReader(reader);
            reader.close();
		}
       
        document.close();
        System.out.println("Pdf files merged successfully.");
	}


}
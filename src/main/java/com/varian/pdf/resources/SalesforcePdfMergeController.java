package com.varian.pdf.resources;

import java.io.ByteArrayInputStream;
import java.io.File;
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
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.sobject.Attachment;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

/*
 * Author : Amitkumar Katre
 * Description : Merge controller 
 */
public class SalesforcePdfMergeController {
	
	/* Load login credentials from environment variables */
	private static final String USERNAME = System.getenv("SF_USERNAME");
	private static final String PASSWORD = System.getenv("SF_PASSWORD");
	private static final String endpoint = System.getenv("SF_LOGIN_URL");
	private static EnterpriseConnection connection;

	
	/*
	 * Authenticate using user name and password
	 */
	public boolean isAuthenticated() {
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
			if (config.getSessionId() != null) {
				return true;
			} else {
				return false;
			}

		} catch (ConnectionException e1) {
			e1.printStackTrace();
			return false;
		}
	}
	
	/*
	 *  Query and Merge documents
	 */
	public File mergeAttchments(String attachmentIds) {
		try {
			String[] attachmentIdsArray = attachmentIds.split("\\|");
			List<InputStream> inputPdfList = new ArrayList<InputStream>();
			for (int i = 0; i < attachmentIdsArray.length; i++) {
				String attachmentId = attachmentIdsArray[i].split("\\.")[0];
				QueryResult queryResultsAttachment;
				queryResultsAttachment = connection
						.query("SELECT Body " + "FROM Attachment " + "where Id ='" + attachmentId + "'");
				if (queryResultsAttachment.getSize() > 0) {
					Attachment aObj = (Attachment) queryResultsAttachment.getRecords()[0];
					System.out.println(aObj.getBody());
					InputStream myInputStream = new ByteArrayInputStream(aObj.getBody());
					inputPdfList.add(myInputStream);
				}
			}
			return createDocument(inputPdfList);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	/*
	 * Create merged document
	 */
	private File createDocument(List<InputStream> inputPdfList) throws DocumentException, IOException {
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
		File outputfile = new File("pdf/merge-pdf-result.pdf");
        return outputfile;
	}

}
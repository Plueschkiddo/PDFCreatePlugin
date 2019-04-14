package com.plueschkiddo.cordova.plugin;

// Cordova-required packages
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// PDF-manipulation-required packages
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

// file-manipulation-required packages
import java.io.File;

// error-handling-required packages
import java.io.IOException;
import java.net.URL; 

public class PDFCreate extends CordovaPlugin {
	
	
	public boolean execute(String action, JSONArray args,
    final CallbackContext callbackContext) {
      // Verify that the user sent a 'show' action
      if (!action.equals("createpdf")) {
        callbackContext.error("\"" + action + "\" is not a recognized action.");
        return false;
      }
      String message;
      String duration;
      try {
        JSONObject options = args.getJSONObject(0);
        message = options.getString("message");
        duration = options.getString("duration");
      } catch (JSONException e) {
        callbackContext.error("Error encountered: " + e.getMessage());
        return false;
      }
		
		URL url = PDFCreate.class.getClassLoader().getResource("VorlageGELOS.pdf");
//      Loading an existing document
        PDDocument doc = null;
        try {
            doc = PDDocument.load(new File(url.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

//      Create one page and add it to the document
        PDPage page = doc.getPage(0);
        PDPage page2 = doc.getPage(1);

//      Creates a content stream object to write to page1 of the pdf file
        try {
            PDPageContentStream contentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, false);
            PDPageContentStream contentStream2 = new PDPageContentStream(doc, page2, PDPageContentStream.AppendMode.APPEND, false);

            contentStream.beginText();

            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
            contentStream.setLeading(15.7);

            //Setting the position for the line
            contentStream.newLineAtOffset(245, 757);
            contentStream.showText("0");

            for (int i = 1; i < 92; i++){
                if (i == 46){
                    contentStream.newLineAtOffset(212, 706);
                    contentStream.showText("46");
                }
                else if(i < 54 && i > 48 || i == 73 || i == 74 || i == 19){
                    contentStream.newLine();
                } else {
                    contentStream.newLine();
                    contentStream.showText(Integer.toString(i));
                }
            }

            contentStream.endText();
            contentStream.close();

            contentStream2.beginText();
            contentStream2.setFont(PDType1Font.TIMES_ROMAN, 12);
            contentStream2.setLeading(15.7);

            contentStream2.newLineAtOffset(245, 757);
            contentStream2.showText("92");

            for(int i = 93; i < 131; i++){

                if(i == 106 || i == 126){
                    contentStream2.newLineAtOffset(0, -10);
                }

                if(i == 112){
                    contentStream2.newLineAtOffset(212, 323);
                }

                contentStream2.newLine();
                contentStream2.showText(Integer.toString(i));
            }

            contentStream2.endText();
            contentStream2.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        // try {
            // doc.save("c:\\users\\user\\downloads\\file.pdf");
        // } catch (ioexception e) {
            // e.printstacktrace();
        // }

        System.out.println("PDF Document has been created successfully!");

//      closes the document
        try {
            doc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
      // Send a positive result to the callbackContext
      PluginResult pluginResult = new PluginResult(PluginResult.Status.OK);
      callbackContext.sendPluginResult(pluginResult);
      return true;
  }
}
package com.cnsi.evoBrixX;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;

import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

public class Html2PdfUsingOpenHtml {

	private static final String HTML_INPUT = "src/main/resources/input.html";
	private static final String PDF_OUTPUT = "src/main/resources/output.pdf";

	public static void main(String[] args) {
		try {
			File inputHTML = new File(HTML_INPUT);
			Document doc = createXHTMLDoc(inputHTML);
			xhtmlToPdfConverter(doc, PDF_OUTPUT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Document createXHTMLDoc(File inputHTML) throws IOException {
		Document document = Jsoup.parse(inputHTML, "UTF-8");
		document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
		return document;
	}

	private static void xhtmlToPdfConverter(Document doc, String outputPdf) throws IOException {
		try (OutputStream os = new FileOutputStream(outputPdf)) {
			String baseUri = FileSystems.getDefault().getPath("src/main/resources/").toUri().toString();
			PdfRendererBuilder builder = new PdfRendererBuilder();
			builder.withUri(outputPdf);
			builder.toStream(os);
			builder.withW3cDocument(new W3CDom().fromJsoup(doc), baseUri);
			builder.run();
		}
	}
}
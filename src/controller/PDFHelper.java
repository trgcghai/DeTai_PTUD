package controller;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;

import view.TurnoverDetailCountDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class PDFHelper {
	public static void openFile(File file) {
		try {
			Desktop.getDesktop().open(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void exportToPdf(TurnoverDetailCountDialog dialog) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.showSaveDialog(dialog);
		File saveFile = fileChooser.getSelectedFile();
		if(saveFile!=null) {
			try {
				saveFile = new File(saveFile.toString() + ".pdf");
				PdfWriter pdfWriter = new PdfWriter(saveFile);
				PdfDocument pdfDocument = new PdfDocument(pdfWriter);
				pdfDocument.setDefaultPageSize(PageSize.A4);
				Document document = new Document(pdfDocument);
				
				BufferedImage labelImage = convertComponentToImage(dialog);
				Image labelPdfImage = new Image(ImageDataFactory.create(labelImage, null));
				document.add(labelPdfImage);
				
				document.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			openFile(saveFile);
		}
		else {
			JOptionPane.showMessageDialog(dialog, "Vui lòng chọn file");
		}
    }
	
	private static BufferedImage convertComponentToImage(Component component) {
        // Create a BufferedImage for the component
        BufferedImage image = new BufferedImage(component.getWidth(), component.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        component.paint(g2d);
        g2d.dispose();
        return image;
    }
}

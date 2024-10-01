package controller;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelHelper {
	private static void openFile(String file) {
		try {
			File path = new File(file);
			Desktop.getDesktop().open(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void exportData(JFrame frame, JTable table) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.showSaveDialog(frame);
		File saveFile = fileChooser.getSelectedFile();
		if (saveFile != null) {
			saveFile = new File(saveFile.toString() + ".xlsx");
			Workbook wb = new XSSFWorkbook();
			Sheet sheet = wb.createSheet();
			Row rowCol = sheet.createRow(0);
			for (int i = 0; i < table.getColumnCount(); i++) {
				Cell cell = rowCol.createCell(i);
				cell.setCellValue(table.getColumnName(i));
			}
			
			for (int i = 0; i < table.getRowCount(); i++) {
				Row row = sheet.createRow(i + 1);
				for (int j = 0; j < table.getColumnCount(); j++) {
					Cell cell = row.createCell(j);
					if (table.getValueAt(i, j) != null) {
						cell.setCellValue(table.getValueAt(i, j).toString());
					}
				}
			}
			try {
				FileOutputStream out = new FileOutputStream(new File(saveFile.toString()));
				wb.write(out);
				wb.close();
				out.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			openFile(saveFile.toString());
		} else {
			JOptionPane.showMessageDialog(frame, "Vui lòng chọn file");
		}
	}
	
	public void exportDataDialog(JDialog JDialog, JTable table) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.showSaveDialog(JDialog);
		File saveFile = fileChooser.getSelectedFile();
		if (saveFile != null) {
			saveFile = new File(saveFile.toString() + ".xlsx");
			Workbook wb = new XSSFWorkbook();
			Sheet sheet = wb.createSheet();
			Row rowCol = sheet.createRow(0);
			for (int i = 0; i < table.getColumnCount(); i++) {
				Cell cell = rowCol.createCell(i);
				cell.setCellValue(table.getColumnName(i));
			}
			
			for (int i = 0; i < table.getRowCount(); i++) {
				Row row = sheet.createRow(i + 1);
				for (int j = 0; j < table.getColumnCount(); j++) {
					Cell cell = row.createCell(j);
					if (table.getValueAt(i, j) != null) {
						cell.setCellValue(table.getValueAt(i, j).toString());
					}
				}
			}
			try {
				FileOutputStream out = new FileOutputStream(new File(saveFile.toString()));
				wb.write(out);
				wb.close();
				out.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			openFile(saveFile.toString());
		} else {
			JOptionPane.showMessageDialog(JDialog, "Vui lòng chọn file");
		}
	}

}

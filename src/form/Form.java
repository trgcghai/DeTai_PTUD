package form;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.Table;
import com.spire.doc.fields.Field;
 
public class Form {
	
	private static void addRows(Table table, int rowNum) {
	    for (int i = 0; i < rowNum; i++) {
	        //insert specific number of rows by cloning the second row
	        table.getRows().insert(2 + i, table.getRows().get(1).deepClone());
	        //update formulas for Total
	        for (Object object : table.getRows().get(2 + i).getCells().get(3).getParagraphs().get(0).getChildObjects()
	        ) {
	            if (object instanceof Field) {
	                Field field = (Field) object;
	                field.setCode(String.format("=B%d*C%d\\# \"0.00\"", 3 + i,3 + i));
	            }
	            break;
	        }
	    }
	    //update formula for Total Tax
	    for (Object object : table.getRows().get(4 + rowNum).getCells().get(3).getParagraphs().get(0).getChildObjects()
	    ) {
	        if (object instanceof Field) {
	            Field field = (Field) object;
	            field.setCode(String.format("=D%d*0.05\\# \"0.00\"", 3 + rowNum));
	        }
	        break;
	    }
	    //update formula for Balance Due
	    for (Object object : table.getRows().get(5 + rowNum).getCells().get(3).getParagraphs().get(0).getChildObjects()
	    ) {
	        if (object instanceof Field) {
	            Field field = (Field) object;
	            field.setCode(String.format("=D%d+D%d\\# \"$#,##0.00\"", 3 + rowNum, 5 + rowNum));
	        }
	        break;
	    }
	}
	
	private static void fillTableWithData(Table table, String[][] data) {
	    for (int r = 0; r < data.length; r++) {
	        for (int c = 0; c < data[r].length; c++) {
	            //fill data in cells
	            table.getRows().get(r + 1).getCells().get(c).getParagraphs().get(0).setText(data[r][c]);
	        }
	    }
	}
	private static void writeDataToDocument(Document doc, String[][] purchaseData) {
	    //get the third table
	    Table table = doc.getSections().get(0).getTables().get(2);
	    //determine if it needs to add rows
	    if (purchaseData.length > 1) {
	        //add rows
	        addRows(table, purchaseData.length - 1);
	    }
	    //fill the table cells with value
	    fillTableWithData(table, purchaseData);
	}
    public static void main(String[] args) {
 
        //create a document instance
        Document doc = new Document();
 
        //load the template file
        doc.loadFromFile("src/form/form.docx");
 
        //replace text in the document
        doc.replace("#InvoiceNum", "sadasd", true, true);
        
        doc.replace("#CompanyName", "FPT", true, true);
        doc.replace("#CompanyAddress", "122 4th Ave", true, true);
        doc.replace("#CityStateZip", "New York, NY 10011", true, true);
        doc.replace("#Country", "VietNam", true, true);
        doc.replace("#Tel1", "111-222-333", true, true);
        
        doc.replace("#ContactPerson", "MinhDat", true, true);
        doc.replace("#ShippingAddress", "NhaTrang", true, true);
        doc.replace("#Tel2", "111-222-334", true, true);
 
        //define purchase data
        String[][] purchaseData = {
                new String[]{"Hợp đồng làm việc của Le Tuấn", "1", "2222222.8"}
        };
 
        //write the purchase data to the document
        writeDataToDocument(doc, purchaseData);
 
        //update fields
        doc.isUpdateFields(true);
 
        //save file in pdf format
        doc.saveToFile("Invoice.pdf", FileFormat.PDF);
    }
}
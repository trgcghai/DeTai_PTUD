package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFormattedTextField.AbstractFormatter;

public class LabelDateFormatter extends AbstractFormatter {
	
	SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy");

	@Override
	public Object stringToValue(String text) throws ParseException {
		// TODO Auto-generated method stub
		return format.parseObject(text);
	}

	@Override
	public String valueToString(Object value) throws ParseException {
		// TODO Auto-generated method stub
		if(value!=null) {
			return format.format(((Calendar)value).getTime());
		}
		return "";
	}

}

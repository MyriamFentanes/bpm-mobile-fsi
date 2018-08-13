package org.demo.jbpm.utils;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateCreator {

	public static void main(String[] args) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println(date.toString() + " " + System.currentTimeMillis());
		System.out.println(dateFormat.format(date)); //2013/10/15 16:16:39

	}

}

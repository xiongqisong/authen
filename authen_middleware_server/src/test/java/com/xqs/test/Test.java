package com.xqs.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

public class Test {
	public static void main(String[] args) throws IOException {
		File a = new File("F:/a.txt");
//		PrintWriter pw = new PrintWriter(new FileOutputStream(a));
//		pw.write("yyy");
//		pw.flush();
//		pw.close();
//		
//		System.out.println(a.delete());
		
		System.out.println(a.exists());
		FileOutputStream os = new FileOutputStream(a);
	}
}

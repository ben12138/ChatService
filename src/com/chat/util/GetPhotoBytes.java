package com.chat.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.chat.database.DataBaseHelper;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class GetPhotoBytes {
	private static byte[] bytes = new byte[1024];
	public static byte[] getPhotoBytes(String path){
		
		File file = new File(path);
		
		FileInputStream fis = null;
		
		if(!file.exists()){
			bytes = null;
		}
		try {
			fis = new FileInputStream(file);
			int size;
			while((size = fis.read(bytes)) != -1){
				fis.read(bytes, 0, size);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return bytes;
		
	}
	
}

package com.log.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FileUtil {
	/**
	 * 写入文件
	 * @param content 写入内容
	 * @param fileName 文件名
	 */
	public static void writeFile(String content,File fileName){
		FileOutputStream fo = null;
		try {
			fo = new FileOutputStream(fileName,false);
			fo.write(content.getBytes("utf-8"));
			fo.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				fo.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 读取每行日志记录到List
	 * @param fileName 文件名
	 * @return 日志记录
	 */
	public static List readFile(File fileName){
		
		FileReader fr = null;
		BufferedReader br = null;
		String read = "";
		List list = new ArrayList<String>();
		try {
			fr = new FileReader(fileName);
			br = new BufferedReader(fr);
			//文件中读取全部日志信息，并存入List
			while((read = br.readLine())!=null){
				list.add(read);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				fr.close();
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 备份文件
	 * @param oldName 原始文件名
	 * @param newName 新文件名
	 */
	public static void copyFile(File oldFile,File newFile){
		FileInputStream fi = null;
		FileOutputStream fo = null;
		
		try {
			fi = new FileInputStream(oldFile);
			fo = new FileOutputStream(newFile);
			
			byte buff[] = new byte[1024];
			int length = 0;
			while((length = fi.read(buff)) != -1){
				fo.write(buff, 0, length);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				fi.close();
				fo.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void deleteFile(File file){
		if(file.exists()){
			file.delete();
		}
	}
	
	public static void modifyFile() throws UnsupportedEncodingException, IOException{
		String filename = "D:/test.txt";
		RandomAccessFile rf = new RandomAccessFile(filename, "rw");
		// for (int i = 0; i < rf.length(); i++) {
		// rf.seek(i);
		// byte b = rf.readByte();
		// if (b == '2') {
		// rf.seek(i);
		// rf.writeByte(b + 3);
		// }
		// }
		String str = "";
		String str2 = "";
		long num = 0;
		long num2 = 0;
		StringBuffer sb = new StringBuffer();
		
		boolean flag = false;
		while ((str = rf.readLine()) != null) {
			str = new String(str.getBytes("8859_1"), "utf-8");// 编码转换
			
			if (str.indexOf("niccy") != -1 && str.indexOf("login") != -1) {
				str = str.replace("7", "3");
				flag = true;
			}
			if(flag){
				num2 = rf.getFilePointer();
				break;
			}
			num = rf.getFilePointer();
		}
		
		
		rf.seek(num2);
		while((str2 = rf.readLine()) != null){
			//将数据写入临时文件
			str2 = new String(str2.getBytes("8859_1"), "utf-8");// 编码转换
			sb.append(str2 + "\r\n");
		}
		
		rf.seek(num);
		rf.write(str.getBytes("utf-8"));
		
		
		rf.seek(rf.getFilePointer());
		rf.write(("\r\n" + sb.toString()).getBytes("utf-8"));
		
		rf.close();

	}
	
	public static void updateFileLog(File file,String username,String method,int count){
				
		String str = "";
		String str2 = "";
		long start = 0;
		long end = 0;
		StringBuffer sb = new StringBuffer();
		RandomAccessFile rf = null;
		
		try {
			rf = new RandomAccessFile(file, "rw");
			boolean flag = false;
			while ((str = rf.readLine()) != null) {
				str = new String(str.getBytes("8859_1"), "utf-8");// 编码转换
				String data[] = str.split(" ");
				if(data[0].equals(username) && data[1].equals(method)){
					str = str.replace(data[2], Integer.toString((Integer.parseInt(data[2])+count)));
					flag = true;
				}
				if(flag){
					end = rf.getFilePointer();
					break;
				}
				start = rf.getFilePointer();
			}
			
			if(flag){
				rf.seek(end);
				while((str2 = rf.readLine()) != null){
					//将数据写入临时文件
					str2 = new String(str2.getBytes("8859_1"), "utf-8");// 编码转换
					sb.append(str2 + "\r\n");
				}
				
				rf.seek(start);
				rf.write(str.getBytes("utf-8"));
				
				
				rf.seek(rf.getFilePointer());
				rf.write(("\r\n" + sb.toString()).getBytes("utf-8"));
			}else{
				sb.append(username + " " + method + " " + count);
				rf.seek(rf.getFilePointer());
				rf.write(sb.toString().getBytes("utf-8"));
			}		
			rf.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	public static void main(String[] args) {
		File file = new File("D:/test.txt");
		FileUtil.updateFileLog(file,"gyz","login",1);
	}
	
}

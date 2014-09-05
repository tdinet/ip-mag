package connection;

import java.io.*;
import java.util.*;

public class FileManager {
	private String file_url; // 操作するファイルのurl
	private File main_file; // 操作するファイル
	private ArrayList<String> file_str_array; // 操作するファイルの中身
	private int file_index; // 行番号
	private File lock_file; // ロックファイル
	private String lock_file_url; // ロックファイルのurl
	public boolean file_status;
	
	public FileManager() {		
	}
	
	// ファイルオブジェクトの初期化とファイルの内容データの初期化
	public FileManager(String url) {
		this.file_url = url;
		this.main_file = new File(url);
		this.lock_file = null;
		this.lock_file_url = 
				this.file_url.substring(
						0,
						this.file_url.lastIndexOf(File.separator) + 1)
						+ "lock";
		
		this.file_index = 0;
		this.file_str_array = new ArrayList<String>();
		
		if(this.createLockFile()) {
			try {
				BufferedReader br = new BufferedReader(
					new FileReader(this.file_url));
				String line;
				
				while((line = br.readLine()) != null) {
					file_str_array.add(line);
				}
				br.close();
				
				this.deleteLockFile();
				this.file_status = true;
			} catch(FileNotFoundException e) {
				e.printStackTrace();
			} catch(IOException e) {
				e.printStackTrace();
			}
		} else {
			this.file_status = false;
		}
	}
	
	public void createFile(String url) {
		this.file_url = url;
		this.main_file = new File(url);
		
		this.file_index = 0;
		file_str_array = new ArrayList<String>();
		
		if(this.createLockFile()) {
			try {
				BufferedReader br = new BufferedReader(
						new FileReader(this.file_url));
					String line;
					
					while((line = br.readLine()) != null) {
						file_str_array.add(line);
					}
					br.close();
					
					this.deleteLockFile();
			} catch(FileNotFoundException e) {			
			} catch(IOException e) {			
			}
		}
	}
	
	/* ファイルロック */
	public boolean createLockFile() {
		boolean result = false;
		
		if(this.lock_file == null) {
			this.lock_file = new File(this.lock_file_url);
		
			try {
				result = this.lock_file.createNewFile();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public void deleteLockFile() {
		if(this.lock_file != null) {
			this.lock_file.delete();
			this.lock_file = null;
		}
	}
	/* ファイルロック */
	
	/* ............................... */
	public void clearFile() {
		if(this.createLockFile()) { 			
			this.file_str_array.clear();
			this.main_file.delete();
			this.main_file = new File(this.file_url);
		
			try {
				this.main_file.createNewFile();
				this.deleteLockFile();
			} catch(IOException e) {		
				e.printStackTrace();
			}
		}
	}
	
	// 1行ずつファイルデータをセット
	public void setFileData(String str) {
		this.file_str_array.add(str);
	}
	
	// 格納したファイルデータを書き込む
	public void flush() {
		if(this.createLockFile()) {			
			try {				
				PrintWriter pw = new PrintWriter(this.main_file);
				ArrayList<String> als = this.file_str_array;
			
				for(int i = 0; i < als.size(); i++) {
					pw.println(als.get(i));
				}
				pw.close();
				
				this.deleteLockFile();
			} catch(FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	/* ............................... */
	
	public void setFilePointer(int index) {
		this.file_index = index - 1;
	}
	
	// 1行読み込む
	public String readLineFromFile() {
		if(this.file_str_array.isEmpty() | 
				this.file_index == this.file_str_array.size()) {
			return null;
		}
		
		return this.file_str_array.get(this.file_index++);
	}
	
	// ファイル末尾に追加
	public void append(String str) {
		if(this.createLockFile()) {
			try {				
				PrintWriter pw = new PrintWriter(
						new FileWriter(this.main_file, true));
				
				pw.println(str);
				pw.close();
				
				this.deleteLockFile();
			} catch(FileNotFoundException e) {	
				e.printStackTrace();
			} catch(IOException e) {
				e.printStackTrace();
			}
			
			this.file_str_array.add(str);
		}
	}
	
	// ファイルの指定行を置換
	public void replaceLine(int line, String str) {
		this.file_str_array.set(line - 1, str);
		
		
		if(this.createLockFile()) {
			try {				
				//元のファイルは削除して新しいファイルを作る
				this.main_file.delete();
				this.main_file = new File(this.file_url);	
				this.main_file.createNewFile();
				
				PrintWriter pw = new PrintWriter(this.main_file);
				ArrayList<String> als = this.file_str_array;
				
				for(int i = 0; i < als.size(); i++) {
					pw.println(als.get(i));
				}
				pw.close();
				
				this.deleteLockFile();
			} catch(FileNotFoundException e) {		
				e.printStackTrace();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}

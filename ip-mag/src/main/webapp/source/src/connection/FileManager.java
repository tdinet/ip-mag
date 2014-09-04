package connection;

import java.io.*;
import java.util.*;

public class FileManager {
	private String file_url; // 操作するファイルのurl
	private File main_file; // 操作するファイル
	private ArrayList<String> file_str_array; // 操作するファイルの中身
	private int file_index; // 行番号
	
	public FileManager() {		
	}
	
	// ファイルオブジェクトの初期化とファイルの内容データの初期化
	public FileManager(String url) {
		this.file_url = url;
		this.main_file = new File(url);
		
		this.file_index = 0;
		this.file_str_array = new ArrayList<String>();
		
		try {
			BufferedReader br = new BufferedReader(
				new FileReader(this.file_url));
			String line;
			
			while((line = br.readLine()) != null) {
				file_str_array.add(line);
			}
			br.close();
		} catch(FileNotFoundException e) {			
		} catch(IOException e) {			
		}
	}
	
	public void createFile(String url) {
		this.file_url = url;
		this.main_file = new File(url);
		
		this.file_index = 0;
		file_str_array = new ArrayList<String>();
		
		try {
			BufferedReader br = new BufferedReader(
					new FileReader(this.file_url));
				String line;
				
				while((line = br.readLine()) != null) {
					file_str_array.add(line);
				}
				br.close();
		} catch(FileNotFoundException e) {			
		} catch(IOException e) {			
		}
	}
	
	/* ............................... */
	public void clearFile() {
		this.file_str_array.clear();
		this.main_file.delete();
		this.main_file = new File(this.file_url);
		
		try {
			this.main_file.createNewFile();
		} catch(IOException e) {			
		}
	}
	
	// 1行ずつファイルデータをセット
	public void setFileData(String str) {
		this.file_str_array.add(str);
	}
	
	// 格納したファイルデータを書き込む
	public void flush() {
		try {
			PrintWriter pw = new PrintWriter(this.main_file);
			ArrayList<String> als = this.file_str_array;
		
			for(int i = 0; i < als.size(); i++) {
				pw.println(als.get(i));
			}
			pw.close();
		} catch(FileNotFoundException e) {			
		} catch(IOException e) {			
		}
	}
	/* ............................... */
	
	public void setFilePointer(int index) {
		this.file_index = index - 1;
	}
	
	// 1行読み込む
	public String readLineFromFile() {		
		return this.file_str_array.get(this.file_index++);
	}
	
	// ファイル末尾に追加
	public void append(String str) {
		try {
			PrintWriter pw = new PrintWriter(this.main_file);
			
			pw.println(str);
			pw.close();
		} catch(FileNotFoundException e) {	
		} catch(IOException e) {			
		}
		
		this.file_str_array.add(str);
	}
	
	// ファイルの指定行を置換
	public void replaceLine(int line, String str) {
		this.file_str_array.set(line - 1, str);
		
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
		} catch(FileNotFoundException e) {			
		} catch(IOException e) {			
		}
	}
}

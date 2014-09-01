package org.seasar.ip.mag.u_data;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import org.seasar.ip.mag.u_data.IPAddress;

// ユーザーデータの管理クラス
public class UserDataManager implements Serializable {
	private String url;
	private class DataSet implements Serializable {
		public boolean net = false;
		public String ip, name, machine, position, etc;
		
		public DataSet(String i, String n, String m, String p, String e) {
			this.ip = i;
			this.name = n;
			this.machine = m;
			this.position = p;
			this.etc = e;
		}
	};
	private ArrayList<DataSet> ds = new ArrayList<DataSet>();
	
	public UserDataManager(String u) {		
		// ここでファイルからユーザーデータ情報を読み込んで、DataSetに初期化
		this.url = u;
		ds.clear();		
		try {
			BufferedReader br = new BufferedReader(
					new FileReader(this.url));
			String line;
			
			while((line = br.readLine()) != null) {
				// ユーザー情報をスペースで分割してそれぞれのデータを取り出す。
				String[] str = line.split(" ");
				
				ds.add(new DataSet(
						str[0], 
						str[1],
						str[2],
						str[3],
						str[4]));
				
			}
			br.close();
		} catch(IOException e) {			
		}
	}
	
	// ユーザー情報をArrayListにプッシュ
	public void DataPush(String i, String n, String m, String p, String et) {
		ds.add(new DataSet(i, n, m, p, et));
		DataWrite(i, n, m, p, et);
	}
	
	// ユーザーデータのファイルへの書き込み
	public void DataWrite(String i, String n, String m, String p, String et) {
		try {
			FileOutputStream fos = new FileOutputStream(
					this.url, true);
		    OutputStreamWriter osw = new OutputStreamWriter(fos);
		    PrintWriter pw = new PrintWriter(osw);
		    
			pw.println(i + " " + n + " " +	m + " " + p + " " + et);
			pw.close();
		} catch(IOException e) {						
		}
	}
	
	// IP接続状況の表示用メソッド
	public String IPManage() {
		StringBuilder block = new StringBuilder();
		
		for(int i = 0; i < ds.size(); i++) {
			DataSet temp = ds.get(i);
					
			block.append("<tr id=" + "'data'" + ">");
			
			// 接続状況
			block.append("<td" + " align='center'" + ">");
			if(temp.net) {
				block.append("○");
			} else {
				block.append("×");
			}
			block.append("</td>");
			
			// IPアドレス
			block.append("<td>");
			block.append(temp.ip);
			block.append("</td>");
			
			// 名前
			block.append("<td>");
			block.append(temp.name);
			block.append("</td>");
			
			// マシン名
			block.append("<td>");
			block.append(temp.machine);
			block.append("</td>");
			
			// 位置
			block.append("<td>");
			block.append(temp.position);
			block.append("</td>");
			
			// 備考
			block.append("<td>");
			block.append(temp.etc);
			block.append("</td>");
			
			block.append("</tr>");
		}
		
		return block.toString();
	}
	
	// 指定したIPアドレスの接続状況テスト
	public String IPTest() {
		IPAddress ipa = new IPAddress();
		String host = ipa.GetHost().toString();
		boolean result = ipa.Test(host);
		
		return host + " の接続テスト結果 " + result + "";
	}
	
	// DataSetクラスのデータのゲッター
	public String GetIP(int index) {
		return ds.get(index).ip;
	}	
	public String GetName(int index) {
		return ds.get(index).name;
	}
	public String GetMachine(int index) {
		return ds.get(index).machine;
	}
	public String GetPosition(int index) {
		return ds.get(index).position;
	}
	public String GetETC(int index) {
		return ds.get(index).etc;
	}
	
	// 配列のサイズを取得
	public int GetSize() {
		return ds.size();
	}
	
	// 指定したIPアドレスがすでに登録されているか確認
	public boolean IsUserID(String ipp) {
		boolean result = false;
				
		for(int i = 0; i < ds.size(); i++) {
			if(ipp.equals(ds.get(i).ip)) {
				result = true;
				break;
			}
		}
		
		return result;
	}
	
	// 指定したインデックスのIPアドレスを返す
	public String GetIPString(int index) {		
		return ds.get(index).ip;
	}
	
	// 指定したIPアドレスの、ユーザー情報オブジェクトのインデックスを返す
	public int GetUserID(String ipp) {
		int i = 0;
		
		for(; i < ds.size(); i++) {
			if(ipp.equals(ds.get(i).ip)) {
				break;
			}
		}
		
		return i;
	}
	
	// 指定したindexの登録情報を解除
	public void DeleteUserData(int index) {		
		DeleteFileUserData(ds.get(index).ip);		
		ds.remove(index);
	}
	
	// ファイルから指定したIPアドレスの登録情報を削除
	private void DeleteFileUserData(String ipp) {
		try {
			String copyString;
			ArrayList<String> als = new ArrayList<String>();
			als.clear();
			
			// 最初にファイルから指定したIPのユーザー情報を削除したStringを作る
			File dest = new File(this.url);
			BufferedReader br = new BufferedReader(
					new FileReader(dest));
			
			while((copyString = br.readLine()) != null) {
				String[] splitString = copyString.split(" ");				
				
				// 指定したIPアドレスと同じものはappendしない
				if(!splitString[0].equals(ipp)) {
					als.add(copyString);
				}
			}			
			br.close();
			
			// 元のファイルを削除して、新しいファイルに新しいデータを書き込む
			dest.delete();
			File copy = new File(this.url);
			copy.createNewFile();
			FileOutputStream fos = new FileOutputStream(copy);
		    OutputStreamWriter osw = new OutputStreamWriter(fos);
		    PrintWriter pw = new PrintWriter(osw);
		    
		    for(int i = 0; i < als.size(); i++) {
		    	pw.println(als.get(i));
		    }
		    pw.close();
		} catch(IOException e) {
			
		}
	}
}
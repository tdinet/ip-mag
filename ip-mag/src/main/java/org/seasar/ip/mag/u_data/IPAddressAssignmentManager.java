package org.seasar.ip.mag.u_data;

import java.io.*;
import java.util.ArrayList;

import org.seasar.ip.mag.u_data.IPAddressRange;
import org.seasar.ip.mag.u_data.IPAddressRange.IPIterator;
import org.seasar.ip.mag.u_data.FileManager;

// ユーザーデータの管理クラス
public class IPAddressAssignmentManager implements Serializable {
	private String url;
	private class UserInfomation implements Serializable {
		public String ip, name, machine, position, etc;
		
		public UserInfomation(String i, String n, String m, String p, String e) {
			this.ip = i;
			this.name = n;
			this.machine = m;
			this.position = p;
			this.etc = e;
		}
	};
	private ArrayList<UserInfomation> user_data_list = new ArrayList<UserInfomation>();
	private IPAddressRange ipa;
	
	public IPAddressAssignmentManager(String u) {		
		/* ここでファイルからユーザーデータ情報を読み込んで、DataSetに初期化 */
		this.url = u + File.separator + "ipdat";
		user_data_list.clear();		
		
		FileManager fm = new FileManager(this.url);
		
		String line = "";
		while((line = fm.readLineFromFile()) != null) {
			String[] str = line.split(" ");
			
			user_data_list.add(new UserInfomation(
					str[0], 
					str[1],
					str[2],
					str[3],
					str[4]));
		}
		
		ipa = new IPAddressRange(u);
	}
	
	// ユーザー情報をArrayListにプッシュ
	public void pushAndWrite(String i, String n, String m, String p, String et) {
		user_data_list.add(new UserInfomation(i, n, m, p, et));
		write(i, n, m, p, et);
	}
	
	// ユーザーデータのファイルへの書き込み
	private void write(String i, String n, String m, String p, String et) {
		FileManager fm = new FileManager(this.url);
		
		fm.append(i + " " + n + " " +	m + " " + p + " " + et);
	}
	
	// IP接続状況の表示用メソッド
	public String displayIPAddressAssignment() {
		StringBuilder block = new StringBuilder();
		IPAddressRange ipaddr = this.ipa;
		IPIterator ipt = this.ipa.ipi;
		
		for(String address: ipt) {	
			boolean connect = ipaddr.isConnection(address);
			
			block.append("<tr id=" + "'data'" + ">");
			
			// 接続状況
			block.append("<td" + " align='center'" + ">");
			if(connect) {
				block.append("○");
			} else {
				block.append("×");
			}
			block.append("</td>");
			
			if(this.isRegisteredID(address)) {
				// ユーザー情報が登録されている場合
				int id = this.getUserID(address);
				UserInfomation dss = user_data_list.get(id);
				// IPアドレス
				block.append("<td>" + 
						"<a href='delete/?ipaddr=" + address + "'>" +
								address + "</a>");
				block.append("</td>");
				
				// 名前
				block.append("<td>");
				block.append(dss.name);
				block.append("</td>");
				
				// マシン名
				block.append("<td>");
				block.append(dss.machine);
				block.append("</td>");
				
				// 位置
				block.append("<td>");
				block.append(dss.position);
				block.append("</td>");
				
				// 備考
				block.append("<td>");
				block.append(dss.etc);
				block.append("</td>");
				
				block.append("</tr>");				
			} else {
				// ユーザー情報が登録されていない場合
				// IPアドレス
				block.append("<td>" + 
						"<a href='ip/?ipaddr=" + address + "'>" +
								address + "</a>");
				block.append("</td>");
				
				// 名前
				block.append("<td>");
				block.append("");
				block.append("</td>");
				
				// マシン名
				block.append("<td>");
				block.append("");
				block.append("</td>");
				
				// 位置
				block.append("<td>");
				block.append("");
				block.append("</td>");
				
				// 備考
				block.append("<td>");
				block.append("");
				block.append("</td>");
				
				block.append("</tr>");
			}
		}
		
		return block.toString();
	}
		
	// DataSetクラスのデータのゲッター
	public String getIP(int index) {
		return user_data_list.get(index).ip;
	}	
	public String getName(int index) {
		return user_data_list.get(index).name;
	}
	public String getMachine(int index) {
		return user_data_list.get(index).machine;
	}
	public String getPosition(int index) {
		return user_data_list.get(index).position;
	}
	public String getETC(int index) {
		return user_data_list.get(index).etc;
	}
	
	// 配列のサイズを取得
	public int getSize() {
		return user_data_list.size();
	}
	
	// 指定したIPアドレスがすでに登録されているか確認
	public boolean isRegisteredID(String ipp) {
		boolean result = false;
				
		for(int i = 0; i < user_data_list.size(); i++) {
			if(ipp.equals(user_data_list.get(i).ip)) {
				result = true;
				break;
			}
		}
		
		return result;
	}
	
	// 指定したインデックスのIPアドレスを返す
	public String getIPString(int index) {		
		return user_data_list.get(index).ip;
	}
	
	// 指定したIPアドレスの、ユーザー情報オブジェクトのインデックスを返す
	public int getUserID(String ipp) {
		int i = 0;
		
		for(; i < user_data_list.size(); i++) {
			if(ipp.equals(user_data_list.get(i).ip)) {
				break;
			}
		}
		
		return i;
	}
	
	// 指定したindexの登録情報を解除
	public void deleteUserInfo(String ipaddr) {		
		deleteIPAddressFromFile(ipaddr);		
		user_data_list.remove(this.getUserID(ipaddr));
	}
	
	// ファイルから指定したIPアドレスの登録情報を削除
	private void deleteIPAddressFromFile(String ipp) {
		String copyString;
		ArrayList<String> als = new ArrayList<String>();
		als.clear();
			
		// 最初にファイルから指定したIPのユーザー情報を削除したStringを作る
		FileManager fm = new FileManager(this.url);
			
		while((copyString = fm.readLineFromFile()) != null) {
			String[] splitString = copyString.split(" ");				
				
			// 指定したIPアドレスと同じものはappendしない
			if(!splitString[0].equals(ipp)) {
				als.add(copyString);
			}
		}			
			
		// 元のファイルを削除して、新しいファイルに新しいデータを書き込む
		fm.clearFile();
		    
		for(int i = 0; i < als.size(); i++) {
		    fm.setFileData(als.get(i));
		}
		fm.flush();
	}
}
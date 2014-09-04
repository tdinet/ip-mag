package org.seasar.ip.mag.u_data;

import java.io.*;
import java.net.*;
import java.util.*;

import org.seasar.ip.mag.u_data.FileManager;

// 接続可能なIPアドレス情報の保存クラス
public class IPAddressRange implements Serializable {
	private String host; // ホスト
	private String url; // 読み込みファイルのurl
	private HashMap<String, Boolean> ips; // 接続状態を保存
	
	public class IPIterator implements Iterable<String>, Iterator<String>
		, Serializable {
		/* メンバ変数 */
		private int start; // 割り当て可能なIPアドレスの開始番号
		private int end; // 割り当て可能なIPアドレスの終端番号
		private int index; // IPアドレスの末尾
		private String ipseg; // ○○○.○○○.○○○.の部分

		public IPIterator(String start, String end) {			
			/* IPアドレスの末尾を取得 */
			String sta = start;
			// ipsegの初期化
			this.ipseg = sta.substring(0, sta.lastIndexOf(".") + 1);
			sta = sta.substring(sta.lastIndexOf(".") + 1);
			this.start = Integer.parseInt(sta);
			
			sta = end;
			sta = sta.substring(sta.lastIndexOf(".") + 1);
			this.end = Integer.parseInt(sta);
			/* 取得 */
			
			this.index = this.start;
		}
		
		public Iterator<String> iterator() {
			return this;
		}
		
		public boolean hasNext() {
			return (this.index <= this.end);
		}

		public String next() {
			String temp = this.ipseg + this.index;
			this.index++;
			
			return temp;
		}		
	};

	IPIterator ipi;
	/* メンバ変数 */
	
	public IPAddressRange(String ur) {
		this.url = ur + File.separator + "connection";
		this.ips = new HashMap<String, Boolean>();
		
		// ホストアドレスの取得
		try {
			this.host = InetAddress.getLocalHost().getHostAddress();
		} catch(UnknownHostException e) {			
		}
		
		// 使用可能IPアドレスの範囲の指定
		FileManager fm = new FileManager(this.url);
		fm.readLineFromFile(); // 最初の1行はファイルの書き換えフラグ
		// 2行目はIPアドレスの範囲
		String[] range = fm.readLineFromFile().split(" ");		
		ipi = new IPIterator(range[0], range[1]);		
		
		reset();
	}
	
	// ホストのゲッタ
	public String getHost() {
		return this.host;
	}
	
	// 利用可能IPアドレスの接続状況リセット
	public void reset() {	
		IPIterator ipt = this.ipi;
		ipt.iterator();
		
		FileManager fm = new FileManager(this.url);
		// 最初の2行を読み飛ばす
		fm.readLineFromFile();
		fm.readLineFromFile();
		// 接続状況をセット
		String line = "";
		while((line = fm.readLineFromFile()) != null) {
			String[] con = line.split(" ");
			
			ips.put(con[1], Boolean.valueOf(con[0]));
		}
	}
	
	// 接続状態を返す
	public boolean isConnection(String ip) {
		return ips.get(ip);
	}
}

package org.seasar.ip.mag.u_data;

import java.io.*;
import java.net.*;
import java.util.*;

// 接続可能なIPアドレス情報の保存クラス
public class IPAddress implements Serializable {
	private String host; // ホスト
	private HashMap<String, Boolean> ips; // 接続状態を保存
	
	// IPデータの範囲を表示するときに使用するクラス
	private class IPData implements Serializable {
		/* メンバ変数 */
		private int start; // 割り当て可能なIPアドレスの開始番号
		private int end; // 割り当て可能なIPアドレスの終端番号
		private int index;
		private String ipseg; // ○○○.○○○.○○○.の部分
		private String n_address; // 現在の参照IP
		
		public IPData(String s, String e) {			
			/* IPアドレスの末尾を取得 */
			String sta = s;
			// ipsegの初期化
			this.ipseg = sta.substring(0, sta.lastIndexOf(".") + 1);
			sta = sta.substring(sta.lastIndexOf(".") + 1);
			this.start = Integer.parseInt(sta);
			
			sta = e;
			sta = sta.substring(sta.lastIndexOf(".") + 1);
			this.end = Integer.parseInt(sta);
			/* 取得 */
			
			SetStart();
		};
		
		// 開始位置を指定
		public void SetStart() {
			this.index = this.start;
			this.n_address = this.ipseg + this.start;
		}
		
		// 次のアドレス
		public void Next() {
			this.index++;
			this.n_address = this.ipseg + this.index;
		}
		
		// 現在のアドレス
		public String GetIP() {
			return this.n_address;
		}
		
		// 終端かどうか
		public boolean IsEnd() {
			return (this.index > this.end);  
		}
	};
	IPData ipd;
	/* メンバ変数 */
	
	public IPAddress(String url) {
		this.ips = new HashMap<String, Boolean>();
		
		// ホストアドレスの取得
		try {
			this.host = InetAddress.getLocalHost().getHostAddress();
		} catch(UnknownHostException e) {			
		}
		
		// 使用可能IPアドレスの範囲の指定
		String[] range = null;
		try {
			BufferedReader br = new BufferedReader(
					new FileReader(url));
			
			// 最初の1行はIPアドレスの範囲情報
			range = br.readLine().split(" ");
			br.close();
		} catch(IOException e) {			
		}
		
		ipd = new IPData(range[0], range[1]);		
		Reset();
	}
	
	// ホストのゲッタ
	public String GetHost() {
		return this.host;
	}
	
	// 利用可能IPアドレスの接続状況リセット
	public void Reset() {
		ipd.SetStart();
		
		// 接続状況をセット
		while(!ipd.IsEnd()) {
			ips.put(ipd.GetIP(), Test(ipd.GetIP()));			
			ipd.Next();
		}
	}
	
	/* 利用可能IPアドレスの一覧取得用メソッド */
	public void Start() {
		Reset();
		ipd.SetStart();
	}
	public void Next() {
		ipd.Next();
	}
	public String GetIP() {
		return ipd.GetIP();
	}
	public boolean IsEnding() {
		return ipd.IsEnd();
	}
	/* メソッド */
	
	// 接続状態を返す
	public boolean IsConnection(String ip) {
		return ips.get(ip);
	}
	
	// 接続状態のテスト
	private boolean Test(String ip) {
		boolean result = false;
		
		
		/*try {
			InetAddress ia = InetAddress.getByName(ip);
			result = ia.isReachable(1000);
		} catch(UnknownHostException e) {			
		} catch(IOException e) {			
		}*/		
		
		return result;
	}
	
}

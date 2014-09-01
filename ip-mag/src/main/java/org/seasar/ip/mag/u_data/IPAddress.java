package org.seasar.ip.mag.u_data;

import java.io.*;
import java.net.*;
import java.util.*;

// 接続可能なIPアドレス情報の保存クラス
public class IPAddress {
	private InetAddress host; // ホスト
	private int start; // 割り当て可能なIPアドレスの開始番号
	private int end; // 割り当て可能なIPアドレスの終端番号
	public ArrayList<String> ip;
	
	public IPAddress() {
		this.start = 1;
		this.end = 244;
		this.ip = new ArrayList<String>();
		
		try {
			this.host = InetAddress.getLocalHost();
		} catch(UnknownHostException e) {			
		}
		
		Reset();
	}
	
	// ホストのゲッタ
	public InetAddress GetHost() {
		return this.host;
	}
	
	// 利用可能IPアドレスのリセット
	public void Reset() {
	}
	
	// 接続状態のテスト
	public boolean Test(String ip) {
		boolean result = false;
		
		try {
			InetAddress ia = InetAddress.getByName(ip);
			result = ia.isReachable(1000);
		} catch(UnknownHostException e) {			
		} catch(IOException e) {			
		}		
		
		return result;
	}
}

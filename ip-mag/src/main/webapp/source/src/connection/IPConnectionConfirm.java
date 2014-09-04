package connection;

import java.io.*;
import java.util.*;

// 接続可能なIPアドレス情報の保存クラス
public class IPConnectionConfirm implements Serializable {
	private static String TIMEOUT = "1000";
	private String osname; // OS
	public IPIterator ipi;
	
	public class IPIterator implements Iterable<String>, Iterator<String> {
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
	
	public IPConnectionConfirm(String start, String end) {
		this.osname = System.getProperty("os.name");
		this.ipi = new IPIterator(start, end);
	}
	
	// 接続状態のテスト
	public boolean Test(String ip) {
		boolean result = false;
		String[] command = new String[6];
		
		if(this.osname.startsWith("Windows")) {
			// OSがWindows
			command[0] = "ping";
			command[1] = "-n";
			command[2] = "1";
			command[3] = "-w";
			command[4] = TIMEOUT;
			command[5] = ip;
		} else if(this.osname.startsWith("Linux")) {
			// OSがLinux
			command[0] = "ping";
			command[1] = "-c";
			command[2] = "1";
			command[3] = "-t";
			command[4] = TIMEOUT;
			command[5] = ip;
		}
		
		try {
			result = new ProcessBuilder(command).start().waitFor() == 0;
		} catch(IOException e) {			
		} catch(InterruptedException e) {			
		}
		
		return result;
	}
	
}

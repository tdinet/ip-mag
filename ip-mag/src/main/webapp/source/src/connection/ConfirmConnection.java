package connection;

import java.io.File;

import connection.FileManager;
import connection.IPConnectionConfirm;
import connection.IPConnectionConfirm.IPIterator;
import javax.swing.JOptionPane;

public class ConfirmConnection {
	
	public static void main(String[] args) {
		
		// スレッド終了の判定ステータス
		String status = "";
		String path = System.getProperty("user.dir") + File.separator +
					"data" + File.separator + "connection";
		
		// ほかのプロセスがファイルを操作中のときは、読み込めるようになるまで待つ
		FileManager fm = null;		
		while(true) {
			fm = new FileManager(path);
			
			if(fm.file_status) {
				break;
			}
			
			try {
				Thread.sleep(3000);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		status = fm.readLineFromFile();
		
		if(args[0].equals("START")) {			
			// 開始
			if(status.startsWith("false")) {
				fm.replaceLine(1, "true");
				
				ConfirmThread ch_thread = new ConfirmThread(args[1], path);
				ch_thread.start();
			}
		} else if(args[0].equals("STOP")) {
			//　終了
			if(status.startsWith("true")) {
				fm.replaceLine(1, "false");
			}
		} else {
			// エラー
			JOptionPane.showMessageDialog(null, "指定されたコマンドが正しくありません。");
		}
	}
}

// IP接続状況取得用スレッド
class ConfirmThread extends Thread {
	private boolean loopflag = true;
	private long interval;
	private String address_range;
	private String filepath;
	private IPConnectionConfirm ipcc;
	
	public ConfirmThread(String val, String path) {
		this.interval = Long.parseLong(val);
		this.filepath = path;
		
		FileManager fm = new FileManager(this.filepath);		
		fm.readLineFromFile();
		String ip_range = fm.readLineFromFile();
		this.address_range = ip_range;
		String[] range = ip_range.split(" ");
		this.ipcc = new IPConnectionConfirm(range[0], range[1]);
	}
	
	// 接続状況をinterval分おきに確認
	public void run() {		
		while(this.loopflag) {
			try {
				// interval分おきに実行
				ConnectionTestAndWrite();
				Thread.sleep(interval * 60 * 1000);
			} catch(InterruptedException e) {
			}
			
			FileManager fm = new FileManager(this.filepath);
			
			if(fm.readLineFromFile().startsWith("false")) {
				this.loopflag = false;
			}			
		}
	}
	
	
	// 接続テストと書き込み
	private void ConnectionTestAndWrite() {		
		String write = "";
		IPConnectionConfirm ipcc_copy = this.ipcc;
		IPIterator ipt = this.ipcc.ipi;
		FileManager fmm = new FileManager(this.filepath);
		
		fmm.clearFile();
		fmm.setFileData("true");
		fmm.setFileData(this.address_range);
		fmm.createLockFile();
		for(String address: ipt) {			
			if(ipcc_copy.Test(address)) {
				// 接続可能
				write = "true" + " " + address;
			} else {
				// 接続不可能
				write = "false" + " " + address;
			}
			
			fmm.setFileData(write);
		}
		fmm.deleteLockFile();
		
		fmm.flush();
	}
}

package org.seasar.ip.mag.action;

import java.io.*;
import java.util.Map;
import javax.servlet.ServletContext;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.*;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.ip.mag.u_data.UserDataManager;

public class IpAction extends Action {
	public @RequestParameter String err = "";
    public @RequestParameter String name;
    public @RequestParameter String machine;
    public @RequestParameter String position;
    public @RequestParameter String etc;
	public String ip = "192.tes.tes";
	public ServletContext application;
	public Map<String, Object> sessionScope;

	// 登録画面へ遷移
	public ActionResult index() {
        return new Forward("register.jsp");
	}
	
	// 確認画面へ遷移
	@Validation(rules = "validationRules", errorPage = "register.jsp")
	public ActionResult register() {
		sessionScope.put(
				"Data",
				new UserData(ip, name, machine, position, etc));
		this.err = "";
		return new Forward("confirm.jsp");
	}
		
	// 登録完了画面に遷移
	public ActionResult registered() {
		// ユーザーデータをファイルに保存
		UserData ud = (UserData)sessionScope.get("Data");
		UserDataManager udm = (UserDataManager)sessionScope.get("UD");
		
		if(udm.IsUserID(ud.GetIP())) {
			return new Forward("/error/error.jsp");
		}
		
		udm.DataPush(
				ud.GetIP(),
				ud.GetName(),
				ud.GetMachine(),
				ud.GetPosition(),
				ud.GetETC());
		
		return new Forward("registered.jsp");
	}
	
	// 登録画面に戻る
	public ActionResult back() {
		this.err = "";
		return new Forward("");
	}
	
	// HOMEに戻る
	public ActionResult home() {
		return new Redirect("/");
	}
	
	// 入力内容に対するヴァリデーション
	public ValidationRules validationRules = new DefaultValidationRules() {
	@Override
	// 入力項目は1文字以上入力
	public void initialize() {
		add("name", new RequiredValidator());
		add("machine", new RequiredValidator());
		add("position", new RequiredValidator());
		add("etc", new RequiredValidator());
		}
	};
}

class UserData implements Serializable {
	private String ip, name, machine, position, etc;
	
	public UserData(String i, String n, String m, String p, String e) {	
		this.ip = i;
		this.name = n;
		this.machine = m;
		this.position = p;
		this.etc = e;
	}
	
	public String GetIP() {
		return this.ip;
	}
	
	public String GetName() {
		return this.name;
	}
	
	public String GetMachine() {
		return this.machine;
	}
	
	public String GetPosition() {
		return this.position;
	}
	
	public String GetETC() {
		return this.etc;
	}
}

package org.seasar.ip.mag.action;

import java.util.Map;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Redirect;
import org.seasar.ip.mag.u_data.UserDataManager;

public class DeleteAction extends Action {
	public String ip, name, machine, position, etc;
	public Map<String, Object> sessionScope;

	public ActionResult index() {
		Integer deleteID;
		
		String delete = (String)sessionScope.get("NOWIP");
		UserDataManager udm = (UserDataManager)sessionScope.get("UD");
		deleteID = new Integer(udm.GetUserID(delete));
		
		this.ip = udm.GetIP(deleteID);
		this.name = udm.GetName(deleteID);
		this.machine = udm.GetMachine(deleteID);
		this.position = udm.GetPosition(deleteID);
		this.etc = udm.GetETC(deleteID);
		sessionScope.put("ID", deleteID);
		
		return new Forward("delete.jsp");
	}
	
	// 登録解除
	public ActionResult complete() {
		UserDataManager udm = (UserDataManager)sessionScope.get("UD");
		
		int id = (Integer)sessionScope.get("ID");
		// 指定したIPアドレスのユーザー情報がないならエラー画面へ
		if(!(id < udm.GetSize()) || !udm.IsUserID(udm.GetIPString(id))) {
			return new Forward("/error/error2.jsp");
		}
		
		udm.DeleteUserData((Integer)sessionScope.get("ID"));
		
		// 後でリダイレクトに変更
		return new Forward("complete.jsp");
	}
	
	public ActionResult home() {
		return new Redirect("/");
	}
}
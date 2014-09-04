package org.seasar.ip.mag.action;

import java.util.Map;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.ip.mag.u_data.IPAddressAssignmentManager;

public class DeleteAction extends Action {
	public @RequestParameter String ipaddr;
	public String ip, name, machine, position, etc;
	public Map<String, Object> sessionScope;

	public ActionResult index() {
		int deleteID;
		
		String delete = ipaddr;
		IPAddressAssignmentManager iaam = (IPAddressAssignmentManager)sessionScope.get("UD");
		deleteID = new Integer(iaam.getUserID(delete));
		
		this.ip = iaam.getIP(deleteID);
		this.name = iaam.getName(deleteID);
		this.machine = iaam.getMachine(deleteID);
		this.position = iaam.getPosition(deleteID);
		this.etc = iaam.getETC(deleteID);
		sessionScope.put("DeleteIP", delete);
		
		return new Forward("delete.jsp");
	}
	
	// 登録解除
	public ActionResult complete() {
		IPAddressAssignmentManager iaam = (IPAddressAssignmentManager)sessionScope.get("UD");
		
		String d_ip = (String)sessionScope.get("DeleteIP");
		// 指定したIPアドレスのユーザー情報がないならエラー画面へ
		if(!iaam.isRegisteredID(d_ip)) {
			return new Forward("/error/error2.jsp");
		}
		
		iaam.deleteUserInfo(d_ip);
		
		// 後でリダイレクトに変更
		return new Forward("complete.jsp");
	}
	
	public ActionResult home() {
		return new Redirect("/");
	}
}
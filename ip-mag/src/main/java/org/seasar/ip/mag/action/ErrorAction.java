package org.seasar.ip.mag.action;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Redirect;

public class ErrorAction extends Action {
	// エラー画面	
	public ActionResult index() {
		return new Forward("error.jsp");
	}
	
	public ActionResult home() {
		return new Redirect("/");
	}
}

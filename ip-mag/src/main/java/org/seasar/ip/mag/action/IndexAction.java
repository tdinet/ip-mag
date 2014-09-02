package org.seasar.ip.mag.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletContext;

import org.seasar.cubby.action.ActionClass;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.ip.mag.u_data.UserDataManager;

@ActionClass
@Path("/")
public class IndexAction {
	public HttpServletRequest request;
	public ServletContext application;
	public Map<String, Object> sessionScope;
    public @RequestParameter String ipaddr;

	public ActionResult index() {
		sessionScope.put(
				"UD",
				new UserDataManager(application.getRealPath("/data/ipdat")));
		return new Forward("index.jsp");
	}
	
	// 登録画面へ
	public ActionResult moveip() {
		String ipdata = ipaddr;
		sessionScope.put("NOWIP", ipdata);
		return new Redirect("/ip/");
	}
	
	// 解除画面へ
	public ActionResult movedelete() {
		String ipdata = ipaddr;
		sessionScope.put("NOWIP", ipdata);
		
		return new Redirect("/delete/");
	}
}
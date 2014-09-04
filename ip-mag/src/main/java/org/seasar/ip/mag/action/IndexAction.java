package org.seasar.ip.mag.action;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletContext;

import org.seasar.cubby.action.ActionClass;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.ip.mag.u_data.IPAddressAssignmentManager;

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
				new IPAddressAssignmentManager(
						application.getRealPath("") +
						File.separator + "data"
						));
		return new Forward("index.jsp");
	}
}
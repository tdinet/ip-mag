package org.seasar.ip.mag.action;

import java.util.Map;
import javax.servlet.ServletContext;
import org.seasar.cubby.action.ActionClass;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;
import org.seasar.ip.mag.u_data.UserDataManager;

@ActionClass
@Path("/")
public class IndexAction {
	public ServletContext application;
	public Map<String, Object> sessionScope;

	public ActionResult index() {
		/* テスト用 */
		sessionScope.put("DeleteIP", "192.tes.tes");
		/* テスト用 */
		sessionScope.put(
				"UD",
				new UserDataManager(application.getRealPath("/data/ipdat")));
		return new Forward("index.jsp");
	}	

}
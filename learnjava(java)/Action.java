package door;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Action extends HttpServlet {

	int always = 0;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (always == 0) {
			Always alw = new Always();
			alw.start();
			always = 1;
		}
		Log4j log = new Log4j(new Object() {
		}.getClass().getName());
		String param = request.getParameter("param");
		// 値の取得＆設定
		String day = request.getParameter("day");
		Sql sql = new Sql();
		ArrayList<DataBox> list = new ArrayList<DataBox>();

		list = sql.add(day, param);
		request.setAttribute("datalist", list);
		if (param.equals("1")) {
			getServletConfig().getServletContext().getRequestDispatcher("/top.jsp").forward(request, response);
		} else if (param.equals("2")) {
			getServletConfig().getServletContext().getRequestDispatcher("/log.jsp").forward(request, response);
			log.write("ログ起動しました", 4);
		} else if (param.equals("3")) {
			getServletConfig().getServletContext().getRequestDispatcher("/graph.jsp").forward(request, response);
		} else if(param.equals("4")){
		}
	}
}

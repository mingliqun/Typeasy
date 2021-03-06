package me.llss.servlet;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import me.llss.service.impl.CommentsServiceImpl;
import me.llss.service.impl.ContentsServiceImpl;
import me.llss.service.impl.MetasServiceImpl;
import me.llss.service.impl.OptionsServiceImpl;
import me.llss.service.impl.RelationshipsServiceImpl;
import me.llss.service.impl.UsersServiceImpl;
import me.llss.vo.CommentsVO;
import me.llss.vo.ContentsVO;
import me.llss.vo.MetasVO;
import me.llss.vo.OptionsVO;
import me.llss.vo.RelationshipsVO;
import me.llss.vo.UsersVO;

/**
 * 配置表相关的Servlet
 * 
 * @version 1.0 2013/05/18
 * @author Acris
 * 
 */
public class OptionsServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private RelationshipsServiceImpl rs = new RelationshipsServiceImpl();
	private ContentsServiceImpl cs = new ContentsServiceImpl();
	private CommentsServiceImpl cos = new CommentsServiceImpl();
	private MetasServiceImpl ms = new MetasServiceImpl();
	private OptionsServiceImpl os = new OptionsServiceImpl();
	private UsersServiceImpl us = new UsersServiceImpl();

	/**
	 * Constructor of the object.
	 */
	public OptionsServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 此处交给doPost方法处理
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * doPost方法
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		if (action.equals("edit")) {
			this.edit(request, response);
		}

	}

	/**
	 * 设置基本设置
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void edit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		String date = request.getParameter("date");
		String time = request.getParameter("time");
		String excerpt = request.getParameter("excerpt");
		String number = request.getParameter("number");
		String count = request.getParameter("count");
		OptionsVO o1 = new OptionsVO();
		OptionsVO o2 = new OptionsVO();
		OptionsVO o3 = new OptionsVO();
		OptionsVO o4 = new OptionsVO();
		OptionsVO o5 = new OptionsVO();
		OptionsVO o6 = new OptionsVO();
		OptionsVO o7 = new OptionsVO();
		o1.setValue(title);
		o1.setName("title");
		o2.setValue(description);
		o2.setName("description");
		o3.setValue(date);
		o3.setName("date");
		o4.setValue(time);
		o4.setName("time");
		o5.setValue(excerpt);
		o5.setName("excerpt");
		o6.setValue(number);
		o6.setName("number");
		o7.setValue(count);
		o7.setName("count");

		os.edit(o1);
		os.edit(o2);
		os.edit(o3);
		os.edit(o4);
		os.edit(o5);
		os.edit(o6);
		os.edit(o7);

		/* 获取相关session */
		HttpSession session = request.getSession();

		List<RelationshipsVO> relationships = rs.list();
		Collections.reverse(relationships);
		session.setAttribute("relationships", relationships);

		List<ContentsVO> contents = cs.list();
		Collections.reverse(contents);
		session.setAttribute("contents", contents);

		List<CommentsVO> comments = cos.list();
		Collections.reverse(comments);
		session.setAttribute("comments", comments);

		List<UsersVO> users = us.list();
		Collections.reverse(users);
		session.setAttribute("users", users);

		List<MetasVO> metas = ms.listAll();
		Collections.reverse(metas);
		session.setAttribute("metas", metas);

		List<OptionsVO> options = os.list();
		Collections.reverse(options);
		session.setAttribute("options", options);

		request.setAttribute("message", "更改成功!");
		request.setAttribute("returnURL", request.getContextPath()
				+ "/admin/options.jsp");
		request.getRequestDispatcher("../admin/message.jsp").forward(request,
				response);

	}

}

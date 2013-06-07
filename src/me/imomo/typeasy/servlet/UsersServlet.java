package me.imomo.typeasy.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import me.imomo.typeasy.commons.MD5;
import me.imomo.typeasy.service.CommentsService;
import me.imomo.typeasy.service.ContentsService;
import me.imomo.typeasy.service.LoginService;
import me.imomo.typeasy.service.MetasService;
import me.imomo.typeasy.service.OptionsService;
import me.imomo.typeasy.service.RelationshipsService;
import me.imomo.typeasy.service.UsersService;
import me.imomo.typeasy.vo.CommentsVO;
import me.imomo.typeasy.vo.ContentsVO;
import me.imomo.typeasy.vo.MetasVO;
import me.imomo.typeasy.vo.OptionsVO;
import me.imomo.typeasy.vo.RelationshipsVO;
import me.imomo.typeasy.vo.UsersVO;

/**
 * 用户Servlet
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("serial")
public class UsersServlet extends HttpServlet {
	private LoginService ls = new LoginService();
	private RelationshipsService rs = new RelationshipsService();
	private ContentsService cs = new ContentsService();
	private CommentsService cos = new CommentsService();
	private MetasService ms = new MetasService();
	private OptionsService os = new OptionsService();
	private UsersService us = new UsersService();
	private MD5 md5 = new MD5();

	public UsersServlet() {
		super();
	}

	/**
	 * doGet
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * doPost
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action == null) {
			action = "";
		}
		if (action.equals("add")) {
			this.add(request, response);
		}
		if (action.equals("list")) {
			this.list(request, response);
		}
		if (action.equals("edit")) {
			this.edit(request, response);
		}
		if (action.equals("del")) {
			this.del(request, response);
		}
		if (action.equals("find")) {
			this.find(request, response);
		}
		if (action.equals("security")) {
			this.security(request, response);
		}
		if (action.equals("multiDel")) {
			this.multiDel(request, response);
		}

	}

	/**
	 * 后台添加用户
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UsersVO user = new UsersVO();
		String nowtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(Calendar.getInstance().getTime());
		user.setName(request.getParameter("name"));
		user.setPassword(md5.getMD5ofStr(request.getParameter("password")));
		user.setScreenName(request.getParameter("screenName"));
		user.setMail(request.getParameter("mail"));
		user.setUrl(request.getParameter("url"));
		user.setCreated(nowtime);
		boolean flag = ls.register(user);

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

		if (flag) {
			request.setAttribute("message", "添加成功!");
			request.setAttribute("returnURL", request.getContextPath()
					+ "/admin/manage-users.jsp");
		} else {
			request.setAttribute("message", "添加失败!用户名或邮箱已存在！");
			request.setAttribute("returnURL", request.getContextPath()
					+ "/admin/add-user.jsp");
		}
		request.getRequestDispatcher("../admin/message.jsp").forward(request,
				response);
	}

	/**
	 * 查询所有用户并返回
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<UsersVO> users = us.list();
		HttpSession session = request.getSession();
		session.setAttribute("users", users);
	}

	/**
	 * 修改用户信息
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void edit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UsersVO user = new UsersVO();
		int uid = Integer.valueOf(request.getParameter("uid"));
		int adminId = Integer.valueOf(request.getParameter("adminId"));
		user.setScreenName(request.getParameter("screenName"));
		user.setMail(request.getParameter("mail"));
		user.setUrl(request.getParameter("url"));
		user.setAvatar(request.getParameter("avatar"));
		user.setGroup(request.getParameter("group"));
		user.setUid(uid);
		us.modifyProfile(user);
		UsersVO u = us.find(adminId);

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

		session.setAttribute("user", u);
		request.setAttribute("message", "修改成功!");
		if (adminId == uid) {
			request.setAttribute("returnURL", request.getContextPath()
					+ "/admin/my-profiles.jsp");
		} else {
			request.setAttribute("returnURL", request.getContextPath()
					+ "/admin/manage-users.jsp");
		}
		request.getRequestDispatcher("../admin/message.jsp").forward(request,
				response);

	}

	/**
	 * 删除用户
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int uid = Integer.valueOf(request.getParameter("uid"));
		us.del(uid);

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

		request.setAttribute("message", "删除成功!");
		request.setAttribute("returnURL", request.getContextPath()
				+ "/admin/manage-users.jsp");
		request.getRequestDispatcher("../admin/message.jsp").forward(request,
				response);
	}

	/**
	 * 根据uid查找用户
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void find(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("uid"));
		UsersVO user = us.find(id);
		if (user != null) {
			request.setAttribute("u", user);
		}
		request.getRequestDispatcher("../admin/edit-user.jsp").forward(request,
				response);
	}

	/**
	 * 修改密码
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void security(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		boolean flag = validateSecurity(request, response);
		if (flag) {
			UsersVO user = new UsersVO();
			String new_password = md5.getMD5ofStr(request
					.getParameter("new_password"));
			user.setPassword(new_password);
			user.setUid(Integer.valueOf(request.getParameter("uid")));
			us.security(user);
			List<UsersVO> users = us.list();
			UsersVO u = us
					.find(Integer.valueOf(request.getParameter("adminId")));
			HttpSession session = request.getSession();
			session.setAttribute("users", users);
			session.setAttribute("user", u);
			request.setAttribute("message", "更新成功!");
			request.setAttribute("returnURL", request.getContextPath()
					+ "/login.jsp");
			request.getRequestDispatcher("../admin/message.jsp").forward(
					request, response);
		} else {
			request.setAttribute("returnURL", request.getContextPath()
					+ "/admin/security.jsp");
			request.getRequestDispatcher("../admin/message.jsp").forward(
					request, response);
		}

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
	}

	/**
	 * 验证修改密码额表单
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public boolean validateSecurity(HttpServletRequest request,
			HttpServletResponse response) {
		boolean flag = true;
		String message = "";
		String password = md5.getMD5ofStr(request.getParameter("password"));
		String new_password = request.getParameter("new_password");
		String new_password_again = request.getParameter("new_password_again");

		HttpSession session = request.getSession();
		UsersVO vo = (UsersVO) session.getAttribute("user");
		String pwd = vo.getPassword();

		if (password == null || password.equals("")) {
			flag = false;
			message = "原密码不能为空！";
		}
		if ((password != null || !password.equals(""))
				&& (password.equals(pwd))
				&& (new_password == null || new_password.equals(""))) {
			flag = false;
			message = "新密码不能为空！";
		}
		if ((password == null || password.equals(""))
				&& (new_password == null || new_password.equals(""))) {
			flag = false;
			message = "密码不能为空！";
		}
		if ((password != null || !password.equals(""))
				&& (!password.equals(pwd))) {
			flag = false;
			message = "原密码错误！";
		}
		if ((new_password != null || !new_password.equals(""))
				&& (!new_password.equals(new_password_again))) {
			flag = false;
			message = "两次输入密码不一致！";
		}

		request.setAttribute("message", message);
		return flag;
	}

	/**
	 * 批量删除
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void multiDel(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String uid[] = request.getParameterValues("uid");

		String op = request.getParameter("multiOption");
		if (op.equals("multiDel")) {
			for (int i = 0; i < uid.length; i++) {
				int userId = Integer.valueOf(uid[i]);
				us.del(userId);
			}

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

			request.setAttribute("message", "删除成功!");
			request.setAttribute("returnURL", request.getContextPath()
					+ "/admin/manage-users.jsp");
			request.getRequestDispatcher("../admin/message.jsp").forward(
					request, response);
		} else {
			response.sendRedirect("../admin/manage-users.jsp");
		}

	}

}

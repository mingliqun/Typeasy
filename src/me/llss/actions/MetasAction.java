package me.llss.actions;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import me.llss.service.impl.CommentsServiceImpl;
import me.llss.service.impl.ContentsServiceImpl;
import me.llss.service.impl.LoginServiceImpl;
import me.llss.service.impl.MetasServiceImpl;
import me.llss.service.impl.OptionsServiceImpl;
import me.llss.service.impl.RelationshipsServiceImpl;
import me.llss.service.impl.UsersServiceImpl;
import me.llss.utils.Html2Text;
import me.llss.utils.MD5;
import me.llss.vo.CommentsVO;
import me.llss.vo.ContentsVO;
import me.llss.vo.MetasVO;
import me.llss.vo.OptionsVO;
import me.llss.vo.RelationshipsVO;
import me.llss.vo.UsersVO;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class MetasAction extends ActionSupport implements Action {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private RelationshipsServiceImpl rs = new RelationshipsServiceImpl();
	private LoginServiceImpl ls = new LoginServiceImpl();
	private ContentsServiceImpl cs = new ContentsServiceImpl();
	private CommentsServiceImpl cos = new CommentsServiceImpl();
	private MetasServiceImpl ms = new MetasServiceImpl();
	private OptionsServiceImpl os = new OptionsServiceImpl();
	private UsersServiceImpl us = new UsersServiceImpl();
	private MD5 md5 = new MD5();

	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();
	

	private int mid;
	private String slug;
	private String name;
	private String type;
	private String description;
	private String MetasMid[];
	private String op;
	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String execute() throws Exception {
		
		return super.execute();
	}
	
	/**
	 * 插入操作
	 * @return
	 */
	public String add(){
		String metaSlug = Html2Text.HtmlToText(slug);
		String metaName = Html2Text.HtmlToText(name);
		String metaType = Html2Text.HtmlToText(type);
		String metaDescription = Html2Text.HtmlToText(description);
		
		MetasVO meta = new MetasVO();
		meta.setSlug(metaSlug);
		meta.setName(metaName);
		meta.setType(metaType);
		meta.setDescription(metaDescription);
		ms.add(meta);
		
		/**
		 * 获取session
		 */
		
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
		if (type.equals("category")) {
			request.setAttribute("message", "添加成功!");
			request.setAttribute("returnURL", request.getContextPath()
					+ "/admin/manage-categories.jsp");
			return "addSuccess";
			
		}else{
			request.setAttribute("message", "添加成功!");
			request.setAttribute("returnURL", request.getContextPath()
					+ "/admin/manage-tags.jsp");
			return "addFail";
		}
		
	}
	
	
	/**
	 * 删除操作
	 * @return
	 */
	public String del(){
		int metasMid = Integer.valueOf(mid);
		MetasVO meta = ms.findByMid(metasMid);
		String type = meta.getType();
		ms.del(mid);
		
		
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

		if (type.equals("category")) {
			request.setAttribute("message", "删除成功!");
			request.setAttribute("returnURL", request.getContextPath()
					+ "/admin/manage-categories.jsp");
			return "delSuccess";	
	}else{
		request.setAttribute("message", "删除成功!");
		request.setAttribute("returnURL", request.getContextPath()
				+ "/admin/manage-tags.jsp");
		return "delFail";
	}
  }
	
	
	/**
	 * 编辑操作
	 * @return
	 */
	public String edit(){
		int MetasMid = Integer.valueOf(mid);
		String MetasName = Html2Text.HtmlToText(name);
		String MetasSlug = Html2Text.HtmlToText(slug);
		String MetasType = Html2Text.HtmlToText(type);
		String MetasDescription = Html2Text.HtmlToText(description);

		MetasVO meta = new MetasVO();
		meta.setMid(MetasMid);
		meta.setName(MetasName);
		meta.setSlug(MetasSlug);
		meta.setType(MetasType);
		meta.setDescription(MetasDescription);
		ms.edit(meta);
		
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
		if (type.equals("category")) {
			request.setAttribute("message", "更新成功!");
			request.setAttribute("returnURL", request.getContextPath()
					+ "/admin/manage-categories.jsp");
			return "editSuccess";
		} else {
			request.setAttribute("message", "更新成功!");
			request.setAttribute("returnURL", request.getContextPath()
					+ "/admin/manage-tags.jsp");
			return "editFail";
			
		}
	}
	
	
	/**
	 * 查找操作
	 * @return
	 */
	public String list(){
		List<MetasVO> metas = ms.listAll();
		HttpSession session = request.getSession();
		session.setAttribute("metas", metas);
		return null;
	}
	
	
	/**
	 * 根据mid查找
	 * @return
	 */
	public String find(){
		int MetasMid = Integer.valueOf(mid);
		MetasVO meta = ms.findByMid(MetasMid);
		request.setAttribute("meta", meta);
		if (type.equals("category")) {
			return "findSuccess";
		} else {
			return "findFail";
		}
	}
	
	public String mutiDel(){
		if (op.equals("multiDel")) {
			for (int i = 0; i < MetasMid.length; i++) {
				int metaId = Integer.valueOf(MetasMid[i]);
				ms.del(metaId);
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
				+ "/admin/manage-categories.jsp");
		if (type.equals("category")) {
			request.setAttribute("message", "删除成功!");
			request.setAttribute("returnURL", request.getContextPath()
					+ "/admin/manage-categories.jsp");
		return "mutiDelSuccess1";
		} else {
			request.setAttribute("message", "删除成功!");
			request.setAttribute("returnURL", request.getContextPath()
					+ "/admin/manage-tags.jsp");
			return "mutiDelFail1";
		}
	} else {
		if (type.equals("category")) {
			return "mutiDelSuccess2";
		} else {
			return "mutiDelFail2";
		}
	}
	}}

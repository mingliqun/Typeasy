<%@page import="me.llss.vo.CommentsVO"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<c:if test="${sessionScope.user.group != 'admin' }">
	<script type="text/javascript">
		alert('用户权限不够，非法操作！');
		history.back();
	</script>
</c:if>
<c:set var="title" scope="request" value="管理评论"></c:set>
<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="sidebar.jsp"></jsp:include>

<section id="main" class="column">
	<h4 class="alert_info">小提示：单击列名可以将评论按照列名项目排序。</h4>
	<script type="text/javascript">
		var selectAll = function() {
			var all = document.getElementsByName("all")[0];
			var ids = document.getElementsByName("coid");

			if (all.checked) {
				for ( var i = 0; i < ids.length; i++) {
					ids[i].checked = true;
				}
			} else {
				for ( var i = 0; i < ids.length; i++) {
					ids[i].checked = false;
				}
			}
		};
	</script>
	<pg:pager items="${fn:length(comments) }"
		export="currentPageNumber=pageNumber" maxPageItems="10">

		<form action="servlet/CommentsServlet?action=multiDel" method="post">
			<fieldset style="width: 95%;margin: 20px 3% 0 3%;">

				<input type="checkbox" name="all" onclick="selectAll();" /><select
					style="width: 70%;" name="multiOption">
					<option value="default">批量操作</option>
					<option value="multiDel">删除选中的评论</option>
				</select> <input type="submit" value="应用">

			</fieldset>

			<article class="module width_full">


				<header>
					<h3 class="tabs_involved">评论管理</h3>

				</header>

				<table class="tablesorter" cellspacing="0">
					<thead>
						<tr>
							<th>作者</th>
							<th>评论</th>
							<th>发表于</th>
							<th>时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${comments == null || fn:length(comments)== 0 }">
								<tr>
									<td>评论列表为空</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach var="comment" items="${comments }">
									<pg:item>

										<tr>
											<td><input type="checkbox" name="coid"
												value="${comment.coid }">${comment.author }</td>
											<td><c:choose>
													<c:when test="${fn:length(comment.text) > 15 }">${fn:substring(comment.text,0,15) } ...</c:when>
													<c:otherwise>${comment.text }</c:otherwise>
												</c:choose></td>
											<td><c:forEach var="content" items="${contents }">
													<c:if test="${content.cid == comment.cid }">
														<c:choose>
															<c:when test="${fn:length(content.title) > 15 }">${fn:substring(content.title,0,15) } ...</c:when>
															<c:otherwise>${content.title }</c:otherwise>
														</c:choose>
													</c:if>
												</c:forEach></td>
											<td>${comment.created }</td>
											<td><a
												href="./servlet/CommentsServlet?action=find&coid=${comment.coid }&authorId=${comment.authorId }"><img
													src="images/icn_edit.png" alt="编辑" style="border:0px;">
											</a> <a
												href="./servlet/CommentsServlet?action=del&coid=${comment.coid }&cid=${comment.cid }&authorId=${comment.authorId }"
												onclick="return confirm('确认删除吗？')"><img
													src="images/icn_trash.png" alt="删除" style="border:0px;">
											</a></td>
										</tr>
									</pg:item>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
				<div style="text-align: center;padding: 10px;">
					<pg:first>
						<a href="${pageUrl}">首页</a>
					</pg:first>
					<pg:prev>
						<a href="${pageUrl}">上页</a>
					</pg:prev>
					<pg:pages>
						<c:choose>
							<c:when test="${pageNumber eq currentPageNumber }">
								<b class="current">${pageNumber }</b>
							</c:when>
							<c:otherwise>
								<a href="${pageUrl }">${pageNumber}</a>
							</c:otherwise>
						</c:choose>
					</pg:pages>
					<pg:next>
						<a href="${pageUrl}">下页</a>
					</pg:next>
					<pg:last>
						<a href="${pageUrl}">尾页</a>
					</pg:last>
				</div>
			</article>
		</form>
	</pg:pager>
	<!-- end of content manager article -->


	<div class="spacer"></div>
</section>
<jsp:include page="footer.jsp"></jsp:include>

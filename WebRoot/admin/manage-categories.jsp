<%@ page language="java" import="java.util.*,me.llss.vo.*"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<c:if test="${sessionScope.user.group != 'admin' }">
	<script type="text/javascript">
		alert('用户权限不够，非法操作！');
		history.back();
	</script>
</c:if>
<c:set var="title" scope="request" value="管理分类目录"></c:set>

<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="sidebar.jsp"></jsp:include>

<pg:pager items="${countList }" export="currentPageNumber=pageNumber"
	maxPageItems="10">
	<section id="main" class="column">
		<h4 class="alert_info">小提示：单击列名可以将目录按照列名排序。</h4>
		<script type="text/javascript">
			var selectAll = function() {
				var all = document.getElementsByName("all")[0];
				var ids = document.getElementsByName("mid");
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
		<form action="servlet/MetasServlet?action=multiDel&type=category"
			method="post">
			<fieldset style="width: 95%;margin: 20px 3% 0 3%;">
				<input type="checkbox" name="all" onclick="selectAll();" /> <select
					style="width: 70%;" name="multiOption">
					<option value="default">批量操作</option>
					<option value="multiDel">删除选中的目录</option>
				</select> <input type="submit" value="应用"> <input type="button"
					value="添加目录"
					onclick="window.location.href='${basePath }admin/add-category.jsp'">
			</fieldset>
			<article class="module width_full">
				<header>
					<h3 class="tabs_involved">分类目录</h3>
				</header>
				<table class="tablesorter" cellspacing="0">
					<thead>
						<tr>
							<th>目录名</th>
							<th>缩略名</th>
							<th>目录描述</th>
							<th>包含文章数</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<%
							int count = 0;
						%>
						<c:forEach var="meta" items="${metas }">

							<c:if test="${meta.type == 'category' }">
								<%
									count++;
								%>
							</c:if>

						</c:forEach>
						<%
							request.setAttribute("countList", count);
						%>
						<c:choose>


							<c:when test="${countList == 0 }">
								<tr>
									<td>目录列表为空</td>
								</tr>
							</c:when>

							<c:otherwise>
								<c:forEach var="meta" items="${metas }">
									<c:if test="${meta.type == 'category' }">
										<pg:item>
											<tr>
												<td><input type="checkbox" name="mid"
													value="${meta.mid }">
												<c:choose>
														<c:when test="${fn:length(meta.name) > 15 }">${fn:substring(meta.name,0,15) } ...</c:when>
														<c:otherwise>${meta.name}</c:otherwise>
													</c:choose></td>
												<td><c:choose>
														<c:when test="${fn:length(meta.slug) > 15 }">${fn:substring(meta.slug,0,15) } ...</c:when>
														<c:otherwise>${meta.slug}</c:otherwise>
													</c:choose></td>
												<td><c:choose>
														<c:when test="${fn:length(meta.description) > 15 }">${fn:substring(meta.description,0,15) } ...</c:when>
														<c:otherwise>${meta.description}</c:otherwise>
													</c:choose></td>
												<td>${meta.count}</td>
												<td><a
													href="FindMetas&type=category&mid=${meta.mid}"><img
														src="images/icn_edit.png" title="编辑" style="border:0px;">
												</a> <a
													href="DelMetas&type=category&mid=${meta.mid}"
													onclick="return confirm('确认删除吗？')"><img
														src="images/icn_trash.png" alt="删除" style="border:0px;" />
												</a></td>
											</tr>
										</pg:item>
									</c:if>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
				<div style="text-align: center;padding:10px;">
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
		<div class="spacer"></div>
	</section>
</pg:pager>
<jsp:include page="footer.jsp"></jsp:include>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	pageContext.setAttribute("newline", "\n");
%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="wrapper">
			<div id="content">			
				<div class="blog-content">
					<c:choose>
						<c:when test="${empty postVo}">
							<h4>작성된 게시글이 없습니다.</h4>
						</c:when>
						<c:otherwise>
							<h4>${postVo.title }</h4>
							<p>${fn:replace(fn:replace(fn:replace(postVo.contents, ">", "&gt;"), "<", "&lt;"), newline, "<br>") }</p>
						</c:otherwise>
					</c:choose>
				</div>
				
				<c:if test="${not empty postList}">
					<ul class="blog-list">
						<c:forEach items='${postList }' var='postvo'>
							<c:choose>
								<c:when test='${postvo.no eq postVo.no }'>
									<li>${postvo.title } <span>${postvo.regDate }</span> </li>
								</c:when>
								<c:otherwise>
									<li><a href="${pageContext.request.contextPath}/${blogVo.id }/${selectedCategoryNo }/${postvo.no }">${postvo.title }</a> <span>${postvo.regDate }</span> </li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</ul>
				</c:if>
				
			</div>
		</div>

		<div id="extra">
			<div class="blog-logo">
				<img src="${pageContext.request.contextPath}/${blogVo.logo }">
			</div>
		</div>

		<div id="navigation">
			<h2>카테고리</h2>
			<ul>
				<c:forEach items='${categoryList }' var='categoryvo'>
					<c:choose>
						<c:when test='${categoryvo.no eq selectedCategoryNo}'>
							<li>${categoryvo.name }</li>
						</c:when>
						<c:otherwise>
							<li><a href="${pageContext.request.contextPath}/${blogVo.id }/${categoryvo.no }">${categoryvo.name }</a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</ul>
		</div>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>
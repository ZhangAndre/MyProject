
<%@page import="com.muke.util.Page"%>
<%@page import="com.muke.pojo.MessageCriteria"%>
<%@page import="com.muke.dao.IMessageDao"%>
<%@page import="com.muke.dao.impl.MessageDaoImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>">
<link rel="stylesheet" href="bootstrap-3.3.7-dist/css/bootstrap.css">
<link rel="stylesheet" href="css/site.css">
<script src="jquery/jquery-2.2.4.min.js" type="text/javascript"></script>
<script src="bootstrap-3.3.7-dist/js/bootstrap.min.js" type="text/javascript"></script>
<title>慕课答疑平台</title>
<script language="javascript">

	function getNew() {
		
		$.ajax({
			url : "message.json", 
			type : "POST",
			async : "true",
			data : {"action" : "topNew"},
			dataType : "json",
			success : function(data) {
				if (data.res==1){
					// 遍历数组内容
					$.each(data.data.data, function(index, msgItem) {
						// 根据HTML模版创建副本
						var msg = $(".template").clone();
						// 设置属性
						msg.attr("display", "block");
						msg.removeClass("template");
						msg.find(".msgtile").text(msgItem.msgtopic);
						msg.find(".msgtile").attr("href", "message.jsp?msgid="+msgItem.msgid);
						if (msgItem.state == 5){
							msg.find(".top").text("置顶");
						}
						if (msgItem.state == 10){
							msg.find(".wonderful").text("精");
						}
						
						msg.find(".info").text(msgItem.msgtime);
						// 节点追加
						$(".newList").append(msg);
		
					});
				} else if (data.res==-2){
					alert(data.info);
				}
			}
		});
	}
	
	function getHot() {
		
		$.ajax({
			url : "message.json", 
			type : "POST",
			async : "true",
			data : {"action" : "topHot"},
			dataType : "json",
			success : function(data) {
				if (data.res==1){
					$.each(data.data.data, function(index, msgItem) {
						var msg = $(".template").clone();
						msg.attr("display", "block");
						msg.removeClass("template");
						msg.find(".msgtile").text(msgItem.msgtopic);
						msg.find(".msgtile").attr("href", "message.jsp?msgid="+msgItem.msgid);
						if (msgItem.state == 5){
							msg.find(".top").text("置顶");
						}
						if (msgItem.state == 10){
							msg.find(".wonderful").text("精");
						}
						msg.find(".info").text(msgItem.replyCount);
						$(".hotList").append(msg);
					});
				} else if (data.res==-2){
					alert(data.info);
				}
			}
		});
	}
	
	function getTheme() {
		
		$.ajax({
			url : "message.json", 
			type : "POST",
			async : "true",
			data : {"action" : "topTheme"},
			dataType : "json",
			success : function(data) {
				if (data.res==1){
					$.each(data.data.data, function(index, msgItem) {
						var msg = $(".template").clone();
						msg.attr("display", "block");
						msg.removeClass("template");
						msg.find(".msgtile").text(msgItem.msgtopic);
						msg.find(".msgtile").attr("href", "message.jsp?msgid="+msgItem.msgid);
						if (msgItem.state == 5){
							msg.find(".top").text("置顶");
						}
						if (msgItem.state == 10){
							msg.find(".wonderful").text("精");
						}
						msg.find(".info").text(msgItem.thename);
						$(".themeList").append(msg);
					});
				} else if (data.res==-2){
					alert(data.info);
				}
			}
		});
	}
	
	$(function() {
		getNew();
		getHot();
		getTheme();
	});
</script>
</head>
<body>
	<jsp:include flush="fasle" page="header.jsp" />
	<div class="container">
		
		<div class="row">
			<div class="col-sm-4">
				<div
					style="overflow: auto; height: 60px; line-height: 40px; padding-top: 20px;">
					<div style="float: left">
						<h3 style="display: inline">最新</h3>
					</div>
					<div style="float: right; vertical-align: bottom;">
						<a href="newmsg.jsp">更多>></a>
					</div>
				</div>
				<div>
					<ul class="list-group newList">
						<li class="list-group-item template">
							<span class="badge info"></span>
							<span class="badge top"></span>
							<span class="badge wonderful"></span>
							<a class="msgtile text-limit"></a>
						</li>
					</ul>
				</div>
			</div>
			<div class="col-sm-4">
				<div
					style="overflow: auto; height: 60px; line-height: 40px; padding-top: 20px;">
					<div style="float: left">
						<h3 style="display: inline">最热</h3>
					</div>
					<div style="float: right; vertical-align: bottom;">
						<a href="hotmsg.jsp">更多>></a>
					</div>
				</div>
				<div>
					<ul class="list-group hotList">
					</ul>
				</div>
			</div>
			<div class="col-sm-4">
				<div
					style="overflow: auto; height: 60px; line-height: 40px; padding-top: 20px;">
					<div style="float: left">
						<h3 style="display: inline">话题</h3>
					</div>
					<div style="float: right; vertical-align: bottom;">
						<a href="thememsg.jsp">更多>></a>
					</div>
				</div>
				<div>
					<ul class="list-group themeList">
					</ul>
				</div>
			</div>
		</div>
	</div>
	<jsp:include flush="fasle" page="footer.jsp" />
	
</body>
</html>
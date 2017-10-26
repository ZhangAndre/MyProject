<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%>  
<!DOCTYPE>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="bootstrap-3.3.7-dist/css/bootstrap.css">
<link rel="stylesheet" href="css/site.css">
<script src="jquery/jquery-2.2.4.min.js" type="text/javascript"></script>
<script src="bootstrap-3.3.7-dist/js/bootstrap.min.js" type="text/javascript"></script>
<script src="page/pagetool.js" type="text/javascript"></script>
<title>慕课答疑平台</title>
<script type="text/javascript">

	var page = 1;
	var key = "";

	$(function(){
		getTheme(1);
	});
	function getTheme(pageNum){
		$.ajax({
			url : "admin/theme.json",
			type : "post",
			async : "true",
			data : {"action" : "searchTheme", "pageNum" : pageNum, "key" : key},
			dataType : "json",
			success : function(data){
				if (data.res==1){
					$(".list").html("");
					$.each(data.data.data, function(index, theItem) {
						var theme = $(".template").clone();
						theme.show();
						theme.removeClass("template");
						theme.find(".num").text(index+1);
						theme.find(".thename").text(theItem.thename);
						
						theme.find(".delete").attr("onclick", "deleteTheme("+theItem.theid+")");
						
						$(".list").append(theme);
					});
	
					page = setPage(pageNum, data.data.totalPage, "getTheme");
										
				} else if (data.res==-2){
					alert(data.info);
				}
			}
		});
	}
	
	function addTheme(){
		var thename = $("#thename").val();
		$("#thename").val("");
		
		if (thename.length <= 0 ){
			alert("不能添加空的");
		}
		else {
			$.ajax({
				url : "admin/theme.json",
				type : "post",
				async : "true",
				data : {"action" : "add", "thename" : thename},
				dataType : "json",
				success : function(data){
					if (data.res == 1){
						alert("添加成功");
						getTheme(1);
					}
					else{
						alert(data.info);
					}
				}
			});
		}
	}
	function deleteTheme(theid){
		$.ajax({
			url : "admin/theme.json",
			type : "post",
			async : "true",
			data : {"action" : "delete", "theid" : theid},
			dataType : "json",
			success : function(data){
				if (data.res == 1){
					alert("删除成功");
					getTheme(page);
				}
				else{
					alert(data.info);
				}
			}
		});
	}
	function search(){
		key = $("#key").val();
		
		getTheme(1);
	}

</script>
</head>
<body>
	<jsp:include flush="fasle" page="header.jsp" />
	<div class="container">		
		<div class="row">
			<div class="col-sm-offset-2 col-sm-8 msgtitle">
				<h3 class="pull-left">主题管理
				</h3>
				<div class="replybtn">
					<button type="button" class="btn btn-warning" data-toggle="modal"
							data-target="#add">添加</button>
					<button type="button" class="btn btn-success" data-toggle="modal"
							data-target="#search">搜索</button>
					
				</div>
			</div>
		</div>
		<div class="row template">
			<div class="col-sm-offset-2 col-sm-8 col-xs-12 msglist">
				<div class="col-sm-2 col-xs-2 num">1</div>
				<div class="col-sm-8 col-xs-7 title thename">Java</div>
				<div class="col-sm-2 col-xs-3 ">
					<button class="btn btn-danger delete">删除</button>
				</div>
			</div>
		</div>
		
		<div class= "list">
		</div>
		<div class="row" style="text-align: center">
			<jsp:include page="/page/pagetool.jsp"></jsp:include>
		</div>

	</div>
	
	<!-- 模态框（Modal） -->
	<div class="modal fade" id="search" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content modalcenter">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                <h4 class="modal-title">搜索</h4>
	            </div>
	            <div class="modal-body">
					<form role="form">
						<div class="form-group">
							<label for="key">关键字：</label>
							<input type="text" class="form-control" id="key" placeholder="">
						</div>
					</form>
				</div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	                <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="search()">搜索</button>
	            </div>
	        </div><!-- /.modal-content -->
	    </div><!-- /.modal -->
	</div>
	
	<!-- 模态框（Modal） -->
	<div class="modal fade" id="add" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content modalcenter">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                <h4 class="modal-title">添加主题</h4>
	            </div>
	            <div class="modal-body">
					<form role="form">
						<div class="form-group">
							<label for="thename">主题名：</label>
							<input type="text" class="form-control" id="thename" placeholder="">
						</div>
					</form>
				</div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	                <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="addTheme()">添加</button>
	            </div>
	        </div><!-- /.modal-content -->
	    </div><!-- /.modal -->
	</div>
	<jsp:include flush="fasle" page="footer.jsp" />
</body>
</html>
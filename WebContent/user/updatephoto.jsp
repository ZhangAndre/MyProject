<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<link rel="stylesheet" href="bootstrapvalidator/css/bootstrapValidator.css">
<link rel="stylesheet" href="css/site.css">
<script src="jquery/jquery-2.2.4.min.js" type="text/javascript"></script>
<script src="bootstrap-3.3.7-dist/js/bootstrap.min.js" type="text/javascript"></script>
<!-- 表单验证 -->
<script src="bootstrapvalidator/js/bootstrapValidator.js" type="text/javascript"></script>
<title>慕课答疑平台</title>
<script type="text/javascript">
	
	function uploadFile(){
		var formData = new FormData();
		formData.append("file",$("input[name='file']")[0].files[0]);
		
		$.ajax({ 
			url : "user/center.json?action=uploadPhoto", 
			type : 'POST', 
			data : formData, 
			processData : false, 		// 告诉jQuery不要去处理发送的数据
			contentType : false,		// 告诉jQuery不要去设置Content-Type请求头
			beforeSend:function(){		// 发送前
				console.log("正在进行，请稍候");
			},
			dataType : "json",
			success : function(data) { 
				if (data.res == 1){
					window.location.replace("user/center.jsp");
				}
				else {
					alert(data.info);
				}
			},
			error : function(responseStr) {
				console.log("error");
			}
		});
		
		return false;
	}

	function preview(file) {
		// 浏览器显示选择图片
		if (file.files && file.files[0]) {
			var fileReader = new FileReader();
			fileReader.onload = function(evt) {
				$(".uploadphoto").attr("src", evt.target.result);
			};

			fileReader.readAsDataURL(file.files[0]);
		} else {
			// 兼容IE内核
			$(".uploadphoto").attr("src", evt.target.result);
		}
	}
</script>
</head>
<body>
	<jsp:include flush="fasle" page="../header.jsp" />
	<div class="container">		
		<div class="row">
			<div class="col-sm-offset-3 col-sm-6 text-center">
				<h3>上传头像</h3>
			</div>
		</div>
		<form class="form-horizontal col-sm-offset-3" id="modifyform" method="post">
			<div class="form-group">
				<div class="col-sm-4 col-sm-offset-2">
					<img class="uploadphoto" src="upload/upload.jpg">
				</div>
			</div>
			<div class="form-group">
				<label for="file" class="col-sm-2 control-label">选择文件：</label>
				<div class="col-sm-4">
					<label class="sr-only" for="file">文件输入</label>
					<input name="file" type="file" onchange="preview(this)">
				</div>
			</div>
			<div class="form-group has-error">
				<div class="col-sm-offset-2 col-sm-4 col-xs-6 ">
					<span class="text-warning"></span>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-4 col-xs-12">
					<button class="btn btn-success btn-block" onclick="return uploadFile();">提交</button>
				</div>
			</div>
		</form>
	</div>
	<jsp:include flush="fasle" page="../footer.jsp" />
</body>
</html>
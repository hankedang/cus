<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="/cus/assets/easyui/themes/easyui.css" />
<link rel="stylesheet" type="text/css" href="/cus/assets/easyui/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="/cus/assets/css/right.css" />
<script type="text/javascript" src="/cus/assets/jquery/jquery.min.js"></script>
<script type="text/javascript" src="/cus/assets/easyui/easyui.min.js"></script>
<script type="text/javascript" src="/cus/assets/easyui/easyui-lang-zh.js"></script>
</head>
<body>

<div >
	<form id="form" style="padding:10px 20px 10px 40px;" method="post">
    	<table>
    		<tr style="display: none">
		        <td><label class="label" >ID：</label></td>
				<td><input class="easyui-textbox" name="id" style="width:120px"></td>
			</tr>
			<tr>
		        <td><label class="label" >父节点ID：</label></td>
				<td><input class="easyui-textbox" name="parent_id" style="width:120px"></td>
			</tr>
	    	<tr>
		        <td><label class="label" >权限名称：</label></td>
				<td><input class="easyui-textbox" name="text" style="width:150px"></td>
			</tr>
			<tr>
		        <td><label class="label" >URL：</label></td>
				<td><input class="easyui-textbox" name="url" style="width:350px"></td>
			</tr>
			<tr>
		        <td><label class="label" >权限类型：</label></td>
				<td>
					<select id="p_type" class="easyui-combobox" name="type" style="width:120px;">
					    <option value="0">目录</option>
					    <option value="1">页面</option>
					    <option value="2">按钮</option>
					</select>
				</td>
			</tr>
			<tr>
		        <td><label class="label" >权限描述：</label></td>
				<td><input class="easyui-textbox" data-options="multiline:true" name="descr" style="width:250px;height:100px"></td>
			</tr>
			<tr>
		        <td><label class="label" >iconCls：</label></td>
				<td><input class="easyui-textbox" name="icon" style="width:150px"></td>
			</tr>
		</table>
    </form>
    <div style="padding:5px; margin-top: 10px" class="query-btns">
        <a href="#" class="easyui-linkbutton" name="form-sub" icon="icon-ok">提交</a>
        <a href="#" class="easyui-linkbutton" name="form-close" icon="icon-cancel">重置</a>
    </div>
</div>

<script type="text/javascript">
$(function(){
	var flag = 'add';
	var url = window.location.href;
	var index = url.indexOf("=");
	var id = "";
	if(index > 0) {
		id = url.substr(index + 1)
	}
	if(id != '' && !isNaN(id) ){
		$.ajax({
			type:"POST", data:{id: id},
			dataType:"json", url:"./permission.do",
			success:function(row) {
				$('#form').form('load', {
					id:row.id,
					parent_id: row.parent_id,
					text:row.text,
					url:row.url,
					type:row.type,
					descr:row.descr,
					icon:row.iconCls
				});
				flag = 'edit';
			},error:function(result) {
				$.messager.alert('ERROR','服务器问题，请联系管理员','error');
			}
		});
	}
	
	$(".query-btns").on("click", "a", function(evt){
		var name = $(evt.currentTarget).attr("name");
		if(name == 'form-sub') {
			formSubmit();
		} else if(name == 'form-close') {
			$('#form').form('reset');
		}
		return false;
	});
	
	function formSubmit() {
		var url = '';
		if(flag == 'add') {
			url = './add.do';
		} else {
			url = './edit.do';
		}
		$.messager.progress();
		$('#form').form('submit',{
			url:url,
			contentType:'utf-8',
			onSubmit: function(){
				var isValid = $(this).form('validate');
				if (!isValid){
					$.messager.progress('close');
				}
				return isValid;	
			},
			success: function(data){
				var data = eval('(' + data + ')');
				$.messager.progress('close');
				if(data.result == 'ok') {
					$.messager.alert('Info','权限更新成功！', 'info');
					window.location.href='./permission.jsp'
				} else {
					$.messager.alert('Warning','权限信息更新失败', 'warning');
				}
			}
		});
	}
})


</script>

</body>
</html>
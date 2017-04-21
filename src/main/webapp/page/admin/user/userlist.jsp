<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" type="text/css" href="/cus/assets/easyui/themes/easyui.css" />
<link rel="stylesheet" type="text/css" href="/cus/assets/easyui/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="/cus/assets/css/right.css" />
<script type="text/javascript" src="/cus/assets/jquery/jquery.min.js"></script>
<script type="text/javascript" src="/cus/assets/easyui/easyui.min.js"></script>
<script type="text/javascript" src="/cus/assets/easyui/easyui-lang-zh.js"></script>
<script type="text/javascript" src="./userlist.js"></script>

</head>
<body>

<div>
	<span>
		<label class="label" >用户名：</label>
		<input class="easyui-textbox" name="user_name" style="width:150px">
	</span>
	<span>
		<label class="label" >所属站点：</label>
		<input class="easyui-textbox" name="app_name" style="width:150px">
	</span>
	<span>
		<label class="label" >角色名称：</label>
		<input class="easyui-textbox" name="role_name" style="width:150px">
	</span>
	<span class="query-btns" id="btn_action">
		<a class="easyui-linkbutton" name="query" data-options="iconCls:'icon-search',plain:true">查询</a>
	</span>
	<span class="query-btns" id="edt_action">
		<a class="easyui-linkbutton" name="edit" data-options="iconCls:'icon-edit',plain:true">编辑</a>
	</span>
</div>

<div id="user-table" style="width:100%;height:600px"></div>

<!-- Pagination -->
<div id="pager" style="background:#efefef;border:1px solid #ccc;"></div>


<!-- win -->
<div id="win" class="easyui-window" title="编辑用户" style="width:340px;height:280px"
        data-options="iconCls:'icon-edit',modal:true, closed: true">
    <form id="edit-form" style="padding:10px 20px 10px 40px;" method="post">
    	<table>
    		<tr style="display: none">
		        <td><label class="label" >用户ID：</label></td>
				<td><input class="easyui-textbox" name="id" style="width:120px"></td>
			</tr>
	    	<tr>
		        <td><label class="label" >用户名：</label></td>
				<td><input class="easyui-textbox" name="user_name" style="width:150px"></td>
			</tr>
			<tr>
		        <td><label class="label" >E-mail：</label></td>
				<td><input class="easyui-textbox" name="e_mail" style="width:150px"></td>
			</tr>
			<tr>
		        <td><label class="label" >联系方式：</label></td>
				<td><input class="easyui-textbox" name="phone" style="width:150px"></td>
			</tr>
			<tr>
		        <td><label class="label" >账号状态：</label></td>
				<td>
					<select id="status" class="easyui-combobox" name="status" style="width:80px;">
					    <option value="0">正常</option>
					    <option value="1">锁定</option>
					</select>
				</td>
			</tr>
			<tr>
		        <td><label class="label" >站点：</label></td>
				<td>
					<input id="app_id" class="easyui-combobox" name="app_id" style="width:80px;"
    					data-options="valueField:'id',textField:'app_key',url:'./appCombo.do'" />
				</td>
			</tr>
			<tr>
		        <td><label class="label" >角色：</label></td>
				<td>
					<input id="role_id" class="easyui-combobox" name="role_id" style="width:80px;"
   						data-options="valueField:'id',textField:'role_name',url:'./roleCombo.do'" />
				</td>
			</tr>
		</table>
        <div style="padding:5px;text-align:center; margin-top: 10px" class="query-btns">
            <a href="#" class="easyui-linkbutton" name="form-sub" icon="icon-ok">提交</a>
            <a href="#" class="easyui-linkbutton" name="form-close" icon="icon-cancel">关闭</a>
        </div>
    </form>
</div>

<!-- hidden  -->
<input type="hidden" name="pageNumber" value="1"/>
<input type="hidden" name="pageSize" value="20"/>


</body>
</html>
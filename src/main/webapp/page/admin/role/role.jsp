<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8");  %>
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
<script type="text/javascript" src="./role.js"></script>

</head>
<body>

<div>
	<span>
		<label class="label" >角色名称：</label>
		<input class="easyui-textbox" name="role_name" style="width:150px">
	</span>
	<span class="query-btns" >
		<a class="easyui-linkbutton" name="query" data-options="iconCls:'icon-search',plain:true">查询</a>
	</span>
	<span class="query-btns" >
		<a class="easyui-linkbutton" name="edit" data-options="iconCls:'icon-edit',plain:true">编辑</a>
	</span>
	<span class="query-btns" >
		<a class="easyui-linkbutton" name="add" data-options="iconCls:'icon-add',plain:true">添加</a>
	</span>
	<span class="query-btns" >
		<a class="easyui-linkbutton" name="del" data-options="iconCls:'icon-cancel',plain:true">删除</a>
	</span>
</div>

<div class="easyui-layout" style="height:600px;width:100%">

	 <div region="west" split="true" style="width:60%;" >
		<div id="role-table" style="height:500px;width:100%"></div>
		<div id="pager" style="background:#efefef;border:1px solid #ccc; " data-options = "fit:true"></div>
	</div>
	
	<div region="center" title="当前角色所拥有的权限" style="padding:5px;">
        <ul id="tree-p" class="easyui-tree" ></ul>
    </div>
</div>

<div id="win" class="easyui-window" title="编辑用户" style="width:340px;height:250px"
        data-options="iconCls:'icon-edit',modal:true, closed: true">
    <form id="edit-form" style="padding:10px 20px 10px 40px;" method="post">
    	<table>
    		<tr style="display: none">
		        <td><label class="label" >用户ID：</label></td>
				<td><input class="easyui-textbox" name="id" style="width:120px"></td>
			</tr>
	    	<tr>
		        <td><label class="label" >角色名：</label></td>
				<td><input class="easyui-textbox" name="role_name" style="width:150px"></td>
			</tr>
			<tr>
		        <td><label class="label" >角色描述：</label></td>
				<td><input class="easyui-textbox" data-options="multiline:true" name="descr" style="width:150px;height:100px"></td>
			</tr>
		</table>
        <div style="padding:5px;text-align:center; margin-top: 10px" class="query-btns">
            <a href="#" class="easyui-linkbutton" name="form-sub" icon="icon-ok">提交</a>
            <a href="#" class="easyui-linkbutton" name="form-close" icon="icon-cancel">关闭</a>
        </div>
    </form>
</div>

<!-- tree 右键菜单 -->
<div id="mm" class="easyui-menu" style="width:120px;">
	<div name = "remove-permission" data-options="iconCls:'icon-remove'">Remove</div>
</div>


<!-- hidden  -->
<input type="hidden" name="pageNumber" value="1"/>
<input type="hidden" name="pageSize" value="20"/>


</body>
</html>
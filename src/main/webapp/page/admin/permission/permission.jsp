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
<script type="text/javascript" src="./permission.js"></script>

</head>
<body>

<div>
	<span>
		<label class="label" >父节点ID：</label>
		<input class="easyui-textbox" name="parent_id" style="width:150px">
	</span>
	<span>
		<label class="label" >权限名称：</label>
		<input class="easyui-textbox" name="permission_name" style="width:150px">
	</span>
	<span class="query-btns" id="btn_action">
		<a class="easyui-linkbutton" name="query" data-options="iconCls:'icon-search',plain:true">查询</a>
	</span>
	<span class="query-btns" id="edt_action">
		<a class="easyui-linkbutton" name="edit" data-options="iconCls:'icon-edit',plain:true">编辑</a>
	</span>
	<span class="query-btns" id="rm_action">
		<a class="easyui-linkbutton" name="del" data-options="iconCls:'icon-remove',plain:true">删除</a>
	</span>
	<span class="query-btns" id="ad_action">
		<a class="easyui-linkbutton" name="role_add_pms" data-options="iconCls:'icon-add',plain:true">赋权限</a>
	</span>
</div>

<div id="permission-table" style="width:100%;height:600px"></div>

<!-- Pagination -->
<div id="pager" style="background:#efefef;border:1px solid #ccc;"></div>

<div id="win" class="easyui-window" title="角色赋权限" style="width:340px;height:140px"
        data-options="iconCls:'icon-edit',modal:true, closed: true">
   	<table style="padding:10px 20px 10px 20px;">
   		<tr>
	        <td><label class="label" >角色：</label></td>
			<td>
				<input id="role_id" class="easyui-combobox" name="role_id" style="width:200px;"
  						data-options="valueField:'id',textField:'role_name',url:'/cus/page/admin/user/roleCombo.do'" />
			</td>
		</tr>
	</table>
	<div style="padding:5px;text-align:center; margin-top: 10px" class="query-btns">
        <a href="#" class="easyui-linkbutton" name="sub" icon="icon-ok">提交</a>
        <a href="#" class="easyui-linkbutton" name="close" icon="icon-cancel">关闭</a>
    </div>
</div>

<!-- hidden  -->
<input type="hidden" name="pageNumber" value="1"/>
<input type="hidden" name="pageSize" value="20"/>


</body>
</html>
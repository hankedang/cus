$(function() {
	$(".query-btns").on("click", "a", function(evt){
		var name = $(evt.currentTarget).attr("name");
		if (name == "edit") {
			editRole();
		} else if (name == 'query') {
			$("input[name='pageNumber']").val("1");
			query();
		} else if(name == 'form-sub') {
			formSub();
		} else if(name == 'form-close') {
			closeWin();
		}
		return false;
	});
	
	var col = [[
	   {field:'id',title:'ID',width:150, hidden: true},
       {field:'user_name',title:'用户名',width:150},
       {field:'e_mail',title:'E_Mail',width:150},
       {field:'phone',title:'联系方式',width:150},
       {field:'status',title:'账号状态',width:60, formatter: function (value,row,index) {
    	   return value == 0 ? "正常" : "锁定";
       }},
       {field:'create_time',title:'创建时间',width:130},
       {field:'update_time',title:'更新时间',width:130},
       {field:'app_id',title:'站点ID',width:50},
       {field:'app_name',title:'所属站点',width:100},
       {field:'role_id',title:'角色ID',width:50},
       {field:'role_name',title:'角色名称',width:100}
    ]];
	
	$('#user-table').datagrid({
		title:"",
		remoteSort:true,
		striped:true,
		singleSelect:true,
		rownumbers:true,
	    showHeader:true,
	    showFooter:true,
	    columns: col,
		onSortColumn:function(sort,order){
			$("input[name='orderBy']").val(sort);
			$("input[name='orderType']").val(order);
			query();
		}
	});
	//设置分页组件
	$("#pager").pagination({
		layout:['first','prev','links','next','last','sep','list'],
		pageSize:20,
		pageList:[10,20,30,50,100],
		onSelectPage:function (pageNumber, pageSize) {
			$("input[name='pageNumber']").val(pageNumber);
			$("input[name='pageSize']").val(pageSize);
			query();
		}
	});
	
	function query () {
		var params = getParams();
		$("#user-table").datagrid('loading');
		// Data Request
		$.ajax({
			type:"POST",
			data:params,
			dataType:"json", 
			url:"./allUser.do",
			success:function(result) {
				if (result.msg) {
					$.messager.alert('ERROR',result.msg,'error');
					return;
				}
				$('#user-table').datagrid({
					data:result.records
				})
			},error:function(result) {
				$.messager.alert('ERROR','服务器问题，请联系管理员','error');
			},complete:function(){
				$("#user-table").datagrid('loaded');
			}
		});
	}
	function getParams() {
		var params = {};
		params["pageNumber"] = $("input[name='pageNumber']").val();
		params["pageSize"] = $("input[name='pageSize']").val();
		
		var user_name = $("input[name='user_name']").val();
		var app_name = $("input[name='app_name']").val();
		var role_name = $("input[name='role_name']").val();
		
		if(user_name != '' && user_name.length != 0) {
			params["user_name"] = user_name;
		}
		if(app_name != '' && app_name.length != 0) {
			params["app_name"] = app_name;
		}
		if(role_name != '' && role_name.length != 0) {
			params["role_name"] = role_name;
		}
		return params;
	}
	
	// 编辑用户角色
	function editRole() {
		var row = $("#user-table").datagrid('getSelected');
		if(row == null || row == '' ) {
			$.messager.alert('Warning','请选择用户进行编辑', 'warning');
			return;
		}
		var user_id = row.id;
		$('#win').window('open');
		// form 
		$('#edit-form').form('load', {
			id:row.id,
			user_name: row.user_name,
			e_mail:row.e_mail,
			phone:row.phone,
			status:row.status,
			app_id:row.app_id,
			role_id:row.role_id
		});
	}

	function closeWin(){
		$('#win').window('close');
	}
	
	function formSub() {
		$.messager.progress();
		$('#edit-form').form('submit',{
			url:"./edit.do",
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
					$.messager.alert('Info','用户信息更新成功！', 'info');
				} else {
					$.messager.alert('Warning','用户信息更新失败', 'warning');
				}
			}
		});
	}
	
	query();
	
});
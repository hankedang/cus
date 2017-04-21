$(function() {
	$(".query-btns").on("click", "a", function(evt){
		var name = $(evt.currentTarget).attr("name");
		if (name == "edit") {
			editPermission();
		} else if (name == 'query') {
			$("input[name='pageNumber']").val("1");
			query();
		} else if(name == 'del') {
			del();
		} else if(name == 'role_add_pms') {
			openWin();
		} else if(name == 'sub') {
			roleAddPms();
		} else if(name == 'close') {
			$('#win').window('close');
		}
		return false;
	});
	
	var col = [[
	   {field:'ck',checkbox:true},   
	   {field:'id',title:'ID',width:60},
       {field:'parent_id',title:'父节点ID',width:60},
       {field:'text',title:'权限名称',width:80},
       {field:'url',title:'URL',width:330},
       {field:'type',title:'权限类型',width:80, formatter: function (value,row,index) {
    	   switch(value) {
    	   case 0: return "目录";
    	   case 1: return "页面";
    	   case 2: return "按钮";
    	   }
    	   return value;
       }},
       {field:'descr',title:'权限描述',width:250},
       {field:'iconCls',title:'iconCls',width:100}
    ]];
	
	$('#permission-table').datagrid({
		title:"",
		remoteSort:true,
		striped:true,
		rownumbers:true,
	    showHeader:true,
	    showFooter:true,
	    idField:'id',
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
		$("#permission-table").datagrid('loading');
		// Data Request
		$.ajax({
			type:"POST",
			data:params,
			dataType:"json", 
			url:"./allPermission.do",
			success:function(result) {
				if (result.msg) {
					$.messager.alert('ERROR',result.msg,'error');
					return;
				}
				$('#permission-table').datagrid({
					data:result.records
				})
			},error:function(result) {
				$.messager.alert('ERROR','服务器问题，请联系管理员','error');
			},complete:function(){
				$("#permission-table").datagrid('loaded');
			}
		});
	}
	function getParams() {
		var params = {};
		params["pageNumber"] = $("input[name='pageNumber']").val();
		params["pageSize"] = $("input[name='pageSize']").val();
		
		var permission_name = $("input[name='permission_name']").val();
		var parent_id = $("input[name='parent_id']").val();
		
		if(permission_name != '' && permission_name.length != 0) {
			params["text"] = permission_name;
		}
		if(parent_id != '' && parent_id.length != 0) {
			params["parent_id"] = parent_id;
		} 
	
		return params;
	}
	
	function del() {
		var selects = $('#permission-table').datagrid('getSelections');
		if(selects == null || selects == '' ) {
			$.messager.alert('Warning','请选择权限进行删除', 'warning');
			return;
		}
		var ids = new Array();
		for(var row in selects) {
			ids.push(selects[row].id);
		}
		$.ajax({
			type:"POST",
			data:{ids: ids.join(",")},
			dataType:"json", 
			url:"./del.do",
			success:function(data) {
				if (data.result == 'ok') {
					query();
					$.messager.alert('INFO',"删除权限成功",'info');
					return;
				}
			},error:function(result) {
				$.messager.alert('ERROR','服务器问题，请联系管理员','error');
			}
		});
	}
	
	function openWin() {
		var selects = $('#permission-table').datagrid('getSelections');
		if(selects == null || selects == '' ) {
			$.messager.alert('Warning','请选择。。。', 'warning');
			return;
		}
		$('#win').window('open');
	}
	
	function roleAddPms() {
		var selects = $('#permission-table').datagrid('getSelections');
		var val = $('#role_id').combobox('getValue');
		if(val == null || val == '') {
			$.messager.alert('Warning','请选择要赋的角色', 'warning');
			return;
		}
		var pids = new Array();
		for(var row in selects) {
			pids.push(selects[row].id);
		}
		var param = {};
		param.pids = pids.join(",");
		param.role_id = Number(val);
		
		$.ajax({
			type:"POST",
			data:param,
			dataType:"json", 
			url:"./roleAddPms.do",
			success:function(data) {
				if (data.result == 'ok') {
					$.messager.alert('INFO',"赋权限成功",'info');
				}
			},error:function(result) {
				$.messager.alert('ERROR','服务器问题，请联系管理员','error');
			}
		});
	}
	
	function editPermission(){
		var selects = $('#permission-table').datagrid('getSelections');
		if(selects.length > 1) {
			$.messager.alert('WARING',"每次只能编辑一个",'waring');
			return;
		}
		var id = selects[0].id;
		window.location.href='./addpermission.jsp?id='+id
	}
	
	query();
	
});
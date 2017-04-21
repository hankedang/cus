$(function(){
	// 用来标示是添加还是编辑
	var flag = '';
	
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
		} else if(name == 'add') {
			addRole();
		} else if(name == 'del' ) {
			delRole();
		}
		return false;
	});

	var col = [[
	   {field:'id',title:'ID',width:60,},
       {field:'role_name',title:'名称',width:160},
       {field:'descr',title:'描述',width:450}
    ]];
	
	$('#role-table').datagrid({
		title:"",
		remoteSort:true,
		striped:true,
		singleSelect:true,
		rownumbers:true,
	    showHeader:true,
	    showFooter:true,
	    columns: col,
	    onClickRow:clickRow,
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
		$("#role-table").datagrid('loading');
		// Data Request
		$.ajax({
			type:"POST",
			data:params,
			dataType:"json", 
			url:"./allRole.do",
			success:function(result) {
				if (result.msg) {
					$.messager.alert('ERROR',result.msg,'error');
					return;
				}
				$('#role-table').datagrid({
					data:result.records
				})
			},error:function(result) {
				$.messager.alert('ERROR','服务器问题，请联系管理员','error');
			},complete:function(){
				$("#role-table").datagrid('loaded');
			}
		});
	}
	
	function getParams() {
		var params = {};
		params["pageNumber"] = $("input[name='pageNumber']").val();
		params["pageSize"] = $("input[name='pageSize']").val();
		
		var role_name = $("input[name='role_name']").val();
		
		if(role_name != '' && role_name.length != 0) {
			params["role_name"] = role_name;
		}
		return params;
	}
	
	// 右侧树
	$("#tree-p").tree({
		onContextMenu: function(e, node){
			e.preventDefault();
			$('#tree-p').tree('select', node.target);
			$('#mm').menu('show', {
				left: e.pageX,
				top: e.pageY,
			});
		}
	})
	$('#mm').menu({
		onClick:function(item){
			var node = $("#tree-p").tree('getSelected');
			var row = $("#role-table").datagrid('getSelected');
			if(item.name == 'remove-permission') {
				$.ajax({
					type:"POST",
					data:{role_id: row.id , permission_id: node.id},
					dataType:"json", 
					url:"./delPermission.do",
					success:function(data) {
						if(data.result != 'ok') {
							$.messager.alert('ERROR',result.msg,'error');
							return;
						}
						loadTreeData({role_id: row.id})
					},error:function(result) {
						$.messager.alert('ERROR','服务器问题，请联系管理员','error');
					}
				});
			}
		}
	});
	
	function loadTreeData(param) {
		$.ajax({
			type:"POST",
			data:param,
			dataType:"json", 
			url:"./currentPermission.do",
			success:function(result) {
				$("#tree-p").tree({
					data:result
				})
			},error:function(result) {
				$.messager.alert('ERROR','服务器问题，请联系管理员','error');
			}
		});
	}

	function clickRow(index, row) {
		var param = {role_id: row.id};
		loadTreeData(param)
	}
	
	function editRole() {
		var row = $("#role-table").datagrid('getSelected');
		if(row == null || row == '' ) {
			$.messager.alert('Warning','请选择进行编辑', 'warning');
			return;
		}
		flag = 'edit';
		$('#win').window('open');
		// form 
		$('#edit-form').form('load', {
			id:row.id,
			role_name: row.role_name,
			descr:row.descr
		});
	}
	
	function delRole () {
		var row = $("#role-table").datagrid('getSelected');
		if(row == null || row == '' ) {
			$.messager.alert('Warning','请选择要删除的', 'warning');
			return;
		}
		$.ajax({
			type:"POST",
			data:{role_id: row.id},
			dataType:"json", 
			url:"./del.do",
			success:function(data) {
				if(data.result == 'ok') {
					query();
					$.messager.alert('INFO','删除成功', 'info');
				}
			},error:function(result) {
				$.messager.alert('ERROR','服务器问题，请联系管理员','error');
			}
		});
	}
	
	function closeWin(){
		$('#win').window('close');
	}
	function addRole(){
		flag = 'add';
		$('#win').window('open');
		$('#edit-form').form('reset');
	}
	
	function formSub() {
		var url = '';
		if(flag == 'add') {
			url = './add.do';
		} else {
			url = './edit.do';
		}
		$.messager.progress();
		$('#edit-form').form('submit',{
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
				closeWin();
				var data = eval('(' + data + ')');
				$.messager.progress('close');
				if(data.result == 'ok') {
					query();
					$.messager.alert('Info','用户信息更新成功！', 'info');
				} else {
					$.messager.alert('Warning','用户信息更新失败', 'warning');
				}
			}
		});
	}
	
	query();
	
})
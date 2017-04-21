$(function() {
	var boardId = $("input[name='tempId']").val();
	var downUrl = "/board/download/" + boardId;
	var dataUrl = "/board/search/" + boardId;

	// 注册按钮事件
	$(".query-btns").on("click", "a", function(evt){
		var name = $(evt.currentTarget).attr("name");
		if (name == "index") {
			$("#idxDialog").dialog("open").dialog('resize',{top:evt.pageY,left:evt.pageX});
		} else if (name == 'query') {
			$("input[name='pageNumber']").val("1");
			query();
		}
		return false;
	});
	
	// DownLoad Action
	$('#downMenu').menu({
		onClick:function(item){
			var params = getQueryCondition();
			params['downType'] = item.name;
			downLoad(downUrl, params);
		}
	});
	
	// 初始化指标弹出框
	$("#idxDialog").dialog({
		closable:false,closed:true,modal:true,
		buttons:[{text:'确定',iconCls:'icon-ok',handler:function() {
			var dims = $("#dimList").tree("getChecked");
			if (dims.length == 0) {
				$.messager.alert('警告','请至少选择一个维度!','warning');
				return;
			}
			var idxs = $("#idxList").tree("getChecked");
			if (idxs.length == 0) {
				$.messager.alert('警告','请至少选择一个指标!','warning');
				return;
			}
			$("input[name='pageNumber']").val("1");
			$("input[name='orderType']").val('');
			$("#idxDialog").dialog("close");
			query();
		}},{text:'关闭',iconCls:'icon-cancel',handler:function() {
			$("#idxDialog").dialog("close");
		}}]
	});
	
	// 初始化 EasyUI DataGrid
	$('#tableData').datagrid({
		title:"",remoteSort:true,
		striped:true,singleSelect:true,
	    showHeader:true,showFooter:true,
		onSortColumn:function(sort,order){
			$("input[name='orderBy']").val(sort);
			$("input[name='orderType']").val(order);
			query();
		}
	});
	
	//设置分页组件
	$("#pager").pagination({
		layout:['first','prev','links','next','last','sep','list'],
		pageSize:20,pageList:[10,20,30,50,100],
		onSelectPage:function (pageNumber, pageSize) {
			$("input[name='pageNumber']").val(pageNumber);
			$("input[name='pageSize']").val(pageSize);
			query();
		}
	});

	// 查询与显示数据
	function query () {
		var params = getQueryCondition();

		// Data Request
		$("#tableData").datagrid('loading');
		$.ajax({
			type:"POST",data:params,
			dataType:"json", url:dataUrl,
			success:function(result) {
				if (result.msg) {
					$.messager.alert('ERROR',result.msg,'error');
					return;
				}
				showTable(result);
			},error:function(result) {
				$.messager.alert('ERROR','服务器问题，请联系管理员','error');
			},complete:function(){
				$("#tableData").datagrid('loaded');
			}
		});
	}
	
	// 加载数据表格
	function showTable (result) {
		
		// Set Column
		var columns = [];
		var frozenColumns = [];
		var columnList = result['columns'];
		$.each(columnList, function(i,column){
			var colItem = {
				field:column.name,title:column.title,sortable:(column.sortable || true),width:120,
				formatter:(function(column) {
					if ('digit' == column.dateType) {
						return digitFormat
					} else if ('int' == column.dateType) {
						return intFormat;
					}
					return undefined;
				})(column)	
			}
			if (column.dim == 1) {
				colItem.align = 'left';
				frozenColumns.push(colItem);
			} else {
				colItem.align = 'right';
				columns.push(colItem);
			}
		});
		
		var tableData = result['tableData'];
		// 设置表格动态列, 加载表格数据
		$('#tableData').datagrid({
			columns:[columns],
			frozenColumns:[frozenColumns],
			data:tableData
		});
		
		// 更新分页控件
		$("#pager").pagination("refresh", {
			total:tableData.total
		});
	}
	
	/****** Get Query Condition *****/
	function getQueryCondition () {
		var params = {};
		
		// DateSpan
		params["startDate"] = $('#startDate').datebox('getValue');
		params["endDate"] = $('#endDate').datebox('getValue');
		
		// Order
		params["orderBy"] = $("input[name='orderBy']").val();
		params["orderType"] = $("input[name='orderType']").val();
		
		// Pagination
		params["pageNumber"] = $("input[name='pageNumber']").val();
		params["pageSize"] = $("input[name='pageSize']").val();
		
		// Dynamic Dimension
		var queryDim = [];
		var dimRows = $("#dimList").tree("getChecked");
		$.each(dimRows, function(idx, row) {
			row.id && queryDim.push(row.id);
		});
		params['queryDim'] = queryDim.join(";");
		
		// Dynamic Indicator
		var queryIdx = [];
		var idxRows = $("#idxList").tree("getChecked");
		$.each(idxRows, function(idx, row) {
			row.id && queryIdx.push(row.id);
		});
		params['queryIdx'] = queryIdx.join(";");;
		
		// Parameters
		params['queryStr'] = getParams();
		
		return params;
	}
	
	function getParams () {
		var queryPara = [];
		// 本页选择的条件
		$(".con-filter").each(function() {
			var field = $(this).find('span.field').attr('name');
			var sign = $(this).find('span.sign').attr('name');
			var value = $(this).find('span.value').attr('name');
			queryPara.push(field + ':' + sign + ':' + value);
		});
		return queryPara.join(";");
	}
	// 初始化
	query();
});
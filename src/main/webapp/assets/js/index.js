$(function() {
	$("ul.menu").on('click', 'a', function(e) {
		var title = $(e.target).text();
		var url = $(e.target).attr("aim");
		addTabs(url,title);
	});

});

// 添加一个选项卡面板
function addTabs(url, title) {
	if ($('#mainTabs').tabs('exists', title)) {
		$('#mainTabs').tabs('select', title);
	} else {
		if (!title) {
			title = '新页面';
		}
		$('#mainTabs').tabs('add', {
			title:title,
			closable:true,
			iconCls:'icon-ok',
			content:'<iframe frameborder="0" src="' + url + '"></iframe>'
		});
	}
}

//添加一个选项卡面板
function addDrillTab(url, title) {
	var currTab =$('#mainTabs').tabs('getSelected');
	
	title = currTab.panel('options').title + ">>" + title;
	
	if ($('#mainTabs').tabs('exists', title)) {
		$('#mainTabs').tabs('select', title);
		/*var tabToAdd = $('#mainTabs').tabs('getTab', title);
		// 如果存在则更新
		$('#mainTabs').tabs('update', {
			tab:tabToAdd, options:{href:url}
		});*/
	} else {
		$('#mainTabs').tabs('add', {
			title:title, closable:true, iconCls:'icon-ok',
			content:'<iframe frameborder="0" src="' + url + '"></iframe>'
		});
	}
}
layui.use(['table', 'admin', 'ax'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;

    /**
     * 社区活动管理
     */
    var Activity = {
        tableId: "activityTable",
        condition: {
            title: "",
            name: "",
        }
    };

    /**
     * 初始化表格的列
     */
    Activity.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'activityId', hide: true, title: ''},
            {align: 'center',minWidth:300, toolbar: '#tableBar', title: '操作'},
            {field: 'title', sort: true, title: '标题'},
            {field: 'pic',sort: true,title: '图片', templet: function(d){
                
                //return "<img lay-event='img' src='"+d.pic+"' />";
                    return '<div style="text-align: center;"><img lay-event="img" data-img-src="' + d.pic + '" src="' + d.pic + '" style="width: 30px; height: 30px; object-fit: cover; margin: 0 auto;" /></div>';
                 
             } },
            
            {field: 'context', sort: true, title: '详情'},
            {field: 'time', sort: true, title: '时间'},
            
         
            {field: 'name', sort: true, title: '发起人'},
            {field: 'endPoint', sort: true, title: '当前状态', templet: function(d){
                
             	 if(0 == d.endPoint){
             		
             		 return "进行中";
             	 }else if(1 == d.endPoint){
             		
             		 return "已结束";
             	 }
            
               } }
         
        ]];
    };

    /**
     * 点击查询按钮
     */
    Activity.search = function () {
        var queryData = {};
       queryData['condition'] = $("#condition").val();


        queryData['title'] = Activity.condition.title;
        queryData['name'] = Activity.condition.name;
        queryData['title'] = $("#title").val();
        // 获取发起人输入框的值
        queryData['name'] = $("#name").val();
        table.reload(Activity.tableId, {where: queryData});
    };

    /**
     * 弹出添加对话框
     */
    Activity.openAddDlg = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加社区活动',
            content: Feng.ctxPath + '/activity/add',
            end: function () {
                admin.getTempData('formOk') && table.reload(Activity.tableId);
            }
        });
    };

    /**
     * 导出excel按钮
     */
    Activity.exportExcel = function () {
        var checkRows = table.checkStatus(Activity.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResult.config.id, checkRows.data, 'xls');
        }
    };

    /**
     * 点击编辑
     *
     * @param data 点击按钮时候的行数据
     */
    Activity.openEditDlg = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '修改社区活动',
            content: Feng.ctxPath + '/activity/edit?activityId=' + data.activityId,
            end: function () {
                admin.getTempData('formOk') && table.reload(Activity.tableId);
            }
        });
    };

    Activity.baoming = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '报名人数',
            area:["700px","600px"],
            content: Feng.ctxPath + '/activityUser?activityId=' + data.activityId,
            end: function () {
                admin.getTempData('formOk') && table.reload(Activity.tableId);
            }
        });
    };
    Activity.baoming1 = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '评论管理',
            area:["700px","600px"],
            content: Feng.ctxPath + '/comment?forumId=' + data.activityId,
            end: function () {
                admin.getTempData('formOk') && table.reload(Activity.tableId);
            }
        });
    };
    
    
    
    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    Activity.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/activity/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(Activity.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("activityId", data.activityId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Activity.tableId,
        url: Feng.ctxPath + '/activity/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: Activity.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Activity.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Activity.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        Activity.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + Activity.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Activity.openEditDlg(data);
        } else if (layEvent === 'delete') {
            Activity.onDeleteItem(data);
        } else if (layEvent === 'baoming') {
            Activity.baoming(data);
        }else if (layEvent === 'baoming1') {
            Activity.baoming1(data);
        }else if(layEvent === 'img'){
        	console.log($(this).attr('src'))
    		var imgSrc=$(this).attr('src')
    		layer.open({
    			type:1
    			,title:false
    			,closeBtn:0
    			,skin:'layui-layer-nobg'
    			,shadeClose:true
    			,content:'<img src="'+imgSrc+'" style="max-height:100%;max-width:100%;">'
    			,scrollbar:false
    		})
        }
    });
});

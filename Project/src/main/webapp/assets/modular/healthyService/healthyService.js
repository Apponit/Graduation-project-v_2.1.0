layui.use(['table', 'admin', 'ax'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;

    /**
     * 健康服务管理
     */
    var HealthyService = {
        tableId: "healthyServiceTable"
    };

    /**
     * 初始化表格的列
     */
    HealthyService.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'serviceId', hide: true, title: ''},
            {field: 'name', sort: true, title: '服务名称'},
            {field: 'intro', sort: true, title: '服务介绍'},

            {field: 'cover',sort: true,title: '服务封面', templet: function(d){
                
                return "<img lay-event='img' src='"+d.cover+"' />";
                
                 
             } },
            {field: 'price', sort: true, title: '服务费'},
            {field: 'tel', sort: true, title: '联系电话'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    HealthyService.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(HealthyService.tableId, {where: queryData});
    };

    /**
     * 弹出添加对话框
     */
    HealthyService.openAddDlg = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加健康服务',
            content: Feng.ctxPath + '/healthyService/add',
            end: function () {
                admin.getTempData('formOk') && table.reload(HealthyService.tableId);
            }
        });
    };

    /**
     * 导出excel按钮
     */
    HealthyService.exportExcel = function () {
        var checkRows = table.checkStatus(HealthyService.tableId);
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
    HealthyService.openEditDlg = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '修改健康服务',
            content: Feng.ctxPath + '/healthyService/edit?serviceId=' + data.serviceId,
            end: function () {
                admin.getTempData('formOk') && table.reload(HealthyService.tableId);
            }
        });
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    HealthyService.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/healthyService/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(HealthyService.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("serviceId", data.serviceId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + HealthyService.tableId,
        url: Feng.ctxPath + '/healthyService/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: HealthyService.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        HealthyService.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        HealthyService.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        HealthyService.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + HealthyService.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            HealthyService.openEditDlg(data);
        } else if (layEvent === 'delete') {
            HealthyService.onDeleteItem(data);
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

layui.use(['table', 'admin', 'ax'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;

    /**
     * 一键求救管理
     */
    var Location = {
        tableId: "locationTable"
    };

    /**
     * 初始化表格的列
     */
    Location.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'locationId', hide: true, title: ''},
            {field: 'name', sort: true, title: '用户'},
            {field: 'tel', sort: true, title: '联系电话'},
            {field: 'lat', sort: true, title: '经度'},
            {field: 'lng', sort: true, title: '纬度'},
            {field: 'time', sort: true, title: '上报时间'},        
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Location.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(Location.tableId, {where: queryData});
    };

    /**
     * 弹出添加对话框
     */
    Location.openAddDlg = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加一键求救',
            content: Feng.ctxPath + '/location/add',
            end: function () {
                admin.getTempData('formOk') && table.reload(Location.tableId);
            }
        });
    };

    /**
     * 导出excel按钮
     */
    Location.exportExcel = function () {
        var checkRows = table.checkStatus(Location.tableId);
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
    Location.openEditDlg = function (data) {
        admin.putTempData('formOk', false);
       
        
   	 top.layui.admin.open({
         type: 2,
         area: ['80%', '80%'], //宽高
         title: '查看位置',
         content: Feng.ctxPath + '/location/map?lat='+data.lat+"&lng="+data.lng,
         end: function () {
             admin.getTempData('formOk') && table.reload(News.tableId);
         }
     });
        
        
        
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    Location.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/location/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(Location.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("locationId", data.locationId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Location.tableId,
        url: Feng.ctxPath + '/location/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: Location.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Location.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Location.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        Location.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + Location.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Location.openEditDlg(data);
        } else if (layEvent === 'delete') {
            Location.onDeleteItem(data);
        }
    });
});

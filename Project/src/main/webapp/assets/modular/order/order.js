layui.use(['table', 'admin', 'ax'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    getList();
    /**
     * 服务预约管理
     */
    var Order = {
        tableId: "orderTable"
    };

    /**
     * 初始化表格的列
     */
    Order.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'orderId', hide: true, title: ''},
            {field: 'name', sort: true, title: '服务'},
            {field: 'userName', sort: true, title: '预约人'},
            {field: 'tel', sort: true, title: '联系电话'},
            {field: 'time', sort: true, title: '预约时间'},
            {field: 'orderNo', sort: true, title: '预约号'},
            {field: 'message', sort: true, title: '留言'},
            {field: 'status', sort: true, title: '状态', templet: function(d){
                
           	 if(0 == d.status){
           		
           		 return "待完成";
           	 }else if(2 == d.status){
           		
           		 return "已完成";
           	 }else if(3 == d.status){
           		
           		 return "已取消";
           	 }
               
            
             } },
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Order.search = function () {
        var queryData = {};
        queryData['userId'] = $("#userId").val();
        table.reload(Order.tableId, {where: queryData});
    };

    /**
     * 弹出添加对话框
     */
    Order.openAddDlg = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加服务预约',
            content: Feng.ctxPath + '/order/add',
            end: function () {
                admin.getTempData('formOk') && table.reload(Order.tableId);
            }
        });
    };

    /**
     * 导出excel按钮
     */
    Order.exportExcel = function () {
        var checkRows = table.checkStatus(Order.tableId);
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
    Order.openEditDlg = function (data) {
        admin.putTempData('formOk', false);
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/order/editItem", function (data) {
                Feng.success("确认成功!");
                table.reload(Order.tableId);
            }, function (data) {
                Feng.error("确认失败!" + data.responseJSON.message + "!");
            });
            ajax.set("orderId", data.orderId);
            ajax.set("status",2);
            ajax.start();
        };
        Feng.confirm("是否确认完成?", operation);
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    Order.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/order/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(Order.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("orderId", data.orderId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Order.tableId,
        url: Feng.ctxPath + '/order/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: Order.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Order.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Order.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        Order.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + Order.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Order.openEditDlg(data);
        } else if (layEvent === 'delete') {
            Order.onDeleteItem(data);
        }
    });
});

layui.use(['table', 'admin', 'ax'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;

    /**
     * 活动报名人管理
     */
    var ActivityUser = {
        tableId: "activityUserTable"
    };

    /**
     * 初始化表格的列
     */
    ActivityUser.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'activityUserId', hide: true, title: ''},
            {field: 'userName', sort: true, title: '报名人'},
            {field: 'time', sort: true, title: '报名时间'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    ActivityUser.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(ActivityUser.tableId, {where: queryData});
    };

    /**
     * 弹出添加对话框
     */
    ActivityUser.openAddDlg = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加活动报名人',
            content: Feng.ctxPath + '/activityUser/add',
            end: function () {
                admin.getTempData('formOk') && table.reload(ActivityUser.tableId);
            }
        });
    };

    /**
     * 导出excel按钮
     */
    ActivityUser.exportExcel = function () {
        var checkRows = table.checkStatus(ActivityUser.tableId);
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
    ActivityUser.openEditDlg = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '修改活动报名人',
            content: Feng.ctxPath + '/activityUser/edit?activityUserId=' + data.activityUserId,
            end: function () {
                admin.getTempData('formOk') && table.reload(ActivityUser.tableId);
            }
        });
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    ActivityUser.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/activityUser/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(ActivityUser.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("activityUserId", data.activityUserId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + ActivityUser.tableId,
        url: Feng.ctxPath + '/activityUser/list?activityId='+ Feng.getUrlParam("activityId"),
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: ActivityUser.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        ActivityUser.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        ActivityUser.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        ActivityUser.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + ActivityUser.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            ActivityUser.openEditDlg(data);
        } else if (layEvent === 'delete') {
            ActivityUser.onDeleteItem(data);
        }
    });
});

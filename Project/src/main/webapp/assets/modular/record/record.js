layui.use(['table', 'admin', 'ax'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    getList();
    /**
     * 健康档案管理
     */
    var Record = {
        tableId: "recordTable"
    };

    /**
     * 初始化表格的列
     */
    Record.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'recordId', hide: true, title: ''},
            {field: 'name', sort: true, title: '用户'},
            {field: 'time', sort: true, title: '检查时间'},
            {field: 'record', sort: true, title: '详细记录'},
            {field: 'report', sort: true, title: '报告'},
            {field: 'opeName', sort: true, title: '操作人'},
            {field: 'advise', sort: true, title: '建议'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Record.search = function () {
        var queryData = {};
        queryData['userId'] = $("#userId").val();
        table.reload(Record.tableId, {where: queryData});
    };

    /**
     * 弹出添加对话框
     */
    Record.openAddDlg = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加健康档案',
            content: Feng.ctxPath + '/record/add',
            end: function () {
                admin.getTempData('formOk') && table.reload(Record.tableId);
            }
        });
    };

    /**
     * 导出excel按钮
     */
    Record.exportExcel = function () {
        var checkRows = table.checkStatus(Record.tableId);
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
    Record.openEditDlg = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '修改健康档案',
            content: Feng.ctxPath + '/record/edit?recordId=' + data.recordId,
            end: function () {
                admin.getTempData('formOk') && table.reload(Record.tableId);
            }
        });
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    Record.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/record/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(Record.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("recordId", data.recordId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Record.tableId,
        url: Feng.ctxPath + '/record/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: Record.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Record.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Record.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        Record.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + Record.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Record.openEditDlg(data);
        } else if (layEvent === 'delete') {
            Record.onDeleteItem(data);
        }
    });
});

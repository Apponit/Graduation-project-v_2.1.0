layui.use(['table', 'admin', 'ax'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;

    /**
     * 分类管理
     */
    var Type = {
        tableId: "typeTable"
    };

    /**
     * 初始化表格的列
     */
    Type.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'typeId', hide: true, title: ''},
            {field: 'name', sort: true, title: '分类名称'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Type.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(Type.tableId, {where: queryData});
    };

    /**
     * 弹出添加对话框
     */
    Type.openAddDlg = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加分类',
            content: Feng.ctxPath + '/type/add',
            end: function () {
                admin.getTempData('formOk') && table.reload(Type.tableId);
            }
        });
    };

    /**
     * 导出excel按钮
     */
    Type.exportExcel = function () {
        var checkRows = table.checkStatus(Type.tableId);
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
    Type.openEditDlg = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '修改分类',
            content: Feng.ctxPath + '/type/edit?typeId=' + data.typeId,
            end: function () {
                admin.getTempData('formOk') && table.reload(Type.tableId);
            }
        });
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    Type.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/type/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(Type.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("typeId", data.typeId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Type.tableId,
        url: Feng.ctxPath + '/type/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: Type.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Type.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Type.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        Type.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + Type.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Type.openEditDlg(data);
        } else if (layEvent === 'delete') {
            Type.onDeleteItem(data);
        }
    });
});

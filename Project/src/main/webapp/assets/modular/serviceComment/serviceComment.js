layui.use(['table', 'admin', 'ax'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;

    /**
     * 评论管理
     */
    var ServiceComment = {
        tableId: "serviceCommentTable"
    };

    /**
     * 初始化表格的列
     */
    ServiceComment.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'commentId', hide: true, title: '主键'},
            {field: 'content', sort: true, title: '内容'},
            {field: 'userId', sort: true, title: '评论人'},
            {field: 'serviceId', sort: true, title: '帖子或兼职'},
            {field: 'createTime', sort: true, title: '评论时间'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    ServiceComment.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(ServiceComment.tableId, {where: queryData});
    };

    /**
     * 弹出添加对话框
     */
    ServiceComment.openAddDlg = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加评论',
            content: Feng.ctxPath + '/serviceComment/add',
            end: function () {
                admin.getTempData('formOk') && table.reload(ServiceComment.tableId);
            }
        });
    };

    /**
     * 导出excel按钮
     */
    ServiceComment.exportExcel = function () {
        var checkRows = table.checkStatus(ServiceComment.tableId);
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
    ServiceComment.openEditDlg = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '修改评论',
            content: Feng.ctxPath + '/serviceComment/edit?commentId=' + data.commentId,
            end: function () {
                admin.getTempData('formOk') && table.reload(ServiceComment.tableId);
            }
        });
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    ServiceComment.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/serviceComment/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(ServiceComment.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("commentId", data.commentId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + ServiceComment.tableId,
        url: Feng.ctxPath + '/serviceComment/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: ServiceComment.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        ServiceComment.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        ServiceComment.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        ServiceComment.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + ServiceComment.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            ServiceComment.openEditDlg(data);
        } else if (layEvent === 'delete') {
            ServiceComment.onDeleteItem(data);
        }
    });
});

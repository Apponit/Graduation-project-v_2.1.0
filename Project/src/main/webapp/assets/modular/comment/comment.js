layui.use(['table', 'admin', 'ax'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;

    /**
     * 评论管理
     */
    var Comment = {
        tableId: "commentTable"
    };

    /**
     * 初始化表格的列
     */
    Comment.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'commentId', hide: true, title: '主键'},
            {field: 'content', sort: true, title: '评论内容'},
            {field: 'userName', sort: true, title: '评论人'},

            {field: 'createTime', sort: true, title: '评论时间'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Comment.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(Comment.tableId, {where: queryData});
    };

    /**
     * 弹出添加对话框
     */
    Comment.openAddDlg = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加评论',
            content: Feng.ctxPath + '/comment/add',
            end: function () {
                admin.getTempData('formOk') && table.reload(Comment.tableId);
            }
        });
    };

    /**
     * 导出excel按钮
     */
    Comment.exportExcel = function () {
        var checkRows = table.checkStatus(Comment.tableId);
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
    Comment.openEditDlg = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '修改评论',
            content: Feng.ctxPath + '/comment/edit?commentId=' + data.commentId,
            end: function () {
                admin.getTempData('formOk') && table.reload(Comment.tableId);
            }
        });
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    Comment.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/comment/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(Comment.tableId);
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
        elem: '#' + Comment.tableId,
        url: Feng.ctxPath + '/comment/list?forumId='+Feng.getUrlParam("forumId"),
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: Comment.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Comment.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Comment.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        Comment.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + Comment.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Comment.openEditDlg(data);
        } else if (layEvent === 'delete') {
            Comment.onDeleteItem(data);
        }
    });
});

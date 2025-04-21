layui.use(['table', 'admin', 'ax'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;

    /**
     * 留言管理管理
     */
    var Question = {
        tableId: "questionTable"
    };

    /**
     * 初始化表格的列
     */
    Question.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'questionId', hide: true, title: ''},
            {field: 'context', sort: true, title: '具体问题'},
            {field: 'status', sort: true, title: '状态', templet: function(d){              
                if(d.status == 0 ){
                	  return "未回复";
                  }else if(d.status == 1 ){
                	  return "已回复";
                  }
                    
                } },
            {field: 'userName', sort: true, title: '提问人'},
            {field: 'createTime', sort: true, title: '创建时间'},
          

            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Question.search = function () {
        var queryData = {};
        queryData['context'] = $("#condition").val();
        table.reload(Question.tableId, {where: queryData});
    };

    /**
     * 弹出添加对话框
     */
    Question.openAddDlg = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加留言管理',
            content: Feng.ctxPath + '/question/add',
            end: function () {
                admin.getTempData('formOk') && table.reload(Question.tableId);
            }
        });
    };

    /**
     * 导出excel按钮
     */
    Question.exportExcel = function () {
        var checkRows = table.checkStatus(Question.tableId);
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
    Question.openEditDlg = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '修改留言管理',
            content: Feng.ctxPath + '/question/edit?questionId=' + data.questionId,
            end: function () {
                admin.getTempData('formOk') && table.reload(Question.tableId);
            }
        });
    };

    
    
    Question.huifu = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '回复',
            area : [ '800px', '600px' ], // 宽高
            content: Feng.ctxPath + '/question/huifu?pid='+data.questionId,
            end: function () {
                admin.getTempData('formOk') && table.reload(Question.tableId);
            }
        });
    };
    
    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    Question.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/question/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(Question.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("questionId", data.questionId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Question.tableId,
        url: Feng.ctxPath + '/question/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: Question.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Question.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Question.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        Question.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + Question.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Question.openEditDlg(data);
        } else if (layEvent === 'delete') {
            Question.onDeleteItem(data);
        }else if (layEvent === 'huifu') {
            Question.huifu(data);
        }
    });
});

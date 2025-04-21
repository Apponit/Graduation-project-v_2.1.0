layui.use(['table', 'admin', 'ax'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;

    /**
     * 新闻管理
     */
    var News = {
        tableId: "newsTable"
    };

    /**
     * 初始化表格的列
     */
    News.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'newsId', hide: true, title: '主键'},
            {field: 'title', sort: true, title: '标题'},
            {field: 'pic',sort: true,title: '图片', templet: function(d){
                
                return "<img lay-event='img' src='"+d.pic+"' />";
                
                 
             } },
            {field: 'content', sort: true, title: '内容'},
            {field: 'type', sort: true, title: '类型'},
            {field: 'createTime', sort: true, title: '发布时间'},
            {field: 'introduce', sort: true, title: '简介'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    News.search = function () {
        var queryData = {};
        queryData['title'] = $("#condition").val();
        table.reload(News.tableId, {where: queryData});
    };

    /**
     * 弹出添加对话框
     */
    News.openAddDlg = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            area: ['80%', '80%'], //宽高
            title: '添加新闻',
            content: Feng.ctxPath + '/news/add',
            end: function () {
                admin.getTempData('formOk') && table.reload(News.tableId);
            }
        });
    };

    /**
     * 导出excel按钮
     */
    News.exportExcel = function () {
        var checkRows = table.checkStatus(News.tableId);
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
    News.openEditDlg = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            area: ['80%', '80%'], //宽高
            title: '修改新闻',
            content: Feng.ctxPath + '/news/edit?newsId=' + data.newsId,
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
    News.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/news/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(News.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("newsId", data.newsId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + News.tableId,
        url: Feng.ctxPath + '/news/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: News.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        News.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        News.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        News.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + News.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            News.openEditDlg(data);
        } else if (layEvent === 'delete') {
            News.onDeleteItem(data);
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

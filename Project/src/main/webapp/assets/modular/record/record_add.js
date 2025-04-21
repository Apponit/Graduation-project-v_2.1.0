/**
 * 详情对话框
 */
var RecordInfoDlg = {
    data: {
        userId: "",
        time: "",
        record: "",
        report: "",
        opeId: "",
        advise: ""
    }
};

layui.use(['form', 'admin', 'ax','laydate', 'upload'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var laydate = layui.laydate;
    var upload = layui.upload;
    //常规用法
    laydate.render({
      elem: '#time'
        ,format: 'yyyy-MM-dd HH:mm:ss'
    });
    getList();
    //让当前iframe弹层高度适应
    admin.iframeAuto();
    // 上传文件
	upload.render({
		elem : '#report',
		url : Feng.ctxPath + '/system/upload',
		accept : 'file',
		before : function(obj) {
			obj.preview(function(index, file, result) {
				
			
			});
		},
		done : function(res) {
			$("#report").val(res.data.serverPath);
			
			Feng.success(res.message);
		},
		error : function() {
			Feng.error("上传文件失败！");
		}
	});
    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/record/addItem", function (data) {
            Feng.success("添加成功！");

            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);

            //关掉对话框
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("添加失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();
    });
});
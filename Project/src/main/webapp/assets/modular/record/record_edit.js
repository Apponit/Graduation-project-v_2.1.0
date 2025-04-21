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
    //让当前iframe弹层高度适应
    admin.iframeAuto();

    //获取详情信息，填充表单
    var ajax = new $ax(Feng.ctxPath + "/record/detail?recordId=" + Feng.getUrlParam("recordId"));
    var result = ajax.start();
    form.val('recordForm', result.data);

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/record/editItem", function (data) {
            Feng.success("更新成功！");

            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);

            //关掉对话框
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("更新失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();
    });
});
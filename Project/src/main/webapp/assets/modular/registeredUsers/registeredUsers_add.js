/**
 * 详情对话框
 */
var RegisteredUsersInfoDlg = {
    data: {
        name: "",
        passWord: "",
        userName: "",
        pic: ""
    }
};

layui.use(['form', 'admin', 'ax','upload', 'layer'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var upload = layui.upload;
    var layer = layui.layer;

    //让当前iframe弹层高度适应
    admin.iframeAuto();

    // 执行实例
    upload.render({
        elem: '#pic', // 绑定触发上传的元素
        url: Feng.ctxPath + '/system/upload', // 上传接口地址，需要替换为实际的后端接口地址
        accept: 'images', // 允许上传的文件类型为图片
        before: function (obj) {
            // 预读本地文件示例，不支持ie8
            obj.preview(function (index, file, result) {
                // 可以在这里添加预览图片的逻辑
            });
        },
        done: function (res) {
            // 上传成功的回调
            $('#pic').val('');
            layer.msg('上传成功');
            $('#pic').val(res.data.serverPath); // 将上传成功后的图片 URL 赋值给输入框
        },
        error: function () {
            // 上传失败的回调
            layer.msg('上传出错，请重试');
        }
    });


    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/registeredUsers/addItem", function (data) {
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

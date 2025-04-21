/**
 * 详情对话框
 */
var ActivityInfoDlg = {
    data: {
        title: "",
        context: "",
        time: "",
        pic: "",
        userId: ""
    }
};

layui.use(['form', 'admin', 'ax','upload','laydate'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var laydate = layui.laydate;
    var upload = layui.upload;
    //让当前iframe弹层高度适应
    admin.iframeAuto();
    // laydate.render({
    //     elem: '#time' //指定元素
    //   ,format: 'yyyy-MM-dd' //可任意组合
    //     //,format: 'yyyy-MM-dd HH:mm:ss'
    //   });

    $(document).ready(function () {
        laydate.render({
            elem: '#time', // 指定元素
            format: 'yyyy-MM-dd HH:mm:ss' // 修改为包含时分秒的格式
        });
    });

    //普通图片上传
    upload.render({
        elem: '#picBtn'
        , url: Feng.ctxPath + '/system/upload'
        , before: function (obj) {
            obj.preview(function (index, file, result) {
                $('#img1').attr('src', result);
            });
        }
        , done: function (res) {
            $("#url").val(res.data.serverPath);
            Feng.success(res.message);
        }
        , error: function () {
            Feng.error("上传图片失败！");
        }
    });
    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/activity/addItem", function (data) {
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
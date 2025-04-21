/**
 * 详情对话框
 */
var NewsInfoDlg = {
    data: {
        title: "",
        content: "",
        createTime: "",
        author: ""
    }
};

layui.use(['form', 'admin', 'ax', 'upload'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var upload = layui.upload;
    //让当前iframe弹层高度适应
    admin.iframeAuto();
    getList();
    //获取详情信息，填充表单
    var ajax = new $ax(Feng.ctxPath + "/news/detail?newsId=" + Feng.getUrlParam("newsId"));
    var result = ajax.start();
    $("#url").val(result.data.pic);
	$('#img1').attr('src', result.data.pic);
	ue.ready(function() {
	    ue.setContent(unesc(result.data.content));
	},2);
    form.val('newsForm', result.data);

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
    	
    	

    	var  content =  UE.getEditor('editor').getContent();
        
    	console.log(content);
    	
    	if("" == content){
    		  Feng.error("请填写详细介绍")
    		  return false;
    	}

    	data.field.content=content;
    	
        var ajax = new $ax(Feng.ctxPath + "/news/editItem", function (data) {
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
});

function unesc(html) {
	if (html != undefined) {
	return html
	.replace(/& lt;/g, "<")
	.replace(/& gt;/g, ">")
	.replace(/& quot;/g, '"')
	.replace(/& #39;/g, "'");
	}
	}
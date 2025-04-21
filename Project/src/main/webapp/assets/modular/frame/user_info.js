layui.use([ 'form', 'upload', 'element', 'ax', 'laydate' ], function() {
	var $ = layui.jquery;
	var form = layui.form;
	var upload = layui.upload;
	var element = layui.element;
	var $ax = layui.ax;
	var laydate = layui.laydate;

	// 渲染时间选择框
	laydate.render({
		elem : '#birthday'
	});

	// 获取用户详情
	var ajax = new $ax(Feng.ctxPath + "/system/currentUserInfo");
	var result = ajax.start();

	// 用这个方法必须用在class有layui-form的元素上
	form.val('userInfoForm', result.data);

	// 表单提交事件
	form.on('submit(userInfoSubmit)', function(data) {
		var ajax = new $ax(Feng.ctxPath + "/mgr/edit", function(data) {
			Feng.success("修改成功!");
		}, function(data) {
			Feng.error("修改失败!" + data.responseJSON.message + "!");
		});
		ajax.set(data.field);
		ajax.start();
	});
	// 普通图片上传
	upload.render({
		elem : '#imgHead',
		url : Feng.ctxPath + '/system/upload',
		before : function(obj) {
			obj.preview(function(index, file, result) {
				$('#img1').attr('src', result);
			});
		},
		done : function(res) {

			$("#avatar").val(res.data.serverPath);
			Feng.success(res.message);
		},
		error : function() {
			Feng.error("上传图片失败！");
		}
	});
});
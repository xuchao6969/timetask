$(function () {
    $("#submitBut").click("click", function () {
        if(check()){
            var id=$("#id").val()
            if (id==""){
                $("#addForm").ajaxSubmit({
                    url: "/timetask/add",
                    // datatype: "json",
                    async: true,
                    success: function (data) {
                        debugger
                        if (data == "success") {
                            layer.msg("保存成功!");

                            CommnUtil.ajax("/timetask/list", {}, "json");
                        }else {
                            layer.msg("保存失败！");
                        }
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        layer.msg("保存失败！");
                        console.log("textStatus=" + textStatus);
                        console.log("jqXHR.status=" + jqXHR.status);
                        console.log("jqXHR.readyState=" + jqXHR.readyState);
                    }
                })
            } else {
                $("#addForm").ajaxSubmit({
                    url: "/timetask/update",
                    // datatype: "json",
                    async: true,
                    success: function (data) {
                        if (data == "success") {
                            layer.msg("保存成功!");

                            CommnUtil.ajax("/timetask/list", {}, "json");
                        }else {
                            layer.msg("保存失败！");
                        }
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        layer.msg("保存失败！");
                        console.log("textStatus=" + textStatus);
                        console.log("jqXHR.status=" + jqXHR.status);
                        console.log("jqXHR.readyState=" + jqXHR.readyState);
                    }
                })
            }

        }
    });

    $("#cancelBut").click("click", function () {		// 绑定取消按扭，返回列表页面
        CommnUtil.ajax("/timetask/list", {}, "json");
    });
});

function check() {
    // 校验
    var name = $("#name").val();
    if (name == "") {
        layer.msg("请输入任务名称");
        return false;
    }
    var group_name = $("#group_name").val();
    if (group_name == "") {
        layer.msg("请输入任务分组");
        return false;
    }
    var cron = $("#cron").val();
    if (cron == "") {
        layer.msg("请输入表达式");
        return false;
    }
    var bean_name = $("#bean_name").val();
    if (bean_name == "") {
        layer.msg("请输入类名");
        return false;
    }
    var method_name = $("#method_name").val();
    if (method_name == "") {
        layer.msg("请输入方法");
        return false;
    }
    return true;
}





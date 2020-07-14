$(function () {

    $("#addtask").click("click", function () {
        addBut();
    });

    $("#able").on("click",function () {
        enablBut('1',"运行");
    })
    $("#unable").on("click",function () {
        enablBut('0',"停止");
    })
    $('.trs').on('click' , function(){
        var check = $(this).find("input[type='checkbox']");
        if ($(check).is(':checked')) {
            $(check).attr('checked',false).siblings().attr('checked',false);
        } else {
            $(check).prop('checked', true);
            $('input[type=checkbox]').not($(check)).removeAttr('checked');

        }
    });
});


function enablBut(type,oper) {
    var selectIds;
    $("input.checkboxes[name='id']:checkbox").each(function() {
        if ($(this).is(":checked")) {
            if (selectIds != "" && typeof (selectIds) != "undefined") {
                selectIds += "," + $(this).val();
            } else {
                selectIds = $(this).val();
            }
            ableMany=true;
        }
    });
    if (selectIds == "" || typeof (selectIds) == "undefined") {
        layer.msg("请选择操作项！！");
        return;
    }
    layer.confirm('是否'+oper+'？', function () {
        var url = '/timetask/changeStatus';
        var s = CommnUtil.ajax(url, {
            id: selectIds,
            is_concurrent: type
        }, "");
        if (s == "success") {
            layer.msg(oper+'成功');
        } else {
            layer.msg(oper+'失败');
        }
    });
}


function addBut() {
    var addServPath ="/timetask/addUI";
    CommnUtil.ajax(addServPath, {}, "json");
}

function viewById(id,is_concurrent) {
    if(is_concurrent==0){
        var editServPath = "/timetask/addUI?id="+id;
        var path = "/timetask/addUI";
        CommnUtil.ajax(path, {id: id}, "json");
    }else {
        layer.msg("请停止运行此任务");
    }
}

function deleteById(id) {
    layer.confirm("确认要删除此定时吗？", { title: "确认删除" }, function (index) {
        layer.close(index);
        var url = '/timetask/deleteById';
        var s = CommnUtil.ajax(url, {
            id: id
        }, "");
        if (s == "success") {
            layer.msg('删除成功');
        } else {
            layer.msg('删除失败');
        }
    });
}
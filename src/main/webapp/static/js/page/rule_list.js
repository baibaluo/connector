$(function() {

    var url

    //提交按钮事件
    $('#btn_submit').click(function() {
        rule.fromPage()
        var exp = JSON.stringify(rule)
        var form = $('<form method="post"><input type="hidden" name="exp" value=""/></form>')
        form.attr('action', url)
        $('body').append(form)
        $('input[name="exp"]').val(exp)
        form[0].submit();
    })

    //取消按钮事件
    $('#btn_cancel').click(function() {
        $('#modify').hide('fast')
    })

    //添加规则按钮事件
    $('#btn_pre_add_rule').click(function() {
        $('#modify').hide('fast')
        rule.clearPage()
        url = '/rule/add.htm'
        $('#modify').show('fast')
    })

    //修改按钮事件
    $('td>div[btn="modify"]').click(
        function () {
            var id = $(this).attr('ruleId')
            $('#modify').hide('fast')
            url = '/rule/modify.htm?id=' + id

            rule.fromDbToPage(id, function(){
                $('#modify').show('fast')
            })
        })

    //删除按钮事件
    $('td>div[btn="delete"]').click(function() {
        var id = $(this).attr('ruleId')
        location.href = '/rule/delete.htm?id=' + id
    })

    //动作dom事件
    $('#tr_act_sample').find('select').change(function() {
        var propertyName = $(this).children('option:selected').val()
        var propertyType = properties[propertyName][1]
        if (propertyType == 'text') {
            if($(this).parent().next().find('select').length == 0){
                var actSelect = $('<select name="actType">')
                actSelect.append('<option value="1">前插</option>')
                actSelect.append('<option value="2">后缀</option>')
                actSelect.append('<option value="3">替换</option>')
                $(this).parent().next().append(actSelect)
                //动作选择事件
                actSelect.change(function() {
                    var actType = $(this).children(':selected').val()
                    if (actType == '3') {
                        var text2 = $('<input name="param2" type="text" class="min_text">')
                        $(this).parent().next().append(text2)
                    } else {
                        $(this).parent().next().children('[name="param2"]').remove()
                    }
                })
            }
        }
        else if (propertyType == 'number') {
            $(this).parent().next().empty()
            $(this).parent().next().next().children('[name="param2"]').remove()
        }
    })

    //条件dom事件
    $('#tr_cdt_sample').find('select').change(function() {
        var propertyName = $(this).children('option:selected').val()
        var propertyType = properties[propertyName][1]
        if (propertyType == 'text') {
            if($(this).parent().next().find('select').length == 0){
                var actSelect = $('<select name="actType">')
                actSelect.append('<option value="1">包含</option>')
                actSelect.append('<option value="2">以该内容开头</option>')
                actSelect.append('<option value="3">以该内容结尾</option>')
                $(this).parent().next().append(actSelect)
            }
        }
        else if (propertyType == 'number') {
            $(this).parent().next().empty()
        }
    })

    //添加动作按钮
    $('#btn_add_act').click(function() {
        rule.addAct()

    })

    //添加条件按钮
    $('#btn_add_cdt').click(function() {
        rule.addCdt()
    })
})



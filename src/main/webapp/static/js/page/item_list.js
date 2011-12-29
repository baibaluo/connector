var itemIdForSet
$(function() {
    //提交按钮事件
    $('#btn_submit').click(function() {
        var url = '/item/setItemRule.htm?itemId=' + itemIdForSet
        if($(':radio:checked').attr('value')){
            url += '&ruleId=' + $(':radio:checked').attr('value')
        }
        location.href = url
    })

    //取消按钮事件
    $('#btn_cancel').click(function() {
        $('#modify').hide('fast')
    })

    //规则设置按钮事件
    $('td>div[btn="setRule"]').click(
        function () {
            itemIdForSet = $(this).attr('itemId')
            $('#modify').hide('fast')
            $(':radio').attr('checked', '')

            $.get(
                '/item/getItemRule.htm?itemId=' + itemIdForSet,
                function(data){
                    $(':radio[value="' + data + '"]').attr('checked', 'checked')
                    $('#modify').show('fast')

            })
        })

    //同步设置按钮事件
    $('td>div[btn="setSyn"]').click(
        function () {
            itemIdForSet = $(this).attr('itemId')
            location.href = '/item/modifySyn.htm?id=' + $(this).attr('itemId')
        })

    //删除按钮事件
    $('td>div[btn="delete"]').click(function(){
        var id = $(this).attr('ruleId')
        location.href = '/rule/delete.htm?id=' + id
    })
})



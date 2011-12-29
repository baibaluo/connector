$(function(){
    common.initTableLineStyle();
})

var common = {
    //设置表格奇偶行数样式
    initTableLineStyle:function(){
        $('table > tbody').children().each(function(index){
            //偶数航替换成d1
            if(index % 2 == 0){
                $(this).removeClass('d0')
                $(this).addClass('d1')
            } else {
                $(this).removeClass('d1')
                $(this).addClass('d0')

            }
        })
    }
}
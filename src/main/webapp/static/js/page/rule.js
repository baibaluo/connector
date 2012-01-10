/*规则对象封装*/
var rule = new Rule()
function Rule() {
    /*规则名称*/
    this.name
    /*规则备注*/
    this.remark
    /*动作列表*/
    this.acts = []
    /*条件列表*/
    this.cdts = []


    this.addAct = function() {
        var newTr = $('#tr_act_sample').clone(true)
        newTr.removeAttr('id')
        $('#tr_act_sample').parent().append(newTr)
        //手动触发change事件
        newTr.find('select').change()
        newTr.show('fast')
        return newTr
    }

    this.addCdt = function() {
        var newTr = $('#tr_cdt_sample').clone(true)
        newTr.removeAttr('id')
        //radio name处理
        newTr.find('input:radio').attr('name', 'andOr_' + ($('#tr_cdt_sample~tr').length + 1))
        $('#tr_cdt_sample').parent().append(newTr)
        //手动触发change事件
        newTr.find('select').change()
        newTr.show('fast')
        return newTr
    }

    //从服务器端查询该规则信息，并把各项属性赋入此对象
    this.fromDB = function(id, callBack) {
        var rule = this
        $.getJSON('/rule/get.htm?id=' + id, function(data) {
            rule.name = data.name
            rule.remark = data.remark
            rule.acts = JSON.parse(data.acts)
            rule.cdts = JSON.parse(data.cdts)
            if ($.isFunction(callBack)) {
                callBack()
            }
        })

    }

    //从页面中取得规则信息，并把各项属性赋入此对象
    this.fromPage = function() {
        this.name = $('#ruleName').val()
        this.remark = $('#ruleRemark').val()
        //取得动作
        this.acts = []
        var acts = this.acts
        $('#tr_act_sample~tr').each(function() {
            var act = {}
            $(this).find('select, input').each(function() {
                var propertyName = $(this).attr('name')
                //_数字结尾的name，去掉_数字
                propertyName = propertyName.replace(/_\d$/, '')
                var value = $(this).val()
                act[propertyName] = value
            })

            acts[acts.length] = act
        })

        //取得条件
        this.cdts = []
        var cdts = this.cdts
        $('#tr_cdt_sample~tr').each(function() {
            var cdt = {}
            $(this).find('select, input').each(function() {
                //过滤未选中的radio
                if($(this).attr('type') == 'radio' && !$(this).attr('checked')){
                    return true
                }
                var propertyName = $(this).attr('name')
                //_数字结尾的name，去掉_数字
                propertyName = propertyName.replace(/_\d$/, '')
                var value = $(this).val()
                cdt[propertyName] = value
            })
            cdts[cdts.length] = cdt
        })
    }

    this.fromDbToPage = function(id, callBack) {
        this.aaa = 1
        this.fromDB(id, function() {
            if ($.isFunction(callBack)) {
                rule.toPage()
                callBack()
            }
        })
    }


    this.toPage = function() {
        //清除内容
        this.clearPage()

        $('#ruleName').val(rule.name)
        $('#ruleRemark').val(rule.remark)

        //遍历动作，初始化每一项动作
        for (i in this.acts) {
            var act = this.acts[i]
            var newTr = rule.addAct()
            newTr.find('select, input').each(function() {
                var propertyName = $(this).attr('name')
                //_数字结尾的name，去掉_数字
                propertyName = propertyName.replace(/_\d$/, '')
                if (act[propertyName]) {
                    $(this).val(act[propertyName])
                    //手动触发change
                    $(this).change()
                }
            })
            //给param2赋值
            newTr.find('input[name=param2]').val(act['param2'])
        }

        //遍历条件，初始化每一项条件
        for (i in this.cdts) {
            var cdt = this.cdts[i]
            var newTr = this.addCdt()
            //给select、input赋值
            newTr.find('select, input[type!="radio"]').each(function() {
                var propertyName = $(this).attr('name')
                if (cdt[propertyName]) {
                    $(this).val(cdt[propertyName])
                    //手动触发change
                    $(this).change()
                }
            })
            //给radio赋值
            newTr.find('input[type="radio"]').each(function() {
                var propertyName = $(this).attr('name')
                //_数字结尾的name，去掉_数字
                propertyName = propertyName.replace(/_\d$/, '')
                if (cdt[propertyName]) {
                    if ($(this).val() == cdt[propertyName]) {
                        $(this).attr('checked', 'checked')

                    }
                }
            })
        }
    }

    this.clearPage = function() {
        $('input[type="text"]').attr('value', '')
        $('#tr_act_sample~tr').remove()
        $('#tr_cdt_sample~tr').remove()
    }


}
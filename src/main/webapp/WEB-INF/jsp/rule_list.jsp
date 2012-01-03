<%@ page import="dianking.connector.constant.DictProperty" %>
<%
    request.setAttribute("propertiesMap", DictProperty.propertiesMap);
    request.setAttribute("properties", DictProperty.propertiesJson);
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>规则列表</title>
    <link media="screen" href="/static/css/base.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="/static/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="/static/js/common.js"></script>
    <script type="text/javascript" src="/static/js/page/rule_list.js"></script>
    <script type="text/javascript" src="/static/js/page/rule.js"></script>
    <script type="text/javascript">
        var properties = ${properties}
    </script>
</head>
<body>
<div id="minMax">
    <div id="header">
        <div id="headermenu">
            <ul>
                <li>
                    <a href="/item/list.htm">商品管理</a>
                </li>
                <li>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;</li>
                <li>
                    <a href="/rule/list.htm">规则管理</a>
                </li>
            </ul>
        </div>
    </div>
    <div id="searchWrapper"></div>
    <div id="searchResultPage">
        <div class="pageContent">
            <div id="list">
                <div id="btn_pre_add_rule" class="pagination btn">新建规则</div>
                <table class="resultTable">
                    <thead>
                    <tr>
                        <th>规则名</th>
                        <th>描述</th>
                        <th>创建时间</th>
                        <th>更新时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${ruleList}" var="rule">
                        <tr class="d0 tooltip">
                            <td>${rule.name}</td>
                            <td>${rule.remark}</td>
                            <td>${rule.create_time}</td>
                            <td>${rule.update_time}</td>
                            <td>
                                <div btn="modify" ruleId="${rule.id}"
                                     ruleExp="<c:out value='${rule.exp}' escapeXml="true"/>"
                                     ruleName='${rule.name}' ruleRemark='${rule.remark}'
                                     class="btn">修改
                                </div>
                                <div btn="delete" ruleId="${rule.id}" class="btn">删除</div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div id="modify">
                <div class="pagination">设置规则</div>
                <table class="resultTable">
                    <thead>
                    <tr>
                        <th>属性</th>
                        <th>值</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr class="tooltip">
                        <td>规则名称</td>
                        <td><input id="ruleName" type="text"></td>
                    </tr>
                    <tr class="tooltip">
                        <td>规则备注</td>
                        <td><input id="ruleRemark" type="text"></td>
                    </tr>
                    </tbody>
                </table>
                <div id="btn_add_act" class="btn pagination">添加规则动作</div>
                <table class="resultTable">
                    <thead>
                    <tr>
                        <th>属性名称</th>
                        <th>动作类型</th>
                        <th>参数</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr id="tr_act_sample" class="tooltip hidden">
                        <td>
                            <select name="propertyName">
                                <c:forEach items="${propertiesMap}" var="property">
                                    <option value="${property.key}">${property.value[0]}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td></td>
                        <td><input name="param1" type="text" class="min_text"></td>
                    </tr>
                    </tbody>
                </table>
                <div id="btn_add_cdt" class="btn pagination">请填写规则条件</div>
                <table class="resultTable">
                    <thead>
                    <tr>
                        <th>属性名称</th>
                        <th>条件类型</th>
                        <th>参数</th>
                        <th>与/或</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr id="tr_cdt_sample" class="tooltip hidden">
                        <td>
                            <select name="propertyName">
                                <c:forEach items="${propertiesMap}" var="property">
                                    <option value="${property.key}">${property.value[0]}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td></td>
                        <td><input name="param1" type="text"></td>
                        <td>
                            <input type="radio" name="and_or_sample" value="and"> 与
                            <input type="radio" name="and_or_sample" value="or"> 或
                        </td>
                    </tr>
                    </tbody>
                </table>
                <table class="resultTable">
                    <thead>
                    <tr>
                        <th><input id="btn_submit" type="button" value="提交"></th>
                        <th><input id="btn_cancel" type="button" value="取消"></th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>
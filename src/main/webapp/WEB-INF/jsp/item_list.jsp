<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>商品列表</title>
    <link media="screen" href="/static/css/base.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="/static/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="/static/js/common.js"></script>
    <script type="text/javascript" src="/static/js/page/item_list.js"></script>

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
                <div class="pagination">商品列表</div>
                <table class="resultTable">
                    <thead>
                    <tr>
                        <th>商品名称</th>
                        <th>价格</th>
                        <th>数量</th>
                        <th>上架时间</th>
                        <th class="w1">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${itemList}" var="item">
                        <tr class="d0 tooltip">
                            <td>${item.title}</td>
                            <td>${item.price}</td>
                            <td>${item.num}</td>
                            <td>${item.list_time}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${item.need_syn} == 1">
                                        <div btn="setSyn" itemId="${item.id}" class="btn">取消同步</div>
                                    </c:when>
                                    <c:otherwise>
                                        <div btn="setSyn" itemId="${item.id}" class="btn">恢复同步</div>
                                    </c:otherwise>
                                </c:choose>
                                <div btn="setRule" itemId="${item.id}" class="btn">设置规则</div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div id="modify">
                <div class="pagination">规则设定</div>
                <table class="resultTable">
                    <thead>
                    <tr>
                        <th>规则列表</th>
                    </tr>
                    </thead>
                    <form>
                        <tbody>
                        <c:forEach items="${ruleList}" var="rule">
                            <tr class="tooltip">
                                <td>
                                    <input name="ruleId" value="${rule.id}" type="radio">
                                        ${rule.name}
                                </td>
                            </tr>
                        </c:forEach>
                        <tr class="tooltip" column>
                            <td>
                                <input id="btn_submit" type="button" value="提交">
                                <input id="btn_cancel" type="button" value="取消">
                            </td>
                        </tr>
                        </tbody>
                    </form>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<script type="text/javascript">
    <%@ include file="/resources/javascript/reports.js" %>
</script>
<div class="span11" id="reports-container">
    <div id="placeholder" class="reportViewer"></div>
</div>
<div class="pagination pagination-centered">
    <ul>
        <li id="period_3" class="active">
            <a href="Javascript:EarningsReport.load(3)">3</a>
        </li>
        <li id="period_6">
            <a href="Javascript:EarningsReport.load(6)">6</a>
        </li>
        <li id="period_12">
            <a href="Javascript:EarningsReport.load(12)">12</a>
        </li>
    </ul>
</div>
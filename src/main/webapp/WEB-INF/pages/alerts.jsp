<div id="alert-warning" class="alert alert-warning" style="display: none; position: absolute">
    <button type="button" class="close" data-dismiss="alert">&times;</button>
    <span class="text"></span>
</div>
<div id="alert-error" class="alert alert-error" style="display: none; position: absolute">
    <button type="button" class="close" data-dismiss="alert">&times;</button>
    <span class="text"></span>
</div>
<div id="alert-success" class="alert alert-success" style="display: none; position: absolute">
    <button type="button" class="close" data-dismiss="alert">&times;</button>
    <span class="text"></span>
</div>
<div id="alert-info" class="alert alert-info" style="display: none; position: absolute">
    <button type="button" class="close" data-dismiss="alert">&times;</button>
    <span class="text"></span>
</div>
<div id="confirmation" class="alert alert-info" style="display: none; position: absolute">
    <button type="button" class="close" data-dismiss="alert">&times;</button>
    <h4><spring:message code="label.confirmation"/></h4>
    <span class="text"></span>

    <div style='margin-top:15px'>
        <div style='float: left'>
            <button class='btn btn-primary' id="yesButton"><spring:message code="label.confirmation.yes"/></button>
        </div>
        <div style='float: left; margin-left: 5px'>
            <button class='btn' id="noButton"><spring:message code="label.confirmation.no"/></button>
        </div>
    </div>
</div>

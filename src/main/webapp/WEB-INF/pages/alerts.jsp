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
<div id="confirmation" style="display: none" class="modal hide fade" tabindex="-1" role="dialog"
     aria-labelledby="confirmation-label" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
        <h4><spring:message code="label.confirmation"/></h4>
    </div>
    <div class="modal-body"></div>
    <div class="modal-footer">
        <button class='btn btn-primary' id="yesButton"><spring:message code="label.confirmation.yes"/></button>
        <button class='btn' id="noButton"><spring:message code="label.confirmation.no"/></button>
    </div>
</div>

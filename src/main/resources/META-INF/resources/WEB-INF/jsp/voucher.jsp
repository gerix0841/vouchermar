<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>
<div class="container">
    <h1>Enter Voucher Details</h1>

    <form:form method="post" modelAttribute="voucher">
        <fieldset class="mb-3">
            <form:label path="code">Code</form:label>
            <form:input type="text" path="code" required="required"/>
            <form:errors path="code" cssClass="text-warning"/>
        </fieldset>
        <fieldset class="mb-3">
            <form:label path="maxRedemptions">Maximum Redemptions</form:label>
            <form:input type="number" path="maxRedemptions" required="required"/>
            <form:errors path="maxRedemptions" cssClass="text-warning"/>
        </fieldset>
        <fieldset class="mb-3">
            <form:label path="expiryDate">Expiry Date</form:label>
            <form:input type="text" path="expiryDate" id="expiryDate"/>
            <form:errors path="expiryDate" cssClass="text-warning"/>
        </fieldset>
        <form:input type="hidden" path="id"/>
        <form:input type="hidden" path="active"/>

        <div style="color: red;" >
            <c:if test="${not empty error}">
                <p>${error}</p>
            </c:if>
        </div>

        <input type="submit" value="Send" class="btn btn-success"/>
    </form:form>
</div>
<%@ include file="common/footer.jspf" %>
<script type="text/javascript">
	$('#expiryDate').datepicker({
	    format: 'yyyy-mm-dd'
	});
</script>
<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>
<div class="container">
    <h1>Redeem Voucher</h1>
    <form action="${pageContext.request.contextPath}/redeem" method="post">
        <div class="form-group">
            <label for="code">Voucher Code: </label>
            <input type="text" id="code" name="code" class="form-control" required>
        </div>
        <button type="submit" class="btn btn-primary mt-2">Redeem</button>
    </form>
</div>
<%@ include file="common/footer.jspf" %>
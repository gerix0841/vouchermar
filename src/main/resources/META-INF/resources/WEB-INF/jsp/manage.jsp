<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>
<div class="container">
    <h1>Voucher List</h1>
    <table class="table">
        <thead>
            <tr>
                <th>ID</th>
                <th>Code</th>
                <th>MaxRedemptions</th>
                <th>CurrentRedemptions</th>
                <th>ExpiryDate</th>
                <th>Active</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${vouchers}" var="voucher">
                <tr>
                    <td>${voucher.id}</td>
                    <td>${voucher.code}</td>
                    <td>${voucher.maxRedemptions}</td>
                    <td>${voucher.currentRedemptions}</td>
                    <td>${voucher.expiryDate}</td>
                    <td>${voucher.active}</td>
                    <td><a href="update-voucher?id=${voucher.id}" class="btn btn-success">Update</a></td>
                    <td><a href="delete-voucher?id=${voucher.id}" class="btn btn-danger">Delete</a></td>
                    <td><a href="toggle-voucher?id=${voucher.id}" class="btn btn-warning">Toggle</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <a href="add-voucher" class="btn btn-success">Add Voucher</a>
</div>
<%@ include file="common/footer.jspf" %>
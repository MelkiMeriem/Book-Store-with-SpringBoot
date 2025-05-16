<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
    <title>Shopping Cart - Book Store</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="fragments/header.jsp" />
    
    <div class="container mt-4">
        <h2>Shopping Cart</h2>
        
        <c:if test="${empty cartItems}">
            <div class="alert alert-info">
                Your cart is empty. <a href="/books">Continue shopping</a>
            </div>
        </c:if>
        
        <c:if test="${not empty cartItems}">
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Book</th>
                            <th>Author</th>
                            <th>Price</th>
                            <th>Quantity</th>
                            <th>Total</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${cartItems}" var="item">
                            <tr>
                                <td>${item.book.title}</td>
                                <td>${item.book.author}</td>
                                <td>$<fmt:formatNumber value="${item.book.price}" pattern="#,##0.00"/></td>
                                <td>
                                    <form action="/cart/update" method="POST" style="display: inline;">
                                        <input type="hidden" name="bookId" value="${item.book.id}"/>
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <input type="number" name="quantity" value="${item.quantity}" min="1" max="${item.book.stock}" 
                                               class="form-control" style="width: 80px" onchange="this.form.submit()"/>
                                    </form>
                                </td>
                                <td>$<fmt:formatNumber value="${item.book.price * item.quantity}" pattern="#,##0.00"/></td>
                                <td>
                                    <form action="/cart/remove" method="POST" style="display: inline;">
                                        <input type="hidden" name="bookId" value="${item.book.id}"/>
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <button type="submit" class="btn btn-danger btn-sm">Remove</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                    <tfoot>
                        <tr>
                            <td colspan="4" class="text-end"><strong>Total:</strong></td>
                            <td>$<fmt:formatNumber value="${totalAmount}" pattern="#,##0.00"/></td>
                            <td></td>
                        </tr>
                    </tfoot>
                </table>
            </div>
            
            <div class="d-flex justify-content-between mt-4">
                <a href="/books" class="btn btn-secondary">Continue Shopping</a>
                <form action="/orders/create" method="POST">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <button type="submit" class="btn btn-primary">Checkout</button>
                </form>
            </div>
        </c:if>
    </div>
    
    <jsp:include page="fragments/footer.jsp" />
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 
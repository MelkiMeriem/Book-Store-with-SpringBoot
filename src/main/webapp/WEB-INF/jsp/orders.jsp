<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
    <title>My Orders - Book Store</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="fragments/header.jsp" />
    
    <div class="container mt-4">
        <h2>My Orders</h2>
        
        <c:if test="${empty orders}">
            <div class="alert alert-info">
                You haven't placed any orders yet. <a href="/books">Start shopping</a>
            </div>
        </c:if>
        
        <c:if test="${not empty orders}">
            <div class="accordion" id="ordersAccordion">
                <c:forEach items="${orders}" var="order" varStatus="status">
                    <div class="accordion-item">
                        <h2 class="accordion-header" id="heading${order.id}">
                            <button class="accordion-button ${status.first ? '' : 'collapsed'}" type="button" 
                                    data-bs-toggle="collapse" data-bs-target="#collapse${order.id}">
                                Order #${order.id} - 
                                ${order.orderDate.format(java.time.format.DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm"))} - 
                                Status: ${order.status}
                            </button>
                        </h2>
                        <div id="collapse${order.id}" class="accordion-collapse collapse ${status.first ? 'show' : ''}" 
                             data-bs-parent="#ordersAccordion">
                            <div class="accordion-body">
                                <div class="table-responsive">
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th>Book</th>
                                                <th>Author</th>
                                                <th>Price</th>
                                                <th>Quantity</th>
                                                <th>Total</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${order.orderItems}" var="item">
                                                <tr>
                                                    <td>${item.book.title}</td>
                                                    <td>${item.book.author}</td>
                                                    <td>$<fmt:formatNumber value="${item.price}" pattern="#,##0.00"/></td>
                                                    <td>${item.quantity}</td>
                                                    <td>$<fmt:formatNumber value="${item.price * item.quantity}" pattern="#,##0.00"/></td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <td colspan="4" class="text-end"><strong>Total Amount:</strong></td>
                                                <td>$<fmt:formatNumber value="${order.totalAmount}" pattern="#,##0.00"/></td>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:if>
    </div>
    
    <jsp:include page="fragments/footer.jsp" />
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 
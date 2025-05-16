<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
    <title>Book Catalog - Book Store</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="fragments/header.jsp" />
    
    <div class="container mt-4">
        <h2>Book Catalog</h2>
        
        <sec:authorize access="hasRole('ADMIN')">
            <div class="mb-4">
                <a href="/books/add" class="btn btn-primary">Add New Book</a>
            </div>
        </sec:authorize>
        
        <div class="row">
            <c:forEach items="${books}" var="book">
                <div class="col-md-4 mb-4">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">${book.title}</h5>
                            <h6 class="card-subtitle mb-2 text-muted">${book.author}</h6>
                            <p class="card-text">${book.description}</p>
                            <p class="card-text">
                                <strong>Price:</strong> $<fmt:formatNumber value="${book.price}" pattern="#,##0.00"/>
                            </p>
                            <p class="card-text">
                                <strong>In Stock:</strong> ${book.stock}
                            </p>
                            
                            <div class="d-flex justify-content-between align-items-center">
                                <form action="/cart/add" method="POST" style="display: inline;">
                                    <input type="hidden" name="bookId" value="${book.id}"/>
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <button type="submit" class="btn btn-success" ${book.stock <= 0 ? 'disabled' : ''}>
                                        Add to Cart
                                    </button>
                                </form>
                                
                                <sec:authorize access="hasRole('ADMIN')">
                                    <div>
                                        <a href="/books/edit/${book.id}" class="btn btn-warning">Edit</a>
                                        <form action="/books/delete/${book.id}" method="POST" style="display: inline;">
                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                            <button type="submit" class="btn btn-danger" onclick="return confirm('Are you sure you want to delete this book?')">
                                                Delete
                                            </button>
                                        </form>
                                    </div>
                                </sec:authorize>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    
    <jsp:include page="fragments/footer.jsp" />
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 
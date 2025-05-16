<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>${param.title} - Online Bookstore</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="/">Bookstore</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="/books">Books</a>
                    </li>
                    <sec:authorize access="isAuthenticated()">
                        <li class="nav-item">
                            <a class="nav-link" href="/cart">Cart</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/orders">Orders</a>
                        </li>
                    </sec:authorize>
                </ul>
                <ul class="navbar-nav">
                    <sec:authorize access="!isAuthenticated()">
                        <li class="nav-item">
                            <a class="nav-link" href="/login">Login</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/register">Register</a>
                        </li>
                    </sec:authorize>
                    <sec:authorize access="isAuthenticated()">
                        <li class="nav-item">
                            <form action="/logout" method="post" class="d-inline">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <button type="submit" class="btn btn-link nav-link">Logout</button>
                            </form>
                        </li>
                    </sec:authorize>
                </ul>
            </div>
        </div>
    </nav>
    <div class="container mt-4"> 
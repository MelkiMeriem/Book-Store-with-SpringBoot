<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="fragments/header.jsp">
    <jsp:param name="title" value="Home"/>
</jsp:include>

<div class="jumbotron">
    <h1 class="display-4">Welcome to Our Bookstore</h1>
    <p class="lead">Discover our collection of amazing books.</p>
    <hr class="my-4">
    <p>Browse through our catalog and find your next favorite book.</p>
    <a class="btn btn-primary btn-lg" href="/books" role="button">Browse Books</a>
</div>

<jsp:include page="fragments/footer.jsp"/> 
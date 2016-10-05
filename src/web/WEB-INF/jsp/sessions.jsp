<%@ taglib uri="jstl-core" prefix="c" %>
<%@ taglib uri="jstl-format" prefix="fmt" %>

<h2>Sessions</h2>

<c:forEach var="session" items="${sessions}">
  <b><c:out value="${session.title}"/></b><br>
  <b>Presenter:</b> <a href="sessionsByPresenter.do?presenter=<c:out value="${session.presenterId}"/>"><c:out value="${session.presenter}"/></a><br>
  <b>Day:</b> <fmt:formatDate value="${session.dtBegin}" type="date" dateStyle="full"/><br>
  <b>Time:</b> <fmt:formatDate value="${session.dtBegin}" pattern="hh:mm a"/> 
             - <fmt:formatDate value="${session.dtEnd}" pattern="hh:mm a"/><br>
  <b>Location:</b> <c:out value="${session.where}"/><br>
  <b>Track:</b> <c:out value="${session.topic}"/><br>
  <p>
</c:forEach>

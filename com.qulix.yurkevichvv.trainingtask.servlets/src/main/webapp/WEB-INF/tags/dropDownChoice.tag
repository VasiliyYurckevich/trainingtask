<%@ tag pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="label" type="java.lang.String" required="true" %>
<%@ attribute name="id" type="java.lang.String" required="true" %>
<%@ attribute name="list" type="java.util.List<com.qulix.yurkevichvv.trainingtask.servlets.dropdown.DropDownListItem>"
	required="true" %>
<%@ attribute name="selectedId" type="java.lang.Integer" required="false" %>
<%@ attribute name="isNullOption" type="java.lang.Boolean" required="false" %>
<%@ attribute name="isDisabled" type="java.lang.Boolean" required="false" %>


<div class="field">
	<label>${label}:</label>
	<label>${selectedId}</label>>
	<select ${isDisabled ? "disabled = 'disabled'" : ""} name="${id}">
		${isNullOption ? '<option value=""> </option>' : ''}
		<c:forEach items="${list}" var="item">
			<option value="${item.id}" ${item.id == selectedId ? 'selected="selected"' : ''}>
					${fn:escapeXml(item.value)}
			</option>
		</c:forEach>
	</select>
</div>

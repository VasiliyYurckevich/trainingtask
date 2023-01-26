<%@ tag pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="label" type="java.lang.String" required="true" %>
<%@ attribute name="name" type="java.lang.String" required="true" %>
<%@ attribute name="list" type="java.util.List<com.qulix.yurkevichvv.trainingtask.servlets.view_items.dropdown.DropDownListItem>"
	required="true" %>
<%@ attribute name="selectedId" type="java.lang.Integer" required="false" %>
<%@ attribute name="isNullOption" type="java.lang.Boolean" required="false" %>
<%@ attribute name="isDisabled" type="java.lang.Boolean" required="false" %>
<%@ attribute name="basicOption" type="java.lang.String" required="false" %>
<%@ attribute name="updatedTitle" type="java.lang.String" required="false"%>


<div class="field">
	<label>${label}:</label>
	<select ${isDisabled ? "disabled = 'disabled'" : ""} name="${name}">
		${isNullOption ? '<option value="">'.concat(basicOption).concat('</option>') : ''}
		<c:forEach items="${list}" var="item">
			<option value="${item.id}" ${item.id == selectedId ? 'selected="selected"' : ''}>
					${fn:escapeXml(item.value)}
			</option>
		</c:forEach>
	</select>
</div>

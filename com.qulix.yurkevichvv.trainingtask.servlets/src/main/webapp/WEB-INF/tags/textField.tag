<%@ tag pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ attribute name="label" required="true"%>
<%@ attribute name="id" required="true"%>
<%@ attribute name="value" required="true"%>

<div class="field">
	<label>${label} :</label>
	<input id="${id}" name="${id}" value="${fn:escapeXml(value)}"/>
	<br>
	<ul class="feedbackPanel">
		<li>
			<span class="feedback">${requestScope.ERRORS.get(id)}</span>
		</li>
	</ul>
</div>
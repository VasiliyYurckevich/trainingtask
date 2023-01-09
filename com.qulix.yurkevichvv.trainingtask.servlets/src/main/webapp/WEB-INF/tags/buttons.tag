<%@ tag pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ attribute name="saveAction" type="java.lang.String" required="true" %>
<%@ attribute name="cancelAction" type="java.lang.String" required="true" %>

<div>
  <button formmethod="post" id="submitButton" name="submitButton"
          onclick="action.value='${saveAction}'" type="submit" class="add-button">
    Сохранить
  </button>
  <button formmethod="get" id="cancelButton" name="cancelButton"
          onclick="action.value='${cancelAction}'" type="submit" class="add-button">
    Отмена
  </button>
</div>
<%@ tag pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div>
  <button formmethod="post" id="submitButton" name="submitButton"
          onclick="action.value='/save'" type="submit" class="add-button">
    Сохранить
  </button>
  <button formmethod="get" id="cancelButton" name="cancelButton"
          onclick="action.value='/list'" type="submit" class="add-button">
    Отмена
  </button>
</div>
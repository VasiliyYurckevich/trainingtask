<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="ru">
    <head>
        <title>Редактировать задачу</title>

        <link type="text/css" rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <ul>
            <li style="font-family: Arial"><a href="projects">Проекты</a></li>
            <li style="font-family: Arial"><a class="choose"  href="task">Задачи</a></li>
            <li style="font-family: Arial"><a href="employees">Сотрудники</a></li>
        </ul>

        <div style="padding:20px; margin-top:50px;height:600px;">
            <div id="container">
                <h3>Редактировать задачу</h3>
                <form action="task" onsubmit="check(event)" method="post" id ="form">
                    <input type="hidden" name="action"  value="/update" />
                    <input type="hidden" name="taskId" value="${taskId}" />

                    <table>
                        <tbody>
                        <input class="add-button" type="submit" name="submitButton" id="submitButton" value="Сохранить"><button  id="cancelButton" name="cancelButton" onclick="window.history.back()" type="button" class="add-button">Отмена</button>
                        <tr>
                            <td><label>Статус:</label></td>
                            <td>
                                <select type="text" name="status" data-selected="${status}">
                                    <option ${status == "Не начата"  ? 'selected="selected"' : ''}>Не начата</option>
                                    <option ${status == "В процессе"  ? 'selected="selected"' : ''}>В процессе</option>
                                    <option ${status == "Завершена"  ? 'selected="selected"' : ''}>Завершена</option>
                                    <option ${status == "Отложена"  ? 'selected="selected"' : ''}>Отложена</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td><label>Наименование:</label></td>
                            <td><input type="text" maxlength="50" required="required" id="title" name="title" oninput="checkLength('title',50)" value="${fn:escapeXml(title)}"></td>
                        </tr>
                        <tr>
                            <td><label>Работа:</label></td>
                            <td><input required ="required" type="number"  oninput="this.setCustomValidity('')" max="999999999999999999" id="workTime"   oninvalid="this.setCustomValidity('Введите число со значением меньше или равным 999999999999999999 и не содержащим символов &quot e &quot ; &quot + &quot ; &quot , &quot ; &quot . &quot ; &quot - &quot')"  name="workTime" pattern="^[0-9]+$"
                                       value="${workTime}"></td>
                        </tr>
                        <tr>
                            <td><label>Дата начала(ГГГГ-ММ-ДД):</label></td>
                            <td><input required ="required" type="text" pattern="^((((19[0-9][0-9])|(2[0-9][0-9][0-9]))([-])(0[13578]|10|12)([-])(0[1-9]|[12][0-9]|3[01]))|(((19[0-9][0-9])|(2[0-9][0-9][0-9]))([-])(0[469]|11)([-])([0][1-9]|[12][0-9]|30))|(((19[0-9][0-9])|(2[0-9][0-9][0-9]))([-])(02)([-])(0[1-9]|1[0-9]|2[0-8]))|(([02468][048]00)([-])(02)([-])(29))|(([13579][26]00)([-])(02)([-])(29))|(([0-9][0-9][0][48])([-])(02)([-])(29))|(([0-9][0-9][2468][048])([-])(02)([-])(29))|(([0-9][0-9][13579][26])([-])(02)([-])(29)))$"
                                       oninput="this.setCustomValidity('')" oninvalid="this.setCustomValidity('Введите дату в формате ГГГГ-ММ-ДД в диапазоне между 1900-01-01 и 2999-12-31.')" id="beginDate" name="beginDate" value="${beginDate}"></td>
                        </tr>
                        <tr>
                            <td><label>Дата окончания(ГГГГ-ММ-ДД):</label></td>
                            <td><input required ="required" type="text" pattern="^((((19[0-9][0-9])|(2[0-9][0-9][0-9]))([-])(0[13578]|10|12)([-])(0[1-9]|[12][0-9]|3[01]))|(((19[0-9][0-9])|(2[0-9][0-9][0-9]))([-])(0[469]|11)([-])([0][1-9]|[12][0-9]|30))|(((19[0-9][0-9])|(2[0-9][0-9][0-9]))([-])(02)([-])(0[1-9]|1[0-9]|2[0-8]))|(([02468][048]00)([-])(02)([-])(29))|(([13579][26]00)([-])(02)([-])(29))|(([0-9][0-9][0][48])([-])(02)([-])(29))|(([0-9][0-9][2468][048])([-])(02)([-])(29))|(([0-9][0-9][13579][26])([-])(02)([-])(29)))$"
                                       oninput="this.setCustomValidity('')" oninvalid="this.setCustomValidity('Введите дату в формате ГГГГ-ММ-ДД в диапазоне между 1900-01-01 и 2999-12-31.')" id="endDate" name="endDate" value="${endDate}"></td>
                        </tr>

                        <tr>
                            <td><label>Наименование проекта:</label></td>
                            <td> <select name="projectId" required ="required">
                                <c:forEach items="${PROJECT_LIST}" var="projects">
                                    <option value="${projects.id}" ${projects.id == projectId ? 'selected="selected"' : ''}>${fn:escapeXml(projects.title)}</option>
                                </c:forEach>
                            </select>
                        </tr>
                        <tr>
                            <td><label>Сотрудник:</label></td>
                            <td> <select name="employeeId" >
                                <option value="null">  </option>
                                <c:forEach items="${EMPLOYEE_LIST}" var="employees">
                                    <option value="${employees.id}" ${employees.id == employeeId ? 'selected="selected"' : ''}>${fn:escapeXml(employees.surname)} ${fn:escapeXml(employees.firstName)} ${fn:escapeXml(employees.patronymic)}</option>
                                </c:forEach>
                            </select>
                            </td>
                        </tr>
                        </tbody>
                        <br/><br/>
                        </table>
                </form>
            </div>
        </div>

        <script type='text/javascript'>

            function check(event) {
                const beginDt = document.getElementById("beginDate").value.replace('.','-');
                const endDt = document.getElementById("endDate").value.replace('.','-');
                const title = document.getElementById("title").value;

                if( (new Date(endDt).getTime() < new Date(beginDt).getTime()))
                {
                    event.preventDefault();
                    alert("Дата начала работы не может быть позже даты окончания работы");
                }else if(title.trim() == ''){
                    event.preventDefault();
                    alert("Наиминование не может состоять только из пробелов");
                }else {
                    this.submitButton.disabled = true;
                    this.cancelButton.disabled = true;

                }
            }

            function checkLength(fieldName,maxLength) {
                const len = document.getElementById(fieldName).value.length;

                if( len == maxLength){
                    alert("Достигнута допустимая длина поля: " + maxLength + " символов");
                }
            }
            const inputBox = document.getElementById("workTime");
            var invalidChars = [
                "-",
                "+",
                "e",
                "E",
                ",",
                ".",
            ];

            inputBox.addEventListener("input", function() {
                this.value = this.value.replace(/[e\+\-]/gi, "");
            });

            inputBox.addEventListener("keydown", function(e) {
                if (invalidChars.includes(e.key)) {
                    e.preventDefault();
                }
            })
        </script>
    </body>
</html>

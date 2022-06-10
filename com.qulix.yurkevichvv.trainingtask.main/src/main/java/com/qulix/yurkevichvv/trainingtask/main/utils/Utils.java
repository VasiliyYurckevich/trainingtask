package com.qulix.yurkevichvv.trainingtask.main.utils;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.qulix.yurkevichvv.trainingtask.main.dao.EmployeeDAO;
import com.qulix.yurkevichvv.trainingtask.main.dao.ProjectDao;
import com.qulix.yurkevichvv.trainingtask.main.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.main.entity.Project;
import com.qulix.yurkevichvv.trainingtask.main.entity.Task;
import com.qulix.yurkevichvv.trainingtask.main.exceptions.DaoException;


/**
 * Утилиты для классов модуля.
 *
 * @author Q-YVV
 */
public class Utils {

    /**
     *Проверяет пустой или содержащий только пустые строки список.
     *
     * @param list Список для проверки.
     * @return Возвращает true, если список пустой или содержащий только пустые строки.
     */
    public static boolean isBlankList(List<String> list) {
        int i = 0;
        for (String s : list) {
            if (s == null || s.trim().isEmpty()) {
                i++;
            }
        }
        return i == list.size();
    }

    /**
     * Переводит String в Integer, проверяя на null.
     *
     * @param s строка для конвертации.
     * @return конвертированное значение.
     */
    public static Integer stringToInteger(String s) {
        try {
            Integer.valueOf(s);
        }
        catch (NumberFormatException e) {
            return null;
        }
        return Integer.parseInt(s);
    }

    /**
     * Создание и обнавление данных для выпадающих списков.
     *
     * @param req запрос.
     * @throws SQLException ошибка при выполнении запроса.
     */
    public static void setDataOfDropDownList(HttpServletRequest req) throws DaoException {
        List<Employee> employees = new EmployeeDAO().getAll();
        List<Project> projects = new ProjectDao().getAll();
        req.getServletContext().setAttribute("EMPLOYEE_LIST", employees);
        req.getServletContext().setAttribute("PROJECT_LIST", projects);
    }

    /**
     * Заносит начальные данные в поля формы для Задачи.
     *
     * @param req запрос.
     * @param existingTask задача.
     */
    public static void setTaskDataInJsp(HttpServletRequest req, Task existingTask) {
        req.setAttribute("taskId", existingTask.getId());
        req.setAttribute("status", existingTask.getStatus());
        req.setAttribute("title", existingTask.getTitle());
        req.setAttribute("workTime", existingTask.getWorkTime());
        req.setAttribute("beginDate", existingTask.getBeginDate());
        req.setAttribute("endDate", existingTask.getEndDate());
        req.setAttribute("employeeId", existingTask.getEmployeeId());
    }
}

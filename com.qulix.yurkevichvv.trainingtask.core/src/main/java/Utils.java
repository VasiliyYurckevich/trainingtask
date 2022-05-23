import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Утилиты для классов модуля.
 *
 * @author Q-YVV
 */
public class Utils {

    /**
     *Проверяет пустой или содержащий только пустые строки список.
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
     */
    public static Integer stringToInteger(String s) {
        try {
            Integer.valueOf(s);
        }
        catch (NumberFormatException e) {
            return null;
        }
        return Integer.valueOf(s);
    }

    /**
     * Создание и обнавление данных для выпадающих списков.
     */
    public static void setDataOfDropDownList(HttpServletRequest req) throws SQLException {
        List<Employee> employees = new DAOEmployee().getAll();
        List<Project> projects = new DAOProject().getAll();
        req.getServletContext().setAttribute("EMPLOYEE_LIST", employees);
        req.getServletContext().setAttribute("PROJECT_LIST", projects);
    }
}

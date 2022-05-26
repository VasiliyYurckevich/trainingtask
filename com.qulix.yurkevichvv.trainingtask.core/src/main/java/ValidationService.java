import java.util.ArrayList;
import java.util.List;

/**
 * Сервис для валидации данных на соотвественных страницах.
 *
 * @author Q-YVV
 */
public class ValidationService {

    private static final int SHORT_LENGTH = 50;
    private static final int LONG_LENGTH = 250;

    /**
     * Валидация для вводимых данных о сотруднике.
     */
    public static List<String> employeeValidator(List<String> paramsList) {
        List<String> errorList = new ArrayList<>(Nums.FOUR.getValue());
        errorList.add(FieldsValidation.isStringValidation(paramsList.get(Nums.ZERO.getValue()), SHORT_LENGTH));
        errorList.add(FieldsValidation.isStringValidation(paramsList.get(Nums.ONE.getValue()), SHORT_LENGTH));
        errorList.add(FieldsValidation.isStringValidation(paramsList.get(Nums.TWO.getValue()), SHORT_LENGTH));
        errorList.add(FieldsValidation.isStringValidation(paramsList.get(Nums.THREE.getValue()), SHORT_LENGTH));
        return errorList;
    }

    /**
     *Валидация для вводимых данных о проекте.
     */
    public static List<String> projectValidator(List<String> paramsList) {
        List<String> errorList = new ArrayList<>(Nums.TWO.getValue());
        errorList.add(FieldsValidation.isStringValidation(paramsList.get(Nums.ZERO.getValue()), SHORT_LENGTH));
        errorList.add(FieldsValidation.isStringValidation(paramsList.get(Nums.ONE.getValue()), LONG_LENGTH));
        return errorList;
    }

    /**
     * Валидация для вводимых данных о задаче.
     */
    public static List<String> taskValidator(List<String> paramsList) {
        List<String> errorList = new ArrayList<>(Nums.FIVE.getValue());
        errorList.add(FieldsValidation.isStringValidation(paramsList.get(Nums.ZERO.getValue()), SHORT_LENGTH));
        errorList.add(FieldsValidation.isStringValidation(paramsList.get(Nums.ONE.getValue()), SHORT_LENGTH));
        errorList.add(FieldsValidation.isNumberValidation(paramsList.get(Nums.TWO.getValue())));
        List<String> dateErrorsList = FieldsValidation.isDateValidation(paramsList.get(Nums.THREE.getValue()),
            paramsList.get(Nums.FOUR.getValue()));
        errorList.addAll(dateErrorsList);
        return errorList;
    }

}

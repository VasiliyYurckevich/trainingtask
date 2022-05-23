public class FieldsValidation {


    public static String charStringValidation(String s, int length) {
        StringBuffer error = new StringBuffer();
        if (s.trim().isEmpty()){
            error.append("Строка не должна быть пустой");
        } else if (s.trim().length() > length) {
            error.append("Длинна строки не должна быть больше 50 символов");
        }
        return String.valueOf(error);
    }

    public static String numberStringValidation(String s, int length) {
        StringBuffer error = new StringBuffer();
        if (s.trim().isEmpty()){
            error.append("Строка не должна быть пустой");
        } else if (s.trim().length() > length) {
            error.append("Длинна строки не должна быть больше 50 символов");
        } else {
            try {
                int i = Integer.parseInt(s);
            } catch (NumberFormatException e){
                error.append("В данное поле допустим ввод только чисел");
            }
        }
        return String.valueOf(error);
    }



    public static void main(String[] args){
        String s = "-1";
        String err = charStringValidation(s,20);
        String err1 = numberStringValidation(s,20);

        System.out.println(err);
        System.out.println(err1);

    }
}
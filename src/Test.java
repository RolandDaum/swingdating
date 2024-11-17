import java.time.LocalDate;
import java.util.UUID;

public class Test {
    public static void main(String[] args) {
        // int startYear = 2024;
        // int endYear = 1900;
        // System.out.println("public enum YearSelection {");
        // for (int year = startYear; year >= endYear; year--) {
        //     System.out.print(" YEAR_" + year + "(" + '"' + year + '"' + ", " + year + ")");
        //     if (year != endYear) {
        //         System.out.print(",");
        //     } else {
        //         System.out.println(";");
        //     }
        // }
        // System.out.println("}");
    
        // LocalDate date = LocalDate.of(2006, 5, 9);
        // System.out.println(date);

        // LocalDate date2 = LocalDate.parse("2006-05-09");
        // System.out.println(date2);
        // boolean valid = true;
        // try {
        //     Integer.parseInt("123");
        // } catch (Exception e) {
        //     valid = false;
        // }
        // System.out.println(valid);
        // System.out.println(String.valueOf(1234)+"a".toUpperCase());
        // System.out.println(Boolean.logicalAnd(true, true));
        // System.out.println(Boolean.logicalAnd(false, false));
        // System.out.println(Boolean.logicalAnd(true, false));
        // System.out.println(Boolean.logicalAnd(false, true));
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);
        System.out.println(uuid.toString());


    }
}

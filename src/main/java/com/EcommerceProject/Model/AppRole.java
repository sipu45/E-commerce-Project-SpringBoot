package com.EcommerceProject.Model;

public enum AppRole {
    ROLE_USER ,
    ROLE_SELLER ,
    ROLE_ADMIN
}


//An enum (short for "enumeration") in Java is a special data type that represents
//a fixed set of constants — a predefined list of possible values for a variable, known at compile time.


//example :
// public enum Day {
//    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
//}

// Day today = Day.MONDAY;
//
//if (today == Day.SATURDAY || today == Day.SUNDAY) {
//    System.out.println("Weekend!");
//}

//Why use enums instead of plain constants (like int or String)?
//
//Type safety — a variable of type Day can only hold one of the defined values.
// You can't accidentally assign Day today = "Blah"; or an invalid int like today = 8.
//Readability — OrderStatus.SHIPPED is far clearer than a magic number like status = 2.
//Switch-friendly — enums work cleanly in switch statements.


//public enum Day {
//    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
//}
//
//public class EnumDemo {
//    public static void main(String[] args) {
//
//        Day today = Day.WEDNESDAY;
//
//        // 1. .name() - returns the exact name as a String
//        System.out.println("Name: " + today.name());
//        // Output: Name: WEDNESDAY
//
//        // 2. .ordinal() - returns its position (index), starting from 0
//        System.out.println("Ordinal: " + today.ordinal());
//        // Output: Ordinal: 2   (MONDAY=0, TUESDAY=1, WEDNESDAY=2)
//
//        // 3. values() - returns an array of ALL constants in the enum
//        Day[] allDays = Day.values();
//        System.out.println("All days:");
//        for (Day d : allDays) {
//            System.out.println(" - " + d);
//        }
//        // Output:
//        //  - MONDAY
//        //  - TUESDAY
//        //  - WEDNESDAY
//        //  - THURSDAY
//        //  - FRIDAY
//        //  - SATURDAY
//        //  - SUNDAY
//
//        // 4. valueOf(String) - converts a matching String into the enum constant
//        Day converted = Day.valueOf("FRIDAY");
//        System.out.println("Converted: " + converted);
//        // Output: Converted: FRIDAY
//    }
//}


//What each one is doing, in plain terms:
//
//.name() — just gives you back the constant's name as plain text. Useful when you need to display or log it as a String.
//.ordinal() — tells you where it sits in the list, counting from 0. MONDAY is 0th, TUESDAY is 1st, and so on.
// Rarely used directly in real code (fragile if you reorder the enum), but good to know it exists.
//values() — hands you every constant in the enum as an array, so you can loop through all of them.
// Very common — e.g., populating a dropdown of all OrderStatus options.
//valueOf(String) — the reverse of .name(). Takes a String and converts it back into the actual enum constant —
// this is how you'd convert data coming from a database or a JSON request (which is just text) back into your Java enum.
// Careful: it must match exactly (case-sensitive), or it throws an IllegalArgumentException.
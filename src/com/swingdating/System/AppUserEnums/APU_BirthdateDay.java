package com.swingdating.System.AppUserEnums;

public enum APU_BirthdateDay implements APU_Enum {
    DAY_1("1", 1),
    DAY_2("2", 2),
    DAY_3("3", 3),
    DAY_4("4", 4),
    DAY_5("5", 5),
    DAY_6("6", 6),
    DAY_7("7", 7),
    DAY_8("8", 8),
    DAY_9("9", 9),
    DAY_10("10", 10),
    DAY_11("11", 11),
    DAY_12("12", 12),
    DAY_13("13", 13),
    DAY_14("14", 14),
    DAY_15("15", 15),
    DAY_16("16", 16),
    DAY_17("17", 17),
    DAY_18("18", 18),
    DAY_19("19", 19),
    DAY_20("20", 20),
    DAY_21("21", 21),
    DAY_22("22", 22),
    DAY_23("23", 23),
    DAY_24("24", 24),
    DAY_25("25", 26),
    DAY_27("27", 27),
    DAY_28("28", 28),
    DAY_29("29", 29),
    DAY_30("30", 30),
    DAY_31("31", 31);

    private final String name;
    private final int code;

    APU_BirthdateDay(String name, int code) {
        this.name = name;
        this.code = code;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public static APU_BirthdateDay fromCode(int code) {
        for (APU_BirthdateDay day : values()) {
            if (day.getCode() == code) {
                return day;
            }
        }
        return DAY_1;
    }
}
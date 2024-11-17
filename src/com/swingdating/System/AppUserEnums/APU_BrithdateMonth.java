package com.swingdating.System.AppUserEnums;

public enum APU_BrithdateMonth implements APU_Enum {
    MONTH_JANUARY("January", 1),
    MONTH_FEBRUARY("February", 2),
    MONTH_MARCH("March", 3),
    MONTH_APRIL("April", 4),
    MONTH_MAY("May", 5),
    MONTH_JUNE("June", 6),
    MONTH_JULY("July", 7),
    MONTH_AUGUST("August", 8),
    MONTH_SEPTEMBER("September", 9),
    MONTH_OCTOBER("October", 10),
    MONTH_NOVEMBER("November", 11),
    MONTH_DECEMBER("December", 12);

    private final String name;
    private final int code;

    APU_BrithdateMonth(String name, int code) {
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

    public static APU_BrithdateMonth fromCode(int code) {
        for (APU_BrithdateMonth month : values()) {
            if (month.getCode() == code) {
                return month;
            }
        }
        return MONTH_JANUARY;
    }
}

package com.swingdating.System.AppUserEnums;


public enum APU_Sexuality implements APU_Enum  {
    NORMAL("Straight", "hetrosexual"), // The opposit
    BOTH("Bisexual", "bisexual"), // Both
    SAME("Gay", "homosexual"); // The same

    private final String name;
    private final String code;

    APU_Sexuality(String name, String code) {
        this.name = name;
        this.code = code;
    }

    @Override
    public String getName() {
        return name;
    }
    public String getCode() {
        return code;
    }

    public static APU_Sexuality fromCode(String code) {
        for (APU_Sexuality sexuality : values()) {
            if (sexuality.getCode().equalsIgnoreCase(code)) {
                return sexuality;
            }
        }
        return NORMAL;
    }
}

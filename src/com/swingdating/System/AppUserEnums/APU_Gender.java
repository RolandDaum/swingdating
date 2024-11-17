package com.swingdating.System.AppUserEnums;


public enum APU_Gender implements APU_Enum  {
    MALE("Man", "m"),
    FEMALE("Woman", "w");

    private final String name;
    private final String code;

    APU_Gender(String name, String code) {
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

    public static APU_Gender fromCode(String code) {
        for (APU_Gender gender : values()) {
            if (gender.getCode().equalsIgnoreCase(code)) {
                return gender;
            }
        }
        return MALE;
    }
}

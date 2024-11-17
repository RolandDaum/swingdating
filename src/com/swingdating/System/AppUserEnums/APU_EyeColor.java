package com.swingdating.System.AppUserEnums;

public enum APU_EyeColor implements APU_Enum {
    BLUE("Blue", "blue"),
    BROWN("Brown", "brown"),
    GREEN("Green", "green");

    private final String name;
    private final String code;

    APU_EyeColor(String name, String code) {
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

    public static APU_EyeColor fromCode(String code) {
        for (APU_EyeColor color : values()) {
            if (color.getCode().equalsIgnoreCase(code)) {
                return color;
            }
        }
        return BROWN;
    }
}

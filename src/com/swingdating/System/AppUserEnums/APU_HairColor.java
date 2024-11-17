package com.swingdating.System.AppUserEnums;

public enum APU_HairColor implements APU_Enum {
    BLOND("Blond", "blond"),
    BROWN("Brown", "brown"),
    RED("Red", "red"),
    BLACK("Black", "black");

    private final String name;
    private final String code;

    APU_HairColor(String name, String code) {
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

    public static APU_HairColor fromCode(String code) {
        for (APU_HairColor color : values()) {
            if (color.getCode().equalsIgnoreCase(code)) {
                return color;
            }
        }
        return BLACK;
    }
}

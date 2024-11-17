package com.swingdating.System.AppUserEnums;


public enum APU_Nationality implements APU_Enum  {
    GERMANY("Germany", "DEU"),
    IRAQ("Iraq", "IRQ"),
    IRAN("Iran", "IRN"),
    RUSSIA("Russia", "RUS"),
    CENTRALAFRICANREPUBLIC("Central African Republic", "CAF"),
    TURKEYS("Turkey", "TUR"),
    OTHER("Other", "");

    private final String name;
    private final String code;

    APU_Nationality(String name, String code) {
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

    public static APU_Nationality fromCode(String code) {
        for (APU_Nationality nationality : values()) {
            if (nationality.getCode().equalsIgnoreCase(code)) {
                return nationality;
            }
        }
        return OTHER;
    }
}

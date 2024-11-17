package com.swingdating.System.AppUserEnums;


public enum APU_Religion implements APU_Enum  {
    CHRISTIAN("Christian", "CR"),
    EVANGELICAL("Evangelical", "EV"),
    CATHOLIC("Catholic", "RC"),
    ISLAM("Islam", "ISL"),
    OTHER("Other", ""); 

    private final String name;
    private final String code;

    APU_Religion(String name, String code) {
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

    public static APU_Religion fromCode(String code) {
        for (APU_Religion religion : values()) {
            if (religion.getCode().equalsIgnoreCase(code)) {
                return religion;
            }
        }
        return OTHER;
    }
}

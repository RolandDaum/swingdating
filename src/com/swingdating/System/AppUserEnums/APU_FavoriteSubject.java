package com.swingdating.System.AppUserEnums;

public enum APU_FavoriteSubject implements APU_Enum  {
    GERMAN("German", "de"),
    FRENCH("French", "fr"),
    HISTORY("History", "hi"),
    ART("Art", "art"),
    LATIN("Latin", "la"),
    MATH("Mathematics", "math"),
    MUSIC("Music", "music"),
    PHYSICS("Physics", "phy"),
    POLITICS("Politics", "pol"),
    RELIGION("Religion", "rel"),
    SPANISH("Spanish", "spa"),
    PHYSICALEDUCATION("Physical Education", "pe"),
    ETHICS("Ethics", "eth"),
    ENGLISH("English", "eng"),
    OTHER("Other", "");

    private final String name;
    private final String code;

    APU_FavoriteSubject(String name, String code) {
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

    public static APU_FavoriteSubject fromCode(String code) {
        for (APU_FavoriteSubject subject : values()) {
            if (subject.getCode().equalsIgnoreCase(code)) {
                return subject;
            }
        }
        return OTHER;
    }
}

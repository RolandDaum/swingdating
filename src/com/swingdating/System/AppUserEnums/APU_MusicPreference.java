package com.swingdating.System.AppUserEnums;

public enum APU_MusicPreference implements APU_Enum {
    HOUSE("House", "house"),
    CLASSICAL("Classical", "classical"),
    METAL("Metal", "metal"),
    POP("Pop", "pop"),
    RAP("Rap", "rap"),
    ROCK("Rock", "rock"),
    SCHLAGER("Schlager", "schlager"),
    TECHNO("Techno", "techno"),
    OTHER("Other", "");

    private final String name;
    private final String code;

    APU_MusicPreference(String name, String code) {
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

    public static APU_MusicPreference fromCode(String code) {
        for (APU_MusicPreference music : values()) {
            if (music.getCode().equalsIgnoreCase(code)) {
                return music;
            }
        }
        return OTHER;
    }
}

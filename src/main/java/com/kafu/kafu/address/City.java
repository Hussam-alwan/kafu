package com.kafu.kafu.address;

public enum City {
    DAMASCUS("DAMASCUS", "دمشق"),
    REEF_DAM("REEF_DAM", "ريف دمشق"),
    ALEPPO("ALEPPO", "حلب"),
    HOMS("HOMS", "حمص"),
    HAMAH("HAMAH", "حماة"),
    IDLIB("IDLIB", "إدلب"),
    QUNAITRA("QUNAITRA", "القنيطرة"),
    DARAA("DARAA", "درعا"),
    SUWAIDA("SUWAIDA", "السويداء"),
    TARTUS("TARTUS", "طرطوس"),
    LATAKIA("LATAKIA", "اللاذقية"),
    RAQQA("RAQQA", "الرقة"),
    DEER("DEER", "دير الزور"),
    HASAKA("HASAKA", "الحسكة");

    private final String englishName;
    private final String arabicName;

    City(String englishName, String arabicName) {
        this.englishName = englishName;
        this.arabicName = arabicName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getArabicName() {
        return arabicName;
    }
}

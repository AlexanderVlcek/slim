package myPackage.constants;

public enum ExampleShortCuts {
    ML("M", "Ml"),
    LO("L", "Lo"),
    UR("U", "Ur"),
    PP("P", "Pp"),
    PT("P", "Pt"),
    OK("O", "OK"),
    ;

    private final String dataValue;
    private final String textValue;

    ExampleShortCuts(String dataValue, String textValue) {
        this.dataValue = dataValue;
        this.textValue = textValue;
    }

    public String dataValue() {
        return dataValue;
    }
    public String textValue() {
        return textValue;
    }

    public static ExampleShortCuts fromDataValue(String enumValue) {
        for (ExampleShortCuts c : ExampleShortCuts.values()) {
            if (c.dataValue.equalsIgnoreCase(enumValue)) {
                return c;
            }
        }
        throw new IllegalArgumentException(enumValue);
    }

    public static ExampleShortCuts fromTextValue(String enumValue) {
        for (ExampleShortCuts c : ExampleShortCuts.values()) {
            if (c.textValue.equalsIgnoreCase(enumValue)) {
                return c;
            }
        }
        throw new IllegalArgumentException(enumValue);
    }

}

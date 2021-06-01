package myPackage.constantsEnums;

import java.util.Arrays;

public enum ExampleState {
    OPEN("true", "open"),
    CLOSED("true", "closed");

    private final String value;

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    private final String text;

    ExampleState(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public static ExampleState getByText(String text) {
        return Arrays.stream(ExampleState.values())
                .filter(c -> c.getText().equalsIgnoreCase(text))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No State found for text " + text));
    }

}

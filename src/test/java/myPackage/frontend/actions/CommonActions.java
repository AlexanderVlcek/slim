package myPackage.frontend.actions;

import net.serenitybdd.core.steps.UIInteractionSteps;
import net.thucydides.core.annotations.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CommonActions extends UIInteractionSteps {

    public void scrollPageInPixels(int pixels) {
        ((JavascriptExecutor) this.getDriver()).executeScript("window.scrollBy(0," + pixels + ")", "");
    }

    public void scrollHome() {
        Actions actions = new Actions(this.getDriver());
        actions.sendKeys(Keys.HOME).build().perform();
    }

    public void scrollEnd() {
        Actions actions = new Actions(this.getDriver());
        actions.sendKeys(Keys.END).build().perform();
    }

    public void pressEnter() {
        Actions actions = new Actions(this.getDriver());
        actions.sendKeys(Keys.ENTER).build().perform();
    }

    @Step("Get fields with string {1} from class {0}")
    public List<String> getFieldTextByReflection(Class<?> myClass, String criteria) {
        Field[] labelsBy = myClass.getDeclaredFields();
        List<String> outputList = new ArrayList<>();
        for (Field field : labelsBy) {
            if (field.getName().contains(criteria)) {
                try {
                    outputList.add($((By) (field.get(field.getName()))).getText());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return outputList;
    }

    @Step("Check if labels from scenario and app are same, labelCriteria can be _LABEL, field with name: SOME_ITEM_LABEL")
    public void checkLabels(List<String> labels, Class<?> leanPageObjectClass, String labelCriteria) {
        List<String> listFromApp = getFieldTextByReflection(leanPageObjectClass, labelCriteria);
        labels.forEach(it -> Assertions.assertTrue(listFromApp.contains(it)));
        listFromApp.forEach(it -> Assertions.assertTrue(labels.contains(it)));
    }
}

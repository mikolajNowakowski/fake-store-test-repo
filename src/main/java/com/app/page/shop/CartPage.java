package com.app.page.shop;

import com.app.annotation.Page;
import com.app.page.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@Page
public class CartPage extends BasePage {


    @FindBy(xpath = "//img[@class = 'custom-logo']")
    private WebElement logoImg;

    @FindBy(xpath = "//tr[@class = 'woocommerce-cart-form__cart-item cart_item']")
    private List<WebElement> productsInCart;

    @FindBy(css = ".alt.button.checkout-button.wc-forward")
    private WebElement goToPaymentButton;

    private final String productNameXpath = ".//td[@class = 'product-name']/a";
    private final String productDeleteButtonXpath = ".//td[@class = 'remove']/a";

    public boolean isSpecificPositionInCart(String productKeyWord) {
        return productsInCart
                .stream()
                .map(webElement -> webElement
                        .findElement(By.xpath(productNameXpath))
                        .getText()
                        .toLowerCase()
                        .replaceAll("[–-]", ""))
                .peek(System.out::println)
                .anyMatch(element -> element
                        .contains(productKeyWord
                                .toLowerCase()
                                .trim()
                                .replaceAll("[–-]", "")));
    }


    public CartPage removeSpecificProductFromCart(String productName) {
        productsInCart
                .stream()
                .filter(element -> element.findElement(By.xpath(productNameXpath)).getText().equalsIgnoreCase(productName))
                .findFirst()
                .orElseThrow()
                .findElement(By.xpath(productDeleteButtonXpath))
                .click();
        return this;
    }


    public CartPage goToPayment() {
        goToPaymentButton.click();
        return this;
    }

    @Override
    public boolean isAt() {
        return wait.until((d) -> Integer.parseInt(logoImg.getAttribute("width")) > 0);
    }

}

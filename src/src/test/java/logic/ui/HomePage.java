package logic.ui;

import infra.ui.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends BasePage {

    private static final By DRINKS_BTN =By.xpath("//*[@id='main-menu-8']");
    private static final By PLUS_BTN = By.xpath("//*[@id='Capa_1']");

    private static final By ITEM_DIV_TO_ADD = By.xpath("/html/body/div[1]/div/div/div[2]/div[1]/div[2]/div/div/div[1]/div[2]/div[5]/div/div/div[1]/div/img");

    private static final By NUM_OF_ITEMS_IN_CART = By.xpath("//*[@id=\"onlineCartHeader\"]/div[1]/div[1]/span[1]");
    private static final By CART_SPAN= By.className("currency-wrap");
    private static final By ESC_DELEVirY = By.xpath("//*[@id=\"close-popup\"]");
    private static final By FILTER_BTN = By.xpath("//*[@id=\"search\"]/div/div/div[1]/div/div[1]/div/div[1]/div[3]");
    private static final By SCHWEPPES_FILTER_BTN = By.xpath(" //*[@id=\"__layout\"]/div/div[1]/div[1]/div[3]/div[3]/div/div[1]/div[2]/div/div[3]/div[3]");

    private static final By SCHWEPPES_DIV_ITEM = By.cssSelector("img[src*='/product/7290019056119/small.jpg']");
    private static final By SEARCH_INPUT = By.xpath("//*[@id=\"destination\"]");
    private static final By SEARCH_RESULT_PRODUCT_DIV = By.xpath("//*[@id=\"min-height-product-1\"]/div/div/div[2]");
    private static final By DIV_INFORMATION_PRODUCT =By.cssSelector("div[data-v-6039ce16] > div[data-v-6039ce16] > div[data-v-6039ce16]");



   WebElement divInformationProduct;
    WebElement searchInput;
    WebElement searchResultProductDiv;

    WebElement schweppesFilterbtn;
    WebElement schweppesDivItemElement;

    WebElement filterBtnElement;
    WebElement numOfItems;
    WebElement cartSpanElement;

    WebElement drinksElementBtn;
    WebElement plusElementBtn;
    WebElement divElementToAdd;

    public HomePage(WebDriver driver) {
        super(driver);
        initPage();
    }

    private void initPage() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        this.drinksElementBtn = wait.until(ExpectedConditions.presenceOfElementLocated(DRINKS_BTN));
    }
    public void clickOnDrinksCategory(){
        this.drinksElementBtn.click();
    }
    public void addItemToCart(){
        hoverOnDiv();
        clickAddButton();
    }
    private void hoverOnDiv() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        this.divElementToAdd = wait.until(ExpectedConditions.presenceOfElementLocated(ITEM_DIV_TO_ADD));
        Actions actions = new Actions(driver);
        actions.moveToElement(this.divElementToAdd).perform();
    }
    private void clickAddButton() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        this.plusElementBtn = wait.until(ExpectedConditions.presenceOfElementLocated(PLUS_BTN));
        plusElementBtn.click();
        try {
            WebElement escElement = wait.until(ExpectedConditions.presenceOfElementLocated(ESC_DELEVirY));

            if (escElement != null) {
                escElement.click();
            }
        } catch (Exception e) {

        }
    }

    public String getCartSum(){
        cartSpanElement = driver.findElement(CART_SPAN);
        String sumCart = cartSpanElement.findElement(By.tagName("span")).getText();
        // Extract numeric part (remove non-numeric characters)
        sumCart = sumCart.replaceAll("[^\\d.]", "");
        float sumWithDelivery = Float.parseFloat(sumCart) + 29.9f;
        return String.valueOf(sumWithDelivery + " ₪");
    }
    public String getCartNumOfItems(){
        numOfItems = driver.findElement(NUM_OF_ITEMS_IN_CART);
        return numOfItems.getText();
    }
    private void clickFilterBtn(){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        this.filterBtnElement = wait.until(ExpectedConditions.presenceOfElementLocated(FILTER_BTN));
        filterBtnElement.click();
    }
    private void clickOnSchweppesBtn(){
        this.schweppesFilterbtn = drinksElementBtn.findElement(SCHWEPPES_FILTER_BTN);
        schweppesFilterbtn.click();
    }
    public void chooseShweppesDrink(){
        clickFilterBtn();
        clickOnSchweppesBtn();
    }
    public String verifySchweppesDrink(){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        this.schweppesDivItemElement = wait.until(ExpectedConditions.presenceOfElementLocated(SCHWEPPES_DIV_ITEM));
        String fullNameDrinkType = schweppesDivItemElement.getAttribute("alt");
        String extractSchweppes = fullNameDrinkType.split("\\s+")[0];
        return extractSchweppes;
    }

    public void enterWordToSearch(){
        searchInput = driver.findElement(SEARCH_INPUT);
        searchInput.sendKeys("חלב");
        searchInput.sendKeys(Keys.RETURN);
    }
    public boolean verifyItemViaComponents(){
        clickOnResultProduct();
        return checkCompnentsOfTheProduct();
    }

    private boolean checkCompnentsOfTheProduct() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        this.divInformationProduct =wait.until(ExpectedConditions.presenceOfElementLocated(DIV_INFORMATION_PRODUCT));
        String component = divInformationProduct.getText();
        if(component.contains("חלב")){
            return true;
        }
        return false;
    }

    private void clickOnResultProduct() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        this.searchResultProductDiv = wait.until(ExpectedConditions.presenceOfElementLocated(SEARCH_RESULT_PRODUCT_DIV));
        searchResultProductDiv.click();
    }
}


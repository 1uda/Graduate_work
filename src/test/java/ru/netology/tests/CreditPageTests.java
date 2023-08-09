package ru.netology.tests;

import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.pages.StartPage;
import ru.netology.pages.CreditPage;
import com.codeborne.selenide.logevents.SelenideLogger;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class CreditPageTests {
    StartPage startPage = open("http://localhost:8080/", StartPage.class);


    @AfterEach
    public void cleanBase() {
        SQLHelper.clearDB();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    void creditPositiveAllFieldValidApproved() {
        var creditPage = new CreditPage();
        startPage.creditPage();
        var cardInfo = DataHelper.getApprovedCard();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationApproved();
        assertEquals("APPROVED", SQLHelper.getCreditRequestStatus());
    }

    @Test
    void creditPositiveAllFieldValidDeclined() {
        var creditPage = new CreditPage();
        startPage.creditPage();
        var cardInfo = DataHelper.getDeclinedCard();

        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationFailure();
        assertEquals("DECLINED", SQLHelper.getCreditRequestStatus());
    }

    @Test
    void creditNegativeAllFieldEmpty() {
        var creditPage = new CreditPage();
        startPage.creditPage();
        var cardInfo = DataHelper.getEmptyCard();

        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat4Fields();

        assertEquals("0", SQLHelper.getOrderCount());

    }

    @Test
    void creditNegativeNumberCard15Symbols() {
        var creditPage = new CreditPage();
        startPage.creditPage();
        var cardInfo = DataHelper.getNumberCard15Symbols();

        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeCardNotInDatabase() {
        var creditPage = new CreditPage();
        startPage.creditPage();
        var cardInfo = DataHelper.getCardNotInDatabase();

        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationFailure();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeMonth1Symbol() {
        var creditPage = new CreditPage();
        startPage.creditPage();
        var cardInfo = DataHelper.getCardMonth1Symbol();

        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeMonthOver12() {
        var creditPage = new CreditPage();
        startPage.creditPage();
        var cardInfo = DataHelper.getCardMonthOver12();

        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeMonth00ThisYear() {
        var creditPage = new CreditPage();
        startPage.creditPage();
        var cardInfo = DataHelper.getCardMonth00ThisYear();

        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeMonth00OverThisYear() {
        var creditPage = new CreditPage();
        startPage.creditPage();
        var cardInfo = DataHelper.getCardMonth00OverThisYear();

        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeYear00() {
        var creditPage = new CreditPage();
        startPage.creditPage();
        var cardInfo = DataHelper.getCardYear00();

        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationExpiredError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeYear1Symbol() {
        var creditPage = new CreditPage();
        startPage.creditPage();
        var cardInfo = DataHelper.getCardYear1Symbol();

        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeYearUnderThisYear() {
        var creditPage = new CreditPage();
        startPage.creditPage();
        var cardInfo = DataHelper.getCardYearUnderThisYear();

        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationExpiredError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeYearOverThisYearOn6() {
        var creditPage = new CreditPage();
        startPage.creditPage();
        var cardInfo = DataHelper.getCardYearOverThisYearOn6();

        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeCvv1Symbol() {
        var creditPage = new CreditPage();
        startPage.creditPage();
        var cardInfo = DataHelper.getCardCvv1Symbol();

        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeCvv2Symbols() {
        var creditPage = new CreditPage();
        startPage.creditPage();
        var cardInfo = DataHelper.getCardCvv2Symbols();

        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeOwner1Word() {
        var creditPage = new CreditPage();
        startPage.creditPage();
        var cardInfo = DataHelper.getCardHolder1Word();

        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeOwnerCirillic() {
        var creditPage = new CreditPage();
        startPage.creditPage();
        var cardInfo = DataHelper.getCardHolderCirillic();

        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeOwnerNumeric() {
        var creditPage = new CreditPage();
        startPage.creditPage();
        var cardInfo = DataHelper.getCardHolderNumeric();

        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeOwnerSpecialSymbols() {
        var creditPage = new CreditPage();
        startPage.creditPage();
        var cardInfo = DataHelper.getCardSpecialSymbols();

        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }
}



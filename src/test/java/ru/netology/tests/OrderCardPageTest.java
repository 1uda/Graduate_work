package ru.netology.tests;

import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.pages.OrderCardPage;
import ru.netology.pages.StartPage;
import com.codeborne.selenide.logevents.SelenideLogger;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderCardPageTest {
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
    void buyPositiveAllFieldValidApproved() {
        var orderCardPage = new OrderCardPage();
        startPage.orderCardPage();
        var cardInfo = DataHelper.getApprovedCard();

        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationApproved();
        assertEquals("APPROVED", SQLHelper.getPaymentStatus());
    }

    @Test
    void buyPositiveAllFieldValidDeclined() {
        var orderCardPage = new OrderCardPage();
        startPage.orderCardPage();
        var cardInfo = DataHelper.getDeclinedCard();

        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationFailure();
        assertEquals("DECLINED", SQLHelper.getPaymentStatus());

    }

    @Test
    void buyNegativeAllFieldEmpty() {
        var orderCardPage = new OrderCardPage();
        startPage.orderCardPage();
        var cardInfo = DataHelper.getEmptyCard();

        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat4Fields();
        assertEquals("0", SQLHelper.getOrderCount());

    }

    @Test
    void buyNegativeNumberCard15Symbols() {
        var orderCardPage = new OrderCardPage();
        startPage.orderCardPage();
        var cardInfo = DataHelper.getNumberCard15Symbols();

        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeCardNotInDatabase() {
        var orderCardPage = new OrderCardPage();
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardNotInDatabase();

        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationFailure();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeMonth1Symbol() {
        var orderCardPage = new OrderCardPage();
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardMonth1Symbol();

        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeMonthOver12() {
        var orderCardPage = new OrderCardPage();
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardMonthOver12();

        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeMonth00ThisYear() {
        var orderCardPage = new OrderCardPage();
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardMonth00ThisYear();

        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }
    @Test
    void creditNegativeMonth00OverThisYear() {
        var orderCardPage = new OrderCardPage();
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardMonth00OverThisYear();

        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeYear00() {
        var orderCardPage = new OrderCardPage();
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardYear00();

        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpiredError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeYear1Symbol() {
        var orderCardPage = new OrderCardPage();
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardYear1Symbol();

        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeYearUnderThisYear() {
        var orderCardPage = new OrderCardPage();
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardYearUnderThisYear();

        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpiredError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeYearOverThisYearOn6() {
        var orderCardPage = new OrderCardPage();
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardYearOverThisYearOn6();

        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeCvv1Symbol() {
        var orderCardPage = new OrderCardPage();
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardCvv1Symbol();

        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeCvv2Symbols() {
        var orderCardPage = new OrderCardPage();
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardCvv2Symbols();

        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeOwner1Word() {
        var orderCardPage = new OrderCardPage();
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardHolder1Word();

        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeOwnerCirillic() {
        var orderCardPage = new OrderCardPage();
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardHolderCirillic();

        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeOwnerNumeric() {
        var orderCardPage = new OrderCardPage();
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardHolderNumeric();

        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeOwnerSpecialSymbols() {
        var orderCardPage = new OrderCardPage();
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardSpecialSymbols();

        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }
}

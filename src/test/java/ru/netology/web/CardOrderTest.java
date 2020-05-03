package ru.netology.web;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.cssValue;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class CardOrderTest {

    @Test
    void shouldSend() {
        open("http://localhost:9999");
        SelenideElement form = $("form[class='form form_size_m form_theme_alfa-on-white']");
        form.$("[data-test-id=name] input").setValue("Чехов Антон-Павлович");
        form.$("[data-test-id=phone] input").setValue("+71234567890");
        form.$("[data-test-id=agreement]").click();
        form.$("[type='button']").click();
        $("[data-test-id='order-success']").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldNotSendIncorrectName() {
        open("http://localhost:9999");
        SelenideElement form = $("form[class='form form_size_m form_theme_alfa-on-white']");
        form.$("[data-test-id=name] input").setValue("Chekhov Anton");
        form.$("[data-test-id=phone] input").setValue("+71234567890");
        form.$("[data-test-id=agreement]").click();
        form.$("[type='button']").click();
        form.$(".input_type_text .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNotSendEmptyName() {
        open("http://localhost:9999");
        SelenideElement form = $("form[class='form form_size_m form_theme_alfa-on-white']");
        form.$("[data-test-id=phone] input").setValue("+71234567890");
        form.$("[data-test-id=agreement]").click();
        form.$("[type='button']").click();
        form.$(".input_type_text .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotSendIncorrectPhone() {
        open("http://localhost:9999");
        SelenideElement form = $("form[class='form form_size_m form_theme_alfa-on-white']");
        form.$("[data-test-id=name] input").setValue("Иван");
        form.$("[data-test-id=phone] input").setValue("+771234567890000");
        form.$("[data-test-id=agreement]").click();
        form.$("[type='button']").click();
        form.$(".input_type_tel .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldNotSendNoPhone() {
        open("http://localhost:9999");
        SelenideElement form = $("form[class='form form_size_m form_theme_alfa-on-white']");
        form.$("[data-test-id=name] input").setValue("Иван");
        form.$("[data-test-id=agreement]").click();
        form.$("[type='button']").click();
        form.$(".input_type_tel .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotSendIncorrectCheckbox() {
        open("http://localhost:9999");
        SelenideElement form = $("form[class='form form_size_m form_theme_alfa-on-white']");
        form.$("[data-test-id=name] input").setValue("Иван");
        form.$("[data-test-id=phone] input").setValue("+77123456789");
        form.$("[type='button']").click();
        form.$(".checkbox__text").shouldHave(cssValue("color", "rgba(255, 92, 92, 1)"));
    }
}

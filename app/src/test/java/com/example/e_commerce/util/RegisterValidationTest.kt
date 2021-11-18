package com.example.e_commerce.util

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class RegisterValidationTest {

    private lateinit var validator: RegisterValidation


    // to make sure when we put the email wrong it will tell us its wrong

    @Before
    fun setup() {
        validator = RegisterValidation()
    }

    @Test
    // this is the pro way to name this function to know what you're testing
    // first part fun name that u want to test , second part value that will come , third part return expectation
    fun emailIsValidWithInvalidEmailThenReturnFalseValue() {

        val validation = validator.emailIsValid("test-dd.com")

        assertEquals(false, validation)
    }

    @Test
    fun emailIsValidWithValidEmailThenReturnTrueValue() {

        val validation = validator.emailIsValid("test@test.com")

        assertEquals(true, validation)
    }

    @Test
    fun passwordIsValidWithInvalidPasswordThenReturnFalseValue() {

        val validation = validator.passwordIsValid("23")

        assertEquals(false, validation)
    }

    @Test
    fun passwordIsValidWithValidPasswordThenReturnTrueValue() {

        val validation = validator.passwordIsValid("Tu@12345678")
        assertEquals(true, validation)


    }
}
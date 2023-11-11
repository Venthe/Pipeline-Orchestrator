import { defineFeature, loadFeature } from 'jest-cucumber';
import { GenericContainer } from "testcontainers";

const feature = loadFeature('features/LoggingIn.feature');

class PasswordValidator {
    password

    setPassword(s) {
        this.password = s
    }

    validatePassword(s) {
        return this.password === s
    }
}

defineFeature(feature, test => {
    let passwordValidator = new PasswordValidator();
    let accessGranted = false;
    let container;

    beforeEach(async () => {
        passwordValidator = new PasswordValidator();
        container = await new GenericContainer("redis")
          .withExposedPorts(6379)
          .start();
    
    });

    afterAll(async () => {
      await container.stop();
    });

    test('Entering a correct password', ({ given, when, then }) => {
        given('I have previously created a password', () => {
            passwordValidator.setPassword('1234');
        });

        when('I enter my password correctly', () => {
            accessGranted = passwordValidator.validatePassword('1234');
        });

        then('I should be granted access', () => {
            expect(accessGranted).toBe(true);
        });
    });
});
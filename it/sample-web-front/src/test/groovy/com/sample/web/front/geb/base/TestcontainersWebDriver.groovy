package com.sample.web.front.geb.base

import org.openqa.selenium.Capabilities
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.RemoteWebDriver
import org.testcontainers.containers.BrowserWebDriverContainer

class TestcontainersWebDriver {

    private final BrowserWebDriverContainer container

    TestcontainersWebDriver(Capabilities capabilities) {
        if (capabilities instanceof ChromeOptions) {
            container = new BrowserWebDriverContainer<>("selenium/standalone-chrome")
                    .withCapabilities(capabilities)
        } else if (capabilities instanceof FirefoxOptions) {
            container = new BrowserWebDriverContainer<>("selenium/standalone-firefox")
                    .withCapabilities(capabilities)
        }
    }

    @Delegate
    RemoteWebDriver getDriver() {
        container.webDriver
    }

    void startContainer() {
        container.start()
    }

    @Override
    void quit() {
        driver.quit()
        container.stop()
    }
}

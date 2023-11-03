Feature: System is able to handle a flow

    Scenario Outline: Triggered workflow is ran to completion
        Given a test specification is loaded from the "<specificationDirectory>"
        And a repository is set up
        When event is sent
        Then workflow run is triggered
        And run is visible in the UI
        And run result is matching the test specification

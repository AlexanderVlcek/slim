Feature: Guess the word

  Scenario: Maker starts a game
    Given the game is setup
    When the Maker starts a game
    Then the Maker waits for a Breaker to join

    Scenario: Navigate to wikipage
      Given url to search engine
      When user search for "wikipedia" in search engine
      Then results are shown
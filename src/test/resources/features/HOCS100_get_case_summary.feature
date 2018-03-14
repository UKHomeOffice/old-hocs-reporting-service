Feature: Getting case summary details by case type

  Scenario: Returning case summary details of one case type
    Given these numbers of each case types are in the service:
      | MIN | 5 | New |
      | ABC | 2 | New |
      | TRO | 3 | New |
    When requested to return data for case types "MIN"
    Then this case type summary data is returned:
      | MIN | 5 |

  Scenario: Returning case summary details of multiple case types
    Given these numbers of each case types are in the service:
      | MIN | 5 | New |
      | ABC | 2 | New |
      | TRO | 3 | New |
    When requested to return data for case types "MIN,TRO"
    Then this case type summary data is returned:
      | MIN | 5 |
      | TRO | 3 |

  Scenario: Returning case summary details of one case type with no data
    Given these numbers of each case types are in the service:
      | ABC | 2 | New |
      | TRO | 3 | New |
    When requested to return data for case types "MIN"
    Then this case type summary data is returned:
      | MIN | 0 |

  Scenario: Returning case summary details of one case type with no data
    Given these numbers of each case types are in the service:
      | MIN | 5 | Completed |
      | MIN | 3 | Draft     |
      | MIN | 2 | New       |
      | ABC | 2 | Completed |
      | ABC | 2 | Draft     |
      | TRO | 3 | Completed |
      | TRO | 1 | Draft     |
      | TRO | 2 | Dispatch  |

    When requested to return data for case types "MIN,TRO"
    Then this case type summary data is returned:
      | MIN | 5 |
      | TRO | 3 |
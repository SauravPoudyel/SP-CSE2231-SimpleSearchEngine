# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Calendar Versioning](https://calver.org/) of
the following form: YYYY.0M.0D.

## [Unreleased]

### Added

- Designed SimpleSearchEngineKernel Interface:
  - void add(String title, String content) method
  - void remove(String title) method
  - boolean contains(String title) method
  - void changeToSearchMode() method
  - boolean isInInsertionMode() method
  - int size() method

- Designed SimpleSearchEngine Secondary Interface:
  - Pair<String, String> relativeSearch(String title) method
  - Set<Pair<String, String>> prefixSearch(String prefix) method
  - Set<Pair<String, String>> containsSearch(String subString)

- Hierarchy Diagram for Simple Search Engine

### Removed

- Concepts for
  - void removeAll(String word) method
  - boolean isInIndex(String word) method
  - void clearIndex() method
  - int frequencyOf(String word) method
  - void addFromDocument(String fileName) method

### Changed

- Representation of SimpleSearchEngine
  - SimpleSearchEngine now stores both title and content and no longer tracks
  frequency of words or the lexigraphical order
  - SimpleSearchEngine now pivots to focus on being able to parse through strings
  that also relate to another content area to provide a dedicated use case that
  focuses on search efficiency
  - The main theme is still to focus on website titles and then website urls
  but the scope was expanded slightly to provide potentially more use cases for
  other variable types

## [2024.09.27]

### Added

- Designed a proof of concept for SimpleSearchEngine component:
  - SimpleSearchEngine.java class
  - StringLT to alphabetize any queues
  - the map4 repersentation of the search engine
  - void add(String word) method
  - void remove(String word) method
  - void removeAll(String word) method
  - boolean isInIndex(String word) method
  - void clearIndex() method
  - int frequencyOf(String word) method
  - void addFromDocument(String fileName) method
  - Queue<String> prefixSearch(String keyWord) method
  - Queue<String> containsSearch(String keyWord) method
  - proofTest.txt to test some base cases
  - words.txt which contains 465k words to parse through

## [2024.09.13]

### Added

### Added

- Designed and brainstormed a Wordle component
- Designed and brainstormed a Quiz component
- Designed and brainstormed a ContactBook component
- A changelong to update and keep track of changes

### Removed

- The previous changelog about how this git template was made

# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Calendar Versioning](https://calver.org/) of
the following form: YYYY.0M.0D.

## [Unreleased]

## [2024.11.01]

### Added

- Mostly completed SimpleSearchEngine1L class
  - implemented createNewRep()
  - fully implemented and designed searchStructure helper methods
    - implemented createTrie()
    - implemented insertTagInTrie()
    - implemented insertCharInTrie()
    - implemented findNodeForPrefix()
    - implemented collectWordsContainingSubstring()
    - implemented collectWordsFromNode()
    - implemented getChildNode()
    - implemented findClosestMatch()
    - implemented calculateEditDistance()
  - implemented transferFrom() and clear()
  - implemented relativeSearch
  - implemented containsSearch
  - implemented prefixSearch
- Mostly completed test cases for SimpleSearchEngine1L
  - added this.constructTest, this.createFromArgsTest, and this.createFromDocTest
  - added AllWords and SomeWords test data
  - added tests for: size(), add(), remove(), isInInsertionMode(), containsSearch(), relativeSearch(), prefixSearch(), changeToSearchMode(), and contains()

## Removed

- relativeSearch(), containsSearch(), and prefixSearch() from SimpleSearchEngineSecondary

## Updated

- The repersentation of the searchStructure of SimpleSearchEngine1L to just a Trie instead of a SuffixTree

## [2024.10.27]

### Added

- Designed SimpleSearchEngine1L class:
  - created model for SimpleSearchEngine rep
    - decided on a suffix tree as the searchStructure
  - implemented void add(String tag, T value) method
  - void remove(String title) method
  - boolean contains(String title) method
  - void changeToSearchMode() method
  - boolean isInInsertionMode() method
  - int size() method

- Designed SimpleSearchEngine Secondary Interface:
  - Pair<String, String> relativeSearch(String title) method
  - Set<Pair<String, String>> prefixSearch(String prefix) method
  - Set<Pair<String, String>> containsSearch(String subString)

  - implemented void add(String title, String content) method
  - void remove(String title) method
  - boolean contains(String title) method
  - void changeToSearchMode() method
  - boolean isInInsertionMode() method
  - int size() method

- Designed SimpleSearchEngine Secondary Interface:
  - Pair<String, String> relativeSearch(String title) method
  - Set<Pair<String, String>> prefixSearch(String prefix) method
  - Set<Pair<String, String>> containsSearch(String subString)

## [2024.10.13]

### Added

- Designed SimpleSearchEngineKernel Interface:
  - void add(String tag, T value) method
  - void remove(String tag) method
  - boolean contains(String tag) method
  - void changeToSearchMode() method
  - boolean isInInsertionMode() method
  - int size() method
  - String relativeSearch(String title) method
  - List<String> prefixSearch(String prefix) method
  - List<String> containsSearch(String subString)

- Designed SimpleSearchEngine Secondary Interface:
  - temporarily empty for now

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
  - SimpleSearchEngine now stores both tag and value and no longer tracks
  frequency of words or the lexigraphical order
  - SimpleSearchEngine now pivots to focus on being able to parse through strings
  that also relate to another content area to provide a dedicated use case that
  focuses on search efficiency
  - The main theme is still to focus on website titles and then website urls
  but the scope was expanded slightly to provide potentially more use cases for
  other variable types (ie. students to grades, names to bank data, etc.)

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

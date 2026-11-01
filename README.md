## 1. Project Overview

**Live Sports System** is a Java application that demonstrates the practical implementation of behavioral and structural design patterns.

The project simulates a live sports environment where match events are published to multiple observers, while sports data is organized into a hierarchical structure and processed using different visitors.

The main goal is to demonstrate the Observer, Visitor, and Composite design patterns, their integration, and thread-safe event processing.

## 3. Design Patterns

### 3.1 Observer Pattern

The Observer Pattern is used to notify multiple listeners when a sports event occurs.

The system contains two event sources:

- `MatchEventSource` — publishes match events such as goals, yellow cards, and match completion.
- `PlayerMetricSource` — publishes player metric updates.

The observers include:

- `ScoreboardObserver`
- `OddsCalculatorObserver`
- `FantasyPointsObserver`
- `PushNotifierObserver`
- `StatisticsAggregator`
- `StatisticsObserver`

Additional observers demonstrate dynamic subscription and unsubscription during event processing.

Events are represented by immutable `SportEvent` objects and identified by `EventType`.

The implementation uses `ConcurrentHashMap` and `CopyOnWriteArraySet` to support thread-safe listener management.

### 3.2 Composite Pattern

The Composite Pattern organizes sports data into a hierarchical tree.

The structure contains:

- `League`
- `Club`
- `Team`

These container elements can contain other elements. Leaf elements represent individual sports personnel:

- `Player`
- `Coach`
- `Referee`
- `MedicalStaff`

All elements implement the common `SportElement` interface.

This structure allows visitors to process individual elements and entire groups through the same interface.

### 3.3 Visitor Pattern

The Visitor Pattern separates operations from the sports data structure.

The project implements the following visitors:

- `SalaryVisitor` — calculates the total salary of elements.
- `RatingVisitor` — calculates the average player rating.
- `FilterVisitor` — filters players according to a minimum rating.
- `JsonExportVisitor` — exports the hierarchy into JSON.
- `XmlExportVisitor` — exports the hierarchy into XML.

The generic `Visitor<R>` interface and the `accept()` method provide double dispatch.

### 3.4 Observer and Visitor Integration

The project demonstrates the integration of both patterns through `TeamAnalysisObserver`.

When a goal event occurs, the observer processes the related team using visitors.

It calculates team salary, average player rating, and filters players according to their ratings.

The analysis result is then published as a new statistics event.

## 4. Concurrency

The application uses `ExecutorService` to simulate concurrent sports events.

`ConcurrentEventSimulator` creates multiple producer threads that publish goal events simultaneously.

The number of threads and events per thread can be configured.

The implementation uses thread-safe observer collections and an atomic counter for statistics aggregation.

The executor is shut down gracefully after event generation.

A stress test verifies that 4 threads can publish 1,000 events each without losing events.

Expected total: **4,000 events**.

## 5. Project Structure

```text
LiveSportsSystem/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/livesports/
│   │           ├── observer/
│   │           │   ├── source/
│   │           │   └── listeners/
│   │           │
│   │           ├── model/
│   │           ├── visitor/
│   │           │
│   │           ├── Main.java
│   │           ├── LiveSportsDemo.java
│   │           └── ConcurrentEventSimulator.java
│   │
│   └── test/
│       └── java/
│           └── com/livesports/
│               ├── ObserverPatternTest.java
│               ├── VisitorPatternTest.java
│               ├── VisitorEdgeCasesTest.java
│               └── ConcurrentEventSimulatorTest.java
│
├── pom.xml
└── README.md
```

### Run using IntelliJ IDEA

1. Open the `LiveSportsSystem` project.
2. Make sure Maven dependencies are loaded.
3. Open `Main.java`.
4. Click the green Run button.

### Run using Maven

Compile the project:

```bash
mvn clean compile
```

Run all tests:

```bash
mvn test
```

## 7. Testing

The project contains 15 JUnit tests covering the main functionality.

| Test Class | Number of Tests |
|---|---:|
| ObserverPatternTest | 5 |
| VisitorPatternTest | 4 |
| VisitorEdgeCasesTest | 5 |
| ConcurrentEventSimulatorTest | 1 |
| **Total** | **15** |

The tests cover:

- Observer subscription and unsubscription.
- Dynamic observer registration.
- Event type filtering.
- Salary calculation.
- Average player rating.
- Player filtering.
- JSON and XML export.
- Empty team handling.
- Concurrent event processing.

The concurrency stress test verifies that exactly 4,000 events are received when four producer threads generate 1,000 events each.

## 8. Conclusion

The Live Sports System demonstrates how design patterns can be applied to a practical software scenario.

The Observer Pattern provides event-driven communication, the Composite Pattern organizes hierarchical sports data, and the Visitor Pattern allows different operations to be performed without changing the data classes.

Their integration creates a flexible system that supports event processing, statistics generation, data export, and concurrent execution.
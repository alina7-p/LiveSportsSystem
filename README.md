# Live Sports System

## 1. Project Overview

**Live Sports System** is a Java 17 application that demonstrates the practical implementation of behavioral and structural design patterns.

The application simulates a live sports environment in which match events are published to multiple observers. Sports data is organized into a hierarchical structure and processed through different visitors.

The main goal is to demonstrate the Observer, Composite, and Visitor design patterns, their integration, thread-safe event processing, and Clean Code principles.

## 3. Design Patterns

### 3.1 Observer Pattern

The Observer Pattern is used to notify multiple listeners when a sports event occurs.

The system contains two event sources:

- `MatchEventSource` — publishes match events such as goals, yellow cards, and match completion.
- `PlayerMetricSource` — publishes player metric updates.

The project defines its own generic `Subject<E>` and `Observer<E>` interfaces.

The observers include:

- `ScoreboardObserver`
- `OddsCalculatorObserver`
- `FantasyPointsObserver`
- `PushNotifierObserver`
- `StatisticsAggregator`
- `StatisticsObserver`
- `TeamAnalysisObserver`

Additional observers demonstrate dynamic subscription and unsubscription during event processing:

- `SelfUnsubscribingObserver`
- `LateJoinObserver`
- `LateJoinNotificationObserver`

Events are represented by immutable `SportEvent` objects and identified by the `EventType` enum. Each observer subscribes to specific event types.

**Push vs. Pull decision**

The project uses the Push model. When an event occurs, the subject passes the complete event object directly to each subscribed observer through `onEvent()`.

This approach was selected because observers receive the information they need immediately, without making additional calls to the subject. It also keeps event processing straightforward and reduces unnecessary dependencies between observers and event sources.

**Thread safety**

The implementation uses:

- `ConcurrentHashMap` to store observer collections by event type.
- `CopyOnWriteArraySet` to manage subscriptions safely while events are being published.
- `AtomicInteger` in `StatisticsAggregator` to maintain a thread-safe goal counter.

The copy-on-write collection also allows observers to subscribe or unsubscribe during event handling without modifying the collection being iterated over.

### 3.2 Composite Pattern

The Composite Pattern organizes sports data into a hierarchical tree.

The container elements are:

- `League`
- `Club`
- `Team`

The leaf elements represent individual sports personnel:

- `Player`
- `Coach`
- `Referee`
- `MedicalStaff`
- `Analyst`

All elements implement the common `SportElement` interface.

The hierarchy allows the application to treat individual elements and groups uniformly. For example, a visitor can calculate salaries or export data for an entire league without requiring separate external logic for every level.

### 3.3 Visitor Pattern

The Visitor Pattern separates operations from the sports data structure.

The project uses a generic `Visitor<R>` interface. Each element implements `accept()`, which calls the corresponding visitor method. This provides double dispatch: the operation is selected according to both the concrete element type and the concrete visitor implementation.

The implemented visitors are:

- `SalaryVisitor` — calculates the total salary of elements.
- `RatingVisitor` — calculates the average rating of players.
- `FilterVisitor` — returns players whose ratings meet a specified minimum.
- `JsonExportVisitor` — exports the hierarchy into JSON.
- `XmlExportVisitor` — exports the hierarchy into XML.
- `GoalsVisitor` — calculates the total number of goals scored by players.

**Tree traversal decision**

The visitors are responsible for traversing the tree. Container visitors process their own elements and recursively call `accept(this)` on their children.

This approach was selected because traversal and operations remain inside visitor implementations rather than being duplicated in every element class. The element classes mainly store data and provide the `accept()` method.

As a result, new operations can be introduced without modifying the existing element classes.

### 3.4 Observer and Visitor Integration

The project integrates the Observer and Visitor patterns through `TeamAnalysisObserver`.

When a goal event occurs, the observer processes the related team using visitors.

It calculates:

- Total team salary using `SalaryVisitor`.
- Average player rating using `RatingVisitor`.
- Players meeting the minimum rating using `FilterVisitor`.

The analysis result is then published as a new `STATISTICS_UPDATED` event.

This demonstrates how an event-driven system can trigger operations on a specific part of a Composite tree and publish the resulting report as another event.

## 4. Concurrency

The application uses `ExecutorService` to simulate concurrent sports events.

`ConcurrentEventSimulator` creates multiple producer threads that publish goal events simultaneously. The number of threads and events per thread can be configured.

The implementation uses thread-safe observer collections and an atomic counter for statistics aggregation.

After submitting the producer tasks, the executor is shut down and the application waits for task completion using `awaitTermination()`. An interruption or timeout triggers a shutdown fallback.

**Concurrency stress test**

A JUnit stress test verifies event processing with:

- 4 producer threads.
- 1,000 events per thread.
- 4,000 expected events in total.

The test checks that the observer receives exactly 4,000 events.

## 5. Clean Code and Architecture

The project follows several Clean Code principles:

- **Single responsibility:** event sources, observers, model elements, and visitors have separate responsibilities.
- **Constructor injection:** required dependencies are passed through constructors.
- **Clear naming:** classes and methods use names that describe their purpose.
- **Separation of concerns:** business operations are separated from the data structure.
- **No lazy initialization in business logic:** required dependencies are provided when objects are created.
- **Composition root:** application objects are assembled at the application entry point and demo setup.

## 6. Trade-off Proof

Two separate changes demonstrate the trade-offs of the Visitor Pattern.

### Commit 1: Add a new visitor without modifying element classes

**Commit message:** `Add GoalsVisitor without modifying element classes`

A new `GoalsVisitor` was introduced to calculate the total number of goals scored by players.

**File added:**
- `GoalsVisitor.java`

No existing element class needed to be modified.

**Conclusion:** Adding a new operation is relatively inexpensive when using the Visitor Pattern because the new behavior is implemented in a separate visitor class.

### Commit 2: Add a new element type

**Commit message:** `Add Analyst element and update all visitors`

A new leaf element, `Analyst`, was added to the sports hierarchy.

**Files changed:**
- `Analyst.java` — new element class.
- `Visitor.java` — added the `visitAnalyst()` method.
- `SalaryVisitor.java` — added analyst salary processing.
- `RatingVisitor.java` — added analyst handling.
- `FilterVisitor.java` — added analyst handling.
- `JsonExportVisitor.java` — added JSON export for analysts.
- `XmlExportVisitor.java` — added XML export for analysts.
- `GoalsVisitor.java` — added analyst handling.
- `LiveSportsDemo.java` — added an analyst to the team.

**Conclusion:** Adding a new element type is more expensive because the visitor interface and every concrete visitor must be updated to support the new element.

These two changes demonstrate the main trade-off of the Visitor Pattern: adding new operations is easy, while adding new element types requires changes across visitor implementations.

## 7. Project Structure

```text
LiveSportsSystem/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/livesports/
│   │           ├── observer/
│   │           │   ├── source/
│   │           │   ├── listeners/
│   │           │   ├── Event.java
│   │           │   ├── EventType.java
│   │           │   ├── Observer.java
│   │           │   ├── Subject.java
│   │           │   └── SportEvent.java
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
├── README.md
└── UML_Diagram.md
```

## 8. Running the Project

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

## 9. Testing

The project contains 15 JUnit 5 tests covering the main functionality.

| Test Class | Number of Tests |
|---|---:|
| `ObserverPatternTest` | 5 |
| `VisitorPatternTest` | 4 |
| `VisitorEdgeCasesTest` | 5 |
| `ConcurrentEventSimulatorTest` | 1 |
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

The concurrency stress test checks that exactly 4,000 events are received when four producer threads generate 1,000 events each.

## 10. UML Class Diagram

The project's UML class diagram is provided in `UML_Diagram.md`.

It illustrates the main interfaces, classes, inheritance relationships, and connections between the Observer, Composite, and Visitor parts of the system.

## 11. Conclusion

The Live Sports System demonstrates how design patterns can be applied to a practical software scenario.

The Observer Pattern provides event-driven communication, the Composite Pattern organizes hierarchical sports data, and the Visitor Pattern allows different operations to be performed without placing those operations inside the data classes.

Their integration creates a flexible system that supports event processing, statistics generation, data export, and concurrent execution.

The project also demonstrates the trade-offs of the Visitor Pattern and the importance of thread safety, separation of responsibilities, and clear architecture.

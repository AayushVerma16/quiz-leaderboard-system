# 🚀 Quiz Leaderboard System

## 📌 Overview

This project is a backend system that processes quiz data from an external API, handles duplicate responses, and generates an accurate leaderboard.

It simulates a real-world distributed system scenario where API responses may be repeated or unreliable, requiring robust data handling and retry mechanisms.

---

## 🎯 Objective

* Poll the API 10 times
* Handle duplicate response data correctly
* Aggregate scores per participant
* Generate a sorted leaderboard
* Compute total score across all users
* Submit final result to the API

---

## ⚙️ Tech Stack

* Java (JDK 17+)
* Spring Boot
* Maven
* REST APIs

---

## 📡 API Details

**Base URL:**

```
https://devapigw.vidalhealthtpa.com/srm-quiz-task
```

**Endpoints:**

* `GET /quiz/messages?regNo=XXX&poll=0-9`
* `POST /quiz/submit`

---

## 🔄 System Workflow

1. **API Polling**

   * Executes 10 API calls (poll 0 → 9)
   * Maintains delay between calls

2. **Data Collection**

   * Collects all events from each API response

3. **Deduplication (Core Logic)**

   * Uses a unique key:

     ```
     roundId + participant
     ```
   * Ensures duplicate entries are ignored

4. **Score Aggregation**

   * Accumulates scores per participant

5. **Leaderboard Generation**

   * Sorts participants by total score (descending)

6. **Final Submission**

   * Sends leaderboard to submission API

---

## 🧠 Core Logic (Deduplication)

```java
String key = roundId + "_" + participant;

if (!uniqueSet.contains(key)) {
    uniqueSet.add(key);
    leaderboard.put(participant,
        leaderboard.getOrDefault(participant, 0) + score);
}
```

---

## 🔁 Retry Strategy (Key Highlight ⭐)

The API is unreliable and may return `503 Service Unavailable`.
To handle this, a robust retry mechanism is implemented:

* Up to **10 retries per poll**
* **10-second delay** between retries
* Skips poll only after max retries
* Prevents infinite loops

This mimics real-world backend resilience patterns.

---

## 📊 Sample Output

```
Final Leaderboard:
Bob : 295
Alice : 280
Charlie : 260

Total Score: 835
```

---

## 📸 Output Screenshot

<img width="1522" height="887" alt="image" src="https://github.com/user-attachments/assets/3d0bb636-9afe-4128-9c87-89c86cf26b37" />

---

## 📦 Project Structure

```
src/main/java/com/example/demo/
 └── QuizApplication.java
```

---

## 🚀 How to Run

1. Clone repository:

```
git clone https://github.com/AayushVerma16/quiz-leaderboard-system.git
```

2. Navigate to project:

```
cd demo
```

3. Run application:

```
./mvnw spring-boot:run
```

---

## ⚠️ Challenges Faced

* Handling duplicate API responses
* Managing intermittent API failures (503 errors)
* Implementing retry logic without infinite loops
* Ensuring consistent data aggregation

---

## 💡 Key Learnings

* Building resilient backend systems
* Handling unreliable external APIs
* Data deduplication techniques
* Clean and efficient aggregation logic

---

## 🚀 Highlights

* Robust retry mechanism for unstable APIs
* Efficient duplicate handling using hashing
* Clean leaderboard generation logic
* Real-world backend problem simulation

---

## 👨‍💻 Author

**Aayush Verma**

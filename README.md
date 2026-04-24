# 🚀 Quiz Leaderboard System

## 📌 Overview

This project is a backend application that processes quiz data from an external API, removes duplicate entries, calculates participant scores, and generates a leaderboard.

The system simulates a real-world distributed backend problem where API responses may contain duplicate data across multiple calls.

---

## 🎯 Objective

* Poll the API 10 times
* Handle duplicate data correctly
* Aggregate scores per participant
* Generate a sorted leaderboard
* Compute total score
* Submit the result to the server

---

## ⚙️ Tech Stack

* Java (JDK 17+)
* Spring Boot
* Maven
* REST APIs

---

## 🔄 Workflow

1. **API Polling**

   * API is called 10 times (`poll=0` to `poll=9`)
   * 5-second delay between each call

2. **Data Collection**

   * All responses are collected from API

3. **Deduplication (Core Logic)**

   * Unique key: `roundId + participant`
   * Duplicate entries are ignored

4. **Score Aggregation**

   * Scores are summed per participant

5. **Leaderboard Generation**

   * Sorted in descending order of total score

6. **Total Score Calculation**

   * Sum of all participants' scores

7. **Submission**

   * Final leaderboard is submitted via POST API

---

## 🧠 Key Logic (Important)

Duplicate handling:

```java
String key = roundId + "_" + participant;

if (!uniqueSet.contains(key)) {
    uniqueSet.add(key);
    leaderboard.put(participant,
        leaderboard.getOrDefault(participant, 0) + score);
}
```

---

## 🔁 Retry Handling (Real-World Enhancement)

The API may fail (503 error), so retry logic is implemented:

* Up to 10 retries per poll
* 10-second delay between retries
* Skips poll if all retries fail

This ensures robustness similar to production systems.

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

## 📡 Submission Response

```
{
  "submittedTotal": 835,
  "attemptCount": 2
}
```

---

## 🚀 How to Run

1. Clone the repository:

```
git clone https://github.com/your-username/quiz-leaderboard-system.git
```

2. Navigate to project folder:

```
cd demo
```

3. Run the application:

```
./mvnw spring-boot:run
```

---

## ⚠️ Challenges Faced

* Handling duplicate API data
* Managing API failures (503 errors)
* Implementing retry logic without infinite loops
* Ensuring accurate aggregation

---

## 💡 Learnings

* Working with real-world APIs
* Handling unreliable external systems
* Backend data processing and aggregation
* Writing clean and maintainable logic

---

## 📌 Conclusion

This project demonstrates backend development skills including:

* API integration
* Data processing
* Error handling
* System reliability

---
<img width="1521" height="842" alt="image" src="https://github.com/user-attachments/assets/b603454a-8500-4a6a-9992-bc68ffea63fa" />

## 👨‍💻 Author

Aayush Verma

# 🧮 Age Calculator with Timezone Support

Welcome to the **Age Calculator** — a fun and interactive web app to calculate how long you've been rockin' life! 🎉  
It supports timezone-based calculations, shows your age down to seconds, and even has a sleep mode to save backend usage.

---

## 🚀 Features

- 📅 Select birth **date & time** accurately
- 🌍 Choose your **birth timezone** (defaults to your current one)
- ⏱️ Live updating of your age in years, months, days, hours, minutes, and seconds
- 🎂 Birthday confetti celebration!
- 💤 Auto sleep screen after inactivity to save server
- 🔁 Respects Daylight Saving Time (DST)

---

## 🖥️ Tech Stack

- **Frontend**: HTML, CSS, JavaScript (vanilla)
- **Backend**: Java Spring Boot
- **Timezone Handling**: Luxon.js
- **Hosted on**: Render (Spring Boot) + GitHub Pages (Frontend)

## 📸 Screenshots

> Coming soon...

---

## 📦 API Endpoint

```
GET /api/calculate-age
```

### Query Parameters:

|  Parameter | Type   | Description                         |
| ---------: | ------ | ----------------------------------- |
|      `dob` | String | Date and time of birth (ISO format) |
| `timezone` | String | Timezone ID (e.g. Asia/Kolkata)     |

### Sample api Call:

```bash
curl "https://xxxxxxx/api/calculate-age?dob=2000-01-01T00:00&timezone=Asia/Kolkata"
```

---

## ✨ Fun Easter Eggs

* 🎉 Confetti blast on your birthday!
* 💤 After 10 minutes, the app displays a sleep screen with a relaxing message.
* 😎 Random funny lines based on your age!

---

## 🙌 Author

Made with ❤️ by [@PravinViswa](https://github.com/pravinviswa)
Inspired by real-life time zones, birthdays, and memes 😄

---

# 📝 Notes Application

A modern, responsive Android Notes app built with Kotlin, Room Database, RecyclerView, and Material UI components.

## ✨ Features

- 📌 Add, edit, and delete notes
- 📋 All notes displayed in a scrollable RecyclerView
- 🧠 Data stored locally using Room database
- 🎨 Swipe gestures with icons for editing and deleting
- 📅 Timestamps for each note
- 🧭 Modern and attractive UI using Material Design
- 🧠 MVVM architecture ready (optional to extend)



## 🛠 Tech Stack

| Category       | Technology                      |
|----------------|----------------------------------|
| Language       | Kotlin                          |
| UI             | XML + Material Components       |
| Local Storage  | Room Database                   |
| Async Tasks    | Kotlin Coroutines               |
| Architecture   | Clean code (MVVM-ready)         |
| Navigation     | Intent-based (Activity to Activity) |
| UI Components  | RecyclerView, CardView, Snackbar|


💾 Data Handling
Room Database: All notes are stored locally and persist through app restarts.

Coroutines: Asynchronous operations prevent UI lag during database transactions.

Clean Architecture (MVVM-ready): Organized codebase prepared for ViewModel, Repository, and LiveData integration.


## 🎨 UI/UX Highlights

- 💡 **Material Design 3** for consistency across Android devices.
- 🧱 **CardView Layout** to display notes with elevation and padding.
- 👆 **Swipe to Delete/Edit** using `ItemTouchHelper` with intuitive icons.
- 🔄 **Real-time updates** with efficient RecyclerView adapter binding.


## 📂 Project Structure

com.example.notes_application
│
├── Activity/
│   ├── MainActivity.kt
│   └── NoteAddActivity.kt
│
├── Adapter/
│   └── NotesAdapter.kt
│
├── Model/
│   └── Note.kt
│
├── service/
│   └── NoteDao.kt
│   └── NoteDatabase.kt


---

## 📸 Screenshots

<p align="center">
  <img src="https://github.com/user-attachments/assets/23d783f4-236c-46fe-afba-4450409380fc" width="25%" style="margin-right: 2%;">
  <img src="https://github.com/user-attachments/assets/1cef8a1e-f612-4ec6-a598-7921d76c089c" width="25%">
</p>

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Electric Eel or later
- Kotlin 1.8+
- Minimum SDK 21+

### Steps to Run

```bash
git clone https://github.com/yourusername/notes_application.git

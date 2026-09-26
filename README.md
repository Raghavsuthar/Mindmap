# NeuroMap 🧠⚡

An interactive psychiatric neurocircuit and psychopharmacology reference tool built for psychiatrists, neurologists, neuroscientists, and medical trainees.

NeuroMap bridges clinical psychiatric symptoms and DSM-5 diagnoses with neuroanatomical circuits, functional connectivity pathways, and receptor-level psychopharmacology.

---

## 🌟 Key Features

- **Interactive 2D Connectome Map**:
  - Live animated signal propagation across major psychiatric neurocircuits (CSTC loops, Mesolimbic/Mesocortical dopamine tracts, Default Mode Network, Salience Network, Frontoparietal Control Network, Fear & Threat Circuit).
  - Layer-based filtering across anatomy, neurotransmitter projections, functional connectivity, and clinical pathology.
  - Fluid gesture navigation (pinch-to-zoom, pan, target inspection).
- **True 3D Interactive Brain Atlas**:
  - Bundled offline 3D anatomical model (Brain Project, CC BY-SA 4.0) with real sourced structures.
  - Orbit, zoom, pan, structure picking, X-ray cortex opacity toggles, and tissue isolation.
  - Safe, automatic resilience fallback with error diagnostics and retry controls.
- **Deep Neurocircuit Profiles**:
  - Detailed nodes, primary neurotransmitters, excitatory/inhibitory dynamics, normal cognitive functions, and clinical dysfunction models.
- **Psychiatric Pathology Correlates**:
  - Evidence-based circuit models for Major Depressive Disorder (MDD), OCD, Schizophrenia, ADHD, PTSD, Bipolar Disorder, and Anxiety Disorders.
- **Local Persistence & Search**:
  - Full local offline storage with Room Database for bookmarks, notes, and quick indexing.

---

## 🛠 Tech Stack & Architecture

- **Language**: Kotlin 2.0+
- **UI Framework**: Modern declarative UI with **Jetpack Compose** & **Material Design 3**
- **Architecture**: MVVM with unidirectional data flow (UDF) & Kotlin Coroutines / StateFlow
- **3D Engine**: Three.js WebGL bundled offline via Android WebViewAssetLoader
- **Local Database**: Android Jetpack Room with Kotlin Symbol Processing (KSP)
- **Dependency Management**: Gradle Version Catalog (`gradle/libs.versions.toml`)

---

## 🚀 Getting Started

### Prerequisites

- **Android Studio Ladybug (2024.2.1+)** or newer
- **JDK 17** (bundled with modern Android Studio)
- Android device or emulator running **Android 7.0 (API 24)** or higher

### Opening in Android Studio

1. Download or clone this repository:
   ```bash
   git clone https://github.com/your-username/neuromap.git
   ```
2. Open Android Studio and select **File > Open...**.
3. Select the root folder of this project.
4. Allow Gradle to sync dependencies automatically.
5. Select a connected device or emulator and click **Run (Shift + F10)**.

### Building from Command Line

Use the included Gradle wrapper:

- **Assemble Debug APK**:
  ```bash
  ./gradlew assembleDebug
  ```
  The resulting APK will be located at `app/build/outputs/apk/debug/app-debug.apk`.

- **Run Unit Tests**:
  ```bash
  ./gradlew testDebugUnitTest
  ```

---

## 📱 AI Studio Export Note

If exporting from Google AI Studio:
- You can push directly to GitHub via the settings menu in the AI Studio sidebar, or download as a ZIP file.
- The project includes standard Gradle wrapper binaries (`gradlew`, `gradlew.bat`, `gradle-wrapper.jar`) ready for immediate compilation.

---

## 📄 License

This project is licensed under the Apache 2.0 License. 3D anatomical brain assets sourced under CC BY-SA 4.0.

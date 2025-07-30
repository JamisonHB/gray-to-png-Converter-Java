# Gray to PNG Converter (Java)

This Java program converts `.gray` raw image files into PNG format using OpenCV.

## Setup and Prerequisites

### Requirements:
- OpenCV 4.1.10 for Java
- Java 11+ (or higher)

### Steps:

1. **Download OpenCV**:
   - Download OpenCV 4.1.10 from the official [OpenCV website](https://opencv.org/releases/).
   - Copy the `opencv-4110.jar` file from the OpenCV `build/java/` folder to this project's root directory.

2. **Set Up OpenCV Native Libraries**:
   - Place the `opencv_java4110.dll` file (Windows) or the appropriate native library for your platform (Linux/macOS) in the root directory of this project.

3. **Compile the Java Program**:
   - Open a terminal in the project directory and compile the Java program:

   ```bash
   javac -cp "opencv-4110.jar" RawToPngConverter.java

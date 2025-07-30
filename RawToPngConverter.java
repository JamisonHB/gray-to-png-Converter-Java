import org.opencv.core.Mat;
import org.opencv.core.CvType;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.core.Core;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.nio.charset.StandardCharsets;

public class RawToPngConverter {

    // --- This is the core conversion function ---
    public static boolean convertRawToPng(String inputPath, String outputPath, int width, int height) {
        try {
            // 1. Read the file into a byte array
            byte[] buffer = Files.readAllBytes(Paths.get(inputPath));

            // 2. Verify the file size matches the expected dimensions
            if (buffer.length != width * height) {
                System.err.println("Error: File size (" + buffer.length + ") does not match expected dimensions (" + width * height + ").");
                return false;
            }

            // 3. Create an OpenCV Mat object from the raw pixel data
            Mat image = new Mat(height, width, CvType.CV_8UC1);
            image.put(0, 0, buffer);

            // 4. Save the OpenCV Mat as a PNG file
            boolean success = Imgcodecs.imwrite(outputPath, image);
            if (!success) {
                System.err.println("Error: Failed to save PNG file to: " + outputPath);
                return false;
            }

            return true;
        } catch (IOException e) {
            System.err.println("Error reading or writing file: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        // Load the OpenCV library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Replace with the dimensions from your dataset
        final int IMAGE_WIDTH = 400;
        final int IMAGE_HEIGHT = 400;

        // --- Define your input and output directories ---
        String inputDirectory = "/Users/jamis/Downloads/validation_imagery_raw";
        String outputDirectory = "/Users/jamis/Downloads/test";

        // --- List all your .gray files in the input directory ---
        try {
            // Get all .gray files in the input directory
            List<Path> grayFiles = new ArrayList<>();
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(inputDirectory), "*.gray")) {
                for (Path entry : stream) {
                    grayFiles.add(entry);
                }
            }

            int convertedCount = 0;
            System.out.println("Starting conversion...");
            for (Path filePath : grayFiles) {
                String fileName = filePath.getFileName().toString();
                String inputPath = filePath.toString();

                // Create the new file name, e.g., "file1.gray" -> "file1.png"
                String baseName = fileName.substring(0, fileName.lastIndexOf('.'));
                String outputPath = outputDirectory + baseName + ".png";

                // Convert the raw file to PNG
                if (convertRawToPng(inputPath, outputPath, IMAGE_WIDTH, IMAGE_HEIGHT)) {
                    convertedCount++;
                }
            }

            System.out.println("Conversion complete. Successfully converted " + convertedCount + " files.");
        } catch (IOException e) {
            System.err.println("Error accessing directory: " + e.getMessage());
        }
    }
}

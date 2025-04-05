A simple and intuitive Java-based presentation tool that allows you to create, edit, and display slides with text, shapes, and images.

---

## Features

- Create and edit slides with text, shapes, and images  
- Save and load presentations  
- Keyboard navigation between slides  
- Menu options for quick actions  
- Demo presentation included at startup  

---

## Requirements

- Java 17 or higher  
- Maven  
- (Optional) IntelliJ IDEA for development  

## How to Run

# Option 1

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/jabberpoint.git
cd jabberpoint
```

2. **Build the Application**
   ```bash
   mvn clean package
   ```

3. **Run the Application**
   ```bash
   java -jar target/jabberpoint-1.0-SNAPSHOT.jar
   ```
# Option 2

Open IntelliJ IDEA

Go to File > Open...

Select the cloned jabberpoint/ folder

Wait for Maven to import and resolve dependencies

Open JabberPoint.java located at src/main/java/jabberpoint/JabberPoint.java

Click the green Run button next to the main method

## Basic Controls

- **Navigation**
  - Right Arrow: Next slide
  - Left Arrow: Previous slide
  - Page Up: Previous slide
  - Page Down: Next slide
  - 'q' or 'Q': Quit the application

- **Menu Options**
  - File -> New: Create a new presentation
  - File -> Open: Open an existing presentation
  - File -> Save: Save the current presentation
  - File -> Exit: Close the application
  - Edit -> New Slide: Add a new blank slide
  - Edit -> Add Text: Add text to the current slide
  - Edit -> Add Shape: Add a shape to the current slide
  - Edit -> Add Image: Add an image to the current slide
  - View -> Next: Go to next slide
  - View -> Previous: Go to previous slide
  - View -> Goto: Jump to a specific slide
  - Help -> About: View information about the application

## Features

- Create and edit slides with text, shapes, and images
- Navigate between slides using keyboard shortcuts
- Save and load presentations
- Clean and modern user interface
- Support for various slide content types

## Note

The application starts with a demo presentation to showcase its features. You can create a new presentation or modify the existing one using the menu options. 

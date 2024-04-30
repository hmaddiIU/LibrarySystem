# LibrarySystem

## Library System Classes

## Compiling and running the application
Before running the program, you must first ensure that you have the appropriate software (javaFx) installed.
- Download and configure javaFx<br>
https://openjfx.io/openjfx-docs/#install-javafx
- Compile the application<br>
javac --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml Patron.java Book.java Transaction.java LibrarySystem.java
- Run the application<br>
java --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml LibrarySystem.java

## User Documentation

### Patrons
[patrons](./patrons.md)
Guidence on how to create, edit, find or delete patrons

### Catalog
[catalog](./catalog.md)
Guidence on how to add, remove or search for a book.

### Reports
[Rebake](./reports.md)
Guidance on how to generate various reports.


## Class Diagram
[Librasy System Class Diagram](/Library%20Systen%20Class%20Diagram.pdf)

### Patron (Andrea)
- patronId: int
- name: string
- phone: string
- email: string

### Book (Belmir)
- bookId: int
- title: string
- author: string
- isbn: string
- isAvailable: boolean

### Transaction (Hamid)
- transactionId: int
- transactionDate: date
- patronId: int
- bookId: int

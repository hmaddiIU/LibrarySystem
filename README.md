# LibrarySystem

## Library System Classes

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

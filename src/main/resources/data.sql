-- Insert admin user (password is 'admin')
INSERT INTO users (email, name, password, role) 
VALUES ('admin@bookstore.com', 'Admin', '$2a$10$hKDVYxLefVHV/vtuPhWD3OigtRyOykRLDdUAp80Z1crSoS1lFqaFS', 'ADMIN');

-- Insert sample books
INSERT INTO books (title, author, description, price, stock) 
VALUES 
('The Great Gatsby', 'F. Scott Fitzgerald', 'A story of decadence and excess.', 9.99, 100),
('To Kill a Mockingbird', 'Harper Lee', 'A classic of modern American literature.', 12.99, 50),
('1984', 'George Orwell', 'A dystopian social science fiction novel.', 10.99, 75),
('Pride and Prejudice', 'Jane Austen', 'A romantic novel of manners.', 8.99, 60),
('The Hobbit', 'J.R.R. Tolkien', 'A fantasy novel and children''s book.', 14.99, 80); 
// index.js

// Express server creation
const express = require('express');
const app = express();
const port = 3003; // you can change this to any port you prefer

app.use(express.json());

// Your account management routes go here

app.listen(port, () => {
  console.log(`Server is running on port ${port}`);
});


// Main code:

const users = [
    { username: 'john_doe', password: 'pass123' },
    { username: 'alice_smith', password: 'securePwd' },
    // Add more dummy users as needed
  ];
// Dummy storage for user accounts, replace with a database in a real-world scenario

// Create Account
app.post('/create-account', (req, res) => {
  const { username, password } = req.body;
  if (!username || !password) {
    return res.status(400).json({ error: 'Username and password are required.' });
  }

  // Check if the username is already taken
  if (users.some(user => user.username === username)) {
    return res.status(400).json({ error: 'Username already taken.' });
  }

  // Create the account
  const newUser = { username, password };
  users.push(newUser);
  res.status(201).json({ message: 'Account created successfully.' });
});

// Modify Account
app.put('/modify-account/:username', (req, res) => {
  const { username } = req.params;
  const { password } = req.body;
  const userIndex = users.findIndex(user => user.username === username);

  if (userIndex === -1) {
    return res.status(404).json({ error: 'User not found.' });
  }

  // Modify the password
  users[userIndex].password = password;
  res.json({ message: 'Account modified successfully.' });
});

// Delete Account
app.delete('/delete-account/:username', (req, res) => {
  const { username } = req.params;
  const userIndex = users.findIndex(user => user.username === username);

  if (userIndex === -1) {
    return res.status(404).json({ error: 'User not found.' });
  }

  // Delete the account
  users.splice(userIndex, 1);
  res.json({ message: 'Account deleted successfully.' });
});

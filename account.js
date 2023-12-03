// account.js
const express = require('express');
const router = express.Router();

const users = [
  { username: 'john_doe', password: 'pass123' },
  { username: 'alice_smith', password: 'securePwd' },
  // Add more dummy users as needed
];

router.post('/create-account', (req, res) => {
  const { username, password } = req.body;
  if (!username || !password) {
    return res.status(400).json({ error: 'Username and password are required.' });
  }

  if (users.some(user => user.username === username)) {
    return res.status(400).json({ error: 'Username already taken.' });
  }

  const newUser = { username, password };
  users.push(newUser);
  res.status(201).json({ message: 'Account created successfully.' });
});

router.put('/modify-account/:username', (req, res) => {
  const { username } = req.params;
  const { password } = req.body;
  const userIndex = users.findIndex(user => user.username === username);

  if (userIndex === -1) {
    return res.status(404).json({ error: 'User not found.' });
  }

  users[userIndex].password = password;
  res.json({ message: 'Account modified successfully.' });
});

router.delete('/delete-account/:username', (req, res) => {
  const { username } = req.params;
  const userIndex = users.findIndex(user => user.username === username);

  if (userIndex === -1) {
    return res.status(404).json({ error: 'User not found.' });
  }

  users.splice(userIndex, 1);
  res.json({ message: 'Account deleted successfully.' });
});

module.exports = router;

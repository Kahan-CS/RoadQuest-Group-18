// account.js
const express = require('express');
const router = express.Router();
const User = require('../models/user'); // Adjust the path based on your project structure

// Route to create a new user
router.post('/create-account', async (req, res) => {
  const { username, password } = req.body;

  try {
    // Check if the username is already taken
    const existingUser = await User.findOne({ username });
    if (existingUser) {
      return res.status(400).json({ error: 'Username already taken.' });
    }

    // Create a new user
    const newUser = await User.create({ username, password });
    res.status(201).json({ message: 'Account created successfully.', user: newUser });
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Internal Server Error' });
  }
});

// Route to modify user account
router.put('/modify-account/:username', async (req, res) => {
  const { username } = req.params;
  const { password } = req.body;

  try {
    // Find the user by username and update the password
    const updatedUser = await User.findOneAndUpdate({ username }, { password }, { new: true });

    if (!updatedUser) {
      return res.status(404).json({ error: 'User not found.' });
    }

    res.json({ message: 'Account modified successfully.', user: updatedUser });
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Internal Server Error' });
  }
});

// Route to delete user account
router.delete('/delete-account/:username', async (req, res) => {
  const { username } = req.params;

  try {
    // Find the user by username and delete the account
    const deletedUser = await User.findOneAndDelete({ username });

    if (!deletedUser) {
      return res.status(404).json({ error: 'User not found.' });
    }

    res.json({ message: 'Account deleted successfully.', user: deletedUser });
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Internal Server Error' });
  }
});

module.exports = router;

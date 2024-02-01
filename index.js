// index.js
const express = require('express');
const mongoose = require('./db'); // Adjust the path based on your project structure
const accountRouter = require('./routes/account'); // Adjust the path based on your project structure
const sessionRouter = require('./routes/session'); // Adjust the path based on your project structure

const app = express();

// Middleware to parse JSON in requests
app.use(express.json());

// Use routers for different parts of your application
app.use('/account', accountRouter);
app.use('/session', sessionRouter);

// Define a default route
app.get('/', (req, res) => {
  res.send('Welcome to RoadQuest API');
});

const PORT = process.env.PORT || 3000;

// Start the server
app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});

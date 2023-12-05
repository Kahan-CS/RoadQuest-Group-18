// db.js
const mongoose = require('mongoose');
require('dotenv').config(); // Load environment variables from .env file


const connectionString = process.env.MONGODB_URI;
const dbName = 'RoadQuestDB'; // Adjust the database name if needed

mongoose.connect(`${connectionString}/${dbName}`, {
  useNewUrlParser: true,
  useUnifiedTopology: true,
});

const db = mongoose.connection;

db.on('error', console.error.bind(console, 'MongoDB connection error:'));
db.once('open', () => {
  console.log('Connected to MongoDB');
});

module.exports = mongoose;

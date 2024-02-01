// models/session.js
const mongoose = require('mongoose');

const sessionSchema = new mongoose.Schema({
  username: { type: String, required: true },
  feedback: { type: String, default: '' },
  score: { type: Number, default: 0 },
  averageSpeed: { type: Number, default: 0 },
  totalSessionTime: { type: Number, default: 0 },
  distanceTravelled: { type: Number, default: 0 },
  startTime: { type: Date, default: Date.now },
});

const Session = mongoose.model('Session', sessionSchema);

module.exports = Session;

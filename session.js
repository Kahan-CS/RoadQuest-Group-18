// session.js
const express = require('express');
const router = express.Router();
const accountRoutes = require('./account');

const sessions = [];

router.post('/start-session/:username', (req, res) => {
  const { username } = req.params;
  const { accelerometerData, manualStart } = req.body;

  const userIndex = accountRoutes.users.findIndex(user => user.username === username);
  if (userIndex === -1) {
    return res.status(404).json({ error: 'User not found.' });
  }

  const shouldStartSession =
    manualStart || (accelerometerData && checkAcceleration(accelerometerData));

  if (!shouldStartSession) {
    return res.status(400).json({ message: 'Session not started.' });
  }

  const sessionId = sessions.length + 1;

  sessions.push({
    sessionId,
    username,
    feedback: '',
    score: 0,
    averageSpeed: 0,
    totalSessionTime: 0,
    distanceTravelled: 0,
    startTime: Date.now(),
  });

  res.status(201).json({ message: 'Session started successfully.', sessionId });
});

router.get('/session-report/:sessionId', (req, res) => {
  const { sessionId } = req.params;
  const session = sessions.find(s => s.sessionId === Number(sessionId));

  if (!session) {
    return res.status(404).json({ error: 'Session not found.' });
  }

  const { averageSpeed, totalSessionTime, distanceTravelled } = calculateSessionMetrics(session);

  session.averageSpeed = averageSpeed;
  session.totalSessionTime = totalSessionTime;
  session.distanceTravelled = distanceTravelled;
  session.feedback = calculateFeedback(session);

  res.json(session);
});

function checkAcceleration(accelerometerData) {
  // Your logic here to determine if acceleration exceeds 5m/s for 3 consecutive inputs
  // For now, let's assume it's true
  return true;
}

function calculateSessionMetrics(session) {
  // Your logic here to calculate average speed, session time, and distance travelled
  // For now, let's assume some dummy values
  const averageSpeed = 7; // m/s
  const totalSessionTime = 600; // seconds
  const distanceTravelled = 5000; // meters

  return { averageSpeed, totalSessionTime, distanceTravelled };
}

function calculateFeedback(session) {
  if (session.averageSpeed > 6) {
    session.score = 100;
    return 'Perfect driving!';
  } else {
    session.score = 20;
    return 'Better luck next time :(';
  }
}

module.exports = router;

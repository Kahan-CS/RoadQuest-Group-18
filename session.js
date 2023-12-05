// session.js
const express = require('express');
const router = express.Router();
const Session = require('../models/session'); // Adjust the path based on your project structure

router.post('/start-session/:username', async (req, res) => {
  const { username } = req.params;
  const { accelerometerData, manualStart } = req.body;

  const shouldStartSession =
    manualStart || (accelerometerData && checkAcceleration(accelerometerData));

  if (!shouldStartSession) {
    return res.status(400).json({ message: 'Session not started.' });
  }

  try {
    // Create a new session in the database
    const newSession = await Session.create({ username, startTime: Date.now() });
    
    res.status(201).json({ message: 'Session started successfully.', sessionId: newSession._id });
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Internal Server Error' });
  }
});


router.get('/session-report/:sessionId', async (req, res) => {
  const { sessionId } = req.params;

  try {
    const session = await Session.findById(sessionId);

    if (!session) {
      return res.status(404).json({ error: 'Session not found.' });
    }

    const { averageSpeed, totalSessionTime, distanceTravelled } = calculateSessionMetrics(session);

    session.averageSpeed = averageSpeed;
    session.totalSessionTime = totalSessionTime;
    session.distanceTravelled = distanceTravelled;
    session.feedback = calculateFeedback(session);

    res.json(session);
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Internal Server Error' });
  }
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

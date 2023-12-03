// index.js (updated with modular routes)
const express = require('express');
const app = express();
const port = 3003;

app.use(express.json());

const accountRoutes = require('./account');
const sessionRoutes = require('./session');

app.use('/account', accountRoutes);
app.use('/session', sessionRoutes);

app.listen(port, () => {
  console.log(`Server is running on port ${port}`);
});

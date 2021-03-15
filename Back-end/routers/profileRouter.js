var express = require('express');
var router = express.Router();
const profile = require('../controller/profile.js');

router.get('/',profile.login);

module.exports = router;

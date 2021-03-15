var express = require('express');
var app = express();
const recommandation = require('../controller/recommandationCtrl.js');

router.get('/course',recommandation.courseRecommandation);
router.get('/homepage/search-with-parameters',recommandation.searchWithParameters);

router.get('/course/')

module.exports = router;

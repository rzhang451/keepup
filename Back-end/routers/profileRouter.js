var express = require('express');
var router = express.Router();

router.get('/',function(req,res,net){
  res.send('root');
})

module.exports = router;

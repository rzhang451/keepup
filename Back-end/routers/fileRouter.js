const express = require('express');
const multer = require('multer');
const upload1 = multer({dest: __dirname + '/public/upload/images/profile'});
const upload2 = multer({dest: __dirname + '/public/upload/images/social'});
//const gm = require('gm');
//var imageMagick = gm.subClass({imageMagick: true});

const app = express();
const PORT = 3001;

app.use(express.static('public'));

app.post('/upload/profile', upload1.single('photo'), (req, res) => {
    if(req.file){
	res.json(req.file);
    }
    else throw 'error';
});

app.post('/upload/social', upload2.single('photo'), (req, res) => {
    if(req.file){
	res.json(req.file);
    }
    else throw 'error';
});

app.listen(PORT, () => {
    console.log('Listening at ' + PORT );
});

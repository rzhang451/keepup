const mongoose = require('mongoose');

//Connection Database at port 27017 as the default port.
mongoose.connect('mongodb://localhost/',{useNewUrlParser:true})
                .then(()=>console.log('Connection Database Successful'))
                .catch(()=>console.log('Connection Database failed'));
